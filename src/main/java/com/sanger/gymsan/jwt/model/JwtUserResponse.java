package com.sanger.gymsan.jwt.model;

import java.util.Set;

import com.sanger.gymsan.dto.user.GetUsersDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtUserResponse extends GetUsersDto {

    private String token;

    @Builder(builderMethodName = "jwtUserResponseBuilder")
    public JwtUserResponse(Long id, String username, String avatar, String fullName, String email, Set<String> roles,
            String token, Boolean enabled) {
        super(id, username, avatar, fullName, email, roles, enabled);
        this.token = token;
    }

}
