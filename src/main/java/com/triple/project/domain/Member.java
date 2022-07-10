package com.triple.project.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
	public Member(String memberId) {
		super.id = memberId;
	}
}
