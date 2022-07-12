package com.triple.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.project.domain.*;
import com.triple.project.dto.EventDTO;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.ReviewService;
import com.triple.project.service.ReviewServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class EventControllerTest {
	@Autowired
	private ReviewService reviewService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PlaceService placeService;
	ObjectMapper objectMapper;
	MockMvc mockMvc;
	ReviewServiceTest.TestReviewDTO testReviewDTO;

	@BeforeEach
	void beforeEach(WebApplicationContext webApplicationContext) {
		testReviewDTO = new ReviewServiceTest.TestReviewDTO();
		Member member = memberService.createMember(new MemberDTO.CreateRequest(testReviewDTO.getMemberId()));
		Place place = placeService.createPlace(new PlaceDTO(testReviewDTO.getPlaceId()));
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
	}

	@DisplayName("리뷰 이벤트를 작성한다.")
	@Test
	void createReview() throws Exception {
		EventDTO request = EventDTO.builder()
				.type(EventType.REVIEW.name())
				.action(ActionType.ADD.name())
				.reviewId(testReviewDTO.getReviewId())
				.content(testReviewDTO.getContent())
				.attachedPhotoIds(testReviewDTO.getAttachedPhotoIds())
				.memberId(testReviewDTO.getMemberId())
				.placeId(testReviewDTO.getPlaceId()).build();
		String body = objectMapper.writeValueAsString(request);

		this.mockMvc.perform(post("/events")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(body))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@DisplayName("리뷰를 수정한다.")
	@Test
	void updateReview() throws Exception {
		Review review = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		testReviewDTO.getAttachedPhotoIds().clear();
		EventDTO request = EventDTO.builder()
				.type(EventType.REVIEW.name())
				.action(ActionType.MOD.name())
				.reviewId(review.getId())
				.content(testReviewDTO.getContent())
				.attachedPhotoIds(testReviewDTO.getAttachedPhotoIds()).build();
		String body = objectMapper.writeValueAsString(request);

		this.mockMvc.perform(post("/events")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(body))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@DisplayName("리뷰를 삭제한다.")
	@Test
	void deleteReview() throws Exception {
		Review review = reviewService.createReview(testReviewDTO.getCreateReviewDTO());
		EventDTO request = EventDTO.builder()
				.type(EventType.REVIEW.name())
				.action(ActionType.DELETE.name())
				.reviewId(review.getId()).build();
		String body = objectMapper.writeValueAsString(request);

		this.mockMvc.perform(post("/events")
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON)
						.content(body))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
