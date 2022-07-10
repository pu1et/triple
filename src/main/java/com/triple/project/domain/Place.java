package com.triple.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Place extends BaseEntity {
	public Place(String placeId) {
		super.id = placeId;
	}
}
