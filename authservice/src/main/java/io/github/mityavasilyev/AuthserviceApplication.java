package io.github.mityavasilyev;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class AuthserviceApplication implements CommandLineRunner {

    private final UserService service;

    public static void main(String[] args) {
        SpringApplication.run(AuthserviceApplication.class);
    }

    @Override
    public void run(String... args) throws Exception {
        service.saveUser(new User(1L, "admin", "root"));
    }
}
