package org.angnysa.bank.services.mapper;

import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.angnysa.bank.jpa.entities.TransactionEntity;
import org.angnysa.bank.services.model.Transaction;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BizTransactionMapperTest {
    BizTransactionMapper bizTransactionMapper = new BizTransactionMapperImpl();

    @Test
    public void toBiz() {
        TransactionEntity expected = new TransactionEntity();
        expected.setTimestamp(Instant.now());
        expected.setAmount(100);
        expected.setCustomer(new CustomerEntity());

        Transaction actual = bizTransactionMapper.toBiz(expected);

        assertEquals(expected.getTimestamp(), actual.getTimestamp());
        assertEquals(expected.getAmount(), actual.getAmountInCent());
        assertEquals(expected.getCustomer().getId(), actual.getCustomerId());
        assertEquals(expected.getId(), actual.getId());
    }
}