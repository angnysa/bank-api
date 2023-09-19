package org.angnysa.bank.jpa.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer")
@Data
public class CustomerEntity implements Persistable<Integer> {
    @Id
    @Column(nullable = false)
    private int id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private long balance;

    @OneToMany(mappedBy = "customer")
    @ToString.Exclude
    private List<TransactionEntity> transactions = new ArrayList<>();

    @Getter
    @Transient
    private boolean update;

    public Integer getId() {
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
