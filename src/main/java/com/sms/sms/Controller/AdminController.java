package com.sms.sms.Controller;

import com.sms.sms.DTO.admin.ProvisionCredentialRequest;
import com.sms.sms.DTO.common.*;
import com.sms.sms.Service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final AdminService subjectService;

    @PostMapping("/schools")
    public ResponseEntity<SchoolDto> createSchool(@RequestBody SchoolDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.upsertSchool(null, request));
    }

    @PutMapping("/schools/{schoolId}")
    public ResponseEntity<SchoolDto> updateSchool(@PathVariable Long schoolId, @RequestBody SchoolDto request) {
        return ResponseEntity.ok(adminService.upsertSchool(schoolId, request));
    }

    @PostMapping("/teachers")
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody TeacherDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.upsertTeacher(null, request));
    }

    @PutMapping("/teachers/{teacherId}")
    public ResponseEntity<TeacherDto> updateTeacher(@PathVariable Long teacherId, @RequestBody TeacherDto request) {
        return ResponseEntity.ok(adminService.upsertTeacher(teacherId, request));
    }

    @PostMapping("/parents")
    public ResponseEntity<ParentDto> createParent(@RequestBody ParentDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.upsertParent(null, request));
    }

    @PutMapping("/parents/{parentId}")
    public ResponseEntity<ParentDto> updateParent(@PathVariable Long parentId, @RequestBody ParentDto request) {
        return ResponseEntity.ok(adminService.upsertParent(parentId, request));
    }

    @PostMapping("/students")
    public ResponseEntity<StudentDto> createStudent(@RequestBody StudentDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.upsertStudent(null, request));
    }

    @PutMapping("/students/{studentId}")
    public ResponseEntity<StudentDto> updateStudent(@PathVariable Long studentId, @RequestBody StudentDto request) {
        return ResponseEntity.ok(adminService.upsertStudent(studentId, request));
    }

    @PostMapping("/classes")
    public ResponseEntity<SchoolClassDto> createClass(@RequestBody SchoolClassDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.upsertClass(null, request));
    }

    @PutMapping("/classes/{classId}")
    public ResponseEntity<SchoolClassDto> updateClass(@PathVariable Long classId, @RequestBody SchoolClassDto request) {
        return ResponseEntity.ok(adminService.upsertClass(classId, request));
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectDto> createSubject(@RequestBody SubjectDto request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subjectService.upsertSubject(null, request));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectDto> updateSubject(@PathVariable Long subjectId, @RequestBody SubjectDto request) {
        return ResponseEntity.ok(subjectService.upsertSubject(subjectId, request));
    }

    @PostMapping("/teachers/{teacherId}/credentials")
    public ResponseEntity<UserCredentialResponse> provisionTeacherCredentials(@PathVariable Long teacherId,
                                                                              @RequestBody ProvisionCredentialRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.provisionTeacherCredentials(teacherId, request));
    }

    @PostMapping("/parents/{parentId}/credentials")
    public ResponseEntity<UserCredentialResponse> provisionParentCredentials(@PathVariable Long parentId,
                                                                             @RequestBody ProvisionCredentialRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(adminService.provisionParentCredentials(parentId, request));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
