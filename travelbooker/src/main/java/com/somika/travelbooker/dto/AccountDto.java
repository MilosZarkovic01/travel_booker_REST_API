package com.somika.travelbooker.dto;

import com.somika.travelbooker.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDto {
    private Long id;
    private String email;
    private boolean active;
    private Role role;
}
