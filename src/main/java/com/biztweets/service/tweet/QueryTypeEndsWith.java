package com.biztweets.service.tweet;

import java.util.ArrayList;
import java.util.List;

import com.biztweets.model.Tweets;

public class QueryTypeEndsWith extends QueryType {

	public QueryTypeEndsWith(QueryCriteria criteria) {
		super();
		this.criteria = criteria;
	}

	public List<Iterable<Tweets>> searchTweets() {
		List<Iterable<Tweets>> tweetList = new ArrayList<>();
		
		String searchQuery =  buildQuery("entityName") + "}";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));
		
		searchQuery =  buildQuery("metadata.key") + "}";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));
		
		searchQuery =  buildQuery("metadata.value") + "}";
		tweetList.add(executeQuery(searchQuery, criteria.getNumberOfTweetsToFetch()));

		return tweetList;
	}
	
	private String buildQuery(String fieldName) {
		String query = addQuotesAroundFieldNameOnlyIfItsArrayType(fieldName);
		query = query + "{ $regex: '" + criteria.getSearchString() + "$', $options: 'i'}" + "}";
		return query;
		
	}

	@Override
	public List<Iterable<Tweets>> getTweetsByEntityName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Iterable<Tweets>> getTweetsByField() {
		// TODO Auto-generated method stub
		return null;
	}
}
