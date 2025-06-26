package com.animal.api.admin.donation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;

@Mapper
public interface AdminDonationMapper {
	
	public List<AdminAllDonationResponseDTO> getAdminDonationList(Map map);
	
	public List<AdminAllDonationResponseDTO> searchAdminDonation(AdminDonationSearchRequestDTO dto);
	

}
