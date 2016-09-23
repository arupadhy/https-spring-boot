package com.sapient.app.shop.org.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.app.shop.org.model.Recommendation;
import com.sapient.app.shop.org.repository.RecommendationRepo;

@RestController
@RequestMapping("/data-mgr/v1")
public class RecommendationController {
	
	@Autowired
	private RecommendationRepo recommendationRepo;

	@RequestMapping(value="/recommendation/{userId}",method=RequestMethod.GET)
	public ResponseEntity<Recommendation> recommendation(@PathVariable String userId) {
		return new ResponseEntity<Recommendation>(recommendationRepo.findByPersonId(userId),HttpStatus.OK);
	}
	
	@RequestMapping(value="/recommendation",method=RequestMethod.POST)
	public ResponseEntity<String> saveRecommendation(@RequestBody Recommendation recommendation) {
		if(StringUtils.isEmpty(recommendation.getPersonId())) {
			throw new IllegalArgumentException(String.format("PersonId must be set in the recommendation", ""));
		}
		recommendation.setTimestamp(new Date().toString());
		return new ResponseEntity<String>(recommendationRepo.save(recommendation).getRecommendationId(), HttpStatus.CREATED);
	}
}
