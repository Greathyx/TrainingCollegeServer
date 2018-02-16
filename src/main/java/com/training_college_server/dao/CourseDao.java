package com.training_college_server.dao;

import com.training_college_server.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface CourseDao extends JpaRepository<Course, Integer> {

    List<Course> findAllByPublisher(int publisher);

    List<Course> findAllByHasClasses(boolean has_classes);

}
