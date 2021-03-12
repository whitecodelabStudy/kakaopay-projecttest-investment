package com.kakaopay.project.api.investment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.kakaopay.project.api.investment.dto.investor.AddInvestorDto;
import com.kakaopay.project.api.investment.dto.investor.InvestorDetailDto;
import com.kakaopay.project.api.investment.dto.investor.UpdateInvestorDto;

@Mapper
@Component
public interface InvestorMapper {

  int insertInvestor(AddInvestorDto AddInvestorDto);

  int updateInvestor(UpdateInvestorDto AddInvestorDto);

  InvestorDetailDto selectInvestorById(String investorId);

}
