package com.medical.api.controller;

import com.medical.api.domain.AppUser;
import com.medical.api.entities.User;
import com.medical.api.mapper.UserMapper;
import com.medical.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public AppUser getUser(@AuthenticationPrincipal User user) {
        return UserMapper.toAppUser(user);
    }

    @PostMapping("/username/{newUsername}")
    public void updateUserName(@AuthenticationPrincipal User user, @PathVariable String newUsername) {
        User newUser = user.toBuilder()
                .username(newUsername)
                .build();
        userRepository.save(newUser);
    }

    @PostMapping("/email/{newEmail}")
    public void updateEmail(@AuthenticationPrincipal User user, @PathVariable String newEmail) {
        User newUser = user.toBuilder()
                .email(newEmail)
                .build();
        userRepository.save(newUser);
    }

    @PostMapping("/password/{newPassword}")
    public void updatePassword(@AuthenticationPrincipal User user, @PathVariable String newPassword) {
        User newUser = user.toBuilder()
                .password(passwordEncoder.encode(newPassword))
                .build();
        userRepository.save(newUser);
    }
}
