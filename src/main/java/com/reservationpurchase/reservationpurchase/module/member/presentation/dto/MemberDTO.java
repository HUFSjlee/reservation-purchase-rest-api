package com.reservationpurchase.reservationpurchase.module.member.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

        //이 어노테이션 공부하기 -> json 형식 매핑(camel case <-> snake case)
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
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        //private Long id;
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
