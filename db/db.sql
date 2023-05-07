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
                       `password` VARCHAR(256) NOT NULL,
                       view_yn VARCHAR(10) NOT NULL,
                       create_date DATE NOT NULL
);


DROP TABLE `user`;

#로그인 기능 테스트 사용자----------------------------------------------------------------
INSERT INTO `user`
SET `name` = "admin",
email = "test@naver.com",
user_id = "123456",
`password` = "123",
view_yn = "yes",
create_date = NOW();

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


SELECT * FROM `user`;







#거래내역 테이블----------------------------------------------------------------------------------------------------------------------------------------------------
CREATE TABLE `transaction`(
                              id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                              price INT(20) NOT NULL,
                              `type` VARCHAR(10) NOT NULL,
                              transaction_date DATE NOT NULL,
                              memo VARCHAR(20) NOT NULL,
                              #budget int(10) not null,   #예산을 월에 한번 존재 하는데 거래내역에 사용하면 중복 데이터가 많다 -> dP예산액만 월이랑 따로 테이블 만들어 뺀다?
                                  userId INT NOT NULL,
                              sortId INT NOT NULL
)


    INSERT INTO `transaction`
SET price = 10000,
`type` = "지출",
transaction_date = NOW(),
memo = "식비",
userId = 1,
sortId = 1;

INSERT INTO `transaction`
SET price = 2000,
`type` = "지출",
transaction_date = NOW(),
memo = "교통비",
userId = 1,
sortId = 2;


INSERT INTO `transaction`
SET price = 1500,
`type` = "지출",
transaction_date = NOW(),
memo = "교통",
userId = 1,
sortId = 2;


INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = NOW(),
memo = "배당금",
userId = 1,
sortId = 3;

INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = ('2023-05-22'),
memo = "이자",
userId = 1,
sortId = 3;

INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = ('2023-05-31'),
memo = "이자",
userId = 1,
sortId = 3;

INSERT INTO `transaction`
SET price = 8000,
`type` = "지출",
transaction_date = ('2023-05-31'),
memo = "버스",
userId = 1,
sortId = 2;

INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = ('2023-05-29'),
memo = "이자",
userId = 1,
sortId = 3;

INSERT INTO `transaction`
SET price = 8000,
`type` = "지출",
transaction_date = ('2023-05-29'),
memo = "버스",
userId = 1,
sortId = 2;

INSERT INTO `transaction`
SET price = 8000,
`type` = "수입",
transaction_date = ('2023-05-16'),
memo = "이자",
userId = 1,
sortId = 3;

INSERT INTO `transaction`
SET price = 8000,
`type` = "지출",
transaction_date = ('2023-05-16'),
memo = "버스",
userId = 1,
sortId = 2;

SELECT * FROM `transaction`;
SELECT DISTINCT transaction_date  FROM `transaction` WHERE MONTH(transaction_date)=4 ORDER BY transaction_date DESC;

DELETE FROM `transaction` WHERE memo = "이자";
DELETE FROM `transaction` WHERE transaction_date = ('2021-04-22');

SELECT MONTH(transaction_date) FROM `transaction`;

#테이블 3개 조인 테스트(transaction:내역에 대한 정보, user:어떠한 사용자인지, sort:분류에 대한 정보)
SELECT * FROM `transaction` LEFT JOIN sort ON `transaction`.sortId = sort.transactionId  LEFT JOIN `user` ON `transaction`.userId = `user`.id AND `user`.id=1 WHERE MONTH(`transaction_date`)=4;
SELECT sort.name, `transaction`.memo, `transaction`.type, `transaction`.price, `transaction`.transaction_date FROM `transaction` LEFT JOIN sort ON `transaction`.sortId = sort.transactionId  LEFT JOIN `user` ON `transaction`.userId = `user`.id AND `user`.id=1 WHERE MONTH(`transaction_date`)=4 ORDER BY `transaction`.transaction_date DESC;

SELECT DISTINCT `transaction`.transaction_date FROM `transaction` LEFT JOIN sort ON `transaction`.sortId = sort.transactionId  LEFT JOIN `user` ON `transaction`.userId = `user`.id AND `user`.id=1 WHERE MONTH(`transaction_date`)=4 ORDER BY `transaction`.transaction_date DESC;
SELECT * FROM `transaction` ORDER BY `transaction`.transaction_date DESC;


SELECT sort.name, `transaction`.memo, `transaction`.type, `transaction`.price, `transaction`.transaction_date
FROM `transaction`
         LEFT JOIN sort ON `transaction`.sortId = sort.transactionId
         LEFT JOIN `user`
                   ON `transaction`.userId = `user`.id
WHERE MONTH (`transaction_date`)=4 AND `user`.id = 1 AND DATE_FORMAT(`transaction_date`,'%y')=23  AND sort.name LIKE '%기타%'
ORDER BY `transaction`.transaction_date DESC;


#예산액을 정하는 테이블  (예산액의 경우 어떤 사용자의 예산액인지 알기 위해 => 1 : N = 사용자 : 예산액)--------------------------------------------------------------------
CREATE TABLE budget(
                       id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       price INT(10) DEFAULT -1,
                       `month` DATE NOT NULL,
                       userId INT NOT NULL
)

    #alter table budget modify `month`date not null;
DROP TABLE budget;

INSERT INTO budget
SET price = 100000,
`month` = NOW(),
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


SELECT * FROM budget;
###
SELECT * FROM `user`;
SELECT NOW();
SELECT * FROM budget WHERE MONTH(`month`)="04";  #테블에서 특정 월만 추출 하는 코드
SELECT budget.price FROM budget LEFT JOIN `user` ON budget.userId = user.id WHERE MONTH(`month`)="04" AND user.id =6;
SELECT id FROM `user` WHERE user_id = "123456";
SELECT id FROM `user` WHERE user_id = "1234568";
SELECT id FROM `user` WHERE user_id = "1234568999";
###
#budget의 month에서 year의 yyyy를 yy로 변경하여 추출 하는 방법
SELECT DATE_FORMAT(`month`,'%y') FROM budget;

#분류에 대한 테이블---------------------------------------------------------------------------------------------------------------------------------------------------
create table sort(
                     id int unsigned not null primary key auto_increment,
                     `name` varchar(20) not null,
                     create_date date not null,
                     userId int not null,
                     transactionId int not null
)

    insert into sort
set `name` = "식비",
create_date = now(),
userId = 1,
transactionId =1;  #해당 id와 transaction테이블의 sort id가 같을때만 사용

INSERT INTO sort
SET `name` = "교통비",
create_date = NOW(),
userId = 1,
transactionId =2;

INSERT INTO sort
SET `name` = "기타",
create_date = NOW(),
userId = 1,
transactionId =3;

select * from sort;

drop table sort;









