package com.portfolio.stockmanager.service;

import com.portfolio.stockmanager.dto.LoginRequest;
import com.portfolio.stockmanager.dto.MemberSignupRequest;
import com.portfolio.stockmanager.entity.Member;
import com.portfolio.stockmanager.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public Long signup(MemberSignupRequest request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .name(request.getName())
                .build();

        return memberRepository.save(member).getId();
    }

    public Member login(LoginRequest request){

        Member member = memberRepository
                .findByEmail(request.getEmail())
                .orElse(null);

        if(member == null){
            return null;
        }

        if(passwordEncoder.matches(
                request.getPassword(),
                member.getPassword()
        )){
            return member;
        }

        return null;
    }
}