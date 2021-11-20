package com.paymentsapp.paymentsapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelper {
    @Autowired
    private BCryptPasswordEncoder encoder;

    public String encode (String password) {
        return encoder.encode(password);
    }

    public PasswordEncoder getEncoder() {
        return this.encoder;
    }
}


