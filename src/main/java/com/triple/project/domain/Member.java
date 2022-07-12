package com.triple.project.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Getter
@Entity
public class Member extends BaseEntity {
	public Member(String memberId) {
		super.id = memberId;
	}
}
