package com.kakaopay.project.api.investment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investment.dto.admin.AddAdminDto;
import com.kakaopay.project.api.investment.dto.admin.AdminDetailDto;
import com.kakaopay.project.api.investment.dto.admin.UpdateAdminDto;
import com.kakaopay.project.api.investment.mapper.AdminMapper;

@Service
public class AdminService {
  private final AdminMapper adminMapper;

  @Autowired
  public AdminService(AdminMapper adminMapper) {
    this.adminMapper = adminMapper;
  }

  @Transactional
  public int addAdmin(AddAdminDto addAdminDto) {
    return adminMapper.insertAdmin(addAdminDto);
  }

  @Transactional
  public int modifyAdmin(UpdateAdminDto updateAdminDto) {
    return adminMapper.updateAdmin(updateAdminDto);
  }

  @Transactional
  public AdminDetailDto selectAdminById(String adminId) {
    return adminMapper.selectAdminById(adminId);
  }

}
