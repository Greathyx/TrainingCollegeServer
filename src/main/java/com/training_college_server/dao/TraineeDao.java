package com.training_college_server.dao;

import com.training_college_server.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TraineeDao extends JpaRepository<Trainee, Integer> {

    Trainee findByEmail(String email);

    List<Trainee> findAllByName(String name);

}