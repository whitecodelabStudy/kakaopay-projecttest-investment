package com.kakaopay.project.api.invest.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.invest.dto.ProductInvestDto;
import com.kakaopay.project.api.invest.dto.ProductInvestStatusDto;

@Mapper
@Component
public interface InvestMapper {

  ProductInvestStatusDto insertProductInvest(ProductInvestDto productInvestDto);

}
