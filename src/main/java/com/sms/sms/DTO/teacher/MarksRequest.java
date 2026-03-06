package com.sms.sms.DTO.teacher;

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
}
