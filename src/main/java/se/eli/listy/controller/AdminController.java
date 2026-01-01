package se.eli.listy.controller;

import se.eli.listy.dto.StoreRequest;
import se.eli.listy.model.Store;
import se.eli.listy.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final StoreRepository storeRepository;

    // Add new store
    @PostMapping("/stores")
    public ResponseEntity<Store> addStore(@RequestBody StoreRequest request) {
        Store store = Store.builder()
                .name(request.getName())
                .resetIntervalWeeks(request.getResetIntervalWeeks())
                .resetWeekday(request.getResetWeekday())
                .resetStartDate(request.getResetStartDate())
                .build();
        return ResponseEntity.ok(storeRepository.save(store));
    }

    // Get all stores
    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getAllStores() {
        return ResponseEntity.ok(storeRepository.findAll());
    }

    // Update store
    @PutMapping("/stores/{storeId}")
    public ResponseEntity<Store> updateStore(@PathVariable String storeId, @RequestBody StoreRequest request) {
        Store store = storeRepository.findById(java.util.UUID.fromString(storeId)).orElseThrow();
        store.setName(request.getName());
        store.setResetIntervalWeeks(request.getResetIntervalWeeks());
        store.setResetWeekday(request.getResetWeekday());
        store.setResetStartDate(request.getResetStartDate());
        return ResponseEntity.ok(storeRepository.save(store));
    }
}
