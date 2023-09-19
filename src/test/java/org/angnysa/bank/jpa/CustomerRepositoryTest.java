package org.angnysa.bank.jpa;

import jakarta.persistence.EntityManager;
import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void insertAndGetValid() {
        CustomerEntity actual = createCustomer();

        Optional<CustomerEntity> expected = customerRepository.findById(12);

        assertTrue(expected.isPresent());
        assertEquals(actual.getId(), expected.get().getId());
        assertEquals(actual.getFirstName(), expected.get().getFirstName());
        assertEquals(actual.getLastName(), expected.get().getLastName());
        assertEquals(actual.getBalance(), expected.get().getBalance());
    }

    @Test
    public void insertDuplicate() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setId(12);
        customer1.setFirstName("first1");
        customer1.setLastName("last1");
        customer1.setBalance(200);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setId(12);
        customer2.setFirstName("first2");
        customer2.setLastName("last2");
        customer2.setBalance(200);

        customerRepository.saveAndFlush(customer1);
        entityManager.clear();

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> customerRepository.saveAndFlush(customer2));
        assertEquals(ConstraintViolationException.class, e.getCause().getClass());
    }

    @Test
    public void insertInvalidFirstName() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(12);
        customer.setFirstName(null);
        customer.setLastName("x");

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> customerRepository.saveAndFlush(customer));
        assertEquals(ConstraintViolationException.class, e.getCause().getClass());
    }

    @Test
    public void insertInvalidLastName() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(12);
        customer.setFirstName("x");
        customer.setLastName(null);

        DataIntegrityViolationException e = assertThrows(DataIntegrityViolationException.class, () -> customerRepository.saveAndFlush(customer));
        assertEquals(ConstraintViolationException.class, e.getCause().getClass());
    }

    @Test
    public void decreaseBalanceValid() {
        CustomerEntity customer = createCustomer();
        assertEquals(1, customerRepository.decreaseBalance(customer.getId(), 20));

        customer = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(80, customer.getBalance());
    }

    @Test
    public void decreaseBalanceNotFound() {
        assertEquals(0, customerRepository.decreaseBalance(12, 20));
    }

    @Test
    public void decreaseBalanceInsufficentFunds() {
        CustomerEntity customer = createCustomer();
        assertEquals(0, customerRepository.decreaseBalance(customer.getId(), 2000));

        customer = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(100, customer.getBalance());
    }

    @Test
    public void increaseBalanceValid() {
        CustomerEntity customer = createCustomer();
        assertEquals(1, customerRepository.increaseBalance(customer.getId(), 100));

        customer = customerRepository.findById(customer.getId()).orElseThrow();
        assertEquals(200, customer.getBalance());
    }

    @Test
    public void increaseBalanceNotFound() {
        assertEquals(0, customerRepository.increaseBalance(12, 20));
    }

    private CustomerEntity createCustomer() {
        CustomerEntity customer = new CustomerEntity();
        customer.setId(12);
        customer.setFirstName("first");
        customer.setLastName("last");
        customer.setBalance(100);

        customerRepository.saveAndFlush(customer);
        entityManager.clear();

        return customer;
    }
}