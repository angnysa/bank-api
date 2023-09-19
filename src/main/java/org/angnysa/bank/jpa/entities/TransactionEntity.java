package org.angnysa.bank.jpa.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import java.time.Instant;

@Entity
@Table(name = "transaction")
@Data
public class TransactionEntity implements Persistable<Long> {

    @SequenceGenerator(name = "seq_transaction", sequenceName = "seq_transaction", allocationSize = 1)

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(generator = "seq_transaction")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private CustomerEntity customer;

    private Instant timestamp;

    private long amount;


    @Transient
    private boolean update;

    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return !this.update;
    }

    @PrePersist
    @PostLoad
    void markUpdated() {
        this.update = true;
    }
}
