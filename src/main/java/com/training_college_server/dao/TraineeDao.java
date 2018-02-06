package com.training_college_server.dao;

import com.training_college_server.entity.Trainee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TraineeDao extends JpaRepository<Trainee, Integer> {

//    @Query(value = "select new training_college_server.entity.Trainee(t.trainee_id, t.email, t.name, t.password, t.expenditure, t.balance, t.is_active) from trainee t where t.trainee_id = ?1")
//    Trainee findByTrainee_id(String trainee_id);
}