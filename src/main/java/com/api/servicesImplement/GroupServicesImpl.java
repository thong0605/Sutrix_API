package com.api.servicesImplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Group;
import com.api.repositories.GroupRepository;
import com.api.services.GroupService;

@Service("groupService")
public class GroupServicesImpl implements GroupService {

	@Autowired
	private GroupRepository groupRepository;

	@Override
	public Group save(Group group) {
		return groupRepository.save(group);
	}

	@Override
	public Iterable<Group> findAll() {
		return groupRepository.findAll();
	}

	@Override
	public Group findById(int id) {
		return groupRepository.findById(id).get();
	}

	@Override
	public boolean delete(int id) {
		try {
			groupRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
}
