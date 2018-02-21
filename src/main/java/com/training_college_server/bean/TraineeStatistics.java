package com.training_college_server.bean;

import java.io.Serializable;

public class TraineeStatistics implements Serializable {

    private String traineeName; // 学员姓名
    private String email; // 学员邮箱
    private double total_expense; // 总支出
    private double this_year_expense; // 本年支出
    private int total_course_amount; // 总订课程数
    private int this_year_paid_amount; // 本年订课数
    private int this_year_unsubscribe_amount; // 本年退课数
    private int level; // 会员等级
    private int credit; // 会员积分
    private boolean is_active; // 会员状态


    public TraineeStatistics(String traineeName, String email, double total_expense,
                             double this_year_expense, int total_course_amount,
                             int this_year_paid_amount, int this_year_unsubscribe_amount,
                             int level, int credit, boolean is_active) {
        this.traineeName = traineeName;
        this.email = email;
        this.total_expense = total_expense;
        this.this_year_expense = this_year_expense;
        this.total_course_amount = total_course_amount;
        this.this_year_paid_amount = this_year_paid_amount;
        this.this_year_unsubscribe_amount = this_year_unsubscribe_amount;
        this.level = level;
        this.credit = credit;
        this.is_active = is_active;
    }

    public String getTraineeName() {
        return traineeName;
    }

    public void setTraineeName(String traineeName) {
        this.traineeName = traineeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal_expense() {
        return total_expense;
    }

    public void setTotal_expense(double total_expense) {
        this.total_expense = total_expense;
    }

    public double getThis_year_expense() {
        return this_year_expense;
    }

    public void setThis_year_expense(double this_year_expense) {
        this.this_year_expense = this_year_expense;
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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

}
