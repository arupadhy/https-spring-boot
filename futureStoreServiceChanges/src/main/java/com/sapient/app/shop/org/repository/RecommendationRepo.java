package com.sapient.app.shop.org.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.sapient.app.shop.org.model.Recommendation;

public interface RecommendationRepo extends CrudRepository<Recommendation, ObjectId>{
	
	Recommendation findByPersonId(String userId);
}
