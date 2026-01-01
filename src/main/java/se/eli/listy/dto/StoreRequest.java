package se.eli.listy.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class StoreRequest {
    private String name;
    private int resetIntervalWeeks = 0;
    private int resetWeekday; // 1=Monday..7=Sunday
    private LocalDate resetStartDate;
}
