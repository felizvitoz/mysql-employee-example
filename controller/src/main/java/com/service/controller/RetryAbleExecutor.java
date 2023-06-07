package com.service.controller;

import com.error.customerror.ApplicationException;
import com.error.customerror.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

@Slf4j
public abstract class RetryAbleExecutor {
    @Retryable(value = { Exception.class }, maxAttempts = 3, backoff = @Backoff(delay = 500))
    public static void retryAbleExecution(Runnable runnable) {
        try {
            runnable.run();
        } catch (ApplicationException ax) {
            throw ax;
        } catch (BusinessException bx) {
            throw bx;
        } catch (Exception ex) {
            log.error("There is error when executing UseCase {} ", ex.getMessage());
            throw new ApplicationException("Something went wrong.");
        }
    }

}
