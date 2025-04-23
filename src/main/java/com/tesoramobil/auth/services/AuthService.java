



package com.tesoramobil.auth.services;

import com.tesoramobil.auth.models.TokenDto;
import com.tesoramobil.auth.models.UserLoginDto;


public interface AuthService {
	
	TokenDto login(UserLoginDto user);
	
	TokenDto validateToken(TokenDto token);
	

}
