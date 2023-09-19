create table transaction (
    id long not null primary key,
    customer_id int not null references customer(id),
    timestamp timestamp with time zone not null,
    amount long not null
);

create index on transaction(customer_id);

create sequence seq_transaction;