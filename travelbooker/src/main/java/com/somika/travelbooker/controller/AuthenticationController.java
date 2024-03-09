package com.somika.travelbooker.controller;

import com.somika.travelbooker.dto.request.AuthenticationRequestDto;
import com.somika.travelbooker.dto.response.AuthenticationResponseDto;
import com.somika.travelbooker.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public AuthenticationResponseDto login(@Valid @RequestBody AuthenticationRequestDto authenticationRequest) {
        return authenticationService.login(authenticationRequest);
    }

    @GetMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/validate-token")
    public void validateToken() {
        log.info("Token is valid");
    }
}
