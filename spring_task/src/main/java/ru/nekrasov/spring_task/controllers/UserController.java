package ru.nekrasov.spring_task.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.nekrasov.spring_task.data.UserRepository;
import ru.nekrasov.spring_task.model.User;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepo;
	
	@PostMapping
	public Map<String,Object> addUser(@RequestBody Map<String, String> userParam) {
		Map<String,Object> result = new HashMap<String, Object>();
		boolean noError = true;
		String errorText = null;
		
		if (!userParam.containsKey("username") || userParam.get("username").equals("") ||
			!userParam.containsKey("password") || userParam.get("password").equals("") ||
			!userParam.containsKey("email") || userParam.get("email").equals(""))
			noError = false;
		else {
			String username = userParam.get("username");
			String password = userParam.get("password");
			String email = userParam.get("email");
			
			if (userRepo.findByName(username) != null) {
				noError = false;
				errorText = "Username";
			}else if (userRepo.findByEmail(email) != null) {
				noError = false;
				errorText = "Email";
			} else {
				User user = new User();
				user.setName(username);
				user.setHashPass(password);
				user.setEmail(email);
				userRepo.save(user);
			}
		}
		result.put("hasError", !noError);
		if (errorText != null) result.put("errorText", errorText);
		return result;
	}
	
	public class EmbeddedUser {
		private Integer id;
		private String username;
		private String email;
		
		public EmbeddedUser(User user) {
			id = user.getId();
			username = user.getName();
			email = user.getEmail();
		}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	@GetMapping
	public List<EmbeddedUser> getUsers(){
		List<EmbeddedUser> result = new ArrayList<>();
		
		for (User a: userRepo.findAll())
			result.add(new EmbeddedUser(a));
		return result;
	}
}
