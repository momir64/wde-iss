package wedoevents.eventplanner.userManagement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wedoevents.eventplanner.userManagement.dtos.UserReportDTO;
import wedoevents.eventplanner.userManagement.dtos.UserReportResponseDTO;
import wedoevents.eventplanner.userManagement.models.Profile;
import wedoevents.eventplanner.userManagement.models.UserReport;
import wedoevents.eventplanner.userManagement.services.ProfileService;
import wedoevents.eventplanner.userManagement.services.UserReportService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/userReports")
public class UserReportController {

    private final UserReportService userReportService;
    private final ProfileService profileService;

    @Autowired
    public UserReportController(UserReportService userReportService, ProfileService profileService) {
        this.userReportService = userReportService;
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<?> createUserReport(@RequestBody UserReportDTO reportDTO) {

        if(reportDTO.getUserProfileToId() == reportDTO.getUserProfileFromId()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cannot report yourself");
        }

        Optional<Profile> from = profileService.findProfileById(reportDTO.getUserProfileFromId());
        if(from.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile not found");
        }
        Optional<Profile> to = profileService.findProfileById(reportDTO.getUserProfileToId());
        if(to.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Profile not found");
        }
        userReportService.createUserReport(from.get(),to.get(),reportDTO.getReason());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<?> processUserReport(@RequestBody UserReportDTO reportDTO) {
        Optional<UserReport> reportOptional = userReportService.getUserReportById(reportDTO.getId());
        if(reportOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(reportDTO);
        }
        UserReport report = reportOptional.get();
        report.setStatus(reportDTO.getStatus());
        userReportService.processUserReport(report);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getPendingUserReports() {
        List<UserReportResponseDTO> pendingReports = userReportService.getAllPendingReports();
        return ResponseEntity.ok(pendingReports);
    }
}