package com.hrapp.global.config;

import lombok.extern.log4j.Log4j2;
import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import com.hrapp.global.repository.AuthorityRepo;
import com.hrapp.global.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Set;

@Component
@Log4j2
public class DataLoader implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthorityRepo authorityRepository;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running DataLoader...");

		// Check if any users already exist
		long userCount = userRepository.count();
		if (userCount > 0) {
			log.info("Users already exist in the database. No users will be created.");
			return;
		}

		log.info("No users found in the database. Creating default users...");

		// Create roles
		Authority roleAdmin = authorityRepository.findById("ROLE_ADMIN").orElse(new Authority("ROLE_ADMIN"));
		Authority roleUser = authorityRepository.findById("ROLE_USER").orElse(new Authority("ROLE_USER"));
		authorityRepository.save(roleAdmin);
		authorityRepository.save(roleUser);

		// Create admin users
		createUser("admin1", "Admin", "One", "admin1@example.com", "AdminAdmin@123", Set.of(roleAdmin));
		createUser("admin2", "Admin", "Two", "admin2@example.com", "AdminAdmin@123", Set.of(roleAdmin));

		// Create regular users
		createUser("user1", "User", "One", "user1@example.com", "AdminAdmin@123", Set.of(roleUser));
		createUser("user2", "User", "Two", "user2@example.com", "AdminAdmin@123", Set.of(roleUser));

		log.info("Default users created successfully.");
	}

	private void createUser(String login, String firstName, String lastName, String email, String password, Set<Authority> authorities) {
		User user = new User();
		user.setLogin(login);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.setCreatedBy("system");
		user.setActivated(true);
		user.setAuthorities(authorities);

		userRepository.save(user);
		log.info("User '{}' created with roles: {}", login, authorities);
	}
}
