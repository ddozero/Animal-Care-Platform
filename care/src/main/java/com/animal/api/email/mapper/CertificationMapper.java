package com.animal.api.email.mapper;

import java.sql.Timestamp;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.animal.api.email.model.vo.CertificationVO;

@Mapper
public interface CertificationMapper {

	void insertCertification(CertificationVO cert);

	CertificationVO findLatestValidByEmail(@Param("email") String email);

	void markAsUsed(@Param("idx") int idx);

	CertificationVO findLatestUsedByEmail(String email);

	// 이메일 변경 전용 인증 코드 저장
	void insertCertificationWithEmail(@Param("userIdx") int userIdx, @Param("email") String email,
			@Param("token") String token, @Param("expiresAt") Timestamp expiresAt);

	// 이메일 변경 전용 이메일 만 인증
	CertificationVO findLatestValidByNewEmailOnly(@Param("email") String email);

}
