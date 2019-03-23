show databases;
use test;
drop table if exists user;
create table if not exists User(
  id int primary key ,
  name varchar(50) not null,
  brithday date  not null,
  sex tinyint not null comment "0-女 1-男 2-未知"
);
