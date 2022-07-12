package com.triple.project.web;

import com.triple.project.domain.Review;
import com.triple.project.dto.EventDTO;
import com.triple.project.dto.ReviewDTO;
import com.triple.project.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/events")
@RestController
public class EventController {
	private final EventService eventService;

	public EventController(EventService eventService) {
		this.eventService = eventService;
	}

	@PostMapping("")
	public ResponseEntity<Review> handleEvent(@RequestBody EventDTO request) {
		Review review = eventService.checkEvent(request);
		return ResponseEntity.ok(review);
	}
}
