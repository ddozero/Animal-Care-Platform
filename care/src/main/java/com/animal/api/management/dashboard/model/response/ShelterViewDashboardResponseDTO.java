package com.animal.api.management.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterViewDashboardResponseDTO {
	private String month;
	private int views;
}
