package com.reservationpurchase.reservationpurchase.module.user.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
public class EmailLoginRequestDto {
    @Email
    private String email;
    @NotNull
    private String password;
}
