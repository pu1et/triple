package com.triple.project.web;

import com.triple.project.domain.Place;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.service.PlaceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/place")
@RestController
public class PlaceController {
	private final PlaceService placeService;

	public PlaceController(PlaceService placeService) {
		this.placeService = placeService;
	}

	@PostMapping
	public ResponseEntity<Place> createPlace(@RequestBody PlaceDTO request) {
		Place place = placeService.createPlace(request);
		return ResponseEntity.ok(place);
	}
}
