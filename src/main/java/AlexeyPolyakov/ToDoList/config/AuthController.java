package AlexeyPolyakov.ToDoList.config;


import AlexeyPolyakov.ToDoList.config.JwtTokenFilter;
import AlexeyPolyakov.ToDoList.model.User;
import AlexeyPolyakov.ToDoList.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtTokenFilter jwtTokenFilter;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username, @RequestParam String password) {
        userService.registerUser(username, password);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        User user = (User) userService.loadUserByUsername(username);
        if (userService.getPasswordEncoder().matches(password, user.getPassword())) {
            String token = JwtTokenFilter.generateToken(username);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }
}