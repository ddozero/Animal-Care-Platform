package com.animal.api.admin.member.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminPointTypeUpdateRequestDTO {
    private Integer pointTypeIdx; // 수정할 포인트 타입 IDX
    private Integer point;        // 수정할 포인트 금액
}
