package com.triple.project.service;

import com.triple.project.domain.EventType;
import com.triple.project.domain.Review;
import com.triple.project.dto.EventDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class EventService {
	private final ReviewService reviewService;

	public EventService(ReviewService reviewService) {
		this.reviewService = reviewService;
	}

	@Transactional
	public Review checkEvent(EventDTO eventDTO) {
		if (EventType.from(eventDTO.getType()) == EventType.REVIEW) {
			return reviewService.checkReview(eventDTO);
		}
		return null;
	}
}
