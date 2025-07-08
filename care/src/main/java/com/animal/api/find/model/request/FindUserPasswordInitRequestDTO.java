package com.animal.api.find.model.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserPasswordInitRequestDTO {

    @NotBlank(message = "아이디는 필수 입력입니다.")
    private String userid;
}
