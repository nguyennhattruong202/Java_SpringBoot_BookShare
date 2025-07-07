package vn.bookshare.service.implement;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.bookshare.common.mapper.UserInfoMapper;
import vn.bookshare.dto.response.UserInfoResponse;
import vn.bookshare.entity.UserInfo;
import vn.bookshare.exception.UserInfoNotFoundException;
import vn.bookshare.repository.UserInfoRepository;
import vn.bookshare.service.UserInfoService;

@Service
@Transactional
public class IUserInfoService implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public IUserInfoService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    @Override
    public UserInfoResponse getUserInfoByUsername(String username) {
        UserInfo userInfo = userInfoRepository.findByUsername(username)
                .orElseThrow(() -> new UserInfoNotFoundException("User not found"));
        return UserInfoMapper.toUserInfoResponse(userInfo);
    }
}
