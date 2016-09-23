package com.sapient.app.shop.org.repository;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.sapient.app.shop.org.model.ProductProfile;

@Repository
public class MongoDbRepository {

	@Autowired
	private MongoClient mongoClient;
	
	@Autowired
	private ProductProfileRepository productProfileRepository;
	
	public void createCollection(String collection,String database) {
		mongoClient.getDatabase(database).createCollection(collection);
	}
	
	public FindIterable<Document> getAllTables(String collection,String database) {
		MongoCollection<Document> docs =  mongoClient.getDatabase(database).getCollection(collection);
		return docs.find();
	}
	
	public Long insertProductProfile(ProductProfile profile) {
		productProfileRepository.save(profile);
		return System.currentTimeMillis();
	}
	
}
