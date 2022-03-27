package io.github.mityavasilyev;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    // Really basic implementation. No checks or validation
    public User saveUser(User user) {
        log.info("Saved user: {}", user);
        return repository.save(user);
    }

    public User getUser(String username) {
        return repository
                .findByUsername(username)
                .orElseThrow(() -> {
                    log.error("No user with username: {}", username);
                    throw new RuntimeException("No user found");
                });
    }
}
