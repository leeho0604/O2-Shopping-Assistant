create table LOOKS_PRODUCT_TB(
look_id int,
product_id varchar(20),
foreign key(product_id) references PRODUCT_INFO_TB (product_id),
foreign key(look_id) references LOOKS_INFO_TB (look_id),
primary key(look_id,product_id)
);


# 수정전 버전으로 적용한 사람은 alter table LOOKS_PRODUCT_TB add primary key(look_id,product_id);
#을 적용시켜주세용