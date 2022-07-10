package com.triple.project.dto;

import com.triple.project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDTO {
	private String memberId;

	public Member toMember() {
		return new Member(memberId);
	}
}
