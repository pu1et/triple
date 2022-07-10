package com.triple.project.service;

import com.triple.project.domain.Place;
import com.triple.project.dto.PlaceDTO;
import com.triple.project.repository.PlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class PlaceService {
	private PlaceRepository placeRepository;

	public PlaceService(PlaceRepository placeRepository) {
		this.placeRepository = placeRepository;
	}

	public Place findPlace(String placeId) {
		return placeRepository.findById(placeId)
				.orElseThrow(() -> {
					throw new IllegalStateException("장소가 존재하지 않습니다.");
				});
	}

	@Transactional
	public Place savePlace(PlaceDTO placeDTO) {
		return placeRepository.save(placeDTO.toPlace());
	}
}
