package com.lzg.learning.springlearning.controller;

import com.lzg.learning.springlearning.dto.AccessTokenDTO;
import com.lzg.learning.springlearning.dto.GIthubUser;
import com.lzg.learning.springlearning.provider.GIthubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorizeController {
    @Autowired
    private GIthubProvider gIthubProvider;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code,
                           @RequestParam(name = "state") String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String acessToken = gIthubProvider.getAcessToken(accessTokenDTO);
        GIthubUser user = gIthubProvider.getUser(acessToken);
        System.out.println(user.getName());
        return "index";
    }
}
