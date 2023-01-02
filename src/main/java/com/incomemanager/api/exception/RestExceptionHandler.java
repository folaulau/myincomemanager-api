package com.incomemanager.api.exception;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// import javax.persistence.PersistenceException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.TransientPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import jakarta.persistence.PersistenceException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ApiError> handleGetException(ApiException ex) {
        log.info("handleApiException(..)");

        ApiError apiError = ex.getError();

        log.debug("Friendly Msg: {}", apiError.getMessage());
        String errors = StringUtils.join(apiError.getErrors(), ",");
        log.debug("API response detailed message: {}", errors);

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        if (null != apiError.getStatus()) {
            httpStatus = apiError.getStatus();
        }

        return new ResponseEntity<>(ex.getError(), httpStatus);
    }

    // /**
    // * Access Denied Exception
    // *
    // * @param ex
    // * @return
    // */
    // @ExceptionHandler(AccessDeniedException.class)
    // protected ResponseEntity<ApiError> handleAccessDeniedException(AccessDeniedException ex) {
    // log.info("handleAccessDeniedException(..)");
    // ApiError apiError = new ApiError(HttpStatus.FORBIDDEN, "You do not have access to this api.", null,
    // Collections.singletonList(ex.getLocalizedMessage()));
    //
    // log.debug("Friendly Msg: {}", apiError.getMessage());
    // String errors = StringUtils.join(apiError.getErrors(), ",");
    // log.debug("API response detailed message: {}", errors);
    //
    // HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    //
    // if (null != apiError.getStatus()) {
    // httpStatus = apiError.getStatus();
    // }
    //
    // return new ResponseEntity<>(apiError, httpStatus);
    // }

    /**
     * Fall back exception handler - if all fails, I WILL CATCH YOU!
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiError> handleOther(Exception ex) {
        log.error("handleOther(..)", ex);
        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;

        StringBuilder errorString = new StringBuilder();
        try {
            errorString.append(ex.getClass().getName()).append(" ");
            StackTraceElement[] traceArray = ex.getStackTrace();
            if (null != traceArray && 0 < traceArray.length) {
                for (StackTraceElement stackTraceElement : traceArray) {
                    errorString.append(stackTraceElement.toString());
                    if (StringUtils.contains(stackTraceElement.getClassName(), "company")) {
                        break;
                    } else {
                        errorString.append(",");
                    }
                }
            }
        } catch (Exception e) {
            log.warn("ignore");
        }

        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, Collections.singletonList(errorString.toString()));
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        if (null != apiError.getStatus()) {
            httpStatus = apiError.getStatus();
        }

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(JsonMappingException.class)
    protected ResponseEntity<ApiError> handleJsonMappingException(JsonMappingException ex) {
        log.info("handleJsonMappingException(..)");
        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, Collections.singletonList(ex.getLocalizedMessage()));

        HttpStatus httpStatus = apiError.getStatus();

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(JsonProcessingException.class)
    protected ResponseEntity<ApiError> handleJsonProcessingException(JsonProcessingException ex) {
        log.info("handleJsonProcessingException(..)");
        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, Collections.singletonList(ex.getLocalizedMessage()));

        HttpStatus httpStatus = apiError.getStatus();

        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(PersistenceException.class)
    protected ResponseEntity<ApiError> handleDatabaseException(PersistenceException ex) {
        log.info("handleDatabaseException(..)");
        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, Collections.singletonList(ex.getLocalizedMessage()));
        HttpStatus httpStatus = apiError.getStatus();

        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(TransientPropertyValueException.class)
    protected ResponseEntity<ApiError> handleTransientPropertyValueException(TransientPropertyValueException ex) {
        log.info("handleTransientPropertyValueException(..)");
        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, Collections.singletonList(ex.getLocalizedMessage()));
        HttpStatus httpStatus = apiError.getStatus();

        return new ResponseEntity<>(apiError, httpStatus);
    }

    /**
     * Stripe Exception
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ApiError> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.info("handleIllegalArgumentException(..)");

        String errorMessage = null != ex.getLocalizedMessage() ? ex.getLocalizedMessage() : ApiError.DEFAULT_MSG;
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ApiError.DEFAULT_MSG, errorMessage, ex.getMessage());

        log.debug("Friendly Msg: {}", apiError.getMessage());
        String errors = StringUtils.join(apiError.getErrors(), ",");
        log.debug("API response detailed message: {}", errors);

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    private List<ApiSubError> getFormErrors(BindingResult bindingResult) {

        List<ApiSubError> errors = bindingResult.getFieldErrors().stream().map(fieldError -> {

            String field = fieldError.getField();

            String errorMsg = null;

            if (fieldError.getDefaultMessage() != null) {
                errorMsg = fieldError.getDefaultMessage();
            }

            ApiSubError error = new ApiSubError();
            error.setObject(fieldError.getObjectName());
            error.setField(field);
            error.setRejectedValue(fieldError.getRejectedValue());
            error.setMessage(errorMsg);

            return error;
        }).collect(Collectors.toList());
        return errors;
    }

}
