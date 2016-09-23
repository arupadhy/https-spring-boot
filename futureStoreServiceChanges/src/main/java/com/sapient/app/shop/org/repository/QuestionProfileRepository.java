package com.sapient.app.shop.org.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.sapient.app.shop.org.model.Question;

public interface QuestionProfileRepository extends CrudRepository<Question, ObjectId>{

	Question xlId(String xlId);
	
}
