package com.sms.sms.DTO.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemarkRequest {
    private String remark;
    private Long studentId;
    private Long teacherId;
}
