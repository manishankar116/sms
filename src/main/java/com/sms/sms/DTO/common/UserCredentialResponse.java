package com.sms.sms.DTO.common;

import com.sms.sms.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialResponse {
    private Long userId;
    private String username;
    private String email;
    private Role role;
}
