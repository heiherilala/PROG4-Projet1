ALTER TABLE employee
    ADD COLUMN monthly_salary int CHECK (monthly_salary >= 0);