package com.sms.sms.Controller;

import com.sms.sms.DTO.common.SubjectDto;
import com.sms.sms.Service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.upsertSubject(null, request));
    }

    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long subjectId, @RequestBody SubjectDto request) {
        return ResponseEntity.ok(subjectService.upsertSubject(subjectId, request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
