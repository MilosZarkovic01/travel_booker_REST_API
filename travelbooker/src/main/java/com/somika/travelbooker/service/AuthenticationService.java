package com.somika.travelbooker.service;

import com.somika.travelbooker.dto.request.AuthenticationRequestDto;
import com.somika.travelbooker.dto.response.AuthenticationResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {

    AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequest);

    void refreshToken(HttpServletRequest request, HttpServletResponse response);

}
