package com.triple.project.dto;

import com.triple.project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class MemberDTO {
	@AllArgsConstructor
	@Getter
	public static class CreateRequest {
		private String memberId;

		public Member toMember() {
			return new Member(memberId);
		}
	}

	@AllArgsConstructor
	@Getter
	public static class GetResponse {
		private String memberId;
		private int point;
	}
}
