package com.reservationpurchase.reservationpurchase.module.user.presentation.dto;

import com.reservationpurchase.reservationpurchase.module.user.domain.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class EmailRequestDto {

    @NotEmpty(message = "이메일을 입력해주세요")
    @Email
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Pattern(regexp = " ^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "8자 이상이며 최대 20자까지 허용. 반드시 숫자, 문자 포함")
    private String password;

    @Builder
    public User toEntity(){
        return User.builder()
                .userEmail(email)
                .userPassword(password)
                .build();
    }
}
