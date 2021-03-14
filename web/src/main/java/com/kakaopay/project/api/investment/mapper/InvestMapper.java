package com.kakaopay.project.api.investment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.investment.dto.InvestProductDto;
import com.kakaopay.project.api.investment.dto.InvestStatusDto;
import com.kakaopay.project.api.investment.dto.MyInvestProductDto;

import java.util.List;

@Mapper
@Component
public interface InvestMapper {

  InvestStatusDto insertProductInvest(InvestProductDto investProductDto);

  int deleteProductInvest(Long investId);

  List<MyInvestProductDto> selectMyInvestProducts(long memberId);
}
