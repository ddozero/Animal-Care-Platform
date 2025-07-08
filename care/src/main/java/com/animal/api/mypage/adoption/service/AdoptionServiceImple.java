package com.animal.api.mypage.adoption.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.animal.api.auth.exception.CustomException;
import com.animal.api.common.util.FileManager;
import com.animal.api.mypage.adoption.mapper.AdoptionMapper;
import com.animal.api.mypage.adoption.model.entity.AdoptionReviewEntity;
import com.animal.api.mypage.adoption.model.request.AdoptionReviewWriteRequestDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionDetailResponseDTO;
import com.animal.api.mypage.adoption.model.response.AdoptionListResponseDTO;

@Service
@Primary
public class AdoptionServiceImple implements AdoptionService {

    @Autowired
    private AdoptionMapper adoptionMapper;

    @Autowired
    private FileManager fileManager;

    @Override
    public List<AdoptionListResponseDTO> getAdoptionListByUserIdx(int userIdx) {
        List<AdoptionListResponseDTO> list = adoptionMapper.findAdoptionListByUserIdx(userIdx);

        for (AdoptionListResponseDTO dto : list) {
            // 게시글 고유 volunteerRequestIdx 기반으로 이미지 경로 가져오기
            List<String> imagePaths = fileManager.getImagePath("animals", dto.getAdoptionConsultIdx());

            // 경로가 비어있지 않으면 첫 번째 이미지로 대표 이미지 지정
            if (imagePaths != null && !imagePaths.isEmpty()) {
                dto.setImagePath(imagePaths.get(0));
            }
        }

        return list;
    }

    @Override
    public AdoptionDetailResponseDTO getAdoptionDetailByConsultIdx(int adoptionConsultIdx) {
        AdoptionDetailResponseDTO dto = adoptionMapper.findAdoptionDetailByConsultIdx(adoptionConsultIdx);

        if (dto != null) {
            List<String> imagePaths = fileManager.getImagePath("animals", adoptionConsultIdx);
            dto.setImagePaths(imagePaths);
        }

        return dto;
    }

    @Override
    @Transactional
    public void writeAdoptionReview(int userIdx, AdoptionReviewWriteRequestDTO dto, MultipartFile image) {
        int adoptionConsultIdx = dto.getAdoptionConsultIdx();

        // 1. 입양 완료 상태인지 확인
        boolean isCompleted = adoptionMapper.isCompletedAdoptionConsult(adoptionConsultIdx, userIdx);
        if (!isCompleted) {
            throw new CustomException(403, "상담 완료된 입양만 후기를 작성할 수 있습니다.");
        }

        // 2. 이미 작성한 후기인지 확인
        boolean alreadyWritten = adoptionMapper.hasAlreadyWrittenAdoptionReview(adoptionConsultIdx, userIdx);
        if (alreadyWritten) {
            throw new CustomException(409, "이미 후기를 작성한 입양입니다.");
        }

        // 3. ANIMAL_IDX 가져오기
        int animalIdx = adoptionMapper.findAnimalIdxByConsultIdx(adoptionConsultIdx);

        // 4. 후기 등록 (엔티티 기반)
        AdoptionReviewEntity review = new AdoptionReviewEntity();
        review.setUserIdx(userIdx);
        review.setAnimalIdx(animalIdx);
        review.setContent(dto.getContent());

        
        adoptionMapper.insertAdoptionReview(review); //review.getIdx() 생성됨
        adoptionMapper.updateAdoptionReviewRef(review.getIdx()); //ref에 idx 업데이트

        // 5. 포인트 지급
        adoptionMapper.grantAdoptionReviewPoint(userIdx);
        // 6. 사용자 포인트 증가
        adoptionMapper.increaseUserPoint(userIdx);

        // 7. 이미지 저장 (선택)
        if (image != null && !image.isEmpty()) {
            fileManager.uploadImages("adoptionReviews", review.getIdx(), new MultipartFile[]{image});
        }
    }
}
