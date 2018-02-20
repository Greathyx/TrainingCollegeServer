package com.training_college_server.dao;

import com.training_college_server.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BankAccountDao extends JpaRepository<BankAccount, Integer> {

    BankAccount findByIdentity(String identity);

    BankAccount findByHolderAndType(int holder, String type);

}
