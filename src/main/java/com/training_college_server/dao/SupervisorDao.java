package com.training_college_server.dao;

import com.training_college_server.entity.Supervisor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SupervisorDao extends JpaRepository<Supervisor, Integer> {

}
