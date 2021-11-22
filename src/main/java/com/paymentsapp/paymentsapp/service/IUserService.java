package com.paymentsapp.paymentsapp.service;

import com.paymentsapp.paymentsapp.exception.BadCredentialsException;
import com.paymentsapp.paymentsapp.model.User;

public interface IUserService extends IBaseService<User> {
    public User get(String username);
    public void update(User user);
    public Boolean login(String username, String password) throws BadCredentialsException;
}
