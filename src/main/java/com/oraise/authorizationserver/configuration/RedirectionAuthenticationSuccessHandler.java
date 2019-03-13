package com.oraise.authorizationserver.configuration;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class RedirectionAuthenticationSuccessHandler
    extends SimpleUrlAuthenticationSuccessHandler
    implements AuthenticationSuccessHandler {

  public RedirectionAuthenticationSuccessHandler() {
    super();
    setUseReferer(false);
  }

}
