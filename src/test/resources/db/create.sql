
create table person (
  id serial primary key,
  name varchar
);

create table  department (
  id serial primary key,
  name varchar
);

create table  department_person (
   id serial primary key,
   department_id int not null references department(id),

--    driver_id int not null references driver_m(id),
   person_id int not null references person(id)
);

INSERT INTO person (name) VALUES ('Sidorov1');
INSERT INTO person (name) VALUES ('Sidorov2');
INSERT INTO person (name) VALUES ('Sidorov3');
INSERT INTO person (name) VALUES ('Sidorov5');
INSERT INTO person (name) VALUES ('Sidorov6');
INSERT INTO person (name) VALUES ('Sidorov7');



INSERT INTO department (name) VALUES ('engineer');
INSERT INTO department (name) VALUES ('worker');
INSERT INTO department (name) VALUES ('porgrammer');

INSERT INTO department_person (department_id,person_id) VALUES (1,1);
INSERT INTO department_person (department_id,person_id) VALUES (1,2);
INSERT INTO department_person (department_id,person_id) VALUES (2,3);
INSERT INTO department_person (department_id,person_id) VALUES (2,4);
INSERT INTO department_person (department_id,person_id) VALUES (2,5);
