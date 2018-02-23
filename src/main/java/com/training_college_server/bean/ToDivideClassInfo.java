package com.training_college_server.bean;

import java.io.Serializable;
import java.sql.Date;

public class ToDivideClassInfo implements Serializable {

    private int courseID;
    private String courseName;
    private int trainee_amount;
    private int booked_amount;
    private Date book_due_date;
    private Date start_date;
    private int class_amount;
    private boolean canDivide;

    public ToDivideClassInfo(int courseID, String courseName, int trainee_amount, int booked_amount,
                             Date book_due_date, Date start_date, int class_amount, boolean canDivide) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.trainee_amount = trainee_amount;
        this.booked_amount = booked_amount;
        this.class_amount = class_amount;
        this.book_due_date = book_due_date;
        this.start_date = start_date;
        this.canDivide = canDivide;
    }

    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getTrainee_amount() {
        return trainee_amount;
    }

    public void setTrainee_amount(int trainee_amount) {
        this.trainee_amount = trainee_amount;
    }

    public int getBooked_amount() {
        return booked_amount;
    }

    public void setBooked_amount(int booked_amount) {
        this.booked_amount = booked_amount;
    }

    public Date getBook_due_date() {
        return book_due_date;
    }

    public void setBook_due_date(Date book_due_date) {
        this.book_due_date = book_due_date;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public int getClass_amount() {
        return class_amount;
    }

    public void setClass_amount(int class_amount) {
        this.class_amount = class_amount;
    }

    public boolean isCanDivide() {
        return canDivide;
    }

    public void setCanDivide(boolean canDivide) {
        this.canDivide = canDivide;
    }

}
