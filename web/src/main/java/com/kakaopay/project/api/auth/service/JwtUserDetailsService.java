package com.kakaopay.project.api.auth.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.kakaopay.project.api.investment.dto.admin.AdminDetailDto;

public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String adminId) throws UsernameNotFoundException {
    AdminDetailDto member =
        adminMapper.selectAdminById(adminId).orElseThrow(() -> new UsernameNotFoundException(adminId));
    Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
    if (adminId.equals("sup2is@gmail.com")) {
      grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
    }

    return new User(member.getEmail(), member.getPassword(), grantedAuthorities);
  }

  public Member authenticateByEmailAndPassword(String email, String password) {
    Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));

    if (!passwordEncoder.matches(password, member.getPassword())) {
      throw new BadCredentialsException("Password not matched");
    }

    return member;
  }

}
