package com.triple.project.web;

import com.triple.project.domain.Review;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/{reviewId}")
	public ResponseEntity<Review> getReview(@PathVariable String reviewId) {
		Review review = reviewService.findReview(reviewId);
		return ResponseEntity.ok(review);
	}
}
