create extension if not exists "uuid-ossp";

CREATE TABLE IF NOT EXISTS phone (
    id VARCHAR DEFAULT uuid_generate_v4() PRIMARY KEY,
    "number" VARCHAR
        CONSTRAINT unique_phone_number_constraint UNIQUE,

    employee_id VARCHAR REFERENCES employee (id)
        CONSTRAINT check_company_id_employee_id
        CHECK ((company_id IS NULL AND employee_id IS NOT NULL) OR (company_id IS NOT NULL AND employee_id IS NULL)),
    company_id VARCHAR REFERENCES company (id)
        CONSTRAINT check_employee_id_company_id
        CHECK ((employee_id IS NULL AND company_id IS NOT NULL) OR (employee_id IS NOT NULL AND company_id IS NULL))
    );

create index if not exists employee_id_index on phone (employee_id);
create index if not exists company_id_index on phone (company_id);
