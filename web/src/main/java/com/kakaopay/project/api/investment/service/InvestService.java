package com.kakaopay.project.api.investment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.dto.InvestStatusDto;
import com.kakaopay.project.api.investment.mapper.InvestMapper;

@Service
public class InvestService {

  private final InvestMapper investMapper;

  @Autowired
  public InvestService(InvestMapper investMapper) {
    this.investMapper = investMapper;
  }

  @Transactional
  public InvestStatusDto insertProductInvest(InvestProductDto investProductDto) {
    return investMapper.insertProductInvest(investProductDto);
  }

  @Transactional
  public int deleteProductInvest(long investId) {
    return investMapper.deleteProductInvest(investId);
  }

}
