package com.animal.api.common.service;

import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
public class CaptchaService {
	
    public boolean verify(String captchaToken) {
        String apiUrl = "https://www.google.com/recaptcha/api/siteverify";
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("secret", "6LfDx2grAAAAAFFgiLNJMQoa4_FAuQKy264W0AN-");
        params.add("response", captchaToken);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, new HttpHeaders());

        ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, request, Map.class);

        Map<String, Object> body = response.getBody();
        return body != null && Boolean.TRUE.equals(body.get("success"));
    }
}
