package com.kakaopay.project.api.auth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

  // private final MemberMapper memberMapper;
  //
  // @Autowired
  // public CustomUserDetailService(MemberMapper memberMapper) {
  // this.memberMapper = memberMapper;
  // }

  @Override
  public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
    // MemberDetailDto memberDetailDto = memberMapper.selectMemberById(Long.valueOf(memberId));
    // if (memberDetailDto != null && memberId.equals(String.valueOf(memberDetailDto.getMemberId()))) {
    // return new User(String.valueOf(memberDetailDto.getMemberId()), memberDetailDto.getPassword(), new ArrayList<>());
    // } else {
    // throw new UsernameNotFoundException("Member not found!!");
    // }
    return null;
  }

}
