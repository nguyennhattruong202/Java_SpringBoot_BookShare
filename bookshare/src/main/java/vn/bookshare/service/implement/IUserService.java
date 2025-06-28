package vn.bookshare.service.implement;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bookshare.dto.UserRegistrationRequest;
import vn.bookshare.entity.User;
import vn.bookshare.exception.PasswordNotMatchException;
import vn.bookshare.exception.UserAlreadyExistsException;
import vn.bookshare.repository.UserRepository;
import vn.bookshare.service.UserService;

@Transactional
@Service
public class IUserService implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User not found with username " + username));
    }

    @Override
    public void registerUser(UserRegistrationRequest userRegistrationRequest) {
        if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password do not match");
        }
        if (userRepository.findByUsername(userRegistrationRequest.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Email is already registered");
        }
        User user = new User();
        user.setUsername(userRegistrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userRepository.save(user);
    }

}
