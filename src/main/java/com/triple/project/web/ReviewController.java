package com.triple.project.web;

import com.triple.project.domain.Review;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/events")
@RestController
public class ReviewController {
	private final ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@PostMapping("")
	public ResponseEntity<Review> createReview(@RequestBody ReviewDTO.CreateRequest request) {
		Review review = reviewService.createReview(request);
		return ResponseEntity.ok(review);
	}
}
