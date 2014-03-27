package com.biztweets.service.tweet;

import java.util.List;

import com.biztweets.model.Tweets;

public abstract class QueryType {
	QueryCriteria criteria;
	
	public abstract List<Iterable<Tweets>> searchTweets();
	public abstract List<Iterable<Tweets>> getTweetsByEntityName();
	public abstract List<Iterable<Tweets>> getTweetsByField();
	
	Iterable<Tweets> executeQuery(String searchQuery, int numberOfTweetsToFetch) {
		int recordsToFetch = 0;
		long count = criteria.getJongo().getCollection(criteria.getTweets()).count(searchQuery);
		int skipRecords = new Long(count - (criteria.getCursor()*numberOfTweetsToFetch)).intValue();
		if(skipRecords <= 0) {
			skipRecords = 0;
			if(criteria.getCursor() != 1) {
				recordsToFetch = new Long(count - ((criteria.getCursor()-1)*numberOfTweetsToFetch)).intValue();
				recordsToFetch = recordsToFetch < 0 ? 0 : recordsToFetch;
			}
		}
		skipRecords = skipRecords <= 0 ? 0 : skipRecords;
		return criteria.getJongo().getCollection(criteria.getTweets()).find(searchQuery).skip(skipRecords).limit(recordsToFetch).as(Tweets.class);
	}
	
	String addQuotesAroundFieldNameOnlyIfItsArrayType(String fieldName) {
		if(fieldName.contains("."))
			return "{\"" + fieldName + "\": ";
		return "{" + fieldName + ": ";
	}

}