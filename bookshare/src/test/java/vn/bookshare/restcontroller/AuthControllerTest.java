package vn.bookshare.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.service.UserAccountService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountService accoutUserService;

    private final String registerEndpoint = "/api/auth/register";
    private final String loginEndpoint = "/api/auth/login";

    @BeforeEach
    void setup() {
        UserAccountRegistrationRequest request = new UserAccountRegistrationRequest();
        request.setFullname("User Demo");
        request.setUsername("userdemo@gmail.com");
        request.setPassword("1234567");
        request.setConfirmPassword("1234567");
        accoutUserService.registerUser(request);
    }

    @Test
    void registerUser_ValidEmail_ReturnsCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Đinh Thế Hoàng",
                        "username", "dinhthehoang@gmail.com",
                        "password", "1234567",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(201),
                        jsonPath("$.status").value(true),
                        jsonPath("$.code").value("REGISTRATION_SUCCESSFUL"),
                        jsonPath("$.message").value("Registration successful"))
                .andDo(print());
    }

    @Test
    void registerUser_EmailAlreadyExists_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "User Demo",
                        "username", "userdemo@gmail.com",
                        "password", "1234567",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("USER_ALREADY_EXISTS_ERROR"),
                        jsonPath("$.message").value("Email is already registered"),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_PasswordNotMatchConfirm_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Nguyễn Văn Bình",
                        "username", "nguyenvanbinh@gmail.com",
                        "password", "1234569",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_InvalidEmail_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Example",
                        "username", "example.com",
                        "password", "123459",
                        "confirmPassword", "123459"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.data.username").value(hasItem("Invalid email")),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_EmailIsBlank_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Example",
                        "username", "",
                        "password", "123459",
                        "confirmPassword", "123459"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.data.username").value(hasItem("Email cannot be blank")),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_PasswordIsBlank_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Example",
                        "username", "example@gmail.com",
                        "password", "",
                        "confirmPassword", "123457"
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.data.password").value(hasItem("Password cannot be blank")),
                        jsonPath("$.data.password").value(hasItem("Password must be at least 6 characters")),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_ConfirmPasswordIsBlank_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "Example",
                        "username", "example@gmail.com",
                        "password", "123457",
                        "confirmPassword", ""
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.data.confirmPassword",
                                hasItems("Confirm password cannot be blank",
                                        "Confirm password must be at least 6 characters")),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_AllFieldIsBlank_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "fullname", "",
                        "username", "",
                        "password", "",
                        "confirmPassword", ""
                )
        );
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("VALIDATION_ERROR"),
                        jsonPath("$.message").value("Validation error"),
                        jsonPath("$.data.fullname",
                                hasItems("Fullname cannot be blank",
                                        "Fullname must be at least 3 characters and maximum 255 characters")),
                        jsonPath("$.data.username").value(hasItem("Email cannot be blank")),
                        jsonPath("$.data.password",
                                hasItems("Password cannot be blank",
                                        "Password must be at least 6 characters")),
                        jsonPath("$.data.confirmPassword",
                                hasItems("Confirm password cannot be blank",
                                        "Confirm password must be at least 6 characters")),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void loginUser_ValidEmailAndPassword_ReturnsOK() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "userdemo@gmail.com",
                        "password", "1234567"
                )
        );
        mockMvc.perform(post(loginEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(200),
                        jsonPath("$.status").value(true),
                        jsonPath("$.code").value("LOGIN_SUCCESSFUL"),
                        jsonPath("$.message").value("Login successful"),
                        jsonPath("$.data.tokenType").value("Bearer"),
                        jsonPath("$.data.accessToken").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/login"))
                .andDo(print());
    }

    @Test
    void loginUser_UserNotFound_ReturnsBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "user1@gmail.com",
                        "password", "1234567"
                )
        );
        mockMvc.perform(post(loginEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(400),
                        jsonPath("$.status").value(false),
                        jsonPath("$.code").value("BAD_CREDENTIALS"),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/login"))
                .andDo(print());
    }
}
