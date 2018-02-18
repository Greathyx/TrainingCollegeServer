package com.training_college_server.dao;

import com.training_college_server.entity.CourseOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseOrderDao extends JpaRepository<CourseOrder, Integer> {

    List<CourseOrder> findAllByTraineeIDAndStatus(int traineeID, String status);

    List<CourseOrder> findAllByInstitutionIDAndStatus(int institutionID, String status);

}
