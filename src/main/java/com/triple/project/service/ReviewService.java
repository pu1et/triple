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
		Review review = reviewDTO.toReview(member, place);
		return reviewRepository.save(review);
	}

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
}
