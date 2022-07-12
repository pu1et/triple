package com.triple.project.service;

import com.triple.project.domain.Member;
import com.triple.project.domain.Point;
import com.triple.project.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class PointDTO {
	private String pointId;
	private int point;
	private Member member;
	private Review review;

	public PointDTO(int point, Member member, Review review) {
		this.pointId = UUID.randomUUID().toString();
		this.point = point;
		this.member = member;
		this.review = review;
	}

	public Point toPoint() {
		return Point.builder()
				.id(pointId)
				.point(point)
				.member(member)
				.review(review)
				.build();
	}
}
