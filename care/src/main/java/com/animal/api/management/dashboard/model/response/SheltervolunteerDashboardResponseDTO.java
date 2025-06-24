package com.animal.api.management.dashboard.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelterVolunteerDashboardResponseDTO {
	private String month;
	private int count;
}
