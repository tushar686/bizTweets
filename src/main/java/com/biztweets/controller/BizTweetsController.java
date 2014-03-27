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

import com.biztweets.model.Follow;
import com.biztweets.model.Tweets;
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
    
    @RequestMapping(value = "/getUserFollowing", method = RequestMethod.GET)  
    @ResponseBody
	public List<Users> getUserFollowingEntities(@RequestParam String user) {  
    	return bizTweetsService.getUserFollowingEntities(user);
    }
    
    @RequestMapping(value = "/follow", method = RequestMethod.POST)  
    @ResponseStatus(HttpStatus.CREATED)
	public void follow(@RequestBody Users follow) {  
    	bizTweetsService.saveFollow(follow);
    }
    
    /*@RequestMapping(value = "/unfollow", method = RequestMethod.DELETE)  
    @ResponseStatus(HttpStatus.NO_CONTENT)
	public void unfollow(@RequestParam String user, @RequestParam String unfollowingEntity) { 
    	bizTweetsService.deleteFollow(new Users(user, unfollowingEntity));
    }*/
       
    @RequestMapping(value = "/searchTweets", method = RequestMethod.GET)  
    @ResponseBody
	public List<Iterable<Tweets>> searchTweets(@RequestParam String user, @RequestParam String searchString, @RequestParam int cursor, @RequestParam String queryType) {  
		return bizTweetsService.searchTweets(user, cursor, searchString, queryType);  
    }
    
    @RequestMapping(value = "/getTweets", method = RequestMethod.GET)  
    @ResponseBody
	public List<Iterable<Tweets>> getTweets(@RequestParam String user, @RequestParam int cursor) {  
		return bizTweetsService.getTweets(user, cursor);  
    }
        
}
