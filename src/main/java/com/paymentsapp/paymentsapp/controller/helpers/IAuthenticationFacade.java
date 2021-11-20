package com.paymentsapp.paymentsapp.controller.helpers;

import org.springframework.security.core.Authentication;

public interface IAuthenticationFacade {
    Authentication getAuthentication();
}