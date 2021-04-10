package com.api.services;

import java.util.List;

import com.api.entities.User;

public interface UserService {
	
	public Iterable<User> findAll();
	
	public User findById(int id);
	
	public User save(User user);
	
	public boolean delete(int id);
	
	public List<User> search(String keyword);
	
	public User findByEmail(String email);

}
