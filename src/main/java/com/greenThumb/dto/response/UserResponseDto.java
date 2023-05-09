package com.greenThumb.dto.response;

import com.greenThumb.domain.Role;
import com.greenThumb.domain.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;
    private String username;
    private String password;
    private Role role;

    /* entity => dto */
    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.role = user.getRole();
    }
}
