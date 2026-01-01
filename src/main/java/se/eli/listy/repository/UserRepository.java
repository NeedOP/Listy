package se.eli.listy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.eli.listy.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}
