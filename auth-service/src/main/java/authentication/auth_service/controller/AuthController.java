package authentication.auth_service.controller;

import authentication.auth_service.dto.LoginRequestDTO;
import authentication.auth_service.dto.LoginResponseDTO;
import authentication.auth_service.service.AuthService;
import authentication.auth_service.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDTO> signin(@RequestBody @Validated LoginRequestDTO loginRequestDTO){
        Optional<String> userToken =  authService.authenticate(loginRequestDTO);

        if(userToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok().body(new LoginResponseDTO(userToken.get()));
    }

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@RequestBody @Validated LoginRequestDTO loginRequestDTO){
        boolean userExists = userService.isUserExists(loginRequestDTO);

        if(userExists){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        userService.createUser(loginRequestDTO);
        return ResponseEntity.status(201).build();

    }


    @GetMapping("/validate")
    public ResponseEntity<Void> validate(@RequestHeader("Authorization") String authHeader){

        if(!authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }


        boolean isValid = authService.validate(authHeader.substring(7));

        return isValid ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
