package com.training_college_server.dao;

import com.training_college_server.entity.CourseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CourseOrderDao extends JpaRepository<CourseOrder, Integer> {

    List<CourseOrder> findAllByTraineeIDAndStatus(int traineeID, String status);

    List<CourseOrder> findAllByInstitutionIDAndStatus(int institutionID, String status);

    List<CourseOrder> findAllByInstitutionIDAndStatusAndScore(int institutionID, String status, boolean hasScore);

    List<CourseOrder> findAllByInstitutionIDAndStatusAndSettled(int institutionID, String status, boolean settled);

}
