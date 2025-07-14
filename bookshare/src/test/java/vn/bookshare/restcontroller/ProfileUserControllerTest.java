package vn.bookshare.restcontroller;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.entity.UserAccount;
import vn.bookshare.entity.UserInfo;
import vn.bookshare.repository.UserAccountRepository;
import vn.bookshare.security.JwtTokenProvider;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
@DisplayName("Profile API Integration Test")
class ProfileUserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    private static final String GET_PROFILE_ENDPOINT = "/api/profile/me";

    @Test
    @DisplayName("Should return profile when JWT token is valid")
    void shouldReturnUserProfile_WhenValidToken() throws Exception {
        UserAccount userAccount = createUserAccount("user-demo-1", "User demo",
                "userdemo@gmail.com", "0123456789qwertyuiopasdfghjklzxcvbnm");
        UserInfo userInfo = createUserInfo("Nam", "Việt Nam", "0865761892");
        userAccount.setUserInfo(userInfo);
        userInfo.setUserAccount(userAccount);
        userAccountRepository.save(userAccount);
        String token = jwtTokenProvider.generateToken(userAccount.getUsername());
        mockMvc.perform(get(GET_PROFILE_ENDPOINT).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpectAll(
                        status().is(200),
                        jsonPath("$.status").value(true),
                        jsonPath("$.code").value("GET_PROFILE_SUCCESSFUL"),
                        jsonPath("$.message").value("Get profile successful"),
                        jsonPath("$.data").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/profile/me"))
                .andDo(print());
    }

    @Test
    @DisplayName("Should return profile when UserInfo is null")
    void shouldReturnUserProfile_WhenUserInfoIsNull() throws Exception {
        UserAccount userAccount = createUserAccount("user-demo-1", "User demo",
                "userdemo@gmail.com", "0123456789qwertyuiopasdfghjklzxcvbnm");
        userAccountRepository.save(userAccount);
        String token = jwtTokenProvider.generateToken(userAccount.getUsername());
        mockMvc.perform(get(GET_PROFILE_ENDPOINT).header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpectAll(
                        status().is(200),
                        jsonPath("$.status").value(true),
                        jsonPath("$.code").value("GET_PROFILE_SUCCESSFUL"),
                        jsonPath("$.message").value("Get profile successful"),
                        jsonPath("$.data").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty(),
                        jsonPath("$.path").value("/api/profile/me"))
                .andDo(print());
    }

    private UserAccount createUserAccount(String url, String fullname,
            String username, String password) {
        UserAccount userAccount = new UserAccount();
        userAccount.setUrl(url);
        userAccount.setFullname(fullname);
        userAccount.setUsername(username);
        userAccount.setPassword(password);
        return userAccount;
    }

    private UserInfo createUserInfo(String gender, String nationality, String phone) {
        UserInfo userInfo = new UserInfo();
        userInfo.setDateOfBirth(LocalDate.now());
        userInfo.setGender(gender);
        userInfo.setNationality(nationality);
        userInfo.setPhone(phone);
        return userInfo;
    }
}
