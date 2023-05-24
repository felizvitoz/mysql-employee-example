package com.error.customerror;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException {

    private Integer code;

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }


    public ApplicationException(String message, Integer code) {
        super(message);
        this.code = code;
    }
}
