package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "trainee")
public class Trainee implements Serializable {

    private static final long serialVersionUID = 1L;

    private int trainee_id;
    private String email;
    private String name;
    private String password;
    private double expenditure;
    private double balance;
    private boolean is_active;

    public Trainee() {

    }

    public Trainee(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Trainee(int trainee_id, String email, String name, String password, double expenditure, double balance, boolean is_active) {
        this.trainee_id = trainee_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.expenditure = expenditure;
        this.balance = balance;
        this.is_active = is_active;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trainee_id")
    public int getTrainee_id() {
        return trainee_id;
    }

    public void setTrainee_id(int trainee_id) {
        this.trainee_id = trainee_id;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "expenditure")
    public double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(double expenditure) {
        this.expenditure = expenditure;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Column(name = "is_active")
    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

}
