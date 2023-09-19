package org.angnysa.bank.services.model;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NonNull;

import java.time.Instant;

@Data
public class Transaction {
    private long id;
    private int customerId;
    @NonNull @NotNull private Instant timestamp;
    /**
     * Calculation in floating point can bring errors, it's safer to have amounts as BigDecimal or in cents to avoid fractions.
     */
    private long amountInCent;
}
