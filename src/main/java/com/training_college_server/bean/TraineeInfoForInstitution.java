package com.training_college_server.bean;

import java.io.Serializable;

public class TraineeInfoForInstitution implements Serializable {

    private int traineeID;
    private String name;
    private String email;
    private int level;
    private double discount;

    public TraineeInfoForInstitution(int traineeID, String name, String email, int level, double discount) {
        this.traineeID = traineeID;
        this.name = name;
        this.email = email;
        this.level = level;
        this.discount = discount;
    }

    public int getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(int traineeID) {
        this.traineeID = traineeID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

}
