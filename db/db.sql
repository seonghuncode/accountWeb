DROP DATABASE IF EXISTS account_web;
CREATE DATABASE account_web;
USE account_web;



###DB구조
#사용자  : 거래내역 = 1 : N

#거래내역 : 분류 = N : 1

#사용자 : 분류 = 1 : N

#예산액
###



####TestController에서 mybatis로 연결 테스트 데이터
CREATE TABLE test(
                     id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                     test_name CHAR(100) NOT NULL
);

SELECT * FROM test;

SELECT test_name
FROM test
WHERE id = 1;

INSERT INTO test
SET test_name = "테스트 입니다.";

SELECT VERSION();
##-----------------------------------------------------------------##


#사용자 테이블----------------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE `user`(
                       id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       `name` VARCHAR(20) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       user_id VARCHAR(15) NOT NULL,
                       `password` varchar(256) not null,
                       view_yn varchar(10) not null,
                       create_date date not null
);


drop table `user`;

#로그인 기능 테스트 사용자--------------------------------------------
insert into `user`
set `name` = "admin",
email = "test@naver.com",
user_id = "123456",
`password` = "123",
view_yn = "yes",
create_date = now();

INSERT INTO `user`
SET `name` = "test1",
email = "test1@naver.com",
user_id = "1234567",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test2",
email = "test2@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test3",
email = "test3@naver.com",
user_id = "12345689",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test4",
email = "test4@naver.com",
user_id = "123456899",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test5",
email = "test5@naver.com",
user_id = "1234568999",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test6",
email = "test6@naver.com",
user_id = "123456811",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test7",
email = "test7@naver.com",
user_id = "123456822",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test8",
email = "test8@naver.com",
user_id = "123456833",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test9",
email = "test9@naver.com",
user_id = "123456844",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "123456855",
`password` = "123",
view_yn = "yes",
create_date = NOW();


INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "123456866",
`password` = "123",
view_yn = "no",
create_date = NOW();


INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "9",
`password` = "123",
view_yn = "no",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "아이디 중복",
`password` = "123",
view_yn = "no",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "아이디 중복",
`password` = "123",
view_yn = "yes",
create_date = NOW();


#로그인 기능 테스트 사용자--------------------------------------------


select * from `user`;





#거래내역 테이블----------------------------------------------------------------------------------------------------------------------------------------------------
create table `transaction`(
                              id int unsigned not null primary key auto_increment,
                              price int(20) not null,
                              `type` varchar(10) not null,
                              transaction_date date not null,
                              memo varchar(20) not null,
                              #budget int(10) not null,   #예산을 월에 한번 존재 하는데 거래내역에 사용하면 중복 데이터가 많다 -> dP예산액만 월이랑 따로 테이블 만들어 뺀다?
                                  userId int not null,
                              sortId int not null
)

    insert into `transaction`
set price = 10000,
`type` = "지출",
transaction_date = now(),
memo = "식비",
userId = 1,
sortId = 1;

INSERT INTO `transaction`
SET price = 2000,
`type` = "지출",
transaction_date = NOW(),
memo = "교통비",
userId = 1,
sortId = 1;


INSERT INTO `transaction`
SET price = 1500,
`type` = "지출",
transaction_date = NOW(),
memo = "교통",
userId = 1,
sortId = 1;


INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = NOW(),
memo = "배당금",
userId = 1,
sortId = 1;

select * from `transaction`;


#예산액을 정하는 테이블  (예산액의 경우 어떤 사용자의 예산액인지 알기 위해 => 1 : N = 사용자 : 예산액)--------------------------------------------------------------------
create table budget(
                       id int unsigned not null primary key auto_increment,
                       price int(10) default -1,
                       `month` date not null,
                       userId int not null
)

    #alter table budget modify `month`date not null;
drop table budget;

insert into budget
set price = 100000,
`month` = now(),
userId = 1;

INSERT INTO budget
SET price = 200000,
`month` = NOW(),
userId = 2;

INSERT INTO budget
SET price = 300000,
`month` = NOW(),
userId = 3;

INSERT INTO budget
SET
    `month` = NOW(),
userId = 3;


select * from budget;
###
select * from `user`;
select now();
select * from budget where month(`month`)="04";  #테블에서 특정 월만 추출 하는 코드
select budget.price from budget left join `user` on budget.userId = user.id where MONTH(`month`)="04" and user.id =6;
SELECT id FROM `user` WHERE user_id = "123456";
SELECT id FROM `user` WHERE user_id = "1234568";
SELECT id FROM `user` WHERE user_id = "1234568999";
###

#분류에 대한 테이블---------------------------------------------------------------------------------------------------------------------------------------------------
create table sort(
                     id int unsigned not null primary key auto_increment,
                     `name` varchar(20) not null,
                     create_date date not null,
                     userId int not null,
                     transactionId int not null
)








