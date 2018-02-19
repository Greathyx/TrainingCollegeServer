package com.training_college_server.dao;

import com.training_college_server.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseRegistrationDao extends JpaRepository<CourseRegistration, Integer> {

    List<CourseRegistration> findAllByInstitutionID(int institutionID);

}
