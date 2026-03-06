package com.sms.sms.DTO.admin;

import com.sms.sms.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvisionCredentialRequest {
    private String username;
    private String email;
    private String password;
    private Role role;
}
