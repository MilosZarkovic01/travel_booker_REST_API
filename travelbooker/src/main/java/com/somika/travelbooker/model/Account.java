package com.somika.travelbooker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Account {

    private Long accountId;

    private String email;

    private String password;

    private boolean active;

    private Role role;

    private LocalDateTime tokenRevokedLastAt;

}
