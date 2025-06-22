package com.animal.api.auth.model.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
	
    private int idx;
    private int userTypeIdx;
    private String userTypeName;

    private String id;
    private String email;
    private String password;
    private String name;
    private String nickname;

    private LocalDate birthDate;
    private String gender;
    private String tel;

    private int zipCode;
    private String address;
    private String addressDetail;

    private int point;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastLoginAt;
    private LocalDateTime withdrawnAt;

    private int status;
    private int locked;
    private int lockCount;
    private LocalDateTime lockedAt;
    
}
