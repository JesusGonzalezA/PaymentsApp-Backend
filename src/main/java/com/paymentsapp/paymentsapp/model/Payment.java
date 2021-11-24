package com.paymentsapp.paymentsapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @NotNull
    private double amount;
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnoreProperties("payments")
    private User user;

    public Payment() {}

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
