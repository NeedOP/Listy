package se.eli.listy.service;

import se.eli.listy.model.Store;
import se.eli.listy.model.UserStoreList;
import se.eli.listy.repository.ItemRepository;
import se.eli.listy.repository.StoreRepository;
import se.eli.listy.repository.UserStoreListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final StoreRepository storeRepository;
    private final UserStoreListRepository userStoreListRepository;
    private final ItemRepository itemRepository;

    // Runs every day at 00:05
    @Scheduled(cron = "0 5 0 * * *")
    public void resetStoreLists() {
        LocalDate today = LocalDate.now();
        List<Store> stores = storeRepository.findAll();
        for (Store store : stores) {
            if (store.getResetIntervalWeeks() <= 0 || store.getResetStartDate() == null) continue;
            if (today.getDayOfWeek().getValue() != store.getResetWeekday()) continue;

            long weeksElapsed = ChronoUnit.WEEKS.between(store.getResetStartDate(), today);
            if (weeksElapsed % store.getResetIntervalWeeks() == 0) {
                List<UserStoreList> lists = userStoreListRepository.findAll();
                for (UserStoreList list : lists) {
                    if (list.getStore().getId().equals(store.getId())) {
                        itemRepository.deleteAll(itemRepository.findByList(list));
                        list.setLastReset(today.atStartOfDay());
                        userStoreListRepository.save(list);
                    }
                }
            }
        }
    }
}
