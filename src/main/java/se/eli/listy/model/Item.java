package se.eli.listy.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private UserStoreList list;

    @Column(nullable = false)
    private String name;

    private String description;
    private int quantity = 1;
    private boolean done = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}
