package com.portfolio.stockmanager.controller;

import com.portfolio.stockmanager.dto.LoginRequest;
import com.portfolio.stockmanager.dto.MemberSignupRequest;
import com.portfolio.stockmanager.entity.Member;
import com.portfolio.stockmanager.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
@CrossOrigin(origins = "http://localhost:5173")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping
    public Long signup(@RequestBody MemberSignupRequest request) {

        return memberService.signup(request);
    }

    @PostMapping("/login")
    public Member login(
            @RequestBody LoginRequest request
    ) {

        return memberService.login(request);
    }

    @GetMapping("/test")
    public String test() {
        return "member ok";
    }
}
