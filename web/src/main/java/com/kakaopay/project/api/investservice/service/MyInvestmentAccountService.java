package com.kakaopay.project.api.investservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakaopay.project.api.investservice.mapper.MyInvestmentAccountMapper;

@Service
public class MyInvestmentAccountService {

  private final MyInvestmentAccountMapper myInvestmentAccountMapper;

  @Autowired
  public MyInvestmentAccountService(MyInvestmentAccountMapper myInvestmentAccountMapper) {
    this.myInvestmentAccountMapper = myInvestmentAccountMapper;
  }

}
