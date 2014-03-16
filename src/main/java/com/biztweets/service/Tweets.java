package com.biztweets.service;

import java.util.ArrayList;
import java.util.List;

import org.jongo.Find;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import com.biztweets.formatter.JSONResultHandler;

public class Tweets {
	private int cursor;
	private List<String> followingEntities;	
	private int numberTweetsConfiguredPerPage;
	private String delimiter;
	private String tweets;
	private Jongo jongo;
	
	List<Iterable<String>> tweetsList = new ArrayList<Iterable<String>>();
	
	public Tweets(int cursor, List<String> followingEntities, int numberTweetsConfiguredPerPage, String delimiter, String tweets, Jongo jongo) {
		super();
		this.cursor = cursor;
		this.followingEntities = followingEntities;
		this.numberTweetsConfiguredPerPage = numberTweetsConfiguredPerPage;
		this.delimiter = delimiter;
		this.tweets = tweets;
		this.jongo = jongo;
	}

	public List<Iterable<String>> getTweets() {
		int tweetsPerEntity = getSizeOfTweetsPerEntity(followingEntities.size());
		for(String entity : followingEntities) {
			tweetsList.add(getTweetsForAnEntity(tweetsPerEntity, entity));
		}
		
		return tweetsList;
	}
	
	private int calculateNumberOfTweetsToBeFetchedPerEntity() {
		int numberOfEntitiesFollowing  = followingEntities.size();
		for(String entity : followingEntities) {
			if(hasTweetsForThisEntityAreAlreadyDisplayed(numberOfEntitiesFollowing, entity)) {
				numberOfEntitiesFollowing = numberOfEntitiesFollowing - 1;
			}
		}
		return getSizeOfTweetsPerEntity(numberOfEntitiesFollowing);
	}
	
	private boolean hasTweetsForThisEntityAreAlreadyDisplayed(int numberOfEntitiesFollowing, String entity) {
		long countOfTweets = jongo.getCollection(tweets).count("{" + buildQueryToFind_OnlyCollections_WhichIncludeFields(entity.split(delimiter)) +"}");
		int cursorPosition = getSizeOfTweetsPerEntity(numberOfEntitiesFollowing)*cursor;
		if(cursorPosition > countOfTweets) {
			boolean hasCursorPositionPassedTotalNumberTweetsOfEntity = ((cursorPosition - countOfTweets) / getSizeOfTweetsPerEntity(numberOfEntitiesFollowing)) > 0;
			return hasCursorPositionPassedTotalNumberTweetsOfEntity;
		}
		return false;
	}
	
	private int getSizeOfTweetsPerEntity(int numberOfEntitiesFollowing) {
		return numberOfEntitiesFollowing == 0 || numberTweetsConfiguredPerPage/numberOfEntitiesFollowing == 0 ? 1 : numberTweetsConfiguredPerPage/numberOfEntitiesFollowing;
	}

	
	private Iterable<String> getTweetsForAnEntity(int numberOfTweetsToFetch, String entity) {
		long count = jongo.getCollection(tweets).count("{" + buildQueryToFind_OnlyCollections_WhichIncludeFields(entity.split(delimiter)) +"}");
		int skip = new Long(count - (cursor * numberOfTweetsToFetch)).intValue();
		if(skip < 0) {
			numberOfTweetsToFetch = numberOfTweetsToFetch + skip < 0 ? 0 : numberOfTweetsToFetch + skip;
			skip = 0;
		}
		if(numberOfTweetsToFetch == 0)
			return new ArrayList<String>();
		return jongo.getCollection(tweets).find("{" + buildQueryToFind_OnlyCollections_WhichIncludeFields(entity.split(delimiter)) +"}").skip(skip).limit(numberOfTweetsToFetch).map(new JSONResultHandler());
	}
	
	private String buildQueryToFind_OnlyCollections_WhichIncludeFields(String [] fields) {
		String query = "";
		for(String field: fields) {
			query = query + field + ": { $exists: true},";
		}
		
		return query.substring(0, query.length()-1);
	}

}
