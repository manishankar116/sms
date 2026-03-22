package com.sms.sms.DTO.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeworkResponse {
    private Long id;
    private String title;
    private String description;
    private LocalDate assignedDate;
    private LocalDate dueDate;
    private String teacherName;
    private Long classId;
    private String subjectName;
}
