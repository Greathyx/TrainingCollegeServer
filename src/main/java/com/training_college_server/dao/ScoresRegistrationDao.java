package com.training_college_server.dao;

import com.training_college_server.entity.ScoresRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScoresRegistrationDao extends JpaRepository<ScoresRegistration, Integer> {

    List<ScoresRegistration> findAllByInstitutionID(int institutionID);

}
