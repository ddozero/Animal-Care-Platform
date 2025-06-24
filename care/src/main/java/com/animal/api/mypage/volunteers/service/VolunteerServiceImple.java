package com.animal.api.mypage.volunteers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.animal.api.common.util.FileManager;
import com.animal.api.mypage.volunteers.mapper.VolunteerMapper;
import com.animal.api.mypage.volunteers.model.response.VolunteerListResponseDTO;

@Service
@Primary
public class VolunteerServiceImple implements VolunteerService {

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private FileManager fileManager;
    
    @Override
    public List<VolunteerListResponseDTO> getVolunteerListByUserIdx(int userIdx) {
        List<VolunteerListResponseDTO> list = volunteerMapper.findVolunteerListByUserIdx(userIdx);

        for (VolunteerListResponseDTO dto : list) {
            // 게시글 고유 volunteerRequestIdx 기반으로 이미지 경로 가져오기
            List<String> imagePaths = fileManager.getImagePath("volunteerReviews", dto.getVolunteerRequestIdx());

            // 경로가 비어있지 않으면 첫 번째 이미지로 대표 이미지 지정
            if (imagePaths != null && !imagePaths.isEmpty()) {
                dto.setImagePath(imagePaths.get(0));
            }
        }

        return list;
    }
}
