DROP TABLE IF EXISTS categories;
CREATE TABLE categories(
       id INT PRIMARY KEY auto_increment,
       name VARCHAR(255));
INSERT INTO categories VALUES(1, 'Videos');
INSERT INTO categories VALUES(2, 'Books');


DROP TABLE IF EXISTS links;
CREATE TABLE links(
       id INT PRIMARY KEY auto_increment,
       url VARCHAR(255),
       category_id INT);
INSERT INTO links VALUES(1, 'first url', 1);
INSERT INTO links VALUES(2, 'second url', 2);
