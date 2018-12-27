CREATE DATABASE `training` /*!40100 COLLATE 'utf8_general_ci' */;

CREATE TABLE `insurance_info` (
  `id_insurance_info` INT(11) NOT NULL AUTO_INCREMENT,
  `Policy` VARCHAR(50) NOT NULL DEFAULT '0',
  `Group_field` VARCHAR(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB
;

CREATE TABLE `patient_info` (
  `id_patient_info` INT(11) NOT NULL AUTO_INCREMENT,
  `First_Name` VARCHAR(50) NOT NULL,
  `Last_Name` VARCHAR(50) NOT NULL,
  `Phone` VARCHAR(50) NOT NULL,
  `City` VARCHAR(50) NOT NULL,
  `State` VARCHAR(50) NOT NULL,
  `ZIP` VARCHAR(50) NOT NULL,
  `Address` VARCHAR(50) NOT NULL,
  `Gender` VARCHAR(50) NOT NULL,
  `DOB` DATETIME NOT NULL,
  PRIMARY KEY (`id`)
)
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB
;

CREATE TABLE `physician_info` (
  `id_physician_info` INT(11) NOT NULL AUTO_INCREMENT,
  `First_Name` VARCHAR(50) NOT NULL DEFAULT '0',
  `Last_Name` VARCHAR(50) NOT NULL DEFAULT '0',
  `Phone` VARCHAR(50) NOT NULL DEFAULT '0',
  `City` VARCHAR(50) NOT NULL DEFAULT '0',
  `State` VARCHAR(50) NOT NULL DEFAULT '0',
  `ZIP` VARCHAR(50) NOT NULL DEFAULT '0',
  `Address` VARCHAR(50) NOT NULL DEFAULT '0',
  `NPI` VARCHAR(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
)
  COLLATE='utf8_general_ci'
  ENGINE=InnoDB
;



