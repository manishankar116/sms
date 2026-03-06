package com.sms.sms.DTO.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDto {
    private Long id;
    private String className;
    private String section;
    private Long schoolId;
    private Long classTeacherId;
}
