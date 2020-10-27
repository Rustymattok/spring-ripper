
create table IF NOT EXISTS person (
  id serial primary key,
  name varchar
);

create table IF NOT EXISTS department (
  id serial primary key,
  name varchar
);

create table IF NOT EXISTS  department_person (
   id serial primary key,
   department_id int not null references department(id),

--    driver_id int not null references driver_m(id),
   person_id int not null references person(id)
);

