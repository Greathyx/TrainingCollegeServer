package com.training_college_server.dao;

import com.training_college_server.entity.InstitutionApply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitutionApplyDao extends JpaRepository<InstitutionApply, Integer> {

    InstitutionApply findByEmail(String email);

    List<InstitutionApply> findAllByTag(String tag);

}
