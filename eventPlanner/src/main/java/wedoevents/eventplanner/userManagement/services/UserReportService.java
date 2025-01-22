package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.dtos.UserReportResponseDTO;
import wedoevents.eventplanner.userManagement.models.PendingStatus;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.UserReport;
import wedoevents.eventplanner.userManagement.repositories.UserReportRepository;
import java.time.LocalDateTime;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserReportService {

    private final UserReportRepository userReportRepository;

    private final UserService userService;

    @Autowired
    public UserReportService(UserReportRepository userReportRepository, UserService userService) {
        this.userReportRepository = userReportRepository;
        this.userService = userService;
    }

    public UserReport saveUserReport(UserReport userReport) {
        return userReportRepository.save(userReport);
    }

    public Optional<UserReport> getUserReportById(UUID id) {
        return userReportRepository.findById(id);
    }

    public List<UserReport> getAllUserReports() {
        return userReportRepository.findAll();
    }

    public void deleteUserReport(UUID id) {
        userReportRepository.deleteById(id);
    }

    public Optional<UserReport> getMostRecentBanForProfile(UUID profileId) {
        return userReportRepository.findMostRecentBanForProfile(profileId);
    }

    public LocalDateTime getBanEndDateIfBanned(UUID profileId) {
        Optional<UserReport> recentBan = getMostRecentBanForProfile(profileId);

        if (recentBan.isPresent()) {
            LocalDateTime banStartDateTime = recentBan.get().getBanStartDateTime();
            if (banStartDateTime != null && recentBan.get().getStatus() == PendingStatus.APPROVED) {
                LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3);
                if (banStartDateTime.isAfter(threeDaysAgo)) {
                    return banStartDateTime.plusDays(3);
                }
            }
        }
        return null;
    }

    public void createUserReport(Profile from, Profile to, String reason) {
        UserReport userReport = new UserReport();
        userReport.setReportDateTime(LocalDateTime.now());
        userReport.setFrom(from);
        userReport.setTo(to);
        userReport.setReason(reason);
        userReport.setStatus(PendingStatus.PENDING);
        userReport.setBanStartDateTime(null);
        userReportRepository.save(userReport);
    }

    public void processUserReport(UserReport report) {
        if(report.getStatus() == PendingStatus.APPROVED) {
            report.setBanStartDateTime(LocalDateTime.now());
        }
        userReportRepository.save(report);
    }

    public List<UserReportResponseDTO> getAllPendingReports() {
        List<UserReport> pendingReports = userReportRepository.findAllByStatus(PendingStatus.PENDING);

        return pendingReports.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    private UserReportResponseDTO mapToDTO(UserReport userReport) {
        UserReportResponseDTO dto = new UserReportResponseDTO();
        dto.setId(userReport.getId());
        dto.setReason(userReport.getReason());
        dto.setReportDateTime(userReport.getReportDateTime());
        dto.setProfileToCredentials(userService.getUserCredentials(userReport.getTo().getId()));
        dto.setProfileFromCredentials(userService.getUserCredentials(userReport.getFrom().getId()));
        return dto;
    }
}