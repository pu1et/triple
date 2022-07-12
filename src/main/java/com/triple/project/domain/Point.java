package com.triple.project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class Point extends BaseEntity {
	@Column(name = "point", nullable = false, updatable = false)
	private int point;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "member", nullable = false, updatable = false)
	private Member member;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "review", nullable = false, updatable = false)
	private Review review;
}
