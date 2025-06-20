package com.animal.api.signup.model.request;

import java.time.LocalDate;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Data;

@Data
public class UserSignupRequestDTO {

	@NotBlank(message = "아이디는 필수 입력 항목입니다")
	@Pattern(regexp = "^[a-z0-9]{6,10}$", message = "아이디는 영문 소문자와 숫자 조합으로 6~10자 이내여야 합니다")
    private String id;
	
	@NotBlank(message = "이메일은 필수 항목 입니다")
	@Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;
	
	@NotBlank(message = "비밀번호는 필수 입력 항목입니다")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()_+=-]).{9,20}$", 
    message = "비밀번호는 공백 없이 영문, 숫자, 특수문자를 포함하여 9~20자 이내여야 합니다.")
    private String password;

    @NotBlank(message = "이름은 필수 항목입니다.")
    @Pattern(regexp = "^[가-힣]{2,5}$", message = "이름은 한글 2~5자여야 합니다.") // 외국인 포함하고 싶으면 조정 가능
    private String name;
    
    @NotBlank(message = "닉네임은 필수 항목입니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]{2,10}$", message = "닉네임은 특수문자 없이 2~10자 이내여야 합니다.")
    private String nickname;

    @NotNull(message = "생년월일은 필수 항목입니다.")  
    private LocalDate birthDate;
    
    @NotBlank(message = "성별은 필수 항목입니다.")
    @Pattern(regexp = "^[MF]$", message = "성별은 남성 또는 여성이야 합니다.")   
    private String gender; // 'M' or 'F'
    
    @NotNull(message = "전화번호는 필수 항목입니다.")
    @Digits(integer = 11, fraction = 0, message = "전화번호는 숫자만 입력해야 합니다.")   
    private Integer tel;

    @NotNull(message = "우편번호는 필수 항목입니다.")
    private Integer zipCode;
    
    @NotBlank(message = "주소는 필수 항목입니다.")
    private String address;
    
    @NotBlank(message = "상세 주소는 필수 항목 입니다")
    private String addressDetail;

}
