package com.medical.api.service.impl;

import com.medical.api.dao.request.SignUpRequest;
import com.medical.api.dao.request.SignInRequest;
import com.medical.api.dao.response.JwtAuthenticationResponse;
import com.medical.api.entities.Role;
import com.medical.api.entities.User;
import com.medical.api.exception.EmailAlreadyExistException;
import com.medical.api.exception.UsernameAlreadyExistException;
import com.medical.api.repository.UserRepository;
import com.medical.api.service.AuthenticationService;
import com.medical.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signup(SignUpRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new EmailAlreadyExistException();
        }
        existingUser = userRepository.findByUsername(request.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new UsernameAlreadyExistException();
        }
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER).build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
