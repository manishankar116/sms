package com.sms.sms.Controller;

import com.sms.sms.DTO.RemarkRequest;
import com.sms.sms.Service.StudentAccessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final StudentAccessService studentAccessService;

    @GetMapping("/students/{studentId}")
    public ResponseEntity<String> getStudent(@PathVariable Long studentId, Authentication authentication) {
        return ResponseEntity.ok(studentAccessService.getStudentDataForTeacher(authentication.getName(), studentId));
    }

    @PostMapping("/students/{studentId}/remarks")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> createRemark(@PathVariable Long studentId,
                                               @RequestBody RemarkRequest request,
                                               Authentication authentication) {
        String response = studentAccessService.createRemark(authentication.getName(), studentId, request.remark());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/students/{studentId}/remarks/{remarkId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> updateRemark(@PathVariable Long studentId,
                                               @PathVariable Long remarkId,
                                               @RequestBody RemarkRequest request,
                                               Authentication authentication) {
        String response = studentAccessService.updateRemark(authentication.getName(), studentId, remarkId, request.remark());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/students/{studentId}/remarks/{remarkId}")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<String> deleteRemark(@PathVariable Long studentId,
                                               @PathVariable Long remarkId,
                                               Authentication authentication) {
        String response = studentAccessService.deleteRemark(authentication.getName(), studentId, remarkId);
        return ResponseEntity.ok(response);
    }
}
