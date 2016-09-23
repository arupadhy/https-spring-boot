package com.sapient.app.shop.org.controller;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.app.shop.org.model.Question;
import com.sapient.app.shop.org.repository.QuestionProfileRepository;

@RestController
@RequestMapping("/data-mgr/v1")
public class QuestionsController {

	@Autowired
	private QuestionProfileRepository questionProfileRepo;
	
	@RequestMapping(value="/question",method=RequestMethod.POST)
	public ResponseEntity<Question> addQuestion(@RequestBody Question question) {
		return new ResponseEntity<Question>(questionProfileRepo.save(question), HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/question/{id}",method=RequestMethod.GET)
	public ResponseEntity<Question> question(@PathVariable String id) {
		return new ResponseEntity<Question>(questionProfileRepo.findOne(new ObjectId(id)), HttpStatus.OK);
	}
	
	@RequestMapping(value="/question/excel/{id}",method=RequestMethod.GET)
	public ResponseEntity<Question> questionByXlId(@PathVariable String id) {
		return new ResponseEntity<Question>(questionProfileRepo.xlId(id), HttpStatus.OK);
	}
	
	@RequestMapping(value="/question",method=RequestMethod.GET)
	public ResponseEntity<Iterable<Question>> questions() {
		return new ResponseEntity<Iterable<Question>>(questionProfileRepo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/question/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> addQuestion(@PathVariable String id) {
		questionProfileRepo.delete(new ObjectId(id));
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
	
	@RequestMapping(value="/admin/question",method=RequestMethod.DELETE)
	public ResponseEntity<Boolean> deleteQuestions() {
		questionProfileRepo.deleteAll();
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}
}
