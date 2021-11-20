package com.paymentsapp.paymentsapp.service;

import com.paymentsapp.paymentsapp.exception.AlreadyExistsException;

import java.util.List;

public interface IBaseService<T> {
    public T save(T el) throws AlreadyExistsException;
    public List<T> getAll();
    public void delete(Integer id);
    public T get(Integer id);
}
