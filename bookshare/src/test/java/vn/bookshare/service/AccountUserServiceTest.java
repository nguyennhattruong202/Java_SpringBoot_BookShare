package vn.bookshare.service;

import java.time.LocalDate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.entity.UserAccount;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.repository.UserAccountRepository;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class AccountUserServiceTest {

    @Autowired
    private UserAccountRepository userAccountRepository;
    @Autowired
    private UserAccountService userAccountService;

    @Test
    void registerUser_Success() {
        UserAccountRegistrationRequest userAccountRegistrationRequest = new UserAccountRegistrationRequest();
        String username = "userdemo@gmail.com";
        userAccountRegistrationRequest.setUsername(username);
        userAccountRegistrationRequest.setFullname("User demo");
        userAccountRegistrationRequest.setDateOfBirth(LocalDate.now());
        userAccountRegistrationRequest.setGender("Nam");
        userAccountRegistrationRequest.setPhone("0987665178");
        userAccountRegistrationRequest.setPassword("1234567");
        userAccountRegistrationRequest.setConfirmPassword("1234567");
        userAccountService.registerUser(userAccountRegistrationRequest);

        Assertions.assertTrue(userAccountRepository.findByUsername(username).isPresent());
    }

    @Test
    void registerUser_ExistingEmail_ThrowsException() {
        UserAccount userAccountExisting = new UserAccount();
        userAccountExisting.setEndpoint("user-1-1");
        userAccountExisting.setUsername("user1@gmail.com");
        userAccountExisting.setPassword("123456789");
        userAccountExisting.setFullname("User 1");
        userAccountExisting.setDateOfBirth(LocalDate.now());
        userAccountExisting.setGender("Nam");
        userAccountExisting.setPhone("0987665178");
        userAccountRepository.save(userAccountExisting);

        UserAccountRegistrationRequest userRegistrationRequest = new UserAccountRegistrationRequest();
        userRegistrationRequest.setUsername("user1@gmail.com");
        userRegistrationRequest.setFullname("User 1");
        userRegistrationRequest.setDateOfBirth(LocalDate.now());
        userRegistrationRequest.setGender("Nam");
        userRegistrationRequest.setPhone("0987665178");
        userRegistrationRequest.setPassword("1234567");
        userRegistrationRequest.setConfirmPassword("1234567");

        AccountUserAlreadyExistsException ex = Assertions.assertThrows(AccountUserAlreadyExistsException.class, () -> {
            userAccountService.registerUser(userRegistrationRequest);
        });
        Assertions.assertEquals("Email is already registered", ex.getMessage());
    }
}
