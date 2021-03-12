package com.kakaopay.project.api.investservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.project.api.investservice.dto.InvestProductDto;
import com.kakaopay.project.api.investservice.dto.InvestStatusDto;
import com.kakaopay.project.api.investservice.mapper.InvestMapper;

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

}
