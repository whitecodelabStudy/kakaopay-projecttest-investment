package com.kakaopay.project.api.investment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.investment.dto.admin.AddAdminDto;
import com.kakaopay.project.api.investment.dto.admin.AdminDetailDto;
import com.kakaopay.project.api.investment.dto.admin.UpdateAdminDto;

@Mapper
@Component
public interface AdminMapper {
  int insertAdmin(AddAdminDto addAdminDto);

  int updateAdmin(UpdateAdminDto updateAdminDto);

  AdminDetailDto selectAdminById(String adminId);
}
