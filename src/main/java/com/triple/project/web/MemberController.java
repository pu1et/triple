package com.triple.project.web;

import com.triple.project.domain.Review;
import com.triple.project.dto.EventDTO;
import com.triple.project.dto.MemberDTO;
import com.triple.project.service.EventService;
import com.triple.project.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RestController
public class MemberController {
	private final MemberService memberService;

	public MemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberDTO.GetResponse> getMember(@PathVariable String memberId) {
		MemberDTO.GetResponse memberWithPoint = memberService.getMemberWithPoint(memberId);
		return ResponseEntity.ok(memberWithPoint);
	}
}
