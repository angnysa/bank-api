package org.angnysa.bank.api.mapper;

import org.angnysa.bank.api.model.RestCustomer;
import org.angnysa.bank.services.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RestCustomerMapper {
    RestCustomer toRest(Customer customer);
    Customer toBiz(RestCustomer restCustomer);
}
