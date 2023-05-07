package com.greenThumb.dto.request;

import com.greenThumb.domain.Role;
import com.greenThumb.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestDto {

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    private String password;

    private Role role;

    public User toEntity(){

        User user = User.builder()
                .username(username)
                .password(password)
                .role(role.USER)
                .build();

        return user;
    }
}
