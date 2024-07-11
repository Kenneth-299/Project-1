drop table if exists "user";

create table "user"(
	username text,
	password text
);

create table "account"(
	username text,
	balance integer
);

insert into "user" values ('admin', '1234');