package vn.bookshare.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bookshare.common.builder.CustomApiResponseBuilder;
import vn.bookshare.common.enums.ResponseCode;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.dto.request.UserLoginRequest;
import vn.bookshare.dto.response.CustomApiResponse;
import vn.bookshare.dto.response.TokenResponse;
import vn.bookshare.service.AuthService;
import vn.bookshare.service.UserAccountService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAccountService userAccountService;
    private final AuthService authService;

    public AuthController(UserAccountService userService, AuthService authService) {
        this.userAccountService = userService;
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomApiResponse<Void>> register(HttpServletRequest request,
            @Valid @RequestBody UserAccountRegistrationRequest userAccountRegistrationRequest) {
        userAccountService.registerUser(userAccountRegistrationRequest);
        CustomApiResponse<Void> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        true,
                        ResponseCode.REGISTRATION_SUCCESSFUL.name(),
                        "Registration successful",
                        null,
                        request
                );
        return ResponseEntity.status(201).body(customApiResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<CustomApiResponse<TokenResponse>> login(
            @Valid @RequestBody UserLoginRequest userLoginRequest,
            HttpServletRequest request) {
        CustomApiResponse<TokenResponse> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        true,
                        ResponseCode.LOGIN_SUCCESSFUL.name(),
                        "Login successful",
                        authService.login(userLoginRequest),
                        request
                );
        return ResponseEntity.status(200).body(customApiResponse);
    }
}
