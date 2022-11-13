create sequence hibernate_sequence start with 1 increment by 1;

create table account (
id bigint not null,
amount_amt numeric(19,2),
amount_cur varchar(255),
lastname varchar(255),
middlename varchar(255),
firstname varchar(255),
 primary key (id)
 )