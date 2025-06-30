package vn.bookshare.restcontroller;

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

@SpringBootTest
//@ActiveProfiles("test")
//@AutoConfigureMockMvc
public class AuthControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void registerUser_ValidEmail_ReturnsOK() throws Exception {
//        String json = """
//        {
//          "email": "user10@gmail.com",
//          "password": "1234567",
//          "confirmPassword": "1234567"
//        }
//        """;
//        mockMvc.perform(post("/api/auth/register").with(csrf())
//                .contentType(MediaType.APPLICATION_JSON).content(json))
//                .andExpect(status().isOk()).andExpect(jsonPath("$.status")
//                .value(true)).andDo(print());
//    }
}
