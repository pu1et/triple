package com.triple.project.dto;

import com.triple.project.domain.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlaceDTO {
	private String placeId;

	public Place toPlace() {
		return new Place(placeId);
	}
}
