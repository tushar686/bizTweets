package com.biztweets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biztweets.service.BizTweetsService;
   
@Controller    
public class BizTweetsController {
	
	@Autowired
	private BizTweetsService bizTweetsService;
	
    @RequestMapping(value = "/getEntities", method = RequestMethod.GET)  
    @ResponseBody
	public Iterable<String> getEntities() {  
		return bizTweetsService.getEntiities();  
    }
    
    @RequestMapping(value = "/follow", method = RequestMethod.POST)  
    @ResponseBody
	public Iterable<String> follow(@RequestParam("entity") String entity, @RequestParam("user") String user) {  
		return bizTweetsService.getEntiities();  
    }
    
    /*@RequestMapping(value = "/getTweets", method = RequestMethod.GET)  
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
    }*/
    
}
