package vn.bookshare.service.implement;

import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.bookshare.dto.AccountUserRegistrationRequest;
import vn.bookshare.entity.AccountUser;
import vn.bookshare.exception.PasswordNotMatchException;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.repository.AccountUserRepository;
import vn.bookshare.service.AccountUserService;

@Transactional
@Service
public class IAccountUserService implements AccountUserService, UserDetailsService {

    private final AccountUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IAccountUserService(AccountUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException("User not found with username " + username));
    }

    @Override
    public void registerUser(AccountUserRegistrationRequest userRegistrationRequest) {
        if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password do not match");
        }
        if (userRepository.findByUsername(userRegistrationRequest.getEmail()).isPresent()) {
            throw new AccountUserAlreadyExistsException("Email is already registered");
        }
        AccountUser user = new AccountUser();
        user.setUsername(userRegistrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userRepository.save(user);
    }

}
