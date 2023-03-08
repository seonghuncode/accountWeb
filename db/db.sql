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
    `password` varchar(256) not null,
    view_yn varchar(10) not null,
    create_date date not null
);

#로그인 기능 테스트 사용자
insert into `user`
set `name` = "admin",
email = "test@naver.com",
user_id = "123456",
`password` = "123",
view_yn = "yes",
create_date = now();

select * from `user`;


















