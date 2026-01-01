package se.eli.listy.repository;

import se.eli.listy.model.UserStoreList;
import se.eli.listy.model.User;
import se.eli.listy.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserStoreListRepository extends JpaRepository<UserStoreList, UUID> {
    Optional<UserStoreList> findByUserAndStore(User user, Store store);
    List<UserStoreList> findByUser(User user);
}
