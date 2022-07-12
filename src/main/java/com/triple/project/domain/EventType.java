package com.triple.project.domain;

public enum EventType {
	REVIEW;

	public static EventType from(String eventType) {
		return EventType.valueOf(eventType.toUpperCase());
	}
}
