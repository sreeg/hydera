CREATE TABLE `school`.`books` (
  `id` VARCHAR(10) NOT NULL COMMENT '',
  `index` INT NULL COMMENT '',
  `title` VARCHAR(250) NULL COMMENT '',
  `author` VARCHAR(250) NULL COMMENT '',
  `publishers` VARCHAR(250) NULL COMMENT '',
  `category` VARCHAR(100) NULL COMMENT '',
  `image` LONGBLOB NULL COMMENT '',
  `price` DOUBLE NULL COMMENT '',
  PRIMARY KEY (`id`)  COMMENT '');

ALTER TABLE `school`.`books` 
ADD COLUMN `isarchived` TINYINT(4) NULL COMMENT '' AFTER `image`;

ALTER TABLE `school`.`books` 
ADD COLUMN `imagename` VARCHAR(45) NULL COMMENT '' AFTER `isarchived`;

