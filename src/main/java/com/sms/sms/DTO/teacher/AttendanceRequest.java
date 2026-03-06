package com.sms.sms.DTO.teacher;

import com.sms.sms.Entity.AttendanceStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceRequest {
    private LocalDate date;
    private AttendanceStatus status;
    private Long studentId;
    private Long classId;
    private Long teacherId;
}
