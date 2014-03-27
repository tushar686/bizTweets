package com.biztweets.model;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.List;

public class Tweets {
	private String entityName;
	private List<Metadata> metadata;
	private Timestamp insertTime;
	
	public Tweets() {}
	
	public Tweets(String entityName, List<Metadata> metadata, Timestamp insertTime) {
		super();
		this.entityName = entityName;
		this.metadata = metadata;
		this.insertTime = insertTime;
	}

	public String getEntityName() {
		return entityName;
	}

	public List<Metadata> getMetadata() {
		return Collections.unmodifiableList(metadata);
	}

	@Override
	public String toString() {
		return "Tweets [entityName=" + entityName + ", metadata=" + metadata + ", insertTime=" + insertTime
				+ "]";
	}
	
	
	
}
