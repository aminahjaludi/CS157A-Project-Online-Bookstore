CREATE SCHEMA `new_schema` ;

CREATE TABLE `new_schema`.`users` (
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE);

CREATE TABLE `new_schema`.`books` (
  `book_id` INT NOT NULL AUTO_INCREMENT,
  `book_name` VARCHAR(255) NOT NULL,
  `price` DECIMAL(10,2) NOT NULL,
  `quantity` INT NOT NULL,
  `book_code` VARCHAR(50) NOT NULL,
  PRIMARY KEY (`book_id`),
  UNIQUE INDEX `book_id_UNIQUE` (`book_id` ASC) VISIBLE,
  UNIQUE INDEX `book_code_UNIQUE` (`book_code` ASC) VISIBLE);

CREATE TABLE `new_schema`.`orders` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `book_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `total_cost` DECIMAL(10,2) NOT NULL,
  PRIMARY KEY (`order_id`),
  UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE);

USE new_schema;
CREATE TABLE reviews (
    id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    book VARCHAR(255) NOT NULL,
    review TEXT NOT NULL,
    rating INT NOT NULL
);




