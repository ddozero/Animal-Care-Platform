package com.animal.api.admin.donation.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.animal.api.admin.donation.model.request.AdminAddDonationRequestDTO;
import com.animal.api.admin.donation.model.request.AdminDonationSearchRequestDTO;
import com.animal.api.admin.donation.model.request.AdminUpdateRequestDTO;
import com.animal.api.admin.donation.model.response.AdminAllDonationResponseDTO;
import com.animal.api.admin.donation.model.response.AdminDonationUserResponseDTO;

@Mapper
public interface AdminDonationMapper {

	public List<AdminAllDonationResponseDTO> getAdminDonationList(Map map);
	
	public int getAdminDonationTotalCnt(); //페이징 구현을 위한 total count

	public List<AdminAllDonationResponseDTO> searchAdminDonation(AdminDonationSearchRequestDTO dto);
	
	public int getSearchAdminDonationTotalCnt(AdminDonationSearchRequestDTO dto);//페이징 구현을 위한 total count

	public AdminAllDonationResponseDTO getAdminDonationDetail(int idx);
	
	public List<AdminDonationUserResponseDTO> getAdminDonationUser(Map map);
	
	public int getAdminDonationUserTotalCnt(); //페이징 구현을 위한 total count
	
	public int addAdminDonation(AdminAddDonationRequestDTO dto);
	
	public int updateAdminDonation(AdminUpdateRequestDTO dto);
	
	public Integer checkDonationIdx(int idx);
	
	public int deleteAdminDonation(int idx);

}
