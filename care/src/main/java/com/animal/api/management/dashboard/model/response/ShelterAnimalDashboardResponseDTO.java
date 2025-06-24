package com.animal.api.management.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShelterAnimalDashboardResponseDTO {
	private int year;
	private int totalAnimals;
	private int adoptedAnimals;
	private double adoptionRate;
}
