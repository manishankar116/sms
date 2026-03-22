package com.sms.sms.DTO.teacher;

import com.sms.sms.Entity.ExamGrade;
import com.sms.sms.Entity.ExamStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MarksRequest {
    private Integer marks;
    private Integer maxMarks;
    private Long studentId;
    private Long examId;
    private Long teacherId;
    private ExamStatus status;
    private ExamGrade grade;
}
