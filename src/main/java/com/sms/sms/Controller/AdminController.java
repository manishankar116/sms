package com.sms.sms.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping("/dashboard")
    public ResponseEntity<String> dashboard() {
        return ResponseEntity.ok("Admin dashboard");
    }

    @PostMapping("/announcements")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAnnouncement(@RequestBody String announcement) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Created announcement");
    }

    @PutMapping("/announcements/{announcementId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateAnnouncement(@PathVariable Long announcementId, @RequestBody String announcement) {
        return ResponseEntity.ok("Updated announcement " + announcementId);
    }

    @DeleteMapping("/announcements/{announcementId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Long announcementId) {
        return ResponseEntity.ok("Deleted announcement " + announcementId);
    }
}
