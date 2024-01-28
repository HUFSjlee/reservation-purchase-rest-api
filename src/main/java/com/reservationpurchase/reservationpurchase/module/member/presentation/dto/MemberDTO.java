package com.reservationpurchase.reservationpurchase.module.member.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
public class MemberDTO {
    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;

        @JsonProperty(value = "member_name")
        private String memberName;

        @JsonProperty(value = "member_email")
        private String memberEmail;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                message = "비밀번호는 8~30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
        @JsonProperty(value = "member_password")
        private String memberPassword;

        @JsonProperty(value = "member_profile_image")
        private String memberProfileImage;

        @JsonProperty(value = "member_greetings")
        private String memberGreetings;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        @JsonProperty(value = "member_name")
        private String memberName;
        @JsonProperty(value = "member_email")
        private String memberEmail;
        @JsonProperty(value = "member_password")
        private String memberPassword;
        @JsonProperty(value = "member_profile_image")
        private String memberProfileImage;
        @JsonProperty(value = "member_greetings")
        private String memberGreetings;

        private String checkedPassword;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateResponse {
        private Long id;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FindResponse {
        private Long id;
    }
}
