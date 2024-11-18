package wedoevents.eventplanner.userManagement.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wedoevents.eventplanner.userManagement.models.UserReport;
import wedoevents.eventplanner.userManagement.repositories.UserReportRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserReportService {

    private final UserReportRepository userReportRepository;

    @Autowired
    public UserReportService(UserReportRepository userReportRepository) {
        this.userReportRepository = userReportRepository;
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
}