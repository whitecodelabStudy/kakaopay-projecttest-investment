package com.kakaopay.project.api.auth.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kakaopay.project.api.investment.dto.investor.InvestorDetailDto;
import com.kakaopay.project.api.investment.mapper.InvestorMapper;

@Service
public class CustomUserDetailService implements UserDetailsService {

  // @Autowired
  // private AdminMapper adminMapper;

  @Autowired
  private InvestorMapper investorMapper;

  @Override
  public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
    InvestorDetailDto investorDetailDto = investorMapper.selectInvestorById(id);
    if (id.equals(investorDetailDto.getInvestorId())) {
      return new User(investorDetailDto.getInvestorId(), investorDetailDto.getPassword(), new ArrayList<>());
    } else {
      throw new UsernameNotFoundException("Investor not found!!");
    }
  }

}
