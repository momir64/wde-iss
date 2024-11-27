package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.UserReportDTO;
import wedoevents.eventplanner.userManagement.models.UserReport;
import wedoevents.eventplanner.userManagement.services.UserReportService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/userReports")
public class UserReportController {

    private final UserReportService userReportService;

    @Autowired
    public UserReportController(UserReportService userReportService) {
        this.userReportService = userReportService;
    }

    @PostMapping
    public ResponseEntity<?> createUserReport(@RequestBody UserReportDTO reportDTO) {
        try {
            // call create report service
            return ResponseEntity.ok("Report created successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid report data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to report this listing");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
    }

    @PutMapping
    public ResponseEntity<?> processUserReport(@RequestBody UserReportDTO reportDTO) {
        try {
            // call process report service
            return ResponseEntity.ok("Report processed successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid report data");
//        } catch (UnauthorizedException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized to process this report");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body("Exception");
        }
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