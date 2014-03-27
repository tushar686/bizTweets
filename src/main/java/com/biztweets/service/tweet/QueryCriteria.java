package com.biztweets.service.tweet;

import org.jongo.Jongo;

import com.biztweets.model.FollowMetadata;

public class QueryCriteria {
	private String user;
	private int cursor;
	private String searchString;
	private String queryType;
	private Jongo jongo;
	private String tweets;
	private int numberOfTweetsToFetch;
	private FollowMetadata followMetadata;

	
	public QueryCriteria(String user, int cursor, String searchString, String queryType, Jongo jongo, String tweets, int numberOfTweetsToFetch) {
		this(user, cursor, searchString, queryType, jongo, tweets, numberOfTweetsToFetch, null);		
	}
	
	public QueryCriteria(String user, int cursor, String searchString, String queryType, Jongo jongo, String tweets, int numberOfTweetsToFetch, FollowMetadata followMetadata) {
		this.user = user;
		this.cursor = cursor;
		this.searchString = searchString;
		this.queryType = queryType;
		this.jongo = jongo;
		this.tweets = tweets;
		this.numberOfTweetsToFetch = numberOfTweetsToFetch;
		this.followMetadata = followMetadata;
	}


	public String getUser() {
		return user;
	}


	public int getCursor() {
		return cursor;
	}


	public String getSearchString() {
		return searchString;
	}


	public String getQueryType() {
		return queryType;
	}


	public Jongo getJongo() {
		return jongo;
	}


	public String getTweets() {
		return tweets;
	}


	public int getNumberOfTweetsToFetch() {
		return numberOfTweetsToFetch;
	}

	public FollowMetadata getFollowMetadata() {
		return followMetadata;
	}
	
	
	
	/*private String buildQuery(String fieldName) {
		String query = addQuotesAroundFieldNameOnlyIfItsArrayType(fieldName);
		
		switch (queryType) {
		case "Like":
			query = query + "{ $regex: '" + searchString + "'}" + "}";
			break;
		case "StartsWith":
			query = query + "{ $regex: '^" + searchString + "'}" + "}";
			break;
		case "EndsWith":
			query = query + "{ $regex: '" + searchString + "$'}" + "}";
			break;
		case "ExactMatch":
			query = query + "\""+ searchString + "\"}";
			break;
		default:
			query = query + "\""+ searchString + "\"}";
			break;
		}
		
		//{$or:[  {"metadata.key": { $regex: 'depart', $options: 'i' }}, {"metadata.value": { $regex: 'depart', $options: 'i' }}, {entityName: { $regex: 'depart', $options: 'i' }} ]} )
		return query;
		
	}*/
	
}
