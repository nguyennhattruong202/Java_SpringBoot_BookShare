package vn.bookshare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookshare.entity.AccountUser;

@Repository
public interface AccountUserRepository extends JpaRepository<AccountUser, String> {

    @Query("SELECT accountUser FROM AccountUser accountUser WHERE accountUser.username = :username")
    public Optional<AccountUser> findByUsername(@Param("username") String username);
}
