package com.changddao.auth_service.advice;

import com.changddao.auth_service.dto.response.Result;
import com.changddao.auth_service.exception.DuplicatedEmailException;
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
    public Result defaultException() {
        return responseService.handleFailResult(500, "오류가 발생 하였습니다.");
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Result duplicatedEmail(DuplicatedEmailException exception){
        return responseService.handleFailResult(409, exception.getMessage());
    }

}
