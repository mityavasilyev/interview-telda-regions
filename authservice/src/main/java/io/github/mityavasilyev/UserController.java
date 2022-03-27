package io.github.mityavasilyev;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class UserController {

    private final UserService service;

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return service.getUser(username);
    }
}
