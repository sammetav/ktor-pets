create table Pets(
    id BIGSERIAL PRIMARY KEY,
    tracker_type varchar(20) not null,
    pet_type varchar(20) not null,
    owner_id INTEGER not null,
    in_zone BOOLEAN not null,
    lost_tracker BOOLEAN
);