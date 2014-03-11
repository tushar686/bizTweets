package com.biztweets.service;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.biztweets.formatter.JSONResultHandler;
import com.biztweets.model.Users;
import com.mongodb.MongoClient;


@Repository
public class BizTweetsService {
	
	private Jongo jongo;
	@Value("${databaseName}")
	private String databaseName;
	@Value("${entities.collectionName}")
	private String entities;
	
	@Value("${users.collectionName}")
	private String users;
	@Value("${users.followingEntity.field}")
	private String followingEntity;
	
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void init() throws UnknownHostException {
		jongo = new Jongo(new MongoClient().getDB(databaseName));
	}

	public Iterable<String> getEntiities() {
		 return jongo.getCollection(entities).find().map(new JSONResultHandler());
	}

	public Iterable<String> getUserDetails(String user) {
		return jongo.getCollection(users).find("{user:\""+ user +"\"}").map(new JSONResultHandler());
	}
	
	public void saveFollow(Users follow) {
		mongoTemplate.insert(follow);		
	}

	public void deleteFollow(Users unfollow) {
		jongo.getCollection(users).remove("{followingEntity: \"" + unfollow.getFollowingEntity()  + "\"}");
	}

	
}
