package com.triple.project.service;

import com.triple.project.domain.*;
import com.triple.project.dto.PhotoDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final PlaceService placeService;
	private final PointService pointService;

	public ReviewService(ReviewRepository reviewRepository, MemberService Service, PlaceService placeService, PointService pointService) {
		this.reviewRepository = reviewRepository;
		this.memberService = Service;
		this.placeService = placeService;
		this.pointService = pointService;
	}

	@Transactional
	public Review createReview(ReviewDTO.CreateRequest reviewDTO) {
		Member member = memberService.findMember(reviewDTO.getMemberId());
		Place place = placeService.findPlace(reviewDTO.getPlaceId());
		reviewRepository.findByMemberAndPlace(member, place).ifPresent((review) -> {
				throw new IllegalStateException("해당 장소에 대한 사용자의 리뷰가 존재합니다.");
		});
		Review review = reviewRepository.save(reviewDTO.toReview(member, place));
		int reviewPoint = calculateAllPoint(review);
		review.updatePoint(reviewPoint);
		Point point = pointService.addPoint(new PointDTO(reviewPoint, member, review));
		return reviewRepository.save(review);
	}

	@Transactional
	public Review updateReview(String reviewId, ReviewDTO.UpdateRequest reviewDTO) {
		Review review = findReview(reviewId);
		review.updateReview(reviewDTO.getContent(), PhotoDTO.toPhotos(reviewDTO.getAttachedPhotoIds()));
		int updatePoint = calculateAllPoint(review);
		review.updatePoint(updatePoint);
		Point point = pointService.addPoint(new PointDTO(updatePoint - review.getPoint(), review.getMember(), review));
		return reviewRepository.save(review);
	}

	public Review findReview(String reviewId) {
		return reviewRepository.findById(reviewId)
				.orElseThrow(() -> {
					throw new IllegalStateException("리뷰가 존재하지 않습니다.");
				});
	}

	@Transactional
	public void deleteReview(String reviewId) {
		Review review = findReview(reviewId);
		Point point = pointService.addPoint(new PointDTO(-review.getPoint(), review.getMember(), review));
		reviewRepository.delete(review);
	}

	public int calculateAllPoint(Review review) {
		return calculateBonusPoint(review)
				+ calculatePoint(review.getContent(), review.getAttachedPhotoIds());
	}

	public int calculateBonusPoint(Review review) {
		List<Review> allReview = reviewRepository.findAllByPlaceOrderByCreatedAt(review.getPlace());
		if(allReview.size() > 1) {
			System.out.println("member.id" + review.getMember().getId());
			System.out.println(allReview.get(0).getMember().getId() + ", " + allReview.get(0).getCreatedAt());
			System.out.println(allReview.get(1).getMember().getId() + ", " + allReview.get(0).getCreatedAt());
		}
		return allReview.get(0).getMember().equals(review.getMember()) ? 1 : 0;
	}

	public int calculatePoint(String content, List<Photo> photos) {
		int point = 0;
		if (content.length() > 0) ++point;
		if (photos.size() > 0) ++point;
		return point;
	}
}
