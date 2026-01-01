package se.eli.listy.controller;

import se.eli.listy.dto.ItemRequest;
import se.eli.listy.model.*;
import se.eli.listy.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final UserStoreListRepository userStoreListRepository;
    private final ItemRepository itemRepository;

    // Get all stores for user
    @GetMapping("/stores")
    public ResponseEntity<List<Store>> getStores(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        List<UserStoreList> lists = userStoreListRepository.findByUser(user);
        // Optionally sort stores with lists first
        List<Store> stores = storeRepository.findAll();
        stores.sort((a, b) -> {
            boolean aHas = lists.stream().anyMatch(l -> l.getStore().getId().equals(a.getId()));
            boolean bHas = lists.stream().anyMatch(l -> l.getStore().getId().equals(b.getId()));
            return Boolean.compare(!aHas, !bHas);
        });
        return ResponseEntity.ok(stores);
    }

    // Get or create user store list
    @PostMapping("/stores/{storeId}/lists")
    public ResponseEntity<UserStoreList> createList(@PathVariable UUID storeId, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Store store = storeRepository.findById(storeId).orElseThrow();
        UserStoreList list = userStoreListRepository.findByUserAndStore(user, store)
                .orElse(UserStoreList.builder().user(user).store(store).build());
        return ResponseEntity.ok(userStoreListRepository.save(list));
    }

    // Get items for store list
    @GetMapping("/stores/{storeId}/lists")
    public ResponseEntity<List<Item>> getItems(@PathVariable UUID storeId, Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Store store = storeRepository.findById(storeId).orElseThrow();
        UserStoreList list = userStoreListRepository.findByUserAndStore(user, store)
                .orElseThrow();
        return ResponseEntity.ok(itemRepository.findByList(list));
    }

    // Add item
    @PostMapping("/stores/{storeId}/lists/items")
    public ResponseEntity<Item> addItem(@PathVariable UUID storeId,
                                        @RequestBody ItemRequest request,
                                        Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        Store store = storeRepository.findById(storeId).orElseThrow();
        UserStoreList list = userStoreListRepository.findByUserAndStore(user, store)
                .orElseThrow();
        Item item = Item.builder()
                .list(list)
                .name(request.getName())
                .description(request.getDescription())
                .quantity(request.getQuantity())
                .build();
        return ResponseEntity.ok(itemRepository.save(item));
    }

    // Mark item as done
    @PutMapping("/stores/{storeId}/lists/items/{itemId}")
    public ResponseEntity<Item> updateItem(@PathVariable UUID storeId,
                                           @PathVariable UUID itemId,
                                           @RequestBody ItemRequest request,
                                           Authentication auth) {
        Item item = itemRepository.findById(itemId).orElseThrow();
        if (request.getName() != null) item.setName(request.getName());
        if (request.getDescription() != null) item.setDescription(request.getDescription());
        if (request.getQuantity() > 0) item.setQuantity(request.getQuantity());
        item.setDone(request.getQuantity() == 0 ? true : item.isDone());
        return ResponseEntity.ok(itemRepository.save(item));
    }

    // Delete item
    @DeleteMapping("/stores/{storeId}/lists/items/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable UUID storeId,
                                           @PathVariable UUID itemId,
                                           Authentication auth) {
        itemRepository.deleteById(itemId);
        return ResponseEntity.ok().build();
    }
}
