package com.kakaopay.project.api.investment.dto;

import lombok.Data;

@Data
public class MyInvestProductDto {

  private Long productId;
  private String title;
  private Long totalInvestingAmount;
  private Long myInvestedAmount;
  private String modifiedTime;
  private Long totalInvestedAmount;

}
