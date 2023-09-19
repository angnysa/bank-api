package org.angnysa.bank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// TODO Configure Spring Security to use the company's Authn provider (OAuth, AD, etc)
@SpringBootApplication
//@EnableWebSecurity
@EnableJpaRepositories
public class BankServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BankServiceApplication.class, args);
    }


}
