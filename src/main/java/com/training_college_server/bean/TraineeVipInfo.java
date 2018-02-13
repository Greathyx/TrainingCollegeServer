package com.training_college_server.bean;

import java.io.Serializable;


public class TraineeVipInfo implements Serializable {

    private double expenditure; // 累计消费金额
    private int level;  // 等级
    private double discount;  // 优惠折扣
    private int credit;  // 积分

    public TraineeVipInfo() {

    }

    public TraineeVipInfo(double expenditure, int level, double discount, int credit) {
        this.expenditure = expenditure;
        this.level = level;
        this.discount = discount;
        this.credit = credit;
    }

    public double getExpenditure() {
        return expenditure;
    }

    public void setExpenditure(double expenditure) {
        this.expenditure = expenditure;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

}
