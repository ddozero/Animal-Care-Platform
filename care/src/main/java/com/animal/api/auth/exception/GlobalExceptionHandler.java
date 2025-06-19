package com.animal.api.auth.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.animal.api.common.model.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomException(CustomException e) {
        ErrorResponseDTO error = new ErrorResponseDTO(e.getStatusCode(), e.getMessage());
        return ResponseEntity.status(e.getStatusCode()).body(error);
    }

    // 기타 예상치 못한 예외도 오류 전가
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception e) {
        return ResponseEntity.status(500)
                .body(new ErrorResponseDTO(500, "서버 내부 오류: " + e.getMessage()));
    }
}
