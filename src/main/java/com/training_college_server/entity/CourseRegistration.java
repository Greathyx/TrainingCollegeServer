package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;


@Entity
@Table(name = "course_registration")
public class CourseRegistration implements Serializable {

    private int course_registration_id;
    private int traineeID;
    private int courseID;
    private int institutionID;
    private String trainee_name;
    private String course_name;
    private String institution_name;
    private Timestamp registration_date;

    public CourseRegistration() {

    }

    public CourseRegistration(int traineeID, int courseID, int institutionID, String trainee_name,
                              String course_name, String institution_name, Timestamp registration_date) {
        this.traineeID = traineeID;
        this.courseID = courseID;
        this.institutionID = institutionID;
        this.trainee_name = trainee_name;
        this.course_name = course_name;
        this.institution_name = institution_name;
        this.registration_date = registration_date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "course_registration_id")
    public int getCourse_registration_id() {
        return course_registration_id;
    }

    public void setCourse_registration_id(int course_registration_id) {
        this.course_registration_id = course_registration_id;
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

    @Column(name = "trainee_name")
    public String getTrainee_name() {
        return trainee_name;
    }

    public void setTrainee_name(String trainee_name) {
        this.trainee_name = trainee_name;
    }
    @Column(name = "course_name")
    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    @Column(name = "institution_name")
    public String getInstitution_name() {
        return institution_name;
    }

    public void setInstitution_name(String institution_name) {
        this.institution_name = institution_name;
    }

    @Column(name = "registration_date")
    public Timestamp getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Timestamp registration_date) {
        this.registration_date = registration_date;
    }

}
