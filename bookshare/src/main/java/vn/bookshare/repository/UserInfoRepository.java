package vn.bookshare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookshare.entity.UserInfo;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, String> {

    @Query("SELECT userInfo FROM UserInfo userInfo WHERE userInfo.username = :username")
    public Optional<UserInfo> findByUsername(@Param("username") String username);
}
