package com.animal.api.index.shelter.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainShelterRegionResponseDTO {

    private int count; 
    private List<MainShelterResponseDTO> shelters; 
}
