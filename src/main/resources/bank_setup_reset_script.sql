drop table if exists "user";

create table "user"(
	username text Primary key unique,
	password text
);

create table "account"(
	username text,
	balance integer,
	foreign key(username) references user(username)
);

insert into "user" values ('admin', '1234');