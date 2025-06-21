package com.animal.api.find.model.request;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class FindUserPasswordInitRequestDTO {

    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String userid;
}
