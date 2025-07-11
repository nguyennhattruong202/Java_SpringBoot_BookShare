package vn.bookshare.service;

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
        UserAccountRegistrationRequest request = new UserAccountRegistrationRequest();
        String username = "userdemo@gmail.com";
        request.setFullname("User demo");
        request.setUsername(username);
        request.setPassword("1234567");
        request.setConfirmPassword("1234567");
        userAccountService.registerUser(request);

        Assertions.assertTrue(userAccountRepository.findByUsername(username).isPresent());
    }

    @Test
    void registerUser_ExistingEmail_ThrowsException() {
        UserAccount userAccountExisting = new UserAccount();
        userAccountExisting.setUrl("user-1-1");
        userAccountExisting.setFullname("User 1");
        userAccountExisting.setUsername("user1@gmail.com");
        userAccountExisting.setPassword("123456789");
        userAccountRepository.save(userAccountExisting);

        UserAccountRegistrationRequest userRegistrationRequest = new UserAccountRegistrationRequest();
        userRegistrationRequest.setFullname("User 1");
        userRegistrationRequest.setUsername("user1@gmail.com");
        userRegistrationRequest.setPassword("1234567");
        userRegistrationRequest.setConfirmPassword("1234567");

        AccountUserAlreadyExistsException ex = Assertions.assertThrows(AccountUserAlreadyExistsException.class, () -> {
            userAccountService.registerUser(userRegistrationRequest);
        });
        Assertions.assertEquals("Email is already registered", ex.getMessage());
    }
}
