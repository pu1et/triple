package com.triple.project.dto;

import com.triple.project.domain.Member;
import com.triple.project.domain.Photo;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import lombok.*;

import java.util.List;

public class ReviewDTO {
	@Getter
	@Builder
	@AllArgsConstructor
	public static class CreateRequest {
		private String reviewId;
		protected String content;
		protected List<String> attachedPhotoIds;
		private String memberId;
		private String placeId;

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

		public boolean isEqualReview(Review review) {
			return reviewId.equals(review.getId())
					&& content.equals(review.getContent())
					&& PhotoDTO.isEqualPhotos(attachedPhotoIds, review.getAttachedPhotoIds())
					&& memberId.equals(review.getMember().getId())
					&& placeId.equals(review.getPlace().getId());
		}
	}

	@Getter
	@AllArgsConstructor
	public static class UpdateRequest {
		protected String content;
		protected List<String> attachedPhotoIds;

		public boolean isEqualReview(Review review) {
			return content.equals(review.getContent())
					&& PhotoDTO.isEqualPhotos(attachedPhotoIds, review.getAttachedPhotoIds());
		}
	}
}
