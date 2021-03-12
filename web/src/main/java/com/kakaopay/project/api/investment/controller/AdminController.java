package com.kakaopay.project.api.investment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investment.dto.admin.AddAdminDto;
import com.kakaopay.project.api.investment.dto.admin.UpdateAdminDto;
import com.kakaopay.project.api.investment.service.AdminService;
import com.kakaopay.project.common.apiformat.ApiResponseJson;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

  private final transient AdminService adminService;

  @Autowired
  public AdminController(AdminService adminService) {
    this.adminService = adminService;
  }

  @PostMapping
  public ResponseEntity<ApiResponseJson> addAdmin(@RequestBody AddAdminDto addAdminDto) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(adminService.addAdmin(addAdminDto)).build());
  }

  @PutMapping
  public ResponseEntity<ApiResponseJson> modifyAdmin(@RequestBody UpdateAdminDto updateAdminDto) {
    return ResponseEntity.ok(new ApiResponseJson.Builder(adminService.modifyAdmin(updateAdminDto)).build());
  }

}
