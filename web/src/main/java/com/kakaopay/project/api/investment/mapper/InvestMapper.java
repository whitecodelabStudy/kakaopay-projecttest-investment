package com.kakaopay.project.api.investment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.dto.InvestStatusDto;

@Mapper
@Component
public interface InvestMapper {

  InvestStatusDto insertProductInvest(InvestProductDto investProductDto);

}
