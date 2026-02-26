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

    @NotEmpty(message = "?대찓?쇱쓣 ?낅젰?댁＜?몄슂")
    @Email
    private String email;

    @NotEmpty(message = "鍮꾨?踰덊샇瑜??낅젰?댁＜?몄슂")
    @Pattern(regexp = " ^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d~!@#$%^&*()+|=]{8,20}$",
            message = "8???댁긽?대ŉ 理쒕? 20?먭퉴吏 ?덉슜. 諛섎뱶???レ옄, 臾몄옄 ?ы븿")
    private String password;

    @Builder
    public User toEntity(){
        return User.builder()
                .userEmail(email)
                .userPassword(password)
                .build();
    }
}

