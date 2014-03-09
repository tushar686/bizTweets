package com.biztweets.controller;

import java.net.UnknownHostException;

import javax.annotation.PostConstruct;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biztweets.formatter.JSONResultHandler;
import com.biztweets.service.BizTweetsService;
import com.mongodb.DB;
import com.mongodb.MongoClient;
   
@Controller    
public class BizTweetsController {
	
	private Jongo jongo;
	@Value("${databaseName}")
	private String databaseName;
	@Value("${bizEntities.schema}")
	private String bizEntitiesSchema;
	
	@PostConstruct
	public void init() throws UnknownHostException {
		jongo = new Jongo(new MongoClient().getDB(databaseName));
	}
   
	@Autowired
	private BizTweetsService bizTweetsService;
	
	  
    @RequestMapping(value = "/getBizEntities", method = RequestMethod.GET)  
	public String getBizEntities(ModelMap model) {  
    	Iterable<String> schemaAsJson = jongo.getCollection(bizEntitiesSchema).find().map(new JSONResultHandler());
		model.addAttribute("bizEntitiesSchema", schemaAsJson);  
    	return "bizEntitiesSchema";
    }
    
    @RequestMapping(value = "/getBizEntities1", method = RequestMethod.GET)  
    @ResponseBody
	public Iterable<String> getBizEntities1() {  
    	Iterable<String> schemaAsJson = jongo.getCollection(bizEntitiesSchema).find().map(new JSONResultHandler());
		return schemaAsJson;  
    }
    
    @RequestMapping(value = "/getTweets", method = RequestMethod.GET)  
	public String getTweets(ModelMap model, @RequestParam(value = "cursor") int cursor, @RequestParam(value = "field") String field) {  
    	DB db;
		try {
			db = new MongoClient().getDB("bizTweets");
			Jongo jongo = new Jongo(db);
			MongoCollection entities = jongo.getCollection("entities");
			Iterable<String> documentsAsJson = entities.find().skip(cursor).limit(10).map(new JSONResultHandler());
			
			model.addAttribute("bizTweets", documentsAsJson);
			model.addAttribute("cursor", cursor+10); 
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "index";  
    }
    
}
