create table person(
                       id int8 generated always as identity primary key,
                       name varchar(255) not null unique,
                       age int check ( age > 0 )
);

insert into person(name, age) values ('Яценко Виктор Викторович', 1988);
insert into person(name, age) values ('Власов Сергей Дмитриевич', 1981);


create table book(
                     id int8 generated always as identity primary key,
                     person_id int8 references person(id) on delete set null,
                     title varchar(255) not null ,
                     author varchar(255) not null ,
                     year int not null
);

insert into book (title, author, year) VALUES (
                  'Над пропастью во ржи', 'Джером Сэлинджер', 1951);
insert into book (title, author, year) VALUES (
                  'День опричника', 'Владимир Сорокин', 2006);