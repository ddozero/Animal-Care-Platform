package com.animal.api.mypage.information.screen.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.aop.util.SessionUtils;
import com.animal.api.mypage.information.screen.mapper.InformationScreenMapper;
import com.animal.api.mypage.information.screen.model.response.InformationScreenResponseDTO;
import com.animal.api.mypage.information.screen.model.response.RecentActivityDTO;

import lombok.RequiredArgsConstructor;

@Service
@Primary
@RequiredArgsConstructor
public class InformationScreenServiceImple implements InformationScreenService {
	
    private final InformationScreenMapper informationScreenMapper;

    @Override
    public InformationScreenResponseDTO getInformationScreen(HttpServletRequest request) {
    	

        // 세션에서 로그인 사용자 정보 가져오기
        var loginUser = SessionUtils.getLoginUser(request);
        if (loginUser == null) {
            throw new CustomException(401, "로그인 정보가 존재하지 않습니다");
        }

        int userIdx = loginUser.getIdx();

        // Mapper 통해 정보 조회
        int volunteerCount = informationScreenMapper.countVolunteer(userIdx);
        int adoptionCount = informationScreenMapper.countAdoption(userIdx);
        int totalDonationAmount = informationScreenMapper.sumDonations(userIdx);
        List<RecentActivityDTO> activityHistory = informationScreenMapper.selectRecentActivities(userIdx);
        
        // DTO 조립 및 반환
        InformationScreenResponseDTO response = new InformationScreenResponseDTO();
        response.setUsername(loginUser.getName());
        response.setPoint(loginUser.getPoint());
        response.setVolunteerCount(volunteerCount);
        response.setAdoptionCount(adoptionCount);
        response.setTotalDonationAmount(totalDonationAmount);
        response.setActivityHistory(activityHistory);
        
        return response;

    }
}
