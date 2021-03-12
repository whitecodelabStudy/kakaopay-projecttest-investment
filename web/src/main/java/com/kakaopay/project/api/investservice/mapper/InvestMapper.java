package com.kakaopay.project.api.investservice.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.investservice.dto.InvestProductDto;
import com.kakaopay.project.api.investservice.dto.InvestStatusDto;

@Mapper
@Component
public interface InvestMapper {

  InvestStatusDto insertProductInvest(InvestProductDto investProductDto);

}
