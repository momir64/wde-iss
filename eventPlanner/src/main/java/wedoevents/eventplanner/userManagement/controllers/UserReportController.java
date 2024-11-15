package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.models.UserReport;
import wedoevents.eventplanner.userManagement.services.UserReportService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userReports")
public class UserReportController {

    private final UserReportService userReportService;

    @Autowired
    public UserReportController(UserReportService userReportService) {
        this.userReportService = userReportService;
    }

    @PostMapping
    public ResponseEntity<UserReport> createUserReport(@RequestBody UserReport userReport) {
        UserReport savedReport = userReportService.saveUserReport(userReport);
        return ResponseEntity.ok(savedReport);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserReport> getUserReportById(@PathVariable UUID id) {
        return userReportService.getUserReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserReport>> getAllUserReports() {
        return ResponseEntity.ok(userReportService.getAllUserReports());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserReport(@PathVariable UUID id) {
        userReportService.deleteUserReport(id);
        return ResponseEntity.noContent().build();
    }
}