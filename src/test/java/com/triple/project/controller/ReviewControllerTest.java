package com.triple.project.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import com.triple.project.dto.MemberDTO;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.PlaceService;
import com.triple.project.service.ReviewService;
import com.triple.project.service.ReviewServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("test")
@SpringBootTest
public class ReviewControllerTest {
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
		Member member = memberService.saveMember(new MemberDTO.CreateRequest(testReviewDTO.getMemberId()));
		Place place = placeService.savePlace(new PlaceDTO(testReviewDTO.getPlaceId()));
		objectMapper = new ObjectMapper();
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@DisplayName("리뷰를 작성한다.")
	@Test
	void createReview() throws Exception {
		ReviewDTO.CreateRequest request = testReviewDTO.getCreateReviewDTO();

		String body = objectMapper.writeValueAsString(request);

		this.mockMvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(body))
				.andDo(print())
				.andExpect(status().isOk());
	}

	@DisplayName("리뷰를 조회한다.")
	@Test
	void getReview() throws Exception {
		Review review = reviewService.createReview(testReviewDTO.getCreateReviewDTO());

		this.mockMvc.perform(get("/events/{reviewId}", testReviewDTO.getReviewId()))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
