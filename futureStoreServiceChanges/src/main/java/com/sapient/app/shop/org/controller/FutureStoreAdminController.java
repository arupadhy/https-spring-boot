package com.sapient.app.shop.org.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.FindIterable;
import com.sapient.app.shop.org.loader.DataLoader;
import com.sapient.app.shop.org.loader.QuestionLoader;
import com.sapient.app.shop.org.model.Question;
import com.sapient.app.shop.org.repository.MongoDbRepository;
import com.sapient.app.shop.org.repository.ProductProfileRepository;
import com.sapient.app.shop.org.repository.QuestionProfileRepository;

/**
 * To be used only for admin stuff
 */
@RestController
@RequestMapping("/futureStore/admin/v1")
public class FutureStoreAdminController {

	@Autowired
	private MongoDbRepository mongoDbRepo;

	@Autowired
	private ProductProfileRepository productProfilesRepo;
	
	@Autowired
	private QuestionProfileRepository questionProfileRepo;
	
	@Autowired
	private DataLoader dataLoader;
	
	@Autowired
	private QuestionLoader questionsLoader;

	@RequestMapping(value = "/{database}/collection/{name}", method = RequestMethod.PUT)
	public ResponseEntity<String> createcollection(@PathVariable String database, @PathVariable String name) {
		mongoDbRepo.createCollection(name, database);
		return new ResponseEntity<String>("Created", HttpStatus.CREATED);
	}

	@RequestMapping(value = "/{database}/collection/{name}/documents", method = RequestMethod.GET)
	public ResponseEntity<FindIterable<Document>> getAllDocuments(@PathVariable String database,
			@PathVariable String name) {
		return new ResponseEntity<FindIterable<Document>>(mongoDbRepo.getAllTables(name, database), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/load/questions", method = RequestMethod.GET)
	public ResponseEntity<String> loadQuestions() {
		List<String> ids = new ArrayList<>();
		List<Question> qs = questionsLoader.loadAllQuestions();
		questionProfileRepo.save(qs);
		questionProfileRepo.findAll().forEach(q -> ids.add(q.getId()));
		return new ResponseEntity<String>(
				String.format("%s open questions loaded from the xl. %s IDs of the new products are: %s",
						questionProfileRepo.count(), System.lineSeparator(),ids),
				HttpStatus.OK);
	}


	@RequestMapping(value = "/load/products", method = RequestMethod.GET)
	public ResponseEntity<String> loadProducts() {
		dataLoader.loadXl();
		List<String> ids = new ArrayList<>();
		productProfilesRepo.findAll().forEach(p -> {
			ids.add(p.getId());
		});
		return new ResponseEntity<String>(
				String.format("%s products loaded from the xl. %s IDs of the new products are: %s",
						productProfilesRepo.count(), System.lineSeparator(), ids),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/products", method = RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteProducts() {
		productProfilesRepo.deleteAll();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
}
