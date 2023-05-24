package com.error.customerror;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
