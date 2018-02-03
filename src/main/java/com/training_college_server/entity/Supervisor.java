package com.training_college_server.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //加入这个注解，Supervisor就会进行持久化了
@Table(name = "supervisor")
public class Supervisor {

    private int supervisor_id;

    private String password;


    public Supervisor(){

    }

    public Supervisor(int supervisor_id, String password) {
        this.supervisor_id = supervisor_id;
        this.password = password;
    }

    @Id
    @Column(name = "supervisor_id")
    public int getSupervisorId() {
        return supervisor_id;
    }

    public void setSupervisorId(int supervisor_id) {
        this.supervisor_id = supervisor_id;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
