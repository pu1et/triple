package com.triple.project;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

@ActiveProfiles("test")
@SpringBootTest
public class ReviewServiceTest {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PlaceService placeService;

	@DisplayName("리뷰를 작성한다.")
	@Test
	void createReview() {
		final String reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772";
		final String content = "좋아요!";
		final List<String> attachedPhotoIds = new ArrayList<>();
		attachedPhotoIds.add("e4d1a64e-a531-46de-88d0-ff0ed70c0bb8");
		attachedPhotoIds.add("afb0cef2-851d-4a50-bb07-9cc15cbdc332");
		final String userId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
		final String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";

		Member member = memberService.saveMember(new MemberDTO("3ede0ef2-92b7-4817-a5f3-0c575361f745"));
		Place place = placeService.savePlace(new PlaceDTO("2e4baf1c-5acb-4efb-a1af-eddada31b00f"));
		ReviewDTO reviewDTO = ReviewDTO.builder()
				.reviewId(reviewId)
				.content(content)
				.attachedPhotoIds(attachedPhotoIds)
				.memberId(userId)
				.placeId(placeId).build();
		Review review = reviewService.createReview(reviewDTO);
		Assertions.assertTrue(reviewDTO.isEqualReview(review));
	}
}
