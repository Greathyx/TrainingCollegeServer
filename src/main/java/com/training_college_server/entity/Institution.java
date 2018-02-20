package com.training_college_server.entity;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "institution")
public class Institution implements Serializable {

    private int institution_id;
    private String code;
    private String email;
    private String name;
    private String password;
    private String location;
    private String faculty;
    private String introduction;
    private double earning = 0;

    public Institution() {

    }

    public Institution(String email, String name, String password,
                       String location, String faculty, String introduction) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.location = location;
        this.faculty = faculty;
        this.introduction = introduction;
    }

    public Institution(String code, String email, String name, String password,
                       String location, String faculty, String introduction) {
        this.code = code;
        this.email = email;
        this.name = name;
        this.password = password;
        this.location = location;
        this.faculty = faculty;
        this.introduction = introduction;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "institution_id")
    public int getInstitution_id() {
        return institution_id;
    }

    public void setInstitution_id(int institution_id) {
        this.institution_id = institution_id;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "faculty")
    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    @Column(name = "introduction")
    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    @Column(name = "earning")
    public double getEarning() {
        return earning;
    }

    public void setEarning(double earning) {
        this.earning = earning;
    }

}
