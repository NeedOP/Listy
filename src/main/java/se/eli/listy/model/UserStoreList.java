package se.eli.listy.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_store_lists",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id","store_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserStoreList {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private LocalDateTime lastReset;
}
