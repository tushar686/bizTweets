package com.biztweets.controller;

import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.biztweets.model.Users;
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
    
    @RequestMapping(value = "/getUserFollowingEntities", method = RequestMethod.GET)  
    @ResponseBody
	public List<String> getUserFollowingEntities(@RequestParam String user) {  
    	return bizTweetsService.getUserFollowingEntities(user);
    }
    
    @RequestMapping(value = "/follow", method = RequestMethod.POST)  
    @ResponseStatus(HttpStatus.CREATED)
	public void follow(@RequestBody Users follow) {  
    	bizTweetsService.saveFollow(follow);
    }
    
    @RequestMapping(value = "/unfollow", method = RequestMethod.DELETE)  
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void unfollow(@RequestParam String user, @RequestParam String unfollowingEntity) { 
    	bizTweetsService.deleteFollow(new Users(user, unfollowingEntity));
    }
    
    @RequestMapping(value = "/getTweets", method = RequestMethod.GET)  
    @ResponseBody
	public List<Iterable<String>> getTweets(@RequestParam String user, @RequestParam int cursor) {  
		return bizTweetsService.getTweets(user, cursor);  
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
