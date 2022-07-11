package com.triple.project;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PhotoDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.ReviewService;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class ReviewServiceTest {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PlaceService placeService;
	TestReviewDTO testReviewDTO;

	@BeforeEach
	void beforeEach() {
		testReviewDTO = new TestReviewDTO();
		Member member = memberService.saveMember(new MemberDTO(testReviewDTO.memberId));
		Place place = placeService.savePlace(new PlaceDTO(testReviewDTO.placeId));
	}

	@DisplayName("리뷰를 작성한다.")
	@Test
	void createReview() {
		ReviewDTO.CreateRequest reviewDTO = testReviewDTO.getCreateReviewDTO();
		Review review = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		assertTrue(reviewDTO.isEqualReview(review));
	}

	@DisplayName("리뷰를 수정한다.")
	@Test
	void updateReview() {
		Review review = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		final String updateContent = "싫어요!";
		final List<String> updateAttachedPhotoIds = new ArrayList<>();
		updateAttachedPhotoIds.add(testReviewDTO.photoId2);
		testReviewDTO.setContent(updateContent);
		testReviewDTO.setAttachedPhotoIds(updateAttachedPhotoIds);
		ReviewDTO.UpdateRequest reviewDTO = new ReviewDTO.UpdateRequest(updateContent, updateAttachedPhotoIds);

		Review updateReview = reviewService.updateReview(testReviewDTO.reviewId, reviewDTO);

		assertTrue(reviewDTO.isEqualReview(updateReview));
	}

	@DisplayName("리뷰를 삭제한다.")
	@Test
	void deleteReview() {
		Review createdReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		reviewService.deleteReview(testReviewDTO.reviewId);

		assertThrows(IllegalStateException.class, () -> {
			reviewService.findReview(testReviewDTO.reviewId);
		});
	}

	@DisplayName("한 사용자는 장소마다 한 리뷰만 작성할 수 있다.")
	@Test
	void onePlaceOneReview() {
		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		assertThrows(IllegalStateException.class, () -> {
			reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		});
	}

	@DisplayName("보상 점수 중 내용 점수는 1자 이상 텍스트 작성시 1점, 1장 이상 사진 첨부시 1점이다.")
	@Test
	void getAllPoint() {
		final String ContentOfLength0 = "";
		final String ContentOfLength1 = "글";
		testReviewDTO.setContent(ContentOfLength0);
		testReviewDTO.setAttachedPhotoIds(new ArrayList<>());
		int reviewWithZeroLengthAndZeroPhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		testReviewDTO.setContent(ContentOfLength1);
		int reviewWithOneLengthAndZeroPhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		testReviewDTO.setContent(ContentOfLength0);
		final List<String> updateAttachedPhotoIds = new ArrayList<>();
		updateAttachedPhotoIds.add(testReviewDTO.photoId1);
		testReviewDTO.setAttachedPhotoIds(updateAttachedPhotoIds);
		int reviewWithZeroLengthAndOnePhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		testReviewDTO.setContent(ContentOfLength1);
		int reviewWithOneLengthAndOnePhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));

		assertEquals(reviewWithZeroLengthAndZeroPhoto, 0);
		assertEquals(reviewWithOneLengthAndZeroPhoto, 1);
		assertEquals(reviewWithZeroLengthAndOnePhoto, 1);
		assertEquals(reviewWithOneLengthAndOnePhoto, 2);
	}

	@DisplayName("보상 점수 중 보너스 점수는 특정 장소에 첫 리뷰 작성시 1점이다.")
	@Test
	void getBonusPoint() {
		Member member = memberService.saveMember(new MemberDTO(testReviewDTO.memberId2));

		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		int firstReviewPoint = reviewService.calculateBonusPoint(firstReview);
		testReviewDTO.setReviewId(testReviewDTO.reviewId2);
		testReviewDTO.setMemberId(testReviewDTO.memberId2);
		Review secondReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		int secondReviewPoint = reviewService.calculateBonusPoint(secondReview);

		assertEquals(firstReviewPoint, 1);
		assertEquals(secondReviewPoint, 0);
	}

	@Getter
	static class TestReviewDTO {
		private String reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772";
		private String content = "좋아요!";
		private List<String> attachedPhotoIds;
		private String memberId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
		private final String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
		private final String photoId1 = "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8";
		private final String photoId2 = "afb0cef2-851d-4a50-bb07-9cc15cbdc332";
		private final String reviewId2 = "240a0658-dc5f-4878-9381-ebb7b2667773";
		private final String memberId2 = "3ede0ef2-92b7-4817-a5f3-0c575361f746";

		public TestReviewDTO() {
			attachedPhotoIds = new ArrayList<>();
			attachedPhotoIds.add(photoId1);
		}

		public void setReviewId(String reviewId) {
			this.reviewId = reviewId;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public void setAttachedPhotoIds(List<String> attachedPhotoIds) {
			this.attachedPhotoIds = attachedPhotoIds;
		}

		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}

		public ReviewDTO.CreateRequest getCreateReviewDTO() {
			return ReviewDTO.CreateRequest.builder()
					.reviewId(reviewId)
					.content(content)
					.attachedPhotoIds(attachedPhotoIds)
					.memberId(memberId)
					.placeId(placeId).build();
		}

		public ReviewDTO.UpdateRequest getUpdateReviewDTO() {
			return new ReviewDTO.UpdateRequest(content, attachedPhotoIds);
		}
	}
}
