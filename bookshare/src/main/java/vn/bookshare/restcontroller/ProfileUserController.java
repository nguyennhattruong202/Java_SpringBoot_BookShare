package vn.bookshare.restcontroller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.bookshare.common.builder.CustomApiResponseBuilder;
import vn.bookshare.common.enums.ResponseCode;
import vn.bookshare.dto.response.CustomApiResponse;
import vn.bookshare.service.UserInfoService;

@RestController
@RequestMapping("/api/profile")
public class ProfileUserController {

//    private final UserInfoService userInfoService;
//
//    public ProfileUserController(UserInfoService userInfoService) {
//        this.userInfoService = userInfoService;
//    }
//
//    @GetMapping("/me")
//    public ResponseEntity<?> getMyProfile(HttpServletRequest request) {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        CustomApiResponse<?> customApiResponse = CustomApiResponseBuilder
//                .buildCustomApiResponse(
//                        true,
//                        ResponseCode.GET_PROFILE_SUCCESSFUL.name(),
//                        "Get profile successful",
//                        userInfoService.getUserInfoByUsername(username),
//                        request
//                );
//        return ResponseEntity.status(201).body(customApiResponse);
//    }
}
