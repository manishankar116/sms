package com.sms.sms.DTO.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResponse {
    private Long id;
    private String examName;
    private LocalDate examDate;
    private Long schoolId;
    private Long classId;
    private Long subjectId;
    private Long studentId;
    private String studentName;
    private Long teacherId;
    private String teacherName;
}
