package com.sms.sms.Controller;

import com.sms.sms.DTO.parent.ChildAcademicOverviewResponse;
import com.sms.sms.Service.ParentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/parent")
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @GetMapping("/{parentId}/child-overview")
    public ResponseEntity<ChildAcademicOverviewResponse> getChildOverview(@PathVariable Long parentId) {
        return ResponseEntity.ok(parentService.getChildAcademicOverview(parentId));
    }

    @GetMapping("/users/{userId}/child-overview")
    public ResponseEntity<ChildAcademicOverviewResponse> getChildOverviewByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(parentService.getChildAcademicOverviewByUserId(userId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
