package com.triple.project.web;

import com.triple.project.dto.MemberDTO;
import com.triple.project.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@RequestMapping("/{memberId}")
	public ResponseEntity<MemberDTO.GetRequest> getMemberPoint(@PathVariable String memberId) {
		MemberDTO.GetRequest memberWithPoint = memberService.getMemberWithPoint(memberId);
		return ResponseEntity.ok(memberWithPoint);
	}
}
