drop table if exists account CASCADE;
drop table if exists currency CASCADE;
drop table if exists SBP_REGISTER CASCADE;

drop sequence if exists hibernate_sequence;
create sequence hibernate_sequence start with 1 increment by 1;

create table account (
id bigint not null auto_increment,
amount_amt numeric(19,2),
firstname varchar(255),
lastname varchar(255),
middlename varchar(255),
currency_id bigint not null,
primary key (id));

create table currency (
id bigint not null auto_increment,
title varchar(255),
primary key (id));

create table sbp_register (
id bigint not null auto_increment,
error_code varchar(255),
error_message varchar(255),
form_url varchar(255),
order_id varchar(255),
order_number varchar(255),
primary key (id));

alter table account
 add constraint account_currency
 foreign key (currency_id) references currency;