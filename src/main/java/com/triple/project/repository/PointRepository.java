package com.triple.project.repository;

import com.triple.project.domain.Member;
import com.triple.project.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, String> {
	@Query(value =
			"SELECT SUM(p.point) " +
			"FROM Point p " +
			"GROUP BY p.member " +
			"HAVING p.member = :member")
	int sumPointGroupByMember(@Param("member") Member member);
}
