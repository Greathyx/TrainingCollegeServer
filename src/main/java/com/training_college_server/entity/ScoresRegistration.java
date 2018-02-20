package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "scores_registration")
public class ScoresRegistration implements Serializable {

    private int scores_registration_id;
    private int traineeID;
    private int courseID;
    private int institutionID;
    private String trainee_name;
    private String course_name;
    private int scores;

    public ScoresRegistration() {

    }

    public ScoresRegistration(int traineeID, int courseID, int institutionID,
                              String trainee_name, String course_name, int scores) {
        this.traineeID = traineeID;
        this.courseID = courseID;
        this.institutionID = institutionID;
        this.trainee_name = trainee_name;
        this.course_name = course_name;
        this.scores = scores;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "scores_registration_id")
    public int getScores_registration_id() {
        return scores_registration_id;
    }

    public void setScores_registration_id(int scores_registration_id) {
        this.scores_registration_id = scores_registration_id;
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

    @Column(name = "scores")
    public int getScores() {
        return scores;
    }

    public void setScores(int scores) {
        this.scores = scores;
    }

}
