package com.paymentsapp.paymentsapp.service;

import com.paymentsapp.paymentsapp.exception.UserAlreadyExistsException;
import com.paymentsapp.paymentsapp.infraestructure.IUserRepository;
import com.paymentsapp.paymentsapp.model.User;
import com.paymentsapp.paymentsapp.security.PasswordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    public User save(User user) throws UserAlreadyExistsException {
        try {
            String encryptedPassword = passwordHelper.encode(user.getPassword());
            user.setPassword(encryptedPassword);
            return userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public User get(Integer id) {
        return userRepository.findById(id).get();
    }

    public User get(String username) {
        return userRepository.findByUsername(username);
    }

    public void update(User user) {
        String encryptedPassword = passwordHelper.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);
    }
}
