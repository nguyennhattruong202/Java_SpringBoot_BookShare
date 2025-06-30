package vn.bookshare.restcontroller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bookshare.dto.AccountUserRegistrationRequest;
import vn.bookshare.response.CustomResponse;
import vn.bookshare.service.AccountUserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AccountUserService userService;

    public AuthController(AccountUserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomResponse<Void>> register(@Valid @RequestBody AccountUserRegistrationRequest userRegistrationRequest) {
        userService.registerUser(userRegistrationRequest);
        CustomResponse<Void> customResponse = new CustomResponse();
        customResponse.setStatus(true);
        customResponse.setMessage("User registered successfully");
        return ResponseEntity.ok().body(customResponse);
    }
}
