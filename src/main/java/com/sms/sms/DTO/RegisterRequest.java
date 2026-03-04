package com.sms.sms.DTO;

import com.sms.sms.Entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
