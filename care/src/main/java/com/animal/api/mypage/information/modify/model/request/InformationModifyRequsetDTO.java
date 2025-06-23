package com.animal.api.mypage.information.modify.model.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InformationModifyRequsetDTO {

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글 2~5자여야 합니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 입력 목록입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,20}$", message = "닉네임은 특수문자 없이 2~20자 이내여야 합니다.")
    private String nickname;

    @NotBlank(message = "생년월일은 필수 항목입니다.")
    private String birthDate;

    @NotBlank(message = "성별은 필수 선택 입니다.")
    @Pattern(regexp = "^(M|F)$", message = "성별은 'M' 또는 'F'여야 합니다.")
    private String gender;

    @NotBlank(message = "전화번호는 필수 입력 목록입니다.")
    @Pattern(regexp = "^\\d{9,20}$", message = "전화번호는 숫자만 입력하며 9~20자 이내여야 합니다.")
    private String tel;

    @Positive(message = "우편번호는 숫자만 입력가능합니다.")
    private int zipCode;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    private String addressDetail;	
}
