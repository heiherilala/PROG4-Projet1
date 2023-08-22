create extension if not exists "uuid-ossp";

create table if not exists employee (
    id          varchar
        CONSTRAINT employee_pkey primary key
        default uuid_generate_v4(),
    address varchar  ,
    birth_date date  ,
    cin_issue_date date  ,
    cin_issue_place varchar ,
    cin_number varchar ,
    cnaps_number varchar ,
    departure_date date  ,
    first_name varchar ,
    "function" varchar ,
    gender varchar ,
    hiring_date date  ,
    last_name varchar   ,
    number_of_children integer,
    personal_email varchar   ,
    photo text  ,
    professional_email varchar   ,
    registration_no varchar   ,
    socio_professional_category VARCHAR
);
