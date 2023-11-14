package flower.store;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    // @Query("SELECT u FROM app_user u WHERE u.email = ?1")
    // Optional<AppUser> findUserByEmail(@Param("email") String email);
}
