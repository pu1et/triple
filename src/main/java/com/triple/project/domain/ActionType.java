package com.triple.project.domain;

public enum ActionType {
	ADD, MOD, DELETE;

	public static ActionType from(String actionType) {
		return ActionType.valueOf(actionType.toUpperCase());
	}
}
