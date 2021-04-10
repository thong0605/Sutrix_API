package com.api.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.api.entities.Group;

@Repository("groupRepository")
public interface GroupRepository extends CrudRepository<Group, Integer> {

//	@Query(value = "SELECT g.*, up.* " 
//	+ "FROM api_schema.group g, user_group up "
//			+ "WHERE up.user_id = :id AND up.group_id = g.id " 
//	+ "ORDER BY g.id DESC", nativeQuery = true)
//	public Group findByUserId(@Param("id") int id);
}
