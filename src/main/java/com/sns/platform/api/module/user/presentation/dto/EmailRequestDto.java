package com.sns.platform.api.module.user.presentation.dto;

import com.sns.platform.api.module.user.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailRequestDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = " ^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "비밀번호는 8~20자이며 영문과 숫자를 포함해야 합니다.")
    private String password;

    @Builder
    public User toEntity(){
        return User.builder()
                .userEmail(email)
                .userPassword(password)
                .build();
    }
}

