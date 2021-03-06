package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;


@Entity
@Table(name = "course_order")
public class CourseOrder implements Serializable {

    private int course_order_id;
    private int traineeID;
    private int courseID;
    private int institutionID;
    private int classID;
    private double payment;
    private String status = "not_paid";
    private int amount;
    private String description;
    private String trainee_name;
    private String institution_name;
    private String course_name;
    private int add_credits;
    private double payback = 0;
    private int minus_credits = 0;
    private Date bookTime;
    private Date unsubscribe_time;
    private boolean score = false; // 是否登记了成绩的标志
    private boolean settled = false; // 是否将费用结算给机构的标志
    private int use_credit;

    public CourseOrder() {

    }

    public CourseOrder(int traineeID, int courseID, int institutionID, double payment, int amount, String description, int use_credit) {
        this.traineeID = traineeID;
        this.courseID = courseID;
        this.institutionID = institutionID;
        this.payment = payment;
        this.amount = amount;
        this.description = description;
        this.use_credit = use_credit;
    }

    public CourseOrder(int traineeID, int courseID, int institutionID, double payment, int amount, String description,
                       String traineeName, String courseName, String institutionName, int add_credits, Date book_time, int use_credit) {
        this.traineeID = traineeID;
        this.courseID = courseID;
        this.institutionID = institutionID;
        this.payment = payment;
        this.amount = amount;
        this.description = description;
        this.trainee_name = traineeName;
        this.course_name = courseName;
        this.institution_name = institutionName;
        this.add_credits = add_credits;
        this.bookTime = book_time;
        this.use_credit = use_credit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_order_id")
    public int getCourse_order_id() {
        return course_order_id;
    }

    public void setCourse_order_id(int course_order_id) {
        this.course_order_id = course_order_id;
    }

    @Column(name = "traineeID")
    public int getTraineeID() {
        return traineeID;
    }

    public void setTraineeID(int traineeID) {
        this.traineeID = traineeID;
    }

    @Column(name = "courseID")
    public int getCourseID() {
        return courseID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    @Column(name = "institutionID")
    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    @Column(name = "classID")
    public int getClassID() {
        return classID;
    }

    public void setClassID(int classID) {
        this.classID = classID;
    }

    @Column(name = "payment")
    public double getPayment() {
        return payment;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }

    @Column(name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "amount")
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "trainee_name")
    public String getTrainee_name() {
        return trainee_name;
    }

    public void setTrainee_name(String trainee_name) {
        this.trainee_name = trainee_name;
    }

    @Column(name = "institution_name")
    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    @Column(name = "course_name")
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @Column(name = "add_credits")
    public int getAdd_credits() {
        return add_credits;
    }

    public void setAdd_credits(int add_credits) {
        this.add_credits = add_credits;
    }

    @Column(name = "payback")
    public double getPayback() {
        return payback;
    }

    public void setPayback(double payback) {
        this.payback = payback;
    }

    @Column(name = "minus_credits")
    public int getMinus_credits() {
        return minus_credits;
    }

    public void setMinus_credits(int minus_credits) {
        this.minus_credits = minus_credits;
    }

    @Column(name = "book_time")
    public Date getBookTime() {
        return bookTime;
    }

    public void setBookTime(Date bookTime) {
        this.bookTime = bookTime;
    }

    @Column(name = "unsubscribe_time")
    public Date getUnsubscribe_time() {
        return unsubscribe_time;
    }

    public void setUnsubscribe_time(Date unsubscribe_time) {
        this.unsubscribe_time = unsubscribe_time;
    }

    @Column(name = "score")
    public boolean isScore() {
        return score;
    }

    public void setScore(boolean score) {
        this.score = score;
    }

    @Column(name = "settled")
    public boolean isSettled() {
        return settled;
    }

    public void setSettled(boolean settled) {
        this.settled = settled;
    }

    @Column(name = "use_credit")
    public int getUse_credit() {
        return use_credit;
    }

    public void setUse_credit(int use_credit) {
        this.use_credit = use_credit;
    }
}
