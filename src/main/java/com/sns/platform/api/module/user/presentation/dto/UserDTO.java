package com.sns.platform.api.module.user.presentation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sns.platform.api.module.follow.domain.entity.Follow;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

public class UserDTO {

    @Getter
    @SuperBuilder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BaseResponse {
        private Long id;

        @JsonProperty(value = "user_name")
        private String userName;

        @JsonProperty(value = "user_email")
        private String userEmail;

        @NotBlank(message = "비밀번호를 입력해주세요.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                message = "비밀번호는 8~30자이며 영문, 숫자, 특수문자를 포함해야 합니다.")
        @JsonProperty(value = "user_password")
        private String userPassword;

        @JsonProperty(value = "user_profile_image")
        private String userProfileImage;

        @JsonProperty(value = "user_greetings")
        private String userGreetings;

        @OneToMany(mappedBy = "from_user", fetch = FetchType.LAZY)
        private List<Follow> followings;

        @OneToMany(mappedBy = "to_user", fetch = FetchType.LAZY)
        private List<Follow> followers;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateRequest {
        private Long id;
        @NotBlank(message = "이름은 필수입니다.")
        @JsonProperty(value = "user_name")
        private String userName;
        @NotBlank(message = "이메일은 필수입니다.")
        @JsonProperty(value = "user_email")
        private String userEmail;
        @NotBlank(message = "비밀번호는 필수입니다.")
        @JsonProperty(value = "user_password")
        private String userPassword;
        @NotBlank(message = "인사말은 필수입니다.")
        @JsonProperty(value = "user_greetings")
        private String userGreetings;
        @NotBlank(message = "프로필 이미지는 필수입니다.")
        @JsonProperty(value = "user_profile_image")
        private String userProfileImage;
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

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateRequest {
        private Long id;
        @NotBlank(message = "이름은 필수입니다.")
        @JsonProperty(value = "user_name")
        private String userName;
        @NotBlank(message = "프로필 이미지는 필수입니다.")
        @JsonProperty(value = "user_profile_image")
        private String userProfileImage;
        @NotBlank(message = "인사말은 필수입니다.")
        @JsonProperty(value = "user_greetings")
        private String userGreetings;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateResponse {
        private Long id;
        @JsonProperty(value = "user_name")
        private String userName;
        @JsonProperty(value = "user_profile_image")
        private String userProfileImage;
        @JsonProperty(value = "user_greetings")
        private String userGreetings;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdatePasswordRequest {
        @NotBlank(message = "비밀번호는 필수입니다.")
        @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
                message = "비밀번호는 8~30자이며 영문, 숫자, 특수문자를 포함해야 합니다.")
        @JsonProperty(value = "user_password")
        private String userPassword;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdatePasswordResponse {
        @JsonProperty(value = "user_password")
        private String userPassword;
    }
}

