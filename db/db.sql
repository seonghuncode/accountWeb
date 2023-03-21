DROP DATABASE IF EXISTS account_web;
CREATE DATABASE account_web;
USE account_web;

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


#사용자 테이블
CREATE TABLE `user`(
                       id INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
                       `name` VARCHAR(20) NOT NULL,
                       email VARCHAR(50) NOT NULL,
                       user_id VARCHAR(15) NOT NULL,
                       `password` VARCHAR(256) NOT NULL,
                       view_yn VARCHAR(10) NOT NULL,
                       create_date DATE NOT NULL
);

#로그인 기능 테스트 사용자--------------------------------------------
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
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test4",
email = "test4@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test5",
email = "test5@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test6",
email = "test6@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test7",
email = "test7@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test8",
email = "test8@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test9",
email = "test9@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();

INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "yes",
create_date = NOW();


INSERT INTO `user`
SET `name` = "test10",
email = "test10@naver.com",
user_id = "1234568",
`password` = "123",
view_yn = "no",
create_date = NOW();


#로그인 기능 테스트 사용자--------------------------------------------


select * from `user`;
















