package vn.bookshare.restcontroller;

import static org.hamcrest.Matchers.hasItem;
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
import vn.bookshare.dto.request.AccountUserRegistrationRequest;
import vn.bookshare.service.AccountUserService;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AccountUserService accoutUserService;

    private final String registerEndpoint = "/api/auth/register";
    private final String loginEndpoint = "/api/auth/login";

    @BeforeEach
    void setup() {
        AccountUserRegistrationRequest request = new AccountUserRegistrationRequest();
        request.setEmail("user10@gmail.com");
        request.setPassword("1234567");
        request.setConfirmPassword("1234567");
        accoutUserService.registerUser(request);
    }

    @Test
    void registerUser_ValidEmail_ReturnsCreated() throws Exception {
        String json = """
        {
          "email": "user11@gmail.com",
          "password": "1234567",
          "confirmPassword": "1234567"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.code").value("REGISTRATION_SUCCESSFUL"))
                .andExpect(jsonPath("$.message").value("Registration successful"))
                .andDo(print());
    }

    @Test
    void registerUser_EmailAlreadyExists_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "user10@gmail.com",
            "password": "1234567",
            "confirmPassword": "1234567"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("USER_ALREADY_EXISTS_ERROR"))
                .andExpect(jsonPath("$.message").value("Email is already registered"))
                .andDo(print());
    }

    @Test
    void registerUser_PasswordNotMatchConfirm_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "user2@example.com", 
            "password": "123459", 
            "confirmPassword": "123457"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andDo(print());
    }

    @Test
    void registerUser_InvalidEmail_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "example.com", 
            "password": "123459", 
            "confirmPassword": "123459"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.data.email").value(hasItem("Invalid email")))
                .andDo(print());
    }

    @Test
    void registerUser_EmailIsBlank_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "", 
            "password": "123459", 
            "confirmPassword": "123459"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.data.email").value(hasItem("Email cannot be blank")))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_PasswordIsBlank_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "user3@example.com", 
            "password": "", 
            "confirmPassword": "123457"
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.data.password").value(hasItem("Password cannot be blank")))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_ConfirmPasswordIsBlank_ReturnsBadRequest() throws Exception {
        String json = """
        {
            "email": "user3@example.com", 
            "password": "123457", 
            "confirmPassword": ""
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.data.confirmPassword").value(hasItem("Confirm password cannot be blank")))
                .andExpect(jsonPath("$.data.confirmPassword").value(hasItem("Confirm password must be at least 6 characters")))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void registerUser_AllFieldIsBlank_ReturnsBadRequest() throws Exception {

        String json = """
        {
            "email": "", 
            "password": "", 
            "confirmPassword": ""
        }
        """;
        mockMvc.perform(post(registerEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"))
                .andExpect(jsonPath("$.message").value("Validation error"))
                .andExpect(jsonPath("$.data.email").value(hasItem("Email cannot be blank")))
                .andExpect(jsonPath("$.data.password").value(hasItem("Password cannot be blank")))
                .andExpect(jsonPath("$.data.password").value(hasItem("Password must be at least 6 characters")))
                .andExpect(jsonPath("$.data.confirmPassword").value(hasItem("Confirm password cannot be blank")))
                .andExpect(jsonPath("$.data.confirmPassword").value(hasItem("Confirm password must be at least 6 characters")))
                .andExpect(jsonPath("$.path").value("/api/auth/register"))
                .andDo(print());
    }

    @Test
    void loginUser_ValidEmailAndPassword_ReturnsOK() throws Exception {

        String json = """
        {
            "email": "user10@gmail.com", 
            "password": "1234567"
        }
        """;
        mockMvc.perform(post(loginEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.status").value(true))
                .andExpect(jsonPath("$.code").value("LOGIN_SUCCESSFUL"))
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.data.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/auth/login"))
                .andDo(print());
    }

    @Test
    void loginUser_UserNotFound_ReturnsBadRequest() throws Exception {

        String json = """
        {
            "email": "user1@gmail.com", 
            "password": "1234567"
        }
        """;
        mockMvc.perform(post(loginEndpoint).with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.status").value(false))
                .andExpect(jsonPath("$.code").value("BAD_CREDENTIALS"))
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.path").value("/api/auth/login"))
                .andDo(print());
    }
}
