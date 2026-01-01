package se.eli.listy.repository;

import se.eli.listy.model.Item;
import se.eli.listy.model.UserStoreList;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<Item, UUID> {
    List<Item> findByList(UserStoreList list);
}
