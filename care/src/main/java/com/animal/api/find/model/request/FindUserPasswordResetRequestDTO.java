package com.animal.api.find.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 비밀번호 재설정 요청 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FindUserPasswordResetRequestDTO {

    @NotBlank(message = "아이디는 필수입니다.")
    private String userid;

    @NotBlank(message = "새 비밀번호는 필수입니다.")
    @Size(min = 9, max = 20, message = "비밀번호는 9자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{9,20}$",
             message = "영문자+숫자 포함 필수입니다.")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String confirmPassword;

    @NotBlank(message = "캡차 입력은 필수입니다.")
    private String captcha;
}
