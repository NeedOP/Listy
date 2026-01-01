package se.eli.listy.dto;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private String description;
    private int quantity = 1;
}
