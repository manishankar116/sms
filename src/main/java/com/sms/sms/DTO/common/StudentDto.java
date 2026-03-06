package com.sms.sms.DTO.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {
    private Long id;
    private String name;
    private String rollNo;
    private LocalDate dateOfBirth;
    private String gender;
    private Long schoolId;
    private Long classId;
}
