package com.triple.project.controller;

import com.triple.project.domain.Member;
import com.triple.project.dto.MemberDTO;
import com.triple.project.service.MemberService;
import com.triple.project.service.ReviewServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class MemberControllerTest {
	@Autowired
	private MemberService memberService;
	MockMvc mockMvc;
	ReviewServiceTest.TestReviewDTO testReviewDTO;

	@BeforeEach
	void beforeEach(WebApplicationContext webApplicationContext) {
		testReviewDTO = new ReviewServiceTest.TestReviewDTO();
		Member member = memberService.createMember(new MemberDTO.CreateRequest(testReviewDTO.getMemberId()));
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
				.addFilter(new CharacterEncodingFilter("UTF-8", true)).build();
	}

	@DisplayName("사용자를 포인트와 함께 조회한다.")
	@Test
	void createReview() throws Exception {
		this.mockMvc.perform(get("/member/{memberId}", testReviewDTO.getMemberId()))
				.andDo(print())
				.andExpect(status().isOk());
	}
}
