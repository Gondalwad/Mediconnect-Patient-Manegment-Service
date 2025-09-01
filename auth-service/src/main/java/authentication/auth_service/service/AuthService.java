package authentication.auth_service.service;

import authentication.auth_service.dto.LoginRequestDTO;
import authentication.auth_service.model.User;
import authentication.auth_service.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // authenticate signin Request with password of user and db passowrd then genrate token
    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {

        Optional<User> user = userService.findByEmail(loginRequestDTO.getEmail());

        if (user.isEmpty()) {
            return Optional.empty();
        }

        // Check if password matches or not
        boolean password = passwordEncoder.matches(loginRequestDTO.getPassword(), user.get().getPassword());
        if (password) {
            return jwtUtil.generateToken(user.get().getEmail(), user.get().getRole()).describeConstable();
        }

        return Optional.empty();

    }

    public boolean validate(String token){
        return jwtUtil.validateToken(token);
    }
}
