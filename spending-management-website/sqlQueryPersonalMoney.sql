drop database personalmoney;
create database personalmoney;
use personalmoney;

create table users(
id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
first_name varchar(16) NOT NULL,
last_name varchar(16) NOT NULL,
email varchar(64) DEFAULT NULL,
phone varchar(16) DEFAULT NULL,
address varchar(256) DEFAULT NULL,
password varchar(100) DEFAULT NULL,
avatar MEDIUMBLOB DEFAULT NULL,
role nvarchar(20) DEFAULT 'ROLE USER',
is_active bit NOT NULL DEFAULT (1),
annually_spending double(11,2) DEFAULT NULL,
monthly_spending double(11,2) DEFAULT NULL,
monthly_saving double(11,2) DEFAULT NULL,
monthly_earning double(11,2) DEFAULT NULL,
currency varchar(8) NOT NULL DEFAULT 'USD',
account_non_locked bit(1) DEFAULT (1),
failed_attempt TINYINT DEFAULT (0),
lock_time datetime DEFAULT NULL,
total double(11,2) DEFAULT (0)
);

create table otp(
id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
email varchar(64) DEFAULT NULL,
code varchar(6) NOT NULL,
date_create datetime NOT NULL
);

create table accounts(
id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
user_id int (11) DEFAULT NULL,
name varchar(32) NOT NULL,
balance double(11,2) NOT NULL,
is_active bit NOT NULL DEFAULT (1)
);

create table categories(
 id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 user_id int (11) NOT NULL,
 name varchar(255) NOT NULL,
 income_or_expense bit NOT NULL,
 budget double(11,2) NOT NULL
);

create table income(
 id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 title varchar(255) NOT NULL,
 amount double(11,2) NOT NULL,
 user_id int (11) DEFAULT NULL,
 account_id int(11) DEFAULT NULL,
 category_id int(11) DEFAULT NULL,
 income_date datetime NOT NULL
);

create table expenses(
 id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 title varchar(255) NOT NULL,
 amount double(11,2) NOT NULL,
 user_id int (11) DEFAULT NULL,
 account_id int(11) DEFAULT NULL,
 category_id int(11) DEFAULT NULL,
 expense_date datetime NOT NULL
);

create table debtor(
 id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 user_id int (11) DEFAULT NULL,
 name varchar(255) NOT NULL,
 address varchar(255) NOT NULL,
 phone varchar(255) NOT NULL,
 email varchar(255) NOT NULL,
 date_create datetime NOT NULL,
 date_update datetime NOT NULL,
 total double(11,2) DEFAULT (0)
);

create table debt_detail(
 id int(11) PRIMARY KEY NOT NULL AUTO_INCREMENT,
 debtor_id int (11) DEFAULT NULL,
 account_id int(11) DEFAULT NULL,
 note varchar(255) NOT NULL,
 money_debt double(11,2) NOT NULL,
 classify bit NOT NULL,
 date_debt datetime NOT NULL
);

alter table users 
add UNIQUE KEY email (email);

alter table accounts 
add key user (user_id);

alter table categories
add key name (name),
add key uploaded_by (user_id);

alter table income
add key account (account_id),
add key user (user_id);

alter table expenses
add key budget_category (category_id),
add key account (account_id),
add key user (user_id);

alter table debtor
add key user (user_id);

alter table debt_detail
add key debtor (debtor_id),
add key account (account_id);

ALTER TABLE accounts
ADD CONSTRAINT accounts_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE categories
ADD CONSTRAINT user_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE expenses
ADD CONSTRAINT expenses_ibfk_1 FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE SET NULL ON UPDATE CASCADE,
ADD CONSTRAINT expenses_ibfk_2 FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL ON UPDATE CASCADE,
ADD CONSTRAINT expenses_ibfk_3 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE income
ADD CONSTRAINT income_ibfk_1 FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE SET NULL ON UPDATE CASCADE,
ADD CONSTRAINT income_ibfk_2 FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE SET NULL ON UPDATE CASCADE,
ADD CONSTRAINT income_ibfk_3 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL ON UPDATE CASCADE;

ALTER TABLE debtor
ADD CONSTRAINT debtor_ibfk_1 FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE debt_detail
ADD CONSTRAINT history_ibfk_1 FOREIGN KEY (debtor_id) REFERENCES debtor (id) ON DELETE CASCADE ON UPDATE CASCADE,
ADD CONSTRAINT history_ibfk_2 FOREIGN KEY (account_id) REFERENCES accounts (id) ON DELETE CASCADE ON UPDATE CASCADE;
