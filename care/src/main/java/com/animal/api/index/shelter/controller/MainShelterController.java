package com.animal.api.index.shelter.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.common.model.OkResponseDTO;
import com.animal.api.index.shelter.model.response.MainShelterRegionResponseDTO;
import com.animal.api.index.shelter.model.response.MainShelterResponseDTO;
import com.animal.api.index.shelter.service.MainShelterService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/main/shelters")
@RequiredArgsConstructor
public class MainShelterController {

	private final MainShelterService mainShelterService;
	
    @GetMapping("/by-region")
    public ResponseEntity<?> getSheltersByRegion(@RequestParam("region") String region) {
        List<MainShelterResponseDTO> shelters = mainShelterService.getSheltersByRegion(region);
        int count = mainShelterService.countSheltersByRegion(region);
        MainShelterRegionResponseDTO response = new MainShelterRegionResponseDTO(count, shelters);
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<MainShelterRegionResponseDTO>(200,"보호소 조회 성공", response));
    }
    
    @GetMapping("/count")
    public ResponseEntity<?> countAllShelters() {
        int count = mainShelterService.countAllShelters();
        return ResponseEntity.status(HttpStatus.OK).body(new OkResponseDTO<>(200,"전체 보호소 수 조회 성공",count));
    }
}
