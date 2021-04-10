package com.api.entities;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "user_group")
public class UserGroup implements Serializable {

	@EmbeddedId
	private UserGroupId Id;

	@ManyToOne
	@MapsId("UserId")
	@JoinColumn(name = "user_id")
	private User User;

	@ManyToOne
	@MapsId("GroupId")
	@JoinColumn(name = "group_id")
	private Group Group;

	public UserGroup() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserGroup(UserGroupId id, User user, Group group) {
		super();
		Id = id;
		User = user;
		Group = group;
	}

	public UserGroupId getId() {
		return Id;
	}

	public void setId(UserGroupId id) {
		Id = id;
	}

	public User getUser() {
		return User;
	}

	public void setUser(User user) {
		User = user;
	}

	public Group getGroup() {
		return Group;
	}

	public void setGroup(Group group) {
		Group = group;
	}

}
