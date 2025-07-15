package vn.bookshare.common.mapper;

import vn.bookshare.dto.request.UserAccountRegistrationRequest;
import vn.bookshare.dto.response.UserAccountResponse;
import vn.bookshare.entity.UserAccount;

public class UserAccountMapper {

    private UserAccountMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        UserAccountResponse userAccountResponse = new UserAccountResponse();
        userAccountResponse.setUserId(userAccount.getUserId());
        userAccountResponse.setEndpoint(userAccount.getEndpoint());
        userAccountResponse.setUsername(userAccount.getUsername());
        userAccountResponse.setFullname(userAccount.getFullname());
        userAccountResponse.setDateOfBirth(userAccount.getDateOfBirth());
        userAccountResponse.setGender(userAccount.getGender());
        userAccountResponse.setPhone(userAccount.getPhone());
        userAccountResponse.setNote(userAccount.getNote());
        userAccountResponse.setCreatedDate(userAccount.getCreatedDate());
        userAccountResponse.setUpdatedDate(userAccount.getUpdatedDate());

        return userAccountResponse;
    }

    public static UserAccount toUserAccount(UserAccountRegistrationRequest userAccountRegistrationRequest,
            String endpoint, String passwordEncode) {
        UserAccount userAccount = new UserAccount();
        userAccount.setEndpoint(endpoint);
        userAccount.setUsername(userAccountRegistrationRequest.getUsername());
        userAccount.setPassword(passwordEncode);
        userAccount.setFullname(userAccountRegistrationRequest.getFullname());
        userAccount.setDateOfBirth(userAccountRegistrationRequest.getDateOfBirth());
        userAccount.setGender(userAccountRegistrationRequest.getGender());
        userAccount.setPhone(userAccountRegistrationRequest.getPhone());
        return userAccount;
    }
}
