package org.angnysa.bank.services.mapper;

import org.angnysa.bank.jpa.entities.CustomerEntity;
import org.angnysa.bank.services.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BizCustomerMapper {
    CustomerEntity toJpa(Customer customer);
    Customer toBiz(CustomerEntity customerEntity);
}
