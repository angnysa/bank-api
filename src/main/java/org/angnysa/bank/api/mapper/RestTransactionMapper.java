package org.angnysa.bank.api.mapper;

import org.angnysa.bank.api.model.RestTransaction;
import org.angnysa.bank.services.model.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestTransactionMapper {
    RestTransaction toRest(Transaction transaction);
    Transaction toBiz(RestTransaction restTransaction);
}
