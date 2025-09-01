package authentication.auth_service.service;

import authentication.auth_service.dto.LoginRequestDTO;
import authentication.auth_service.dto.LoginResponseDTO;
import authentication.auth_service.model.User;
import authentication.auth_service.repo.UserRepo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo, PasswordEncoder passwordEncoder){
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByEmail(String email){
        Optional<User> user = userRepo.findByEmail(email);
        return user;
    }

    public boolean isUserExists(LoginRequestDTO loginRequestDTO) {

        return userRepo.existsByEmail(loginRequestDTO.getEmail());
    }

    public void createUser(LoginRequestDTO loginRequestDTO) {
        User user = new User();
        user.setEmail(loginRequestDTO.getEmail());
        user.setPassword(encryptPassword(loginRequestDTO.getPassword()));
        user.setRole("Admin");
        userRepo.save(user);
    }

    private String encryptPassword(String password){
        return passwordEncoder.encode(password);
    }
}
