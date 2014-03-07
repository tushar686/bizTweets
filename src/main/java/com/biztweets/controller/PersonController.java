package com.biztweets.controller;

import java.net.UnknownHostException;

import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;

import com.biztweets.formatter.JSONResultHandler;
import com.biztweets.model.Person;
import com.biztweets.service.PersonService;
import com.mongodb.DB;
import com.mongodb.MongoClient;
   
@Controller    
public class PersonController {  
   
	@Autowired
	private PersonService personService;
	
    @RequestMapping(value = "/person", method = RequestMethod.GET)  
	public String getPersonList(ModelMap model) {  
        model.addAttribute("personList", personService.listPerson());  
        return "output";  
    }  
    
    @RequestMapping(value = "/cityloc", method = RequestMethod.GET)  
	public String getCitylocList(ModelMap model) {  
    	DB db;
		try {
			db = new MongoClient().getDB("cityloc");
			Jongo jongo = new Jongo(db);
			//MongoCollection zips = jongo.getCollection("zips");
			//Iterable<String> documentsAsJson = zips.find().skip(new Long(zips.count()-100).intValue()).map(new JSONResultHandler());
			
			MongoCollection persons = jongo.getCollection("person");
			Iterable<String> documentsAsJson = persons.find().map(new JSONResultHandler());
			
			
			model.addAttribute("zips", documentsAsJson);  
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "zips";  
    }
    
    @RequestMapping(value = "/person/save", method = RequestMethod.POST)  
	public View createPerson(@ModelAttribute Person person, ModelMap model) {
    	if(StringUtils.hasText(person.getId())) {
    		personService.updatePerson(person);
    	} else {
    		personService.addPerson(person);
    	}
    	return new RedirectView("/bizTweets/person");  
    }
        
    @RequestMapping(value = "/person/delete", method = RequestMethod.GET)  
	public View deletePerson(@ModelAttribute Person person, ModelMap model) {  
        personService.deletePerson(person);  
        return new RedirectView("/bizTweets/person");  
    }    
}
