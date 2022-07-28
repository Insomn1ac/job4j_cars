create table engine(
    id serial primary key,
    description varchar(255)
);

create table car(
    id serial primary key,
    engine_id int not null unique references engine(id)
);

create table driver(
    id serial primary key
);

create table history_owner(
    id serial primary key,
    driver_id int not null references driver(id),
    car_id int not null references car(id)
);

create table if not exists users(
    id serial primary key,
    name varchar(100),
    email varchar(100) not null unique,
    password varchar(50) not null
);

create table if not exists advertisement(
    id serial primary key,
    description varchar(255),
    isSold boolean,
    photo bytea,
    car_id int references car(id),
    user_id int references users(id)
);

alter table car add column car_brand varchar(50);
alter table car add column body_type varchar(50);