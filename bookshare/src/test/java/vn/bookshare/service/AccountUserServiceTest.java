package vn.bookshare.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.dto.AccountUserRegistrationRequest;
import vn.bookshare.entity.AccountUser;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.repository.AccountUserRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class AccountUserServiceTest {

    @Autowired
    private AccountUserRepository userRepository;
    @Autowired
    private AccountUserService userService;

    @Test
    void registerUser_Success() {
        AccountUserRegistrationRequest request = new AccountUserRegistrationRequest();
        String email = "user1@gmail.com";
        request.setEmail(email);
        request.setPassword("1234567");
        request.setConfirmPassword("1234567");
        userService.registerUser(request);

        Assertions.assertTrue(userRepository.findByUsername(email).isPresent());
    }

    @Test
    void registerUser_ExistingEmail_ThrowsException() {
        AccountUser userExisting = new AccountUser();
        userExisting.setUsername("user1@gmail.com");
        userExisting.setPassword("123456789");
        userRepository.save(userExisting);

        AccountUserRegistrationRequest userRegistrationRequest = new AccountUserRegistrationRequest();
        userRegistrationRequest.setEmail("user1@gmail.com");
        userRegistrationRequest.setPassword("1234567");
        userRegistrationRequest.setConfirmPassword("1234567");

        Exception ex = Assertions.assertThrows(AccountUserAlreadyExistsException.class, () -> {
            userService.registerUser(userRegistrationRequest);
        });
        Assertions.assertEquals("Email is already registered", ex.getMessage());
    }
}
