package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.LoginRequest;
import com.portfolio.stockmanager.dto.MemberSignupRequest;
import com.portfolio.stockmanager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public Long signup(@RequestBody MemberSignupRequest request) {

        return memberService.signup(request);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request){

        boolean succes = memberService.login(request);

        if(succes){
            return "login success";
        }
        return "login fail";
    }
}
