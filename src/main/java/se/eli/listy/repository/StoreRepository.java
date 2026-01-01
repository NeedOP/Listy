package se.eli.listy.repository;

import se.eli.listy.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface StoreRepository extends JpaRepository<Store, UUID> {
}
