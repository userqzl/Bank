
CREATE DATABASE db_bank;

USE db_bank;

CREATE TABLE login(
	card_id INT,
	PASSWORD INT
);


CREATE TABLE account(
	id INT,
	NAME VARCHAR(10),
	sex CHAR(1),
	phone CHAR(11),
	money INT,
	start_card DATE
);

CREATE TABLE opt(
	id INT,
	other_id INT,
	tranfer_date DATE,
	money VARCHAR(15),
	desc_text VARCHAR(50)
);

ALTER TABLE login MODIFY card_id INT PRIMARY KEY;
ALTER TABLE account MODIFY id INT PRIMARY KEY;


ALTER TABLE account ADD CONSTRAINT acc_card FOREIGN KEY (id) REFERENCES login(card_id) ON UPDATE CASCADE ON DELETE CASCADE;



