package com.biztweets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;

@Repository
public class BizTweetsService {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	
	
	
}
