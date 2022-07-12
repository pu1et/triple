package com.triple.project.service;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PhotoDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.PointService;
import com.triple.project.service.ReviewService;
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
	@Autowired
	private PointService pointService;
	TestReviewDTO testReviewDTO;

	@BeforeEach
	void beforeEach() {
		testReviewDTO = new TestReviewDTO();
		Member member = memberService.saveMember(new MemberDTO.CreateRequest(testReviewDTO.memberId));
		Place place = placeService.savePlace(new PlaceDTO(testReviewDTO.placeId));
		testReviewDTO.setMember(member);
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
		testReviewDTO.content = updateContent;
		testReviewDTO.attachedPhotoIds.remove(testReviewDTO.attachedPhotoIds.size() - 1);
		ReviewDTO.UpdateRequest reviewDTO = testReviewDTO.getUpdateReviewDTO();

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

		// 글이 0자이고, 사진도 0개인 경우
		testReviewDTO.content = ContentOfLength0;
		testReviewDTO.attachedPhotoIds = new ArrayList<>();
		int reviewWithZeroLengthAndZeroPhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		// 글이 1자이고, 사진은 0개인 경우
		testReviewDTO.content = ContentOfLength1;
		int reviewWithOneLengthAndZeroPhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		// 글이 0자이고, 사진은 1개인 경우
		testReviewDTO.content = ContentOfLength0;
		testReviewDTO.attachedPhotoIds.add(testReviewDTO.photoId1);
		int reviewWithZeroLengthAndOnePhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));
		// 글이 1자이고, 사진도 1개인 경우
		testReviewDTO.content = ContentOfLength1;
		int reviewWithOneLengthAndOnePhoto = reviewService.calculatePoint(testReviewDTO.content, PhotoDTO.toPhotos(testReviewDTO.attachedPhotoIds));

		assertEquals(reviewWithZeroLengthAndZeroPhoto, 0);
		assertEquals(reviewWithOneLengthAndZeroPhoto, 1);
		assertEquals(reviewWithZeroLengthAndOnePhoto, 1);
		assertEquals(reviewWithOneLengthAndOnePhoto, 2);
	}

	@DisplayName("보상 점수 중 보너스 점수는 특정 장소에 첫 리뷰 작성시 1점이다.")
	@Test
	void getBonusPoint() {
		Member member = memberService.saveMember(new MemberDTO.CreateRequest(testReviewDTO.memberId2));

		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		int firstReviewPoint = reviewService.calculateBonusPoint(firstReview);
		testReviewDTO.reviewId = testReviewDTO.reviewId2;
		testReviewDTO.memberId = testReviewDTO.memberId2;
		Review secondReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		int secondReviewPoint = reviewService.calculateBonusPoint(secondReview);

		assertEquals(firstReviewPoint, 1);
		assertEquals(secondReviewPoint, 0);
	}

	@DisplayName("리뷰를 작성했다가 삭제하면 해당 리뷰로 부여한 내용 점수와 보너스 점수는 회수한다.")
	@Test
	void deleteScoreIfDeleteReview() {
		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		int pointBeforeDeletedReview = pointService.getMemberPoint(testReviewDTO.member);
		reviewService.deleteReview(testReviewDTO.reviewId);
		int pointAfterDeletedReview = pointService.getMemberPoint(testReviewDTO.member);

		assertEquals(pointAfterDeletedReview, pointBeforeDeletedReview - 3);
	}

	@DisplayName("글만 작성한 리뷰에 사진을 추가하면 1점을 부여한다.")
	@Test
	void updateScoreIfAddPhoto() {
		testReviewDTO.attachedPhotoIds.clear();
		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		int pointOfEmptyPhoto = reviewService.findReview(testReviewDTO.reviewId).getPoint();
		testReviewDTO.attachedPhotoIds.add(testReviewDTO.photoId1);
		Review review = reviewService.updateReview(testReviewDTO.reviewId, testReviewDTO.getUpdateReviewDTO());
		int pointOfOnePhoto = reviewService.findReview(testReviewDTO.reviewId).getPoint();

		assertEquals(pointOfOnePhoto, pointOfEmptyPhoto + 1);
	}

	@DisplayName("글과 사진이 있는 리뷰에서 사진을 모두 삭제하면 1점을 회수한다.")
	@Test
	void deleteScoreIfDeleteAllPhotos() {
		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		int pointOfNotEmptyPhoto = firstReview.getPoint();
		testReviewDTO.attachedPhotoIds.clear();
		Review review = reviewService.updateReview(testReviewDTO.reviewId, testReviewDTO.getUpdateReviewDTO());
		int pointOfEmptyPhoto = reviewService.findReview(testReviewDTO.reviewId).getPoint();

		assertEquals(pointOfEmptyPhoto, pointOfNotEmptyPhoto - 1);
	}

	public static class TestReviewDTO {
		private String reviewId = "240a0658-dc5f-4878-9381-ebb7b2667772";
		private String content = "좋아요!";
		private List<String> attachedPhotoIds;
		private String memberId = "3ede0ef2-92b7-4817-a5f3-0c575361f745";
		private Member member;
		private final String placeId = "2e4baf1c-5acb-4efb-a1af-eddada31b00f";
		private final String photoId1 = "e4d1a64e-a531-46de-88d0-ff0ed70c0bb8";
		private final String photoId2 = "afb0cef2-851d-4a50-bb07-9cc15cbdc332";
		private final String reviewId2 = "240a0658-dc5f-4878-9381-ebb7b2667773";
		private final String memberId2 = "3ede0ef2-92b7-4817-a5f3-0c575361f746";

		public TestReviewDTO() {
			attachedPhotoIds = new ArrayList<>();
			attachedPhotoIds.add(photoId1);
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

		public String getReviewId() {
			return reviewId;
		}

		public List<String> getAttachedPhotoIds() {
			return attachedPhotoIds;
		}

		public String getMemberId() {
			return memberId;
		}

		public String getPlaceId() {
			return placeId;
		}

		public Member getMember() {
			return member;
		}

		public void setMember(Member member) {
			this.member = member;
		}
	}
}
