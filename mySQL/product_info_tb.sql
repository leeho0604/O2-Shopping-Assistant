create table PRODUCT_INFO_TB(
product_id varchar(20) not null,
name varchar(20) not null,
price int not null,
comment varchar(300),
category varchar(20) not null,
product_image varchar(150) not null,
material varchar(150) not null,
laundry varchar(150) not null,
primary key(product_id)
);