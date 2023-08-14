package com.pblgllgs.spring.security;

import com.github.javafaker.Faker;
import com.github.javafaker.Name;
import com.pblgllgs.spring.security.entity.Customer;
import com.pblgllgs.spring.security.entity.Gender;
import com.pblgllgs.spring.security.repository.CustomerRepository;
import com.pblgllgs.spring.security.repository.UserRepository;
import com.pblgllgs.spring.security.user.Role;
import com.pblgllgs.spring.security.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class SecurityApplication {

	private final UserRepository userRepository;
	private static final Random RANDOM = new Random();

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(SecurityApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
						.allowedOrigins("http://localhost:5173/");
			}
		};
	}

	@Bean
	CommandLineRunner runner(
			CustomerRepository customerRepository,
			PasswordEncoder passwordEncoder
	) {
		return args -> {
			generateCustomers(10,customerRepository, passwordEncoder);
			User user = User.builder()
					.firstName("user")
					.lastName("user")
					.email("pbl.gllgs@gmail.com")
					.password(passwordEncoder.encode("12345"))
					.role(Role.USER)
					.build();
			userRepository.save(user);
		};
	}

	private static void generateCustomers(int quantity, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
		for (int i = 0; i < quantity; i++) {
			Faker faker = new Faker();
			Name name = faker.name();
			String firstName = name.firstName();
			String lastName = name.lastName();
			int age = RANDOM.nextInt(1, 100);
			Gender gender = age %2 == 0 ? Gender.MALE : Gender.FEMALE;
			Customer customer = new Customer(
					firstName + " " +lastName,
					firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com",
					passwordEncoder.encode(UUID.randomUUID().toString()), age,
					gender);
			customerRepository.save(customer);
		}

	}

}
