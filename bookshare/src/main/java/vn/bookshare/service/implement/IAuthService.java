package vn.bookshare.service.implement;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.dto.request.UserLoginRequest;
import vn.bookshare.dto.response.TokenResponse;
import vn.bookshare.security.JwtTokenProvider;
import vn.bookshare.service.AuthService;

@Service
@Transactional
public class IAuthService implements AuthService {

    private final AuthenticationManager authManager;
    private final JwtTokenProvider jwtTokenProvider;

    public IAuthService(AuthenticationManager authManager, JwtTokenProvider jwtTokenProvider) {
        this.authManager = authManager;
        this.jwtTokenProvider = jwtTokenProvider;
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
}
