package com.sns.platform.api.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("200", "성공"),
    FAIL("404","실패"),
    NOT_FOUND_RESOURCE("C100", "리소스를 찾을 수 없습니다."),
    IMPOSSIBLE_RESERVATION("C200","예약할 수 없습니다."),
    NOT_FOUND_RESERVATION("C200","예약을 찾을 수 없습니다."),
    NULL_POINT_ERROR("C404", "널 포인터 예외")
    ;

    private String code;
    private String defaultMessage;
}

