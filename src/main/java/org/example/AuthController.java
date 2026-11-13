package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    // 1. REGISTER: Hashes password with BCrypt and saves user to PostgreSQL
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new ResponseEntity<>("Username already taken!", HttpStatus.BAD_REQUEST);
        }

        // Default to ROLE_USER if role not provided
        String role = (request.getRole() != null && !request.getRole().isEmpty())
                ? request.getRole()
                : "ROLE_USER";

        // Hash the password with BCrypt!
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User(0, request.getUsername(), hashedPassword, role);
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully as " + role + "!", HttpStatus.CREATED);
    }

    // 2. LOGIN: Validates credentials and returns JWT token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // Authenticate credentials against PostgreSQL
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        // Fetch user to retrieve role
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Generate the JWT token!
        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity.ok(new AuthResponse(token, user.getUsername(), "Login successful!"));
    }
}