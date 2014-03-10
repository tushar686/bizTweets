package com.biztweets.service;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.jongo.Jongo;
import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.biztweets.formatter.JSONResultHandler;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClient;


@Repository
public class BizTweetsService {
	
	private Jongo jongo;
	@Value("${databaseName}")
	private String databaseName;
	@Value("${entities.collectionName}")
	private String entities;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@PostConstruct
	public void init() throws UnknownHostException {
		jongo = new Jongo(new MongoClient().getDB(databaseName));
	}

	public Iterable<String> getEntiities() {
		 return jongo.getCollection(entities).find().map(new JSONResultHandler());
	}	
	
}
