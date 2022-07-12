package com.triple.project;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Point;
import com.triple.project.domain.Review;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.PointService;
import com.triple.project.service.ReviewService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class PointServiceTest {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PlaceService placeService;
	@Autowired
	private PointService pointService;
	ReviewServiceTest.TestReviewDTO testReviewDTO;

	@BeforeEach
	void beforeEach() {
		testReviewDTO = new ReviewServiceTest.TestReviewDTO();
		Member member = memberService.saveMember(new MemberDTO(testReviewDTO.getMemberId()));
		Place place = placeService.savePlace(new PlaceDTO(testReviewDTO.getPlaceId()));
	}

	@DisplayName("포인트 증감이 있을 때마다 이력이 남아야 한다.")
	@Test
	void addPointList() {
		Review firstReview = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		testReviewDTO.getAttachedPhotoIds().clear();
		Review review = reviewService.updateReview(testReviewDTO.getReviewId(), testReviewDTO.getUpdateReviewDTO());

		List<Point> allPoints = pointService.getAllPointsByReview(review);

		assertEquals(allPoints.size(), 2);
	}
}
