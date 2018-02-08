package com.training_college_server.entity;

import javax.persistence.*;


@Entity
@Table(name = "institution_apply")
public class InstitutionApply {

    private int institution_apply_id;
    private String email;
    private String name;
    private String password;
    private String location;
    private String faculty;
    private String introduction;
    private String tag; // 注册或修改信息的标志

    public InstitutionApply() {

    }

    public InstitutionApply(String email, String name, String password, String location,
                            String faculty, String introduction, String tag) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.location = location;
        this.faculty = faculty;
        this.introduction = introduction;
        this.tag = tag;
    }

    public InstitutionApply(int institution_apply_id, String email, String name, String password, String location,
                            String faculty, String introduction, String tag) {
        this.institution_apply_id = institution_apply_id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.location = location;
        this.faculty = faculty;
        this.introduction = introduction;
        this.tag = tag;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "institution_apply_id")
    public int getInstitution_apply_id() {
        return institution_apply_id;
    }

    public void setInstitution_apply_id(int institution_apply_id) {
        this.institution_apply_id = institution_apply_id;
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

    @Column(name = "tag")
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

}
