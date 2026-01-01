package se.eli.listy.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "stores")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    private int resetIntervalWeeks = 0; // 0 = never
    private int resetWeekday; // 1=Monday, 7=Sunday
    private LocalDate resetStartDate;
}
