package com.paymentsapp.paymentsapp.service;

import com.paymentsapp.paymentsapp.model.User;

public interface IUserService extends IBaseService<User> {
    public User get(String username);
    public void update(User user);
}
