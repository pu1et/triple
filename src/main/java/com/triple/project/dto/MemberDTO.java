package com.triple.project.dto;

import com.triple.project.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberDTO {
	@NoArgsConstructor
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
