package com.goom.springapi2.service;

import com.goom.springapi2.advice.exception.CuserNotFoundException;
import com.goom.springapi2.repo.UserJpaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserJpaRepo userJpaRepo;

    @Override
    public UserDetails loadUserByUsername(String userPk){
        return (UserDetails) userJpaRepo.findById(Integer.valueOf(userPk)).orElseThrow(CuserNotFoundException::new);
    }
}
