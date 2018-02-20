package com.training_college_server.bean;

import java.io.Serializable;

public class InstitutionEarningInfo implements Serializable {

    private int institutionID;
    private String institutionName;
    private double institution_earning;
    private double course_earning;
    private double actual_earning;

    public InstitutionEarningInfo(int institutionID, String institutionName, double institution_earning,
                                  double course_earning, double actual_earning) {
        this.institutionID = institutionID;
        this.institutionName = institutionName;
        this.institution_earning = institution_earning;
        this.course_earning = course_earning;
        this.actual_earning = actual_earning;
    }

    public int getInstitutionID() {
        return institutionID;
    }

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public void setInstitutionName(String institutionName) {
        this.institutionName = institutionName;
    }

    public double getInstitution_earning() {
        return institution_earning;
    }

    public void setInstitution_earning(double institution_earning) {
        this.institution_earning = institution_earning;
    }

    public double getCourse_earning() {
        return course_earning;
    }

    public void setCourse_earning(double course_earning) {
        this.course_earning = course_earning;
    }

    public double getActual_earning() {
        return actual_earning;
    }

    public void setActual_earning(double actual_earning) {
        this.actual_earning = actual_earning;
    }

}
