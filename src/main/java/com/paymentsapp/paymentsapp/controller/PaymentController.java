package com.paymentsapp.paymentsapp.controller;

import com.paymentsapp.paymentsapp.controller.helpers.IAuthenticationFacade;
import com.paymentsapp.paymentsapp.exception.AlreadyExistsException;
import com.paymentsapp.paymentsapp.model.Payment;
import com.paymentsapp.paymentsapp.model.User;
import com.paymentsapp.paymentsapp.service.IPaymentService;
import com.paymentsapp.paymentsapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private IPaymentService paymentService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;

    @PostMapping("")
    public ResponseEntity add(@RequestBody Payment payment) {
        UserDetails userDetails = (UserDetails)authenticationFacade.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        try {
            User user = userService.get(username);
            payment.setUser(user);

            paymentService.save(payment);
            return new ResponseEntity<Payment>(payment, HttpStatus.CREATED);
        } catch (AlreadyExistsException exception) {
            return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> get(@PathVariable Integer id) {
        try {
            Payment payment = paymentService.get(id);
            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Payment>> get() {
        List<Payment> payments = paymentService.getAll();
        return new ResponseEntity<List<Payment> >(payments, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> update(@PathVariable Integer id, @RequestBody Payment payment){
        try{
            payment.setId(id);
            paymentService.update(payment);
            return new ResponseEntity<Payment>(payment, HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Payment> delete(@PathVariable Integer id){
        try {
            paymentService.delete(id);
            return new ResponseEntity("Deleted payment " + id, HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<Payment>(HttpStatus.NOT_FOUND);
        }
    }
}


