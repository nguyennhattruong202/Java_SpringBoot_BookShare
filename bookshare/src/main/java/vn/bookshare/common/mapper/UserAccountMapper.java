package vn.bookshare.common.mapper;

import vn.bookshare.dto.response.UserAccountResponse;
import vn.bookshare.entity.UserAccount;

public class UserAccountMapper {

    private UserAccountMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static UserAccountResponse toUserAccountResponse(UserAccount userAccount) {
        UserAccountResponse userInfoResponse = new UserAccountResponse();
        userInfoResponse.setUserId(userAccount.getUserId());
        userInfoResponse.setUrl(userAccount.getUrl());
        userInfoResponse.setFullname(userAccount.getFullname());
        userInfoResponse.setUsername(userAccount.getUsername());
        userInfoResponse.setDateOfBirth(userAccount.getUserInfo().getDateOfBirth());
        userInfoResponse.setGender(userAccount.getUserInfo().getGender());
        userInfoResponse.setNationality(userAccount.getUserInfo().getNationality());
        userInfoResponse.setPhone(userAccount.getUserInfo().getPhone());
        userInfoResponse.setCreatedDate(userAccount.getCreatedDate());
        userInfoResponse.setUpdatedDate(userAccount.getUpdatedDate());
        userInfoResponse.setNote(userAccount.getNote());
        userInfoResponse.setRole(userAccount.getRole().name());
        return userInfoResponse;
    }
}
