package com.training_college_server.dao;

import com.training_college_server.entity.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InstitutionDao extends JpaRepository<Institution, Integer> {

    Institution findByEmail(String email);

    Institution findByCode(String code);

}
