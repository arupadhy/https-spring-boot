package com.sapient.app.shop.org.repository;

import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;

import com.sapient.app.shop.org.model.ProductProfile;

public interface ProductProfileRepository  extends CrudRepository<ProductProfile, ObjectId>{

}
