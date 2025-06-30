package vn.bookshare.service;

import vn.bookshare.dto.AccountUserRegistrationRequest;

public interface AccountUserService {

    public void registerUser(AccountUserRegistrationRequest userRegistrationRequest);
}
