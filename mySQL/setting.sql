create database o2;

use mysql;

insert into user (host, user, password) values ('localhost','badcode',password('1234'));

insert into db values('localhost','o2','badcode','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y','y');

flush privileges; 