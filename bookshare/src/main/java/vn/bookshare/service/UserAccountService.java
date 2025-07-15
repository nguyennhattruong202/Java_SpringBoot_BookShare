package vn.bookshare.service;

import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.dto.request.UserLoginRequest;
import vn.bookshare.dto.response.TokenResponse;
import vn.bookshare.dto.response.UserAccountResponse;

public interface UserAccountService {

    public void registerUser(UserAccountRegistrationRequest userRegistrationRequest);

    public TokenResponse login(UserLoginRequest userLoginRequest);

    public UserAccountResponse getProfileUser();

}
