package vn.bookshare.service;

import vn.bookshare.dto.request.AccountUserRegistrationRequest;

public interface AccountUserService {

    public void registerUser(AccountUserRegistrationRequest userRegistrationRequest);
}
