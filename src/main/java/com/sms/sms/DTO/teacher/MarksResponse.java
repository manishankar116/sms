package com.sms.sms.DTO.teacher;

import com.sms.sms.Entity.ExamGrade;
import com.sms.sms.Entity.ExamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksResponse {
    private Long id;
    private Integer marks;
    private Integer maxMarks;
    private Long studentId;
    private String studentName;
    private Long examId;
    private String examName;
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
    private ExamStatus status;
    private ExamGrade grade;
    private LocalDate examDate;
}
