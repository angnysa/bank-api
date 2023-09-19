package org.angnysa.bank.api.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

@Data
public class RestCustomer {
    private int id;
    @NotNull private String firstName;
    @NotNull private String lastName;
    private long balance;
}
