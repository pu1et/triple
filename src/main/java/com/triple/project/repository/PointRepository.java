package com.triple.project.repository;

import com.triple.project.domain.Member;
import com.triple.project.domain.Point;
import com.triple.project.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, String> {
	@Query(value =
			"SELECT SUM(p.point) " +
			"FROM Point p " +
			"GROUP BY p.member " +
			"HAVING p.member = :member")
	int sumPointGroupByMember(@Param("member") Member member);

	List<Point> findAllByReview(Review review);
}
