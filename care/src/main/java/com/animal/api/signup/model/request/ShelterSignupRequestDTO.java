package com.animal.api.signup.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterSignupRequestDTO {

    // USERS 테이블 정보
    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    @Pattern(regexp = "^[a-z0-9]{6,20}$", message = "아이디는 영문 소문자와 숫자 조합으로 6~20자 이내여야 합니다.")
    private String id;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(
        regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{9,20}$",
        message = "비밀번호는 공백 없이 영문, 숫자, 특수문자를 포함하여 9~20자 이내여야 합니다."
    )
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글 2~5자여야 합니다.")
    private String name;

    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,20}$", message = "닉네임은 특수문자 없이 2~20자 이내여야 합니다.")
    private String nickname;

    @NotNull(message = "생년월일은 필수 항목입니다.")
    private LocalDate birthDate;

    @NotBlank(message = "성별은 필수 항목입니다.")
    @Pattern(regexp = "^[M|F]$", message = "성별은 M 또는 F여야 합니다.")
    private String gender;

    @NotBlank(message = "전화번호는 필수 항목입니다.")
    @Pattern(regexp = "^\\d{9,20}$", message = "전화번호는 숫자만 입력하며 9~20자 이내여야 합니다.")
    private String tel;

    @NotNull(message = "우편번호는 필수 항목입니다.")
    private Integer zipCode;

    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;

    private String addressDetail;

    // SHELTERS 테이블 공통
    @NotNull(message = "보호소 유형은 필수입니다.")
    private Integer shelterTypeIdx;

    @NotBlank(message = "보호소 연락처는 필수입니다.")
    @Pattern(regexp = "^\\d{9,20}$", message = "보호소 연락처는 숫자만 입력하며 9~20자 이내여야 합니다.")
    private String shelterTel;

    @NotBlank(message = "보호소 이름은 필수입니다.")
    private String shelterName;

    @NotBlank(message = "담당자명은 필수입니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z]{2,50}$", message = "담당자명은 한글 또는 영어 2~50자여야 합니다.")
    private String shelterPersonName;

    @NotNull(message = "보호소 우편번호는 필수입니다.")
    private Integer shelterZipCode;

    @NotBlank(message = "보호소 주소는 필수입니다.")
    private String shelterAddress;

    private String shelterAddressDetail;

    private String shelterDescription;

    // 조건 분기
    private String shelterEmail; // 공공(1)
    private String shelterBusinessNumber; // 공공(1), 민간유(2)
    private Integer shelterBusinessFile;  // 공공(1),민간유(2)
	
}
