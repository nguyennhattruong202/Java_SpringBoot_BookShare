package vn.bookshare.service.implement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.common.builder.UserNameBuilder;
import vn.bookshare.common.mapper.UserAccountMapper;
import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.dto.request.UserLoginRequest;
import vn.bookshare.dto.response.TokenResponse;
import vn.bookshare.dto.response.UserAccountResponse;
import vn.bookshare.entity.UserAccount;
import vn.bookshare.exception.PasswordNotMatchException;
import vn.bookshare.exception.AccountUserAlreadyExistsException;
import vn.bookshare.exception.UserAccountNotFoundException;
import vn.bookshare.repository.UserAccountRepository;
import vn.bookshare.security.JwtTokenProvider;
import vn.bookshare.service.UserAccountService;

@Transactional
@Service
@Slf4j
public class IUserAccountService implements UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserNameBuilder usernameBuilder;
    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    public IUserAccountService(UserAccountRepository userRepository,
            PasswordEncoder passwordEncoder, UserNameBuilder usernameBuilder,
            AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        this.userAccountRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.usernameBuilder = usernameBuilder;
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void registerUser(UserAccountRegistrationRequest userAccountRegistrationRequest) {
        if (!userAccountRegistrationRequest.getPassword()
                .equals(userAccountRegistrationRequest.getConfirmPassword())) {
            throw new PasswordNotMatchException("Password does not match confirm password");
        }
        if (userAccountRepository.findByUsername(userAccountRegistrationRequest
                .getUsername()).isPresent()) {
            throw new AccountUserAlreadyExistsException("Email is already registered");
        }
        String endpoint = usernameBuilder.buildUsername(userAccountRegistrationRequest
                .getFullname(), userAccountRepository.findMaxUserId().orElse(0L));
        String passwordEncode = passwordEncoder.encode(userAccountRegistrationRequest.getPassword());
        UserAccount userAccount = UserAccountMapper.toUserAccount(
                userAccountRegistrationRequest, endpoint, passwordEncode);
        userAccountRepository.save(userAccount);
    }

    @Override
    public TokenResponse login(UserLoginRequest userLoginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getUsername(),
                        userLoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken(jwtTokenProvider.generateToken(userLoginRequest.getUsername()));
        tokenResponse.setTokenType("Bearer");
        return tokenResponse;
    }

    @Override
    public UserAccountResponse getProfileUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if (username == null || username.isEmpty()) {
            throw new UserAccountNotFoundException("User not found");
        }
        UserAccount userAccount = userAccountRepository.findByUsername(username)
                .orElseThrow(() -> new UserAccountNotFoundException("User not found"));
        return UserAccountMapper.toUserAccountResponse(userAccount);
    }

}
