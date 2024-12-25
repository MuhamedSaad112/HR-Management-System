package com.hrapp.global.config;

import com.hrapp.global.entity.Authority;
import com.hrapp.global.entity.User;
import com.hrapp.global.repository.AuthorityRepo;
import com.hrapp.global.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AuthorityRepo authorityRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if the database is empty
        if (userRepository.count() == 0) {
            // Create 'ROLE_ADMIN' role if it doesn't exist
            Authority adminRole = authorityRepo.findById("ROLE_ADMIN").orElseGet(() -> {
                Authority newRole = new Authority();
                newRole.setName("ROLE_ADMIN");
                authorityRepo.save(newRole);
                return newRole;
            });

            // Create 'ROLE_USER' role if it doesn't exist
            Authority userRole = authorityRepo.findById("ROLE_USER").orElseGet(() -> {
                Authority newRole = new Authority();
                newRole.setName("ROLE_USER");
                authorityRepo.save(newRole);
                return newRole;
            });

            // Create 2 admin users and assign the 'ROLE_ADMIN' role
            User admin1 = createUser("admin1", "1a41c06ae2@emailvb.pro", "Admin1", " ", "SecureAdmin1@2024", adminRole);
            User admin2 = createUser("admin2", "admin2@example.com", "Admin2", " ",  "StrongPass2@2024", adminRole);

            // Create 2 regular users and assign the 'ROLE_USER' role
            User user1 = createUser("user1", "e6eb8b9fab@emailvb.pro", "User1", " ", "RegularUser1@2024", userRole);
            User user2 = createUser("user2", "user2@example.com", "User2", " ", "NormalUser2@2024", userRole);

            // Save all created users into the database
            userRepository.save(admin1);
            userRepository.save(admin2);
            userRepository.save(user1);
            userRepository.save(user2);

            // Print a success message
            System.out.println("Admin and user accounts created successfully");
        }
    }

    // Helper method to create a user and assign a role to them
    private User createUser(String login, String email, String firstName, String lastName, String password, Authority authority) {
        User user = new User();
        user.setLogin(login);
        user.setEmail(email);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPassword(passwordEncoder.encode(password)); // Encode the password
        user.setActivated(true); // Mark user as activated
        user.setResetKey("resetKey"); // Set a dummy reset key
        user.getAuthorities().add(authority); // Assign the provided role to the user
        return user;
    }
}