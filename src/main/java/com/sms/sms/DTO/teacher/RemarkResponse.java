package com.sms.sms.DTO.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarkResponse {
    private Long id;
    private String remark;
    private LocalDateTime createdAt;
    private Long studentId;
    private Long teacherId;
}
