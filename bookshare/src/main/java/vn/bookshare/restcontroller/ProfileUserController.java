package vn.bookshare.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bookshare.common.builder.CustomApiResponseBuilder;
import vn.bookshare.common.enums.ResponseCode;
import vn.bookshare.dto.response.CustomApiResponse;
import vn.bookshare.dto.response.UserAccountResponse;
import vn.bookshare.service.UserAccountService;

@RestController
@RequestMapping("/api/profile")
public class ProfileUserController {

    private final UserAccountService userAccountService;

    public ProfileUserController(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @GetMapping("/me")
    public ResponseEntity<CustomApiResponse<UserAccountResponse>> getMyProfile(HttpServletRequest request) {
        CustomApiResponse<UserAccountResponse> customApiResponse = CustomApiResponseBuilder
                .buildCustomApiResponse(
                        true,
                        ResponseCode.GET_PROFILE_SUCCESSFUL.name(),
                        "Get profile successful",
                        userAccountService.getProfileUser(),
                        request
                );
        return ResponseEntity.status(201).body(customApiResponse);
    }
}
