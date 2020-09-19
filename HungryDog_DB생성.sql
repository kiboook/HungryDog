DROP DATABASE IF EXISTS  HungryDog;
DROP USER IF EXISTS  HungryDog@localhost;
create user HungryDog@localhost identified WITH mysql_native_password  by 'HungryDog';
create database HungryDog;
grant all privileges on HungryDog.* to HungryDog@localhost with grant option;
commit;
 
USE HungryDog;

CREATE TABLE Score_Board (
  name    VARCHAR(3) NOT NULL PRIMARY KEY,
  score   INTEGER NOT NULL
);

commit;