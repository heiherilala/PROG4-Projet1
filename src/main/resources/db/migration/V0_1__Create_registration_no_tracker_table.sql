create extension if not exists "uuid-ossp";

create table if not exists registration_no_tracker(
    id          integer
    CONSTRAINT registration_no_tracker_pkey primary key,
    last_no     integer
)