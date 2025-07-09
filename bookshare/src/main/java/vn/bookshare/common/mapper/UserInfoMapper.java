package vn.bookshare.common.mapper;

import vn.bookshare.dto.response.UserInfoResponse;
import vn.bookshare.entity.UserInfo;

public class UserInfoMapper {

    public static UserInfoResponse toUserInfoResponse(UserInfo userInfo) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUserId(userInfo.getUserAccount().getUserId());
        userInfoResponse.setUrl(userInfo.getUserAccount().getUrl());
        userInfoResponse.setFullname(userInfo.getUserAccount().getFullname());
        userInfoResponse.setUsername(userInfo.getUserAccount().getUsername());
        userInfoResponse.setDateOfBirth(userInfo.getDateOfBirth());
        userInfoResponse.setGender(userInfo.getGender());
        userInfoResponse.setNationality(userInfo.getNationality());
        userInfoResponse.setPhone(userInfo.getPhone());
        userInfoResponse.setCreatedDate(userInfo.getCreatedDate());
        userInfoResponse.setUpdatedDate(userInfo.getUpdatedDate());
        userInfoResponse.setNote(userInfo.getNote());
        userInfoResponse.setRole(userInfo.getUserAccount().getRole().name());
        return userInfoResponse;
    }
}
