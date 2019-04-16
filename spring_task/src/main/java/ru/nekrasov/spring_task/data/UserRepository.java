package ru.nekrasov.spring_task.data;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.nekrasov.spring_task.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	User findByName(String name);

	User findByEmail(String email);

	User findByNameAndEmail(String name, String email);
}
