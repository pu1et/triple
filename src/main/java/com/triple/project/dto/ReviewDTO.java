package com.triple.project.dto;

import com.triple.project.domain.Member;
import com.triple.project.domain.Photo;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
public class ReviewDTO {
	private String reviewId;
	private String content;
	private List<String> attachedPhotoIds;
	private String memberId;
	private String placeId;

	public boolean isEqualReview(Review review) {
		return reviewId.equals(review.getId())
				&& content.equals(review.getContent())
				&& PhotoDTO.isEqualPhotos(attachedPhotoIds, review.getAttachedPhotoIds())
				&& memberId.equals(review.getMember().getId())
				&& placeId.equals(review.getPlace().getId());
	}

	public Review toReview(Member user, Place place) {
		Review review = Review.builder()
				.id(reviewId)
				.content(content)
				.member(user)
				.place(place).build();
		attachedPhotoIds.forEach(attachedPhotoId -> {
			review.addPhoto(new Photo(attachedPhotoId));
		});
		return review;
	}
}
