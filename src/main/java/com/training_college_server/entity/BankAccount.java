package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "bank_account")
public class BankAccount implements Serializable {

    private int bank_account_id;
    private int holder;
    private String identity;
    private String password;
    private double balance;
    private String type;

    public BankAccount() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_account_id")
    public int getBank_account_id() {
        return bank_account_id;
    }

    public void setBank_account_id(int bank_account_id) {
        this.bank_account_id = bank_account_id;
    }

    @Column(name = "holder")
    public int getHolder() {
        return holder;
    }

    public void setHolder(int holder) {
        this.holder = holder;
    }

    @Column(name = "identity")
    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "balance")
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
