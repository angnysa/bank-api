package org.angnysa.bank.jpa;

import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Integer> {
    @Modifying
    @Query("update CustomerEntity set balance = balance - :amount where id=:customerId and balance >= :amount")
    int decreaseBalance(@Param("customerId") int customerId, @Param("amount") long amount);

    @Modifying
    @Query("update CustomerEntity set balance = balance + :amount where id=:customerId")
    int increaseBalance(@Param("customerId") int customerId, @Param("amount") long amount);
}
