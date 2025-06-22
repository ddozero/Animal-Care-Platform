package com.animal.api.find.shelter.model.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 보호소 비밀번호 초기화 요청 DTO (1단계: 아이디 확인)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindShelterPasswordInitRequestDTO {

    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    private String userid;
}
