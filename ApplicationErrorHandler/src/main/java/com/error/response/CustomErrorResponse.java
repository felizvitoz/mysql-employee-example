package com.error.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomErrorResponse {

    private Integer errorCode;

    private String message;

}
