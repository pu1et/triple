package com.triple.project.service;

import com.triple.project.domain.Member;
import com.triple.project.domain.Point;
import com.triple.project.domain.Review;
import com.triple.project.repository.PointRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class PointService {
	private final MemberService memberService;
	private final PointRepository pointRepository;

	public PointService(MemberService memberService, PointRepository pointRepository) {
		this.memberService = memberService;
		this.pointRepository = pointRepository;
	}

	public int getUserPoint(String memberId) {
		Member member = memberService.findMember(memberId);
		return pointRepository.sumPointGroupByMember(member);
	}

	@Transactional
	public Point addPoint(PointDTO pointDTO) {
		return pointRepository.save(pointDTO.toPoint());
	}

	public List<Point> getAllPointsByReview(Review review) {
		return pointRepository.findAllByReview(review);
	}
}
