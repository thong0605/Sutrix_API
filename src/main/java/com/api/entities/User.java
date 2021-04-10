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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.api.validations.OnUpdate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSetter;

@Entity
@Table(name = "user")
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int Id;

	@Email(message = "Email should be in a well-formed email address")
	@NotNull(message = "Email is required", groups = OnUpdate.class)
	@Column(name = "email", unique = true, nullable = false)
	private String Email;

	@NotNull(message = "Password is required", groups = OnUpdate.class)
	@Size(min = 2, message = "Password should have atleast or more than 8 characters")
	@Column(name = "password", nullable = false)
	private String Password;

	@NotEmpty(message = "Firstname is required")
	@Size(min = 2, message = "First Name should have atleast 2 characters")
	@Column(name = "first_name", nullable = false)
	private String FirstName;

	@NotEmpty(message = "LastName is required")
	@Size(min = 2, message = "Last Name should have atleast 2 characters")
	@Column(name = "last_name", nullable = false)
	private String LastName;

	@Column(name = "is_active", nullable = false)
	private String IsActive;

	@Column(name = "is_deleted", nullable = false)
	private String IsDeleted;

	@OneToMany(mappedBy = "User")
	private Set<UserGroup> userGroup = new HashSet<UserGroup>(0);

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User(int id,
			@Email(message = "Email should be in a well-formed email address") @NotEmpty(message = "Email is required") String email,
			@NotEmpty(message = "Password is required") @Size(min = 2, message = "Password should have atleast or more than 8 characters") String password,
			@NotEmpty(message = "Firstname is required") @Size(min = 2, message = "First Name should have atleast 2 characters") String firstName,
			@NotEmpty(message = "LastName is required") @Size(min = 2, message = "Last Name should have atleast 2 characters") String lastName,
			String isActive, String isDeleted) {
		super();
		Id = id;
		Email = email;
		Password = password;
		FirstName = firstName;
		LastName = lastName;
		IsActive = isActive;
		IsDeleted = isDeleted;
	}

	public User(int id,
			@Email(message = "Email should be in a well-formed email address") @NotEmpty(message = "Email is required") String email,
			@NotEmpty(message = "Password is required") @Size(min = 2, message = "Password should have atleast or more than 8 characters") String password,
			@NotEmpty(message = "Firstname is required") @Size(min = 2, message = "First Name should have atleast 2 characters") String firstName,
			@NotEmpty(message = "LastName is required") @Size(min = 2, message = "Last Name should have atleast 2 characters") String lastName,
			String isActive, String isDeleted, Set<UserGroup> userGroup) {
		super();
		Id = id;
		Email = email;
		Password = password;
		FirstName = firstName;
		LastName = lastName;
		IsActive = isActive;
		IsDeleted = isDeleted;
		this.userGroup = userGroup;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@JsonIgnore
	public String getPassword() {
		return Password;
	}

	@JsonSetter
	public void setPassword(String password) {
		Password = password;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getIsActive() {
		return IsActive;
	}

	public void setIsActive(String isActive) {
		IsActive = isActive;
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
