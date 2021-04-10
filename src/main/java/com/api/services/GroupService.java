package com.api.services;

import com.api.entities.Group;

public interface GroupService {

	public Iterable<Group> findAll();

	public Group findById(int id);

	public Group save(Group user);

	public boolean delete(int id);

}
