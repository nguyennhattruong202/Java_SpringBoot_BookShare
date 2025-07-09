package vn.bookshare.service;

import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.dto.response.UserAccountResponse;

public interface UserAccountService {

    public void registerUser(UserAccountRegistrationRequest userRegistrationRequest);

    public UserAccountResponse getProfileUser();
}
