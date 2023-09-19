package org.angnysa.bank.services;

import lombok.AllArgsConstructor;
import org.angnysa.bank.jpa.CustomerRepository;
import org.angnysa.bank.jpa.TransactionRepository;
import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.angnysa.bank.jpa.entities.TransactionEntity;
import org.angnysa.bank.services.mapper.BizCustomerMapper;
import org.angnysa.bank.services.model.Customer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.time.Instant;

@Service
@Transactional
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final BizCustomerMapper bizCustomerMapper;

    public Customer createCustomer(@Validated Customer customer) {
        CustomerEntity customerEntity = customerRepository.saveAndFlush(bizCustomerMapper.toJpa(customer));

        if (customerEntity.getBalance() != 0) {
            TransactionEntity transaction = new TransactionEntity();
            transaction.setCustomer(customerEntity);
            transaction.setAmount(customerEntity.getBalance());
            transaction.setTimestamp(Instant.now());
            transactionRepository.saveAndFlush(transaction);
        }

        return bizCustomerMapper.toBiz(customerEntity);
    }

    public Customer getCustomer(int customerId) {
        return bizCustomerMapper.toBiz(
                customerRepository.findById(customerId).orElse(null));
    }
}
