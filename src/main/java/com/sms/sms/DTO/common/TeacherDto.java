package com.sms.sms.DTO.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDto {
    private Long id;
    private String name;
    private String subjectSpecialization;
    private String phone;
    private String email;
    private Long schoolId;
    private Long userId;
}
