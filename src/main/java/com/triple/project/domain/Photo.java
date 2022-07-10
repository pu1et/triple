package com.triple.project.domain;

import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@Entity
public class Photo extends BaseEntity {
	@Setter
	@ManyToOne
	@JoinColumn(name = "reviewId", nullable = false)
	private Review review;

	public Photo(String photoId) {
		super.id = photoId;
	}
}
