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
import com.biztweets.model.Users;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;


@Repository
public class BizTweetsService {
	
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
	@Value("${users.type.followingEntity}")
	private String type_followingEntity;
	
	@Value("${tweets.number.perpage}")
	private int numberOfTweetsPerPage;
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

	public List<String> getUserFollowingEntities(String user) {
		List<String> followingEntities = new ArrayList<String>();				
		Iterable<String> userDetails = getUserDetails(user, type_followingEntity);
		ObjectMapper objMapper = new ObjectMapper();
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		for(String details : userDetails) {
			try {
				Users userObj = objMapper.readValue(details, Users.class);
				followingEntities.add(userObj.getFollowingEntity());
			} catch (JsonParseException e) {
				e.printStackTrace();
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return followingEntities;		
	}
	
	private Iterable<String> getUserDetails(String user, String type) {
		return jongo.getCollection(users).find("{user:\""+ user +"\", type:\"" + type + "\"}").map(new JSONResultHandler());
	}
	
	public void saveFollow(Users follow) {
		follow.setType(type_followingEntity);
		mongoTemplate.insert(follow);		
	}

	public void deleteFollow(Users unfollow) {
		jongo.getCollection(users).remove("{followingEntity: \"" + unfollow.getFollowingEntity()  + "\", type: \"" + type_followingEntity + "\"}");
	}

	public List<Iterable<String>> getTweets(String user, int cursor) {
		List<String> followingEntities = getUserFollowingEntities(user);
		Tweets tweetsHelper = new Tweets(cursor, followingEntities, numberOfTweetsPerPage, delimiter, tweets, jongo);
		return tweetsHelper.getTweets();
	}
}
