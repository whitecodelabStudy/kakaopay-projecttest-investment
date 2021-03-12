package com.kakaopay.project.api.invest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.invest.dto.ProductInvestDto;
import com.kakaopay.project.api.invest.dto.ProductInvestStatusDto;
import com.kakaopay.project.api.invest.mapper.InvestMapper;

@Service
public class InvestService {

  private final InvestMapper investMapper;

  @Autowired
  public InvestService(InvestMapper investMapper) {
    this.investMapper = investMapper;
  }

  @Transactional
  public ProductInvestStatusDto insertProductInvest(ProductInvestDto productInvestDto) {
    return investMapper.insertProductInvest(productInvestDto);
  }

}
