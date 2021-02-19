--CREATE TABLE: PRODUCT_STOCK
create table product_stock0(
product_id varchar2(30),
product_name varchar2(30) not null,
price number(10) not null,
description varchar2(50),
stock number default 0,
constraint PK_PRODUCT_ID0  primary key(product_id)
);

--TESTCODE
insert into product_stock0 values ('test_code88', 'test_name_88', 400, 'test88', 88);
select * from product_stock0;
select * from product_io0;
commit;

--CREATE SEQUENCE
create sequence seq_product0;

--CREATE TABLE: PRODUCT_IO
create table product_io0(
io_no number,
product_id varchar2(30),
iodate date default sysdate,
amount number,
status char(1),
constraints CK_IO_STATUS_CONST check(status in ('I', 'O')),
constraints FK_PRODUCT_ID_CONST foreign key(product_id) 
references product_stock0(product_id)
on delete cascade
);
commit;
--drop table product_stock;
--drop table product_stock0;
--drop table product_io0;

select * from all_constraints
;

alter table product_stock0 add constraint FK_Constraint on delete cascade;
alter table product_stock0 drop constraint fk_product_id_co;


ALTER TABLE product_stock0
modify constraints FK_PRODUCT_ID_CO foreign key(product_id) 
references product_stock0(product_id) ON DELETE CASCADE;


--ALTER TABLE PRODUCT_IO: AMOUNT CANNOT BE NEGATIVE 
alter table product_io0 add constraints CK_AMOUNT___NOT_NEGATIVE__
check (amount >= 0);
commit;
--CREATE TRIGGER TRG_PRODUCT: MANAGEMENT STOCK
create or replace trigger trg_product01
after
insert on product_stock0
for each row
BEGIN
insert into product_io0 values(seq_product0.nextval, :new.product_id, default, :new.stock, 'I');
END;
/
commit;
--drop trigger trg_product01;

--drop trigger trg_product1;
--drop trigger trg_product1;
--drop trigger trg_product2;
--drop trigger trg_product3;


create or replace trigger trg_product02
after
insert on product_stock0
for each row
BEGIN
    if :new.stock > :old.stock then
            insert into product_io0 (status) values ('I');
    elsif :new.stock < :old.stock then
    insert into product_io0(status) values ('O');
        end if;
END;
/
--drop trigger trg_product02;

create or replace trigger trg_product03
after
insert on product_io0
for each row
BEGIN
    if :new.status = 'I' then
        update product_stock0
        set stock = stock + :new.amount
        where product_id = :new.product_id;
    elsif
        :new.status  = 'O' then
        update product_stock0
        set stock = stock - :new.amount
        where product_id = :new.product_id;
        end if;
        END;
/
commit;
--drop trigger trg_product03;

--CREATE TRIGGER TRG_PRODUCT_DELETE
create or replace trigger TRG_PRODUCT_DELETE1
after 
delete on product_stock0
for each row
BEGIN
delete from product_io0 where product_id = :old.product_id;
END;
/
drop trigger trg_product_delete1;
commit;
--TESTCODE
select * from product_stock0;
select * from product_io0;
delete from product_stock0 where product_id = 'test_code3';

commit;
insert into product_io values  (seq_product.nextval, 'test01', default, 60, 'O');
commit;