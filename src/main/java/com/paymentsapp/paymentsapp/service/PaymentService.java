package com.paymentsapp.paymentsapp.service;

import com.paymentsapp.paymentsapp.exception.AlreadyExistsException;
import com.paymentsapp.paymentsapp.infraestructure.IPaymentRepository;
import com.paymentsapp.paymentsapp.model.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements IPaymentService {

    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public Payment save(Payment el) {
        return paymentRepository.save(el);
    }

    @Override
    public List<Payment> getAll() {
        return paymentRepository.findAll();
    }

    @Override
    public void delete(Integer id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public Payment get(Integer id) {
        return paymentRepository.findById(id).get();
    }

    @Override
    public void update(Payment el) {
        Payment payment = get(el.getId());
        el.setUser(payment.getUser());
        paymentRepository.save(el);
    }
}
