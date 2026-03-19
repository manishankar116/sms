package com.sms.sms.DTO.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Long subjectId;
    private String subjectName;
    private Long teacherId;
    private String teacherName;
}
