package com.oraise.authorizationserver.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.security.Principal;

/**
 * The type Main controller.
 */
@RestController
public class MainController {


    /**
     * User principal.
     *
     * @param principal the principal
     * @param session   the session
     * @return the principal
     */
    @RequestMapping(value = {"/user", "/me"})
    public Principal user(Principal principal, HttpSession session) {
        Authentication AUTHENTICATION = SecurityContextHolder.getContext().getAuthentication();
        WebAuthenticationDetails details= (WebAuthenticationDetails)((Authentication) AUTHENTICATION).getDetails();
        return principal;
    }

}
