create extension if not exists "uuid-ossp";

create table if not exists company (
    id              varchar
        CONSTRAINT company_pkey primary key
        default uuid_generate_v4(),
    company_name    varchar,
    company_description varchar,
    slogan          text,
    address         varchar,
    contact_email   varchar,
    nif             varchar,
    stat            varchar,
    rcs             varchar,
    logo            BYTEA
);
