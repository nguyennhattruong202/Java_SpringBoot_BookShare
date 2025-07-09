package vn.bookshare.service.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.common.builder.UserNameBuilder;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.entity.UserAccount;
import vn.bookshare.exception.PasswordNotMatchException;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.repository.UserAccountRepository;
import vn.bookshare.service.UserAccountService;

@Transactional
@Service
@Slf4j
public class IUserAccountService implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserNameBuilder usernameBuilder;

    public IUserAccountService(UserAccountRepository userRepository,
            PasswordEncoder passwordEncoder, UserNameBuilder usernameBuilder) {
        this.userAccountRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameBuilder = usernameBuilder;
    }

    @Override
    public void registerUser(UserAccountRegistrationRequest userAccountRegistrationRequest) {
        if (!userAccountRegistrationRequest.getPassword().equals(userAccountRegistrationRequest.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password does not match confirm password");
        }
        if (userAccountRepository.findByUsername(userAccountRegistrationRequest.getUsername()).isPresent()) {
            throw new AccountUserAlreadyExistsException("Email is already registered");
        }
        String url = usernameBuilder.buildUsername(userAccountRegistrationRequest.getFullname(),
                userAccountRepository.findMaxUserId().orElse(0L));
        log.info("[INFO] USERNAME: {}", url);
        UserAccount userAccount = new UserAccount();
        userAccount.setUrl(url);
        userAccount.setFullname(userAccountRegistrationRequest.getFullname());
        userAccount.setUsername(userAccountRegistrationRequest.getUsername());
        userAccount.setPassword(passwordEncoder.encode(userAccountRegistrationRequest.getPassword()));
        userAccountRepository.save(userAccount);
    }

}
