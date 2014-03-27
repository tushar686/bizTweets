package com.biztweets.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.biztweets.formatter.JSONResultHandler;
import com.biztweets.model.Tweets;
import com.biztweets.model.Users;
import com.biztweets.service.tweet.QueryCriteria;
import com.biztweets.service.tweet.QueryType;
import com.biztweets.service.tweet.QueryTypeEndsWith;
import com.biztweets.service.tweet.QueryTypeExactMatch;
import com.biztweets.service.tweet.QueryTypeStartsWith;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;


@Repository
public class BizTweetsService {
	
	private static final String EMPTY_STRING = "";
	private static final String ENDS_WITH = "EndsWith";
	private static final String STARTS_WITH = "StartsWith";
	private static final String EXACT_MATCH = "ExactMatch";
	private Jongo jongo;
	@Value("${databaseName}")
	private String databaseName;
	
	@Value("${entities.collectionName}")
	private String entities;
	@Value("${entities.delimiter}")
	private String delimiter;
	
	@Value("${users.collectionName}")
	private String users;
	@Value("${users.followingEntity.field}")
	private String followingEntity;
	
	@Value("${tweets.number.perfollow}")
	private int numberOfTweetsPerFollow;
	@Value("${tweets.number.permodel}")
	private int numberOfTweetsPerModel;
	@Value("${tweets.collectionName}")
	private String tweets;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void init() throws UnknownHostException {
		jongo = new Jongo(new MongoClient().getDB(databaseName));
	}

	public Iterable<String> getEntiities() {
		 return jongo.getCollection(entities).find().map(new JSONResultHandler());
	}

	public List<Users> getUserFollowingEntities(String user) {
		List<Users> following = new ArrayList<>();				
		Iterable<String> userDetails = jongo.getCollection(users).find("{user:\""+ user +"\"}").map(new JSONResultHandler());
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(String details : userDetails) {
			try {
				Users userObj = objMapper.readValue(details, Users.class);
				following.add(userObj);
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return following;		
	}
	
	public void saveFollow(Users follow) {
		mongoTemplate.insert(follow);		
	}

	public void deleteFollow(Users unfollow) {
		//jongo.getCollection(users).remove("{followingEntity: \"" + unfollow.getFollowingEntity()  + "\", type: \"" + type_followingEntity + "\"}");
	}

	public List<Iterable<Tweets>> searchTweets(String user, int cursor, String searchString, String queryType) {
		QueryCriteria criteria = new QueryCriteria(user, cursor, searchString, queryType, jongo, tweets, numberOfTweetsPerModel);
		return tweetsRetriever(criteria).searchTweets();
	}
	
	private QueryType tweetsRetriever(QueryCriteria criteria) {
		QueryType query = new QueryTypeExactMatch(criteria);
		if(criteria.getQueryType().equalsIgnoreCase(EXACT_MATCH))
			query = new QueryTypeExactMatch(criteria);
		if(criteria.getQueryType().equalsIgnoreCase(STARTS_WITH))
			query = new QueryTypeStartsWith(criteria);
		if(criteria.getQueryType().equalsIgnoreCase(ENDS_WITH))
			query = new QueryTypeEndsWith(criteria);
		return query;
	}

	public List<Iterable<Tweets>> getTweets(String user, int cursor) {
		List<Iterable<Tweets>> result = new ArrayList<>();
		List<Users> users = getUserFollowingEntities(user);
		
		for(Users following: users) {
			QueryCriteria criteria = null;
			if(EMPTY_STRING.equals(following.getFollow().getEntityName())) {
				criteria = new QueryCriteria(user, cursor, "", EXACT_MATCH, jongo, tweets, numberOfTweetsPerFollow, following.getFollow().getFollowMetadata().get(0));
				result.addAll(tweetsRetriever(criteria).getTweetsByField());
			} else {
				criteria = new QueryCriteria(user, cursor, following.getFollow().getEntityName(), EXACT_MATCH, jongo, tweets, numberOfTweetsPerFollow);
				result.addAll(tweetsRetriever(criteria).getTweetsByEntityName());
			}
		}
		return result;
	}
}
