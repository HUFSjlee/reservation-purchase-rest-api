package com.reservationpurchase.reservationpurchase.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    SUCCESS("200", "Success"),

    FAIL("404","Fail"),
    NOT_FOUND_RESOURCE("C100", "Not Found Resources"),

    IMPOSSIBLE_RESERVATION("C200","Impossible Reservation"),
    NOT_FOUND_RESERVATION("C200","Not found Reservation"),

    NULL_POINT_ERROR("C404", "Null Point Exception")
    ;

    private String code;
    private String defaultMessage;
}
