package wedoevents.eventplanner.shared.services.schedulerService;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.eventManagement.models.ServiceBudgetItem;
import wedoevents.eventplanner.eventManagement.repositories.ServiceBudgetItemRepository;
import wedoevents.eventplanner.notificationManagement.models.NotificationType;
import wedoevents.eventplanner.notificationManagement.services.NotificationService;
import wedoevents.eventplanner.userManagement.services.userTypes.SellerService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {
    private final ServiceBudgetItemRepository serviceBudgetItemRepository;
    private final NotificationService notificationService;
    private final SellerService sellerService;

    @Scheduled(fixedRate = 600000)  // 10 minutes
    public void pollAndTriggerEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<ServiceBudgetItem> serviceBudgetItems = serviceBudgetItemRepository.findAll();

        for (ServiceBudgetItem sbi : serviceBudgetItems) {
            if ((sbi.getIsTriggered() == null || !sbi.getIsTriggered()) && sbi.getService() != null && now.isAfter(sbi.getStartTime().minusHours(1))) {
                sellerService.getSellerByServiceId(sbi.getService().getStaticServiceId()).ifPresent(seller -> {
                    notificationService.sendNotification(seller.getProfile(), "Service scheduled", "Your service " + sbi.getService().getName() + " has been scheduled in less than an hour!", NotificationType.SERVICE, sbi.getService().getStaticServiceId());
                });
                sbi.setIsTriggered(true);
                serviceBudgetItemRepository.save(sbi);
            }
        }
    }
}
