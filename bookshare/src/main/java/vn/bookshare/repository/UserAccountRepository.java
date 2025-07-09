package vn.bookshare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookshare.entity.UserAccount;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    @Query("SELECT userAccount FROM UserAccount userAccount WHERE userAccount.username = :username")
    public Optional<UserAccount> findByUsername(@Param("username") String username);

    @Query("SELECT MAX(userAccount.userId) FROM UserAccount userAccount")
    Optional<Long> findMaxUserId();

}
