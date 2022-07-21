package com.webkop.coffeshopweb;

import com.webkop.coffeshopweb.models.Category;
import com.webkop.coffeshopweb.models.Coffe;
import com.webkop.coffeshopweb.models.Role;
import com.webkop.coffeshopweb.models.User;
import com.webkop.coffeshopweb.services.categories.CategoryService;
import com.webkop.coffeshopweb.services.coffees.CoffeService;
import com.webkop.coffeshopweb.services.users.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;

@SpringBootApplication
public class CoffeshopwebApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoffeshopwebApplication.class, args);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOriginPatterns("*").allowCredentials(true);
			};
		};
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	CommandLineRunner run(UserService userService, CategoryService categoryService, CoffeService coffeService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.saveUser(
					new User(
							null,
							"Fazri Ridwan",
							"fazri",
							"fazri",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent finibus.",
							new ArrayList<>()
					)
			);
			userService.saveUser(
					new User(
							null,
							"Hendri Fuad",
							"hendri",
							"123",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent finibus.",
							new ArrayList<>()
					)
			);
			userService.saveUser(
					new User(
							null,
							"Ursklap Urs",
							"ursklap",
							"123",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent finibus.",
							new ArrayList<>()
					)
			);

			userService.addRoleToUser("fazri", "ROLE_USER");
			userService.addRoleToUser("fazri", "ROLE_ADMIN");
			userService.addRoleToUser("hendri", "ROLE_USER");
			userService.addRoleToUser("ursklap", "ROLE_USER");

			Category arabica = categoryService.createCategory(
					new Category(
							null,
							"Arabica",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris imperdiet metus at sapien efficitur ultrices."
					)
			);
			Category robusta = categoryService.createCategory(
					new Category(
							null,
							"Robusta",
							"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris imperdiet metus at sapien efficitur ultrices."
					)
			);

			coffeService.createCoffe(
					new Coffe(null, "Black webkop special bean", 25000.0, 10, arabica)

			);
			coffeService.createCoffe(
					new Coffe(null, "White webkop special bean", 30000.0, 5, arabica)

			);
			coffeService.createCoffe(
					new Coffe(null, "Torabika dark beans", 10000.0, 10, robusta)

			);
		};
	}
}
