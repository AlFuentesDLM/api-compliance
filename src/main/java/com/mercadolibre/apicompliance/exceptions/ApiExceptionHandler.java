package com.mercadolibre.apicompliance.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponseExtended handlerException(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        return processField(fieldErrors);
    }

    @ExceptionHandler
    @ResponseBody
    public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
        return  ResponseEntity
                .status(ex.getStatus())
                .body(new ApiResponse(ex.getError(),ex.getStatus(),ex.getMessage()));
    }


    private ApiResponseExtended processField(List<FieldError> fieldErrors) {
        HashMap<String, String> fields = new HashMap<>();
        for (FieldError fieldError : fieldErrors) {
            fields.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return new ApiResponseExtended(HttpStatus.BAD_REQUEST.value(), "Validations Error", fields);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handlerException(MethodArgumentTypeMismatchException exception) {
        return new ApiResponse("bad_request",400,exception.getMessage());
    }
    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse handlerException(HttpMessageNotReadableException exception) {
        return new ApiResponse("bad_request",400,exception.getMessage());
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
    public ApiResponse handlerException(HttpRequestMethodNotSupportedException exception) {
        return new ApiResponse("method_not_allowed",405,exception.getMessage());
    }
}
