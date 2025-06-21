package com.animal.api.email.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.email.model.vo.CertificationVO;

@Mapper
public interface CertificationMapper {

    void insertCertification(CertificationVO cert);
    CertificationVO findLatestValidByEmail(@Param("email") String email);
    void markAsUsed(@Param("idx") int idx);
    
}
