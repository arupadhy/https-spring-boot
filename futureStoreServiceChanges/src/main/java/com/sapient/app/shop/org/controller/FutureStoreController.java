
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
import com.sapient.app.shop.org.model.ProductProfile;
import com.sapient.app.shop.org.repository.ProductProfileRepository;

@RestController
@RequestMapping("/futureStore/v1")
public class FutureStoreController {
	
	@Autowired(required=true)
	private ProductProfileRepository productProfileRepo;
	
	@RequestMapping(value="/products/{id}",method=RequestMethod.GET)
	public ResponseEntity<ProductProfile> fetchProduct(@PathVariable String id) {
		return new ResponseEntity<ProductProfile>(productProfileRepo.findOne(new ObjectId(id)),HttpStatus.OK);
	}
	
	@RequestMapping(value="/products",method=RequestMethod.POST)
	public ResponseEntity<String> addProductProfile(@RequestBody ProductProfile productProfile) {
		return new ResponseEntity<String>(productProfileRepo.save(productProfile).getId(), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/products",method=RequestMethod.GET)
	public ResponseEntity<Iterable<ProductProfile>> getProducts() {
		return new ResponseEntity<Iterable<ProductProfile>>(productProfileRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/products/{id}",method=RequestMethod.POST)
	public ResponseEntity<String> updateHistoryForUser(@RequestBody History history,@PathVariable String id) {
		ProductProfile profile = productProfileRepo.findOne(new ObjectId(id));
		if(profile == null) {
			throw new IllegalArgumentException(String.format("UserProfile with %s does not exist", id));
		}
		history.setTimestamp(new Date());
		profile.getHistory().add(history);
		return new ResponseEntity<String>(productProfileRepo.save(profile).getId(), HttpStatus.CREATED);
	}
	


}
