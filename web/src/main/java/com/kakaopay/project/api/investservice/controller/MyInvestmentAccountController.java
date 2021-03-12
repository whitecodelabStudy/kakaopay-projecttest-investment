package com.kakaopay.project.api.investservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.project.api.investservice.service.MyInvestmentAccountService;

@RestController
@RequestMapping("/api/invest")
public class MyInvestmentAccountController {

  private final transient MyInvestmentAccountService myInvestmentAccountService;

  @Autowired
  public MyInvestmentAccountController(MyInvestmentAccountService myInvestmentAccountService) {
    this.myInvestmentAccountService = myInvestmentAccountService;
  }

}
