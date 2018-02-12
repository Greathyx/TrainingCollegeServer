package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "course")
public class Course implements Serializable {

    private int course_id;
    private int publisher;
    private String name;
    private int trainee_amount;
    private int periods_per_week;
    private int total_weeks;
    private String teacher;
    private String type;
    private double price;
    private Date start_date;
    private String introduction;

    public Course() {

    }

    public Course(int publisher, String name, int trainee_amount,
                  int periods_per_week, int total_weeks, String teacher, String type,
                  double price, Date start_date, String introduction) {
        this.publisher = publisher;
        this.name = name;
        this.trainee_amount = trainee_amount;
        this.periods_per_week = periods_per_week;
        this.total_weeks = total_weeks;
        this.teacher = teacher;
        this.type = type;
        this.price = price;
        this.start_date = start_date;
        this.introduction = introduction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_id")
    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    @Column(name = "publisher")
    public int getPublisher() {
        return publisher;
    }

    public void setPublisher(int publisher) {
        this.publisher = publisher;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "trainee_amount")
    public int getTrainee_amount() {
        return trainee_amount;
    }

    public void setTrainee_amount(int trainee_amount) {
        this.trainee_amount = trainee_amount;
    }

    @Column(name = "periods_per_week")
    public int getPeriods_per_week() {
        return periods_per_week;
    }

    public void setPeriods_per_week(int periods_per_week) {
        this.periods_per_week = periods_per_week;
    }

    @Column(name = "total_weeks")
    public int getTotal_weeks() {
        return total_weeks;
    }

    public void setTotal_weeks(int total_weeks) {
        this.total_weeks = total_weeks;
    }

    @Column(name = "teacher")
    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "price")
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(name = "start_date")
    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

}
