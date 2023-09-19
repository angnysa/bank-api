package org.angnysa.bank.services.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.domain.Persistable;

@Data
public class Customer {
    private int id;
    @NonNull @NotNull private String firstName;
    @NonNull @NotNull private String lastName;
    long balance;
}
