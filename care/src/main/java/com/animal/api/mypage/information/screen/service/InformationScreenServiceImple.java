package com.animal.api.mypage.information.screen.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.mypage.information.screen.mapper.InformationScreenMapper;
import com.animal.api.mypage.information.screen.model.response.InformationScreenResponseDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class InformationScreenServiceImple implements InformationScreenService {
	
    private final InformationScreenMapper informationScreenMapper;

    @Override
    public InformationScreenResponseDTO getMypageSummary(HttpServletRequest request) {

        // 세션에서 로그인 사용자 정보 가져오기
        var loginUser = SessionUtils.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(401, "로그인 정보가 존재하지 않습니다");
        }

        int userIdx = loginUser.getIdx();

        // 각 항목별 정보 수집
        int point = loginUser.getPoint();
        int volunteerCount = informationScreenMapper.countVolunteer(userIdx);
        int adoptionCount = informationScreenMapper.countAdoption(userIdx);
        int totalDonationAmount = informationScreenMapper.sumDonations(userIdx);
        List<String> activityHistory = informationScreenMapper.getRecentActivities(userIdx);

        // DTO 조립
        return InformationScreenResponseDTO.builder()
                .username(loginUser.getName())
                .point(point)
                .volunteerCount(volunteerCount)
                .adoptionCount(adoptionCount)
                .totalDonationAmount(totalDonationAmount)
                .activityHistory(activityHistory)
                .activityBadge("우리집도 보호시설 등급") // 추후 로직 처리 예정
                .build();
    }
}
