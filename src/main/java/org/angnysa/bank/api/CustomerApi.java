package org.angnysa.bank.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.angnysa.bank.api.mapper.RestCustomerMapper;
import org.angnysa.bank.api.model.RestCustomer;
import org.angnysa.bank.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "customer")
public class CustomerApi {
    private final CustomerService customerService;
    private final RestCustomerMapper restCustomerMapper;

    @PostMapping("/api/customer")
    public ResponseEntity<RestCustomer> createCustomer(@Valid @RequestBody RestCustomer customer) {
        return ResponseEntity.ok(
                restCustomerMapper.toRest(
                        customerService.createCustomer(
                                restCustomerMapper.toBiz(customer))));
    }

    @GetMapping("/api/customer/{customerId}")
    public ResponseEntity<RestCustomer> getCustomer(@PathVariable("customerId") int customerId) {
        return ResponseEntity.ofNullable(
                restCustomerMapper.toRest(
                        customerService.getCustomer(customerId)));
    }
}
