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
	private final PointRepository pointRepository;

	public PointService(PointRepository pointRepository) {
		this.pointRepository = pointRepository;
	}

	public int getMemberPoint(Member member) {
		Integer point = pointRepository.sumPointGroupByMember(member);
		return point == null ? 0 : point;
	}

	@Transactional
	public Point addPoint(PointDTO pointDTO) {
		return pointRepository.save(pointDTO.toPoint());
	}

	public List<Point> getAllPointsByReview(Review review) {
		return pointRepository.findAllByReview(review);
	}
}
