package com.api.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UserGroupId implements Serializable {

	@Column(name = "user_id", nullable = false)
	private int UserId;

	@Column(name = "group_id", nullable = false)
	private int GroupId;

	public UserGroupId() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserGroupId(int userId, int groupId) {
		super();
		UserId = userId;
		GroupId = groupId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getGroupId() {
		return GroupId;
	}

	public void setGroupId(int groupId) {
		GroupId = groupId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + GroupId;
		result = prime * result + UserId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserGroupId other = (UserGroupId) obj;
		if (GroupId != other.GroupId)
			return false;
		if (UserId != other.UserId)
			return false;
		return true;
	}

}
