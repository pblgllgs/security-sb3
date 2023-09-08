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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RequiredArgsConstructor
public class SecurityApplication {

    private static final Random RANDOM = new Random();
    private final UserRepository userRepository;
    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private List<String> allowedOrigins;
    @Value("#{'${cors.allowed-methods}'.split(',')}")
    private List<String> allowedMethods;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private List<String> allowedHeaders;
    @Value("#{'${cors.exposed-headers}'.split(',')}")
    private List<String> exposedHeaders;

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    private static void generateCustomers(int quantity, CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        for (int i = 0; i < quantity; i++) {
            Faker faker = new Faker();
            Name name = faker.name();
            String firstName = name.firstName();
            String lastName = name.lastName();
            int age = RANDOM.nextInt(1, 100);
            Gender gender = age % 2 == 0 ? Gender.MALE : Gender.FEMALE;
            Customer customer = new Customer(
                    firstName + " " + lastName,
                    firstName.toLowerCase() + "." + lastName.toLowerCase() + "@gmail.com",
                    passwordEncoder.encode(UUID.randomUUID().toString()), age,
                    gender);
            customerRepository.save(customer);
        }

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        configuration.setAllowedHeaders(allowedHeaders);
        configuration.setExposedHeaders(exposedHeaders);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

    @Bean
    CommandLineRunner runner(
            CustomerRepository customerRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            generateCustomers(10, customerRepository, passwordEncoder);
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

}
