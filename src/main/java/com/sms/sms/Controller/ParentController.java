package com.sms.sms.Controller;

import com.sms.sms.Service.StudentAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final StudentAccessService studentAccessService;

    @GetMapping("/children/{studentId}/progress")
    public ResponseEntity<String> getChildProgress(@PathVariable Long studentId, Authentication authentication) {
        return ResponseEntity.ok(studentAccessService.getStudentProgressForParent(authentication.getName(), studentId));
    }

    @PutMapping("/children/{studentId}/contact")
    @PreAuthorize("hasRole('PARENT')")
    public ResponseEntity<String> updateContactInfo(@PathVariable Long studentId, Authentication authentication) {
        studentAccessService.getStudentProgressForParent(authentication.getName(), studentId);
        return ResponseEntity.ok("Updated contact info for student " + studentId);
    }
}
