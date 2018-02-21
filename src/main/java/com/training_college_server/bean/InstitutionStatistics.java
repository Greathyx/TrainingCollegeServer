package com.training_college_server.bean;

import java.io.Serializable;

public class InstitutionStatistics implements Serializable {

    private String institutionName; // 机构名称
    private double total_earning; // 总盈利数额
    private double this_year_earning; // 本年盈利数额
    private int total_course_amount; // 总课程数
    private int this_year_paid_amount; // 本年订单数
    private int this_year_unsubscribe_amount; // 本年退订数

    public InstitutionStatistics(String institutionName, double total_earning, double this_year_earning,
                                 int total_course_amount, int this_year_paid_amount, int this_year_unsubscribe_amount) {
        this.institutionName = institutionName;
        this.total_earning = total_earning;
        this.this_year_earning = this_year_earning;
        this.total_course_amount = total_course_amount;
        this.this_year_paid_amount = this_year_paid_amount;
        this.this_year_unsubscribe_amount = this_year_unsubscribe_amount;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public double getTotal_earning() {
        return total_earning;
    }

    public void setTotal_earning(double total_earning) {
        this.total_earning = total_earning;
    }

    public double getThis_year_earning() {
        return this_year_earning;
    }

    public void setThis_year_earning(double this_year_earning) {
        this.this_year_earning = this_year_earning;
    }

    public int getTotal_course_amount() {
        return total_course_amount;
    }

    public void setTotal_course_amount(int total_course_amount) {
        this.total_course_amount = total_course_amount;
    }

    public int getThis_year_paid_amount() {
        return this_year_paid_amount;
    }

    public void setThis_year_paid_amount(int this_year_paid_amount) {
        this.this_year_paid_amount = this_year_paid_amount;
    }

    public int getThis_year_unsubscribe_amount() {
        return this_year_unsubscribe_amount;
    }

    public void setThis_year_unsubscribe_amount(int this_year_unsubscribe_amount) {
        this.this_year_unsubscribe_amount = this_year_unsubscribe_amount;
    }

}
