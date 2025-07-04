package vn.bookshare.service;

import vn.bookshare.dto.request.UserLoginRequest;
import vn.bookshare.dto.response.TokenResponse;

public interface AuthService {

    public TokenResponse login(UserLoginRequest userLoginRequest);
}
