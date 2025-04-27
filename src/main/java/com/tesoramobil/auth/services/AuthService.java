



package com.tesoramobil.auth.services;

import com.tesoramobil.auth.models.TokenDto;
import com.tesoramobil.auth.models.UserLoginDto;
import com.tesoramobil.auth.models.UserLoginResponseDto;


public interface AuthService {
	
	TokenDto login(UserLoginDto user);
	
	TokenDto validateToken(TokenDto token);

	UserLoginResponseDto loginClaims(UserLoginDto user);
	

}
