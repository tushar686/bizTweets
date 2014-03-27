package com.biztweets.service.tweet;

import java.util.ArrayList;
import java.util.List;

import com.biztweets.model.Tweets;

public class QueryTypeExactMatch extends QueryType {

	public QueryTypeExactMatch(QueryCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public List<Iterable<Tweets>> searchTweets() {
		List<Iterable<Tweets>> tweetList = new ArrayList<>();
		String searchQuery =  "{ $or: [ " +  buildQuery("entityName") + ", " + buildQuery("metadata.key") + ", " + buildQuery("metadata.value") + " ] }";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));
		return tweetList;
	}
	
	private String buildQuery(String fieldName) {
		String query = addQuotesAroundFieldNameOnlyIfItsArrayType(fieldName);
		query = query + "\""+ criteria.getSearchString() + "\"}";
		return query;
		
	}

	@Override
	public List<Iterable<Tweets>> getTweetsByEntityName() {
		List<Iterable<Tweets>> tweetList = new ArrayList<>();
		String searchQuery = buildQuery("entityName") + "}";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));
		return tweetList;
	}

	@Override
	public List<Iterable<Tweets>> getTweetsByField() {
		List<Iterable<Tweets>> tweetList = new ArrayList<>();
		String searchQuery =  "{ $and: [ {\"metadata.key\": \"" + criteria.getFollowMetadata().getField() + "\"}," + "{\"metadata.value\": \"" + criteria.getFollowMetadata().getFieldValue() + "\"}" + " ] }";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));
		return tweetList;
	}

}
