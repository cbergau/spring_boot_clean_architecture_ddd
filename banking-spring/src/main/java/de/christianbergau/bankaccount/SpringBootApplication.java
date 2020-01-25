package de.christianbergau.bankaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@org.springframework.boot.autoconfigure.SpringBootApplication
@EnableJpaRepositories
public class SpringBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApplication.class, args);
    }
}
