package vn.bookshare.common.mapper;

import vn.bookshare.dto.response.UserInfoResponse;
import vn.bookshare.entity.UserInfo;

public class UserInfoMapper {
    
    public static UserInfoResponse toUserInfoResponse(UserInfo userInfo) {
        UserInfoResponse userInfoResponse = new UserInfoResponse();
        userInfoResponse.setUsername(userInfo.getUsername());
        userInfoResponse.setOrdinalNumber(userInfo.getOrdinalNumber());
        userInfoResponse.setFullname(userInfo.getFullname());
        userInfoResponse.setDateOfBirth(userInfo.getDateOfBirth());
        userInfoResponse.setGender(userInfo.getGender());
        userInfoResponse.setNationality(userInfo.getNationality());
        userInfoResponse.setPhone(userInfo.getPhone());
        userInfoResponse.setCreatedDate(userInfo.getCreatedDate());
        userInfoResponse.setUpdatedDate(userInfo.getUpdatedDate());
        userInfoResponse.setNote(userInfo.getNote());
        userInfoResponse.setRole(userInfo.getAccountUser().getRole().name());
        return userInfoResponse;
    }
}
