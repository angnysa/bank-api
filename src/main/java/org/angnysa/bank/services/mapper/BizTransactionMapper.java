package org.angnysa.bank.services.mapper;

import org.angnysa.bank.jpa.entities.TransactionEntity;
import org.angnysa.bank.services.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BizTransactionMapper {

    @Mapping(source = "amount", target = "amountInCent")
    @Mapping(source = "customer.id", target = "customerId")
    Transaction toBiz(TransactionEntity transactionEntity);
}
