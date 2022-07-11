package com.triple.project.service;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import com.triple.project.dto.PhotoDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class ReviewService {
	private final ReviewRepository reviewRepository;
	private final MemberService memberService;
	private final PlaceService placeService;

	public ReviewService(ReviewRepository reviewRepository, MemberService Service, PlaceService placeService) {
		this.reviewRepository = reviewRepository;
		this.memberService = Service;
		this.placeService = placeService;
	}

	@Transactional
	public Review createReview(ReviewDTO.CreateRequest reviewDTO) {
		Member member = memberService.findMember(reviewDTO.getMemberId());
		Place place = placeService.findPlace(reviewDTO.getPlaceId());
		reviewRepository.findByMemberAndPlace(member, place).ifPresent((review) -> {
				throw new IllegalStateException("해당 장소에 대한 사용자의 리뷰가 존재합니다.");
		});
		Review review = reviewDTO.toReview(member, place);
		return reviewRepository.save(review);
	}

	@Transactional
	public Review updateReview(String reviewId, ReviewDTO.UpdateRequest reviewDTO) {
		Review review = findReview(reviewId);
		review.updateReview(reviewDTO.getContent(), PhotoDTO.toPhotos(reviewDTO.getAttachedPhotoIds()));
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
		reviewRepository.deleteById(reviewId);
	}
}
