package com.animal.api.animal.model.request;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdoptionSubmitReqestDTO {
	// 유저 번호는 로그인 정보로 대체
    private Integer userIdx;

    // 서비스에서 세팅
    private Integer animalIdx;

    @NotBlank(message = "이름은 필수입니다.")
    @Size(max = 50, message = "이름은 최대 50자까지 입력 가능합니다.")
    private String name;

    @NotBlank(message = "이메일은 필수입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{9,20}$", message = "전화번호 형식이 올바르지 않습니다.")
    private String tel;

    @NotBlank(message = "우편번호는 필수입니다.")
    private String zipCode;

    @NotBlank(message = "주소는 필수입니다.")
    private String address;

    private String addressDetail;

    @NotNull(message = "반려동물 여부는 필수입니다.")
    @Min(value = 0, message = "반려동물 여부는 0 또는 1이어야 합니다.")
    @Max(value = 1, message = "반려동물 여부는 0 또는 1이어야 합니다.")
    private Integer hasPet;

    @NotNull(message = "상담 예약일은 필수입니다.")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm", shape = JsonFormat.Shape.STRING)
    private LocalDateTime consultedAt;

    @NotBlank(message = "상담 내용은 필수입니다.")
    private String description;

}
