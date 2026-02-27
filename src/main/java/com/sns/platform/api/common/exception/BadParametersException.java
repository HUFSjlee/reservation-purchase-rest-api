package com.sns.platform.api.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, code = HttpStatus.BAD_REQUEST, reason = "요청 파라미터가 올바르지 않습니다.")
public class BadParametersException extends RuntimeException {
    public BadParametersException() {
        super("요청 파라미터가 올바르지 않습니다.");
    }

    public BadParametersException(String message) {
        super(message);
    }
}

