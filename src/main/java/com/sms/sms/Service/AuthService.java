package com.sms.sms.Service;

import com.sms.sms.DTO.AuthRequest;
import com.sms.sms.DTO.AuthResponse;
import com.sms.sms.DTO.RegisterRequest;
import com.sms.sms.Entity.Role;
import com.sms.sms.Entity.User;
import com.sms.sms.Repository.UserRepository;
import com.sms.sms.Security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthResponse register(RegisterRequest request) {
        validateUniqueUser(request);

        if (request.isPrivilegedRoleRequested()) {
            throw new IllegalArgumentException("Public registration can only create PARENT accounts");
        }

        User savedUser = createUser(request, Role.PARENT);

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, savedUser.getUsername(), savedUser.getRole().name());
    }

    public AuthResponse provisionUser(RegisterRequest request) {
        if (request.getRole() == null) {
            throw new IllegalArgumentException("Role is required for admin provisioning");
        }

        validateUniqueUser(request);

        User savedUser = createUser(request, request.getRole());

        UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, savedUser.getUsername(), savedUser.getRole().name());
    }

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, user.getUsername(), user.getRole().name());
    }

    private void validateUniqueUser(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
    }

    private User createUser(RegisterRequest request, Role role) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        return userRepository.save(user);
    }
}
