package com.ge.inspection.ia.security;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.ge.inspection.ia.domain.Users;
import com.ge.inspection.ia.repository.UserRepository;

public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	private UserRepository userRepository;
	 
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final Map<String, Object> additionalInfo = new HashMap<>();
        additionalInfo.put("inspectorId", "inspectorId");
      
        Users loginUser=userRepository.findByUsernameCaseInsensitive(user.getUsername());
        additionalInfo.put("inspectorId",  loginUser.getInspectorId());

        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);

        return accessToken;
    }

}