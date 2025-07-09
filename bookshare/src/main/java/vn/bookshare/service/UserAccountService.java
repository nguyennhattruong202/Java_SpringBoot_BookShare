package vn.bookshare.service;

import vn.bookshare.dto.request.UserAccountRegistrationRequest;

public interface UserAccountService {

    public void registerUser(UserAccountRegistrationRequest userRegistrationRequest);
}
