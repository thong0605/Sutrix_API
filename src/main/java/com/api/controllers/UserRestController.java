package com.api.controllers;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.api.entities.User;
import com.api.services.MailServices;
import com.api.services.UserService;
import com.api.validations.OnUpdate;
import com.api.validations.RecordNotFoundException;

@RestController
@RequestMapping("api/user")
public class UserRestController {

	@Autowired
	private UserService userService;

	@Autowired
	private MailServices mailServices;

	@RequestMapping(value = "login", method = RequestMethod.POST, consumes = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	public ResponseEntity<Object> login(@Valid @RequestBody User user) {
		// Find user by email
		User currentUser = userService.findByEmail(user.getEmail());
		// Check if email exists and the user is active plus not deleted
		if (currentUser != null && currentUser.getIsActive().equals("Y") && currentUser.getIsDeleted().equals("N")) {
			// Encrypt input password
			String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

			// Check if passwords match
			if (encodedPassword.equals(currentUser.getPassword())) {
				return new ResponseEntity<>("Logged in as user [" + user.getEmail() + "]", HttpStatus.OK);
			}
		}

		return new ResponseEntity<>("Email or Password does not match", HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "findAll", method = RequestMethod.GET, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Object> findAll() {
		Iterable<User> list = userService.findAll();
		if (list != null) {
			return new ResponseEntity<Object>(list, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No user found", HttpStatus.OK);

	}

	@RequestMapping(value = "create", method = RequestMethod.POST, consumes = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<User> create(@Valid @RequestBody User user) {
		// Encrypted Password
		String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
		user.setPassword(encodedPassword);
		// Set default values
		user.setIsActive("Y");
		user.setIsDeleted("N");

		userService.save(user);
		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@GetMapping(value = "findById/{id}", produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	public ResponseEntity<User> findById(@PathVariable("id") int id) {

		User user = userService.findById(id);

		if (user == null) {
			throw new RecordNotFoundException("Invalid employee id : " + id);
		}

		// Load related groups
		// if()

		return new ResponseEntity<User>(user, HttpStatus.OK);

	}

	@RequestMapping(value = "update", method = RequestMethod.PUT, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	public ResponseEntity<Object> update(@Valid @RequestBody User user) {

		// Create new user entity and find new user by the old user's id
		User newUser = userService.findByEmail(user.getEmail());

		if (newUser != null) {
			// Replace new inputs to new user's values
			newUser.setFirstName(user.getFirstName());
			newUser.setLastName(user.getLastName());
			newUser.setIsActive(user.getIsActive());

			// Save updated user
			userService.save(newUser);

			return new ResponseEntity<Object>(newUser, HttpStatus.OK);
		}
		return new ResponseEntity<>("User " + user.getEmail() + " not found", HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	public ResponseEntity<Object> delete(@PathVariable("id") int id) {

		// Find user by id
		User user = userService.findById(id);
		if (user != null) {
			user.setIsDeleted("Y");
			userService.save(user);
			return new ResponseEntity<Object>("User " + id + " is deleted", HttpStatus.OK);
		}
		return new ResponseEntity<>("User " + id + " is not found", HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "search", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Accept=application/json")
	public ResponseEntity<Object> search(@RequestBody String keyword) {
		try {
			List<User> users = userService.search(keyword);
			if (!users.isEmpty()) {
				return new ResponseEntity<Object>(userService.search(keyword), HttpStatus.OK);
			}

			return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);

		} catch (Exception e) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	@Validated({ OnUpdate.class })
	public ResponseEntity<Object> changepassword(@Valid @RequestBody User user, HttpSession session) {

		// Find user by email
		User user_in_db = userService.findByEmail(user.getEmail());

		if (user_in_db != null && user_in_db.getIsActive().equals("Y")) {

			// Encode input password and check if the password matches
			String encodedPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());

			// Check if the inputed password matches the user's password
			if (encodedPassword.equals(user_in_db.getPassword())) {
				try {
					String url = "localhost:9596/api/user/resetpassword";
					// Set content
					String content = "Hi " + user.getEmail()
							+ ", we have received your changing password request. Please click on the link to continue the process: <a href="
							+ url + ">Click me</a>.";
					// Send mail to user
					mailServices.send(user.getEmail(), "thong.npm@sutrixsolutions.com", "Request to change password",
							"Hi " + content);
				} catch (Exception e) {
					return new ResponseEntity<>("Cannot send mail", HttpStatus.CONFLICT);
				}
				// Set a new session direct to resetpassword
				session.setAttribute("changepassword", user_in_db);
			}

			return new ResponseEntity<>("Check your email Inbox", HttpStatus.OK);
		}

		// If the user does not exists, return a 404 error
		return new ResponseEntity<>("Email or password does not match", HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "resetpassword", method = RequestMethod.POST, produces = {
			MimeTypeUtils.APPLICATION_JSON_VALUE }, consumes = {
					MimeTypeUtils.APPLICATION_JSON_VALUE }, headers = "Content-type=application/json")
	public ResponseEntity<Object> resetpassword(@Valid @RequestBody User user, HttpSession session) {

		// Check existed session and current user
		User currentUser = (User) session.getAttribute("changepassword");

		if (currentUser != null) {

			// Encoded new password
			String newPassword = Base64.getEncoder().encodeToString(user.getPassword().getBytes());
			currentUser.setPassword(newPassword);

			// Save user with new password
			userService.save(currentUser);

			// Remove changepassword session
			session.removeAttribute("changepassword");
			return new ResponseEntity<>("Password changed successfully", HttpStatus.OK);
		}
		return new ResponseEntity<>("Something's wrong. Cannot continue the process", HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
