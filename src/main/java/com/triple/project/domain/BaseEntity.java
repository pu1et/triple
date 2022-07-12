package com.triple.project.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@EqualsAndHashCode
@SuperBuilder
@NoArgsConstructor
@Getter
@MappedSuperclass
public class BaseEntity {
	@Id
	protected String id;
	@Column(name = "created_at", nullable = false, updatable = false)
	protected LocalDateTime createdAt;
	@LastModifiedDate
	@Column(name = "updated_at", nullable = false)
	protected LocalDateTime updatedAt;

	@PrePersist
	public void prePersist() {
		LocalDateTime now = LocalDateTime.now();
		createdAt = now;
		updatedAt = now;
	}

	@PreUpdate
	public void preUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
