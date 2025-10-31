package com.sanger.gymsan.dto.user;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDetailsDto {

    private Long id;
    private String username;
    private String avatar;
    private String fullName;
    private String email;
    private String invoiceType;
    private Set<String> roles;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime lastPasswordChangeAt;

}
