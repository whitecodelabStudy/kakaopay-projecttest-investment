package com.kakaopay.project.api.investment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investment.dto.investor.AddInvestorDto;
import com.kakaopay.project.api.investment.dto.investor.InvestorDetailDto;
import com.kakaopay.project.api.investment.dto.investor.UpdateInvestorDto;
import com.kakaopay.project.api.investment.mapper.InvestorMapper;

@Service
public class InvestorService {

  private final InvestorMapper investorMapper;

  @Autowired
  public InvestorService(InvestorMapper investorMapper) {
    this.investorMapper = investorMapper;
  }

  @Transactional
  public int addInvestor(AddInvestorDto AddInvestorDto) {
    return investorMapper.insertInvestor(AddInvestorDto);
  }

  @Transactional
  public int modifyInvestor(UpdateInvestorDto updateInvestorDto) {
    return investorMapper.updateInvestor(updateInvestorDto);
  }

  @Transactional
  public InvestorDetailDto selectInvestorById(String investorId) {
    return investorMapper.selectInvestorById(investorId);
  }

}
