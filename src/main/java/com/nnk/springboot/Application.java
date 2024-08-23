package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class Application implements CommandLineRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
	private static final String LOG_ID = "[Application]";

	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	public Application(UserService userService, PasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		LOGGER.info("{} - Creating user with ADMIN role...", LOG_ID);

		User admin = User.builder()
				.fullname("Administrator")
				.username("admin")
				.password(passwordEncoder.encode("admin"))
				.role("ROLE_ADMIN")
				.build();

		LOGGER.info("{} - Saving user ADMIN in database...", LOG_ID);
		userService.save(admin);

		LOGGER.info("{} - Creating user with USER role...", LOG_ID);

		User user = User.builder()
				.fullname("User")
				.username("user")
				.password(passwordEncoder.encode("user"))
				.role("ROLE_USER")
				.build();

		LOGGER.info("{} - Saving user USER in database...", LOG_ID);
		userService.save(user);

		LOGGER.info("{} - ADMIN & USER registration success", LOG_ID);
	}
}
