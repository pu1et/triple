package com.triple.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
	private int point;

	public Member(String memberId) {
		super.id = memberId;
		point = 0;
	}

	public void updatePoint(int calculatePoint) {
		this.point += calculatePoint;
	}
}
