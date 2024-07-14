drop table if exists "user";
drop table if exists "account";

create table "user"(
	username text Primary key unique,
	password text
);

create table "account"(
	username text,
	balance integer,
	foreign key(username) references user(username)
);