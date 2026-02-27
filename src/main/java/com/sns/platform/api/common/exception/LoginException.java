package com.sns.platform.api.common.exception;

import com.sns.platform.api.common.response.ResponseCode;

public class LoginException extends BusinessException {
    public LoginException() {
        super(ResponseCode.NOT_FOUND_RESOURCE, ResponseCode.NOT_FOUND_RESOURCE.getDefaultMessage());
    }

    public LoginException(String customMessage) {
        super(ResponseCode.NOT_FOUND_RESOURCE, customMessage);
    }

    public LoginException(ResponseCode responseCode, String customMessage) {
        super(responseCode, customMessage);
    }
}

