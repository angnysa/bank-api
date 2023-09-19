package org.angnysa.bank.jpa;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.angnysa.bank.jpa.entities.TransactionEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TransactionRepositoryTest {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void insertAndGetValid() {
        TransactionEntity actual = new TransactionEntity();
        actual.setAmount(100);
        actual.setTimestamp(Instant.now().with(ChronoField.MILLI_OF_SECOND, 0));
        actual.setCustomer(createCustomer(1));

        actual = transactionRepository.saveAndFlush(actual);
        entityManager.clear();

        Optional<TransactionEntity> expected = transactionRepository.findById(actual.getId());

        assertTrue(expected.isPresent());
        assertEquals(actual.getId(), expected.get().getId());
        assertEquals(actual.getAmount(), expected.get().getAmount());
        assertEquals(actual.getTimestamp(), expected.get().getTimestamp());
        assertEquals(actual.getCustomer().getId(), expected.get().getCustomer().getId());
    }

    @Test
    public void insertInvalidTimestamp() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(100);
        transaction.setTimestamp(null);
        transaction.setCustomer(createCustomer(1));

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.saveAndFlush(transaction));
        assertEquals(ConstraintViolationException.class, e.getCause().getClass());
    }

    @Test
    public void insertInvalidCustomer() {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setAmount(100);
        transaction.setTimestamp(Instant.now());
        transaction.setCustomer(null);

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> transactionRepository.saveAndFlush(transaction));
        assertEquals(ConstraintViolationException.class, e.getCause().getClass());
    }

    @Test
    public void testGetByCustomerId() {
        CustomerEntity c1 = createCustomer(1);
        CustomerEntity c2 = createCustomer(2);

        TransactionEntity transaction1 = new TransactionEntity();
        transaction1.setAmount(100);
        transaction1.setTimestamp(Instant.now());
        transaction1.setCustomer(c1);
        transaction1 = transactionRepository.saveAndFlush(transaction1);

        TransactionEntity transaction2 = new TransactionEntity();
        transaction2.setAmount(200);
        transaction2.setTimestamp(Instant.now().plusSeconds(10));
        transaction2.setCustomer(c2);
        transaction2 = transactionRepository.saveAndFlush(transaction2);

        TransactionEntity transaction3 = new TransactionEntity();
        transaction3.setAmount(300);
        transaction3.setTimestamp(Instant.now());
        transaction3.setCustomer(c1);
        transaction3 = transactionRepository.saveAndFlush(transaction3);

        TransactionEntity transaction4 = new TransactionEntity();
        transaction4.setAmount(400);
        transaction4.setTimestamp(Instant.now().plusSeconds(10));
        transaction4.setCustomer(c2);
        transaction4 = transactionRepository.saveAndFlush(transaction4);

        List<TransactionEntity> trs = transactionRepository.getByCustomer_id(1);
        assertEquals(2, trs.size());
        assertEquals(transaction1.getId(), trs.get(0).getId());
        assertEquals(transaction3.getId(), trs.get(1).getId());
    }

    private CustomerEntity createCustomer(int id) {
        CustomerEntity actual = new CustomerEntity();
        actual.setId(id);
        actual.setFirstName("first");
        actual.setLastName("last");
        actual.setBalance(100);

        return customerRepository.saveAndFlush(actual);
    }
}