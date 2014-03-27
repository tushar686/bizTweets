package com.biztweets.model;

import java.util.Collections;
import java.util.List;

public class Follow {
	private String entityName;
	private List<FollowMetadata> followMetadata;
	
	public Follow() {}
	
	public Follow(String entityName, List<FollowMetadata> followMetadata) {
		super();
		this.entityName = entityName;
		this.followMetadata = followMetadata;
	}

	public String getEntityName() {
		return entityName;
	}

	public List<FollowMetadata> getFollowMetadata() {
		return Collections.unmodifiableList(followMetadata);
	}

	@Override
	public String toString() {
		return "Follow [entityName=" + entityName + ", followMetadata="
				+ followMetadata + "]";
	}

	
}
