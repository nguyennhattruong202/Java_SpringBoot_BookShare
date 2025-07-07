package vn.bookshare.service;

import vn.bookshare.dto.response.UserInfoResponse;

public interface UserInfoService {

    public UserInfoResponse getUserInfoByUsername(String username);
}
