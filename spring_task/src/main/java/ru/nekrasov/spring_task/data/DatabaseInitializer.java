package ru.nekrasov.spring_task.data;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.nekrasov.spring_task.model.User;;

@Component
public class DatabaseInitializer {
	@Autowired
	private UserRepository userRepo;

	private String USERNAME = "admin";
	private String PASS = "123";

	@PostConstruct
	public void init() {
		User users = userRepo.findByName(USERNAME);
		if (users == null) {
			User user = new User();
			user.setName(USERNAME);
			try {
				// user.setHashPass(sha256Transform(PASS));
				user.setHashPass(PASS);
				// } catch (NoSuchAlgorithmException e) {
				// user.setHashPass(PASS);
			} finally {
				userRepo.save(user);
			}
		}
	}
}
