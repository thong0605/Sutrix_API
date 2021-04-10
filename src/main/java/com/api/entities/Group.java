package com.api.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "group")
public class Group implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int Id;

	@NotEmpty(message = "Name is required")
	@Size(min = 2, message = "Name should have atleast or more than 2 characters")
	@Column(name = "name", unique = true, nullable = false)
	private String Name;

	@NotEmpty(message = "Description is required")
	@Column(name = "description", nullable = false)
	private String Description;

	@Column(name = "is_deleted", nullable = false)
	private String IsDeleted;

	@OneToMany(mappedBy = "Group")
	private Set<UserGroup> userGroup = new HashSet<UserGroup>(0);

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(int id,
			@NotEmpty(message = "Name is required") @Size(min = 2, message = "Name should have atleast or more than 2 characters") String name,
			@NotEmpty(message = "Description is required") String description, String isDeleted) {
		super();
		Id = id;
		Name = name;
		Description = description;
		IsDeleted = isDeleted;
	}

	public Group(int id,
			@NotEmpty(message = "Name is required") @Size(min = 2, message = "Name should have atleast or more than 2 characters") String name,
			@NotEmpty(message = "Description is required") String description, String isDeleted,
			Set<UserGroup> userGroup) {
		super();
		Id = id;
		Name = name;
		Description = description;
		IsDeleted = isDeleted;
		this.userGroup = userGroup;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		IsDeleted = isDeleted;
	}

	public Set<UserGroup> getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Set<UserGroup> userGroup) {
		this.userGroup = userGroup;
	}

}
