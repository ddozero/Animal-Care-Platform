package com.animal.api.mypage.volunteers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.util.FileManager;
import com.animal.api.mypage.volunteers.mapper.VolunteerMapper;
import com.animal.api.mypage.volunteers.model.entity.VolunteerReviewEntity;
import com.animal.api.mypage.volunteers.model.request.VolunteerReviewWriteRequestDTO;
import com.animal.api.mypage.volunteers.model.response.VolunteerDetailResponseDTO;
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
    
    @Override
    public VolunteerDetailResponseDTO getVolunteerDetailByRequestIdx(int volunteerRequestIdx) {
        VolunteerDetailResponseDTO dto = volunteerMapper.findVolunteerDetailByRequestIdx(volunteerRequestIdx);
        
        if (dto != null) {
            List<String> imagePaths = fileManager.getImagePath("volunteerReviews", volunteerRequestIdx);
            dto.setImagePaths(imagePaths);
        }

        return dto;
    }
    
    @Override
    @Transactional
    public boolean writeVolunteerReview(int userIdx, VolunteerReviewWriteRequestDTO dto, MultipartFile image) {
        int volunteerRequestIdx = dto.getVolunteerRequestIdx();

        // 1. 참여 완료 상태 확인
        boolean isCompleted = volunteerMapper.isCompletedVolunteerRequest(userIdx, volunteerRequestIdx);
        if (!isCompleted) {
            throw new CustomException(403, "참여 완료된 봉사만 후기를 작성할 수 있습니다.");
        }

        // 2. 기존 후기 작성 여부 확인
        boolean alreadyWritten = volunteerMapper.hasAlreadyWrittenReview(userIdx, volunteerRequestIdx);
        if (alreadyWritten) {
            throw new CustomException(409, "이미 후기를 작성한 봉사입니다.");
        }

        // 3. 봉사 게시글 IDX 조회
        int volunteerIdx = volunteerMapper.findVolunteerIdxByRequestIdx(volunteerRequestIdx);

        // 4. 후기 작성 (insert 후 idx 리턴 받기 위해 객체 생성)
        VolunteerReviewEntity review = new VolunteerReviewEntity();
        review.setUserIdx(userIdx);
        review.setVolunteerIdx(volunteerIdx);
        review.setContent(dto.getContent());

        volunteerMapper.insertVolunteerReview(review); // review.getIdx() 생성됨
        volunteerMapper.updateVolunteerReviewRef(review.getIdx()); // ref에 idx 업데이트

        // 5. 이미지가 있으면 저장
        if (image != null && !image.isEmpty()) {
            fileManager.uploadImages("volunteerReviews", volunteerRequestIdx, new MultipartFile[]{image});
        }

        // 6. 포인트 지급
        volunteerMapper.grantVolunteerReviewPoint(userIdx);
        // 7. 사용자 포인트 증가
        volunteerMapper.increaseUserPoint(userIdx);

        return true;
    }

}
