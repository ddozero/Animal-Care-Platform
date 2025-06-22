package com.animal.api.email.model.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificationVO {

    private int idx;
    private int userIdx;
    private String token;
    private LocalDateTime expiresAt;
    private int used;
    private LocalDateTime createdAt;
    
}
