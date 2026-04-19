package com.sms.sms.Controller;

import com.sms.sms.DTO.common.StudentDto;
import com.sms.sms.DTO.teacher.*;
import com.sms.sms.Entity.SchoolClass;
import com.sms.sms.Service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/attendance")
    public ResponseEntity<AttendanceResponse> recordAttendance(@RequestBody AttendanceRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.recordAttendance(request));
    }

    @PostMapping("/homework")
    public ResponseEntity<HomeworkResponse> createHomework(@RequestBody HomeworkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.createHomework(request));
    }

    @PostMapping("/marks")
    public ResponseEntity<MarksResponse> createMarks(@RequestBody MarksRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.upsertMarks(null, request));
    }

    @PutMapping("/marks/{marksId}")
    public ResponseEntity<MarksResponse> updateMarks(@PathVariable Long marksId, @RequestBody MarksRequest request) {
        return ResponseEntity.ok(teacherService.upsertMarks(marksId, request));
    }

    @PostMapping("/remarks")
    public ResponseEntity<RemarkResponse> createRemark(@RequestBody RemarkRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.upsertRemark(null, request));
    }

    @PutMapping("/remarks/{remarkId}")
    public ResponseEntity<RemarkResponse> updateRemark(@PathVariable Long remarkId, @RequestBody RemarkRequest request) {
        return ResponseEntity.ok(teacherService.upsertRemark(remarkId, request));
    }

    @PostMapping("/exams")
    public ResponseEntity<ExamResponse> createExam(@RequestBody ExamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.upsertExam(null, request));
    }

    @PutMapping("/exams/{examId}")
    public ResponseEntity<ExamResponse> updateExam(@PathVariable Long examId, @RequestBody ExamRequest request) {
        return ResponseEntity.ok(teacherService.upsertExam(examId, request));
    }

    @PostMapping("/announcements")
    public ResponseEntity<AnnouncementResponse> createAnnouncement(@RequestBody AnnouncementRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teacherService.upsertAnnouncement(null, request));
    }

    @PutMapping("/announcements/{announcementId}")
    public ResponseEntity<AnnouncementResponse> updateAnnouncement(@PathVariable Long announcementId,
                                                                   @RequestBody AnnouncementRequest request) {
        return ResponseEntity.ok(teacherService.upsertAnnouncement(announcementId, request));
    }

    @GetMapping("/classes/{classId}/students")
    public ResponseEntity<List<StudentDto>> listStudentsByClass(@PathVariable Long classId) {
        return ResponseEntity.ok(teacherService.listStudentsByClass(classId));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
