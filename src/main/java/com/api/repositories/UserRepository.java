package com.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.entities.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {

	@Query(value = "select * from user where email like %:keyword% or first_name like %:keyword% or last_name like %:keyword%", nativeQuery = true)
	public List<User> search(@Param("keyword") String keyword);
	
	@Query("from User where email =:email")
	public User findByEmail(@Param("email") String email);
}
