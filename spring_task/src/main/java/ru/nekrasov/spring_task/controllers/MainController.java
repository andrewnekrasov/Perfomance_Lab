package ru.nekrasov.spring_task.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.nekrasov.spring_task.data.UserRepository;
import ru.nekrasov.spring_task.model.User;

@Controller
public class MainController {
	@Autowired
	private UserRepository userRepo;

	@PostMapping("/")
	public String addUser(@RequestParam String username, @RequestParam String email, @RequestParam String password, Model model) {
		if (userRepo.findByName(username) != null) {
			model.addAttribute("userExist", true);
			model.addAttribute("userExistText", "Username");
		} else if (userRepo.findByEmail(email) != null) {
			model.addAttribute("userExist", true);
			model.addAttribute("userExistText", "Email");
		} else {
			User user = new User();
			user.setName(username);
			user.setHashPass(password);
			user.setEmail(email);
			userRepo.save(user);
		}
		model.addAttribute("users", userRepo.findAll());
		return "index";
	}

	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("users", userRepo.findAll());
		return "index";
	}

	// @PostMapping("/login")
	// public String checkUser(@RequestParam(name = "login", required = true) String
	// login, @RequestParam(name = "password", required = true) String password,
	// @RequestParam(name = "remember", required = false) String remember) {
	// if (login.equals("123") && password.equals("123")) {
	// return "index";
	// }
	// return "login";
	// }

}
