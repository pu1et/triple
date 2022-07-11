package com.triple.project.repository;

import com.triple.project.domain.Member;
import com.triple.project.domain.Place;
import com.triple.project.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
	Optional<Review> findByMemberAndPlace(Member member, Place place);

	List<Review> findAllByPlace(Place placeId);
}
