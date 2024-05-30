package com.apigateway.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the home page.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OidcUser principal) {
        if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
    }

    @GetMapping(value = "/private")
    public String privateEndpoint(@RegisteredOAuth2AuthorizedClient("okta")
                                  OAuth2AuthorizedClient authorizedClient) {

        var accessToken = authorizedClient.getAccessToken();

        System.out.println("Access Token Value: " + accessToken.getTokenValue());
        System.out.println("Token Type: " + accessToken.getTokenType().getValue());
        System.out.println("Expires At: " + accessToken.getExpiresAt());

        return "Access Token Value: " + accessToken.getTokenValue()+",Token Type: " + accessToken.getTokenType().getValue()+",Expires At: " + accessToken.getExpiresAt();
    }

}
