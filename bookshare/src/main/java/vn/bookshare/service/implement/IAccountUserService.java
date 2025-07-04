package vn.bookshare.service.implement;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.dto.request.AccountUserRegistrationRequest;
import vn.bookshare.entity.AccountUser;
import vn.bookshare.exception.PasswordNotMatchException;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.repository.AccountUserRepository;
import vn.bookshare.service.AccountUserService;

@Transactional
@Service
public class IAccountUserService implements AccountUserService {

    private final AccountUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public IAccountUserService(AccountUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(AccountUserRegistrationRequest userRegistrationRequest) {
        if (!userRegistrationRequest.getPassword().equals(userRegistrationRequest.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password does not match confirm password");
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
