package org.angnysa.bank.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.angnysa.bank.jpa.CustomerRepository;
import org.angnysa.bank.jpa.TransactionRepository;
import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.angnysa.bank.jpa.entities.TransactionEntity;
import org.angnysa.bank.services.mapper.BizTransactionMapper;
import org.angnysa.bank.services.model.Transaction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class TransactionService {
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;
    private final BizTransactionMapper bizTransactionMapper;

    public List<Transaction> getTransactions(int customerId) {
        return transactionRepository.getByCustomer_id(customerId)
                .stream()
                .map(bizTransactionMapper::toBiz)
                .collect(Collectors.toList());
    }

    public void transferMoney(int fromCustomerId, int toCustomerId, long amount) throws InsufficentFundsException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be strictly positive");
        }

        if (customerRepository.decreaseBalance(fromCustomerId, amount) == 1) {
            if (customerRepository.increaseBalance(toCustomerId, amount) == 1) {
                Instant now = Instant.now();

                CustomerEntity from = new CustomerEntity();
                from.setId(fromCustomerId);
                CustomerEntity to = new CustomerEntity();
                to.setId(toCustomerId);

                TransactionEntity transaction = new TransactionEntity();
                transaction.setCustomer(from);
                transaction.setTimestamp(now);
                transaction.setAmount(amount);
                transactionRepository.saveAndFlush(transaction);

                transaction = new TransactionEntity();
                transaction.setCustomer(to);
                transaction.setTimestamp(now);
                transaction.setAmount(amount);
                transactionRepository.saveAndFlush(transaction);
            } else {
                throw new EntityNotFoundException();
            }
        } else {
            throw new InsufficentFundsException();
        }
    }
}
