create extension if not exists "uuid-ossp";

create table if not exists phone (
    id          varchar
        CONSTRAINT phone_pkey primary key
        default uuid_generate_v4(),
    "number" varchar,
    employee_id varchar NOT NULL
        CONSTRAINT employee_id_fk references employee (id)
);

create index if not exists employee_id_index on phone (employee_id);