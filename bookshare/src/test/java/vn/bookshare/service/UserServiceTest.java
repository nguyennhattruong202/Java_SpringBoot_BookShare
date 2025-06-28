package vn.bookshare.service;

import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.bookshare.dto.UserRegistrationRequest;
import vn.bookshare.entity.User;
import vn.bookshare.exception.UserAlreadyExistsException;
import vn.bookshare.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void registerUser_Success() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("test@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("123456");
        Mockito.when(userRepository.findByUsername("test@example.com")).thenReturn(Optional.empty());
        Assertions.assertDoesNotThrow(() -> userService.registerUser(request));
    }

    @Test
    void registerUser_ExistingEmail_ThrowsException() {
        UserRegistrationRequest request = new UserRegistrationRequest();
        request.setEmail("exists@example.com");
        request.setPassword("123456");
        request.setConfirmPassword("123456");
        Mockito.when(userRepository.findByUsername("exists@example.com")).thenReturn(Optional.of(new User()));
        UserAlreadyExistsException ex = Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(request));
        Assertions.assertEquals("Email is already registered", ex.getMessage());
    }
}
