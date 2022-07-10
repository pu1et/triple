package com.triple.project.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@ToString
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
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
	@ManyToOne
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

	public void addPhoto(Photo photo) {
		attachedPhotoIds.add(photo);
		photo.setReview(this);
	}
}
