package com.light.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * author:ligz
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{
    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e) {
        super(message, e);
        this.message = message;
    }
}
