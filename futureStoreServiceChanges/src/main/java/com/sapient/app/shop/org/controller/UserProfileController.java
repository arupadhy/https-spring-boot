package com.sapient.app.shop.org.controller;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.app.shop.org.model.History;
import com.sapient.app.shop.org.model.UserProfile;
import com.sapient.app.shop.org.repository.UserProfileRespository;

@RestController
@RequestMapping("/data-mgr/v1")
public class UserProfileController {
	
	@Autowired
	private UserProfileRespository userProfilesRepo;
	
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public ResponseEntity<UserProfile> createUserProfile(@RequestBody UserProfile profile) {
		profile.setTimestamp(new Date());
		return new ResponseEntity<UserProfile>(userProfilesRepo.save(profile), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/user",method=RequestMethod.GET)
	public ResponseEntity<Iterable<UserProfile>> getUsers() {
		return new ResponseEntity<Iterable<UserProfile>>(userProfilesRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/user",method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteAll() {
		userProfilesRepo.deleteAll();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/history/{id}",method=RequestMethod.PATCH)
	public ResponseEntity<UserProfile> updateHistoryForUser(@RequestBody History history,@PathVariable String id) {
		UserProfile profile = userProfilesRepo.findOne(new ObjectId(id));
		if(profile == null) {
			throw new IllegalArgumentException(String.format("UserProfile with %s does not exist", id));
		}
		history.setTimestamp(new Date());
		profile.getHistory().add(history);
		return new ResponseEntity<UserProfile>(userProfilesRepo.save(profile), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public ResponseEntity<UserProfile> userProfile(@PathVariable String id) {
		return new ResponseEntity<UserProfile>(userProfilesRepo.findOne(new ObjectId(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteUser(@PathVariable String id) { 
		userProfilesRepo.delete(new ObjectId(id));
		return new ResponseEntity<Boolean>(true,HttpStatus.OK);
	}
	

}
