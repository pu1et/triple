package com.triple.project.service;

import com.triple.project.domain.Member;
import com.triple.project.dto.MemberDTO;
import com.triple.project.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class MemberService {
	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member findMember(String memberId) {
		return memberRepository.findById(memberId)
				.orElseThrow(() -> {
					throw new IllegalStateException("사용자가 존재하지 않습니다.");
				});
	}

	@Transactional
	public Member saveMember(MemberDTO memberDTO) {
		return memberRepository.save(memberDTO.toMember());
	}
}
