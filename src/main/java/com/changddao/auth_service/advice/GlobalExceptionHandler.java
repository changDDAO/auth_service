package com.changddao.auth_service.advice;

import com.changddao.auth_service.dto.response.Result;
import com.changddao.auth_service.exception.DuplicatedEmailException;
import com.changddao.auth_service.exception.FileUploadException;
import com.changddao.auth_service.service.ResponseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final ResponseService responseService;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result defaultException(Exception e) {
        return responseService.handleFailResult(500, e.getMessage());
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result duplicatedEmail(DuplicatedEmailException exception){
        return responseService.handleFailResult(409, exception.getMessage());
    }

    @ExceptionHandler(FileUploadException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result failUploadFile(FileUploadException exception){
        return responseService.handleFailResult(500, exception.getMessage());
    }

}
