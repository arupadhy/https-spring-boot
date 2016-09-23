package com.sapient.app.shop.org.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.sapient.app.shop.org.model.UserProfile;

public interface UserProfileRespository extends CrudRepository<UserProfile, ObjectId>{

}
