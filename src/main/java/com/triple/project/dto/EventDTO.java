package com.triple.project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class EventDTO {
	private String type;
	private String action;
	private String reviewId;
	protected String content;
	protected List<String> attachedPhotoIds;
	private String memberId;
	private String placeId;

	public ReviewDTO.CreateRequest toReviewCreateRequest() {
		return ReviewDTO.CreateRequest.builder()
				.reviewId(reviewId)
				.content(content)
				.attachedPhotoIds(attachedPhotoIds)
				.memberId(memberId)
				.placeId(placeId)
				.build();
	}

	public ReviewDTO.UpdateRequest toReviewUpdateRequest() {
		return ReviewDTO.UpdateRequest.builder()
				.reviewId(reviewId)
				.content(content)
				.attachedPhotoIds(attachedPhotoIds)
				.build();
	}

	public ReviewDTO.DeleteRequest toReviewDeleteRequest() {
		return ReviewDTO.DeleteRequest.builder()
				.reviewId(reviewId)
				.build();
	}
}
