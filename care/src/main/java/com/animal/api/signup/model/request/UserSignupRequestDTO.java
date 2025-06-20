package com.animal.api.signup.model.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserSignupRequestDTO {

    private String id;
    private String email;
    private String password;

    private String name;
    private String nickname;

    private LocalDate birthDate;
    private String gender; // 'M' or 'F'
    private Integer tel;

    private Integer zipCode;
    private String address;
    private String addressDetail;
	
}
