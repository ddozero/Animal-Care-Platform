package com.animal.api.find.shelter.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

/**
 * 보호소 아이디 찾기 요청 DTO
 */
@Data
public class FindShelterIdRequestDTO {

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "인증번호는 필수 입력 항목입니다.")
    private String code;  // 이메일 인증번호
}
