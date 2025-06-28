package vn.bookshare.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vn.bookshare.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("SELECT user FROM User user WHERE user.username = :username")
    public Optional<User> findByUsername(@Param("username") String username);
}
