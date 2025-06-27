package com.animal.api.common.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.model.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(CustomException.class)
	public ResponseEntity<Object> handleCustomException(CustomException e) {

	    // 기본 구조 수동 구성
	    Map<String, Object> error = new LinkedHashMap<>();
	    error.put("errorCode", e.getStatusCode());
	    error.put("errorMsg", e.getMessage());

	    // data가 있을 경우만 추가
	    if (e.getData() != null) {
	        error.put("data", e.getData());
	    }

	    return ResponseEntity.status(e.getStatusCode()).body(error);
	}

	// 기타 예상치 못한 예외도 오류 전가
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception e) {
		return ResponseEntity.status(500).body(new ErrorResponseDTO(500, "서버 내부 오류: " + e.getMessage()));
	}
	
	//회원가입 유효성 검사 통과 못했을 시
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDTO> handleValidationException(MethodArgumentNotValidException ex){ 
		
		String message = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> error.getDefaultMessage())
				.findFirst()
				.orElse("입력값이 유효하지 않습니다");
		
		return ResponseEntity.badRequest().body(new ErrorResponseDTO(400, message));
	}


}
