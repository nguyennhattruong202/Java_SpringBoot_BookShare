package vn.bookshare.restcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Map;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import org.junit.jupiter.api.DisplayName;
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
class UserAccountControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountService accoutUserService;

    private static final String REGISTER_ENDPOINT = "/api/auth/register";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";

    @Test
    @DisplayName("Should return successful registration response with HTTP 201 for valid input")
    void testRegisterUserApi_ValidInput_ReturnsHttpCreated() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "dinhthehoang@gmail.com",
                        "fullname", "Đinh Thế Hoàng",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "1234567",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpectAll(
                        status().is(201),
                        jsonPath("$.status").value(true),
                        jsonPath("$.code").value("REGISTRATION_SUCCESSFUL"),
                        jsonPath("$.message").value("Registration successful"),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return http bad request for user already exists")
    void testRegisterUserApi_UserAccountAlreadyExists_ReturnsHttpBadRequest() throws Exception {
        registerUser("userdemo@gmail.com", "User Demo", "1990-01-01", "Nam",
                "0909887654", "1234567", "1234567");
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "userdemo@gmail.com",
                        "fullname", "User Demo",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "1234567",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for password not match confirm password")
    void testRegisterUserApi_PasswordNotMatchConfirmPassword_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "nguyenvanbinh@gmail.com",
                        "fullname", "Nguyễn Văn Bình",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "1234569",
                        "confirmPassword", "1234567"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for invalid email (username)")
    void testRegisterUserApi_InvalidEmail_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "example.com",
                        "fullname", "Example",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "123459",
                        "confirmPassword", "123459"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for email (username) field is blank")
    void testRegisterUserApi_EmailFieldIsBlank_ReturnsHtpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "",
                        "fullname", "Example",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "123459",
                        "confirmPassword", "123459"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for password field is blank")
    void testRegisterUserApi_PasswordFieldIsBlank_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "example@gmail.com",
                        "fullname", "Example",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "",
                        "confirmPassword", "123459"
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for confirm password field is blank")
    void testRegisterUserApi_ConfirmPasswordFieldIsBlank_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "example@gmail.com",
                        "fullname", "Example",
                        "dateOfBirth", "1990-01-01",
                        "gender", "Nam",
                        "phone", "0909887678",
                        "password", "123459",
                        "confirmPassword", ""
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for all field is blank")
    void testRegisterUserApi_AllFieldIsBlank_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "",
                        "fullname", "",
                        "dateOfBirth", "",
                        "gender", "",
                        "phone", "",
                        "password", "",
                        "confirmPassword", ""
                )
        );
        mockMvc.perform(post(REGISTER_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http ok for email and password are valid")
    void testLoginUserApi_EmailAndPasswordAreValid_ReturnsHttpOK() throws Exception {
        registerUser("userdemo@gmail.com", "User Demo", "1990-01-01", "Nam",
                "0909887654", "1234567", "1234567");
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "userdemo@gmail.com",
                        "password", "1234567"
                )
        );
        mockMvc.perform(post(LOGIN_ENDPOINT).with(csrf())
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
    @DisplayName("Should return http bad request for user not found")
    void testLoginUserApi_UserNotFound_ReturnsHttpBadRequest() throws Exception {
        String json = new ObjectMapper().writeValueAsString(
                Map.of(
                        "username", "user1@gmail.com",
                        "password", "1234567"
                )
        );
        mockMvc.perform(post(LOGIN_ENDPOINT).with(csrf())
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

    private void registerUser(String username, String fullname, String dateOfBirth,
            String gender, String phone, String password, String confirmPassword) {
        UserAccountRegistrationRequest userAccountRegisterRequest = new UserAccountRegistrationRequest();
        userAccountRegisterRequest.setUsername(username);
        userAccountRegisterRequest.setFullname(fullname);
        userAccountRegisterRequest.setDateOfBirth(LocalDate.parse(dateOfBirth));
        userAccountRegisterRequest.setGender(gender);
        userAccountRegisterRequest.setPhone(phone);
        userAccountRegisterRequest.setPassword(password);
        userAccountRegisterRequest.setConfirmPassword(confirmPassword);
        accoutUserService.registerUser(userAccountRegisterRequest);
    }

}
