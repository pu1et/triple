package com.triple.project.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Review extends BaseEntity {
	@Column(name = "content", nullable = false)
	private String content;
	@Builder.Default
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "review")
	private List<Photo> attachedPhotoIds = new ArrayList<>();
	@ManyToOne
	@JoinColumn(name = "member", nullable = false)
	private Member member;
	@ManyToOne
	@JoinColumn(name = "place", nullable = false)
	private Place place;
	@Column(name = "point", nullable = false)
	private int point;

	public void addPhoto(Photo photo) {
		attachedPhotoIds.add(photo);
		photo.setReview(this);
	}

	public void updateReview(String content, List<Photo> attachedPhotoIds) {
		this.content = content;
		this.attachedPhotoIds = new ArrayList<>();
		attachedPhotoIds.forEach(this::addPhoto);
	}

	public void updatePoint(int point) {
		this.point = point;
	}
}
