package com.animal.api.signup.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.animal.api.signup.mapper.SignupMapper;

@RestController
@RequestMapping("/api/validate")
public class ValidationController {

    @Autowired
    private SignupMapper signupMapper;

    @GetMapping("/id")
    public Map<String, Boolean> checkId(@RequestParam String id) {
        return Collections.singletonMap("isDuplicate", signupMapper.isDuplicateId(id) > 0);
    }

    @GetMapping("/email")
    public Map<String, Boolean> checkEmail(@RequestParam String email) {
        return Collections.singletonMap("isDuplicate", signupMapper.isDuplicateEmail(email) > 0);
    }

    @GetMapping("/nickname")
    public Map<String, Boolean> checkNickname(@RequestParam String nickname) {
        return Collections.singletonMap("isDuplicate", signupMapper.isDuplicateNickname(nickname) > 0);
    }
	
}
