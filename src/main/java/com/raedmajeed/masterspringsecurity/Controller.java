package com.raedmajeed.masterspringsecurity;

import org.ietf.jgss.Oid;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@org.springframework.stereotype.Controller
public class Controller {


    @GetMapping("/")
    @ResponseBody
    public String homePage() {
        return "WELCOME TO HOME PAGE";
    }

    @GetMapping("/private")
    @ResponseBody
    public String privatePage(Authentication authentication) {
        System.out.println("raed abdul majeed");
        return getname(authentication);
    }

    private String getname(Authentication authentication) {
        return Optional.of(authentication.getPrincipal())
                .filter(OidcUser.class::isInstance)
                .map(OidcUser.class::cast)
                .map(OidcUser::getEmail)
                .orElseGet(authentication::getName);
    }
}
