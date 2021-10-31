-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema hcs2508
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema hcs2508
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `hcs2508` DEFAULT CHARACTER SET utf8mb4 ;
USE `hcs2508` ;

-- -----------------------------------------------------
-- Table `hcs2508`.`about_us`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`about_us` (
  `information` VARCHAR(500) NOT NULL)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `hcs2508`.`work_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`work_types` (
  `work_type_id` INT NOT NULL,
  `work_type_name` VARCHAR(25) NOT NULL,
  PRIMARY KEY (`work_type_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`roles` (
  `role_id` INT NOT NULL,
  `role_name` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  PRIMARY KEY (`role_id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `hcs2508`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`users` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `password` VARCHAR(100) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `name` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `second_name` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NULL DEFAULT NULL,
  `surname` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `email` VARCHAR(20) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `phone` VARCHAR(15) CHARACTER SET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci' NOT NULL,
  `roles_role_fk` INT NOT NULL,
  PRIMARY KEY (`user_id`),
  INDEX `fk_users_roles1_idx` (`roles_role_fk` ASC) VISIBLE,
  CONSTRAINT `fk_users_roles1`
    FOREIGN KEY (`roles_role_fk`)
    REFERENCES `hcs2508`.`roles` (`role_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 53
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `hcs2508`.`users_part_employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`users_part_employee` (
  `value_person_hour` INT NULL DEFAULT NULL,
  `information` VARCHAR(500) NULL DEFAULT NULL,
  `is_blocked` TINYINT(1) NOT NULL DEFAULT '0',
  `part_user_id` INT NOT NULL,
  PRIMARY KEY (`part_user_id`),
  INDEX `fk_users_part_employee_users1_idx` (`part_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_part_employee_users1`
    FOREIGN KEY (`part_user_id`)
    REFERENCES `hcs2508`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`employee_work_types`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`employee_work_types` (
  `ewt_id` INT NOT NULL AUTO_INCREMENT,
  `ewt_work_type_id` INT NOT NULL,
  `ewt_user_id_fk` INT NOT NULL,
  PRIMARY KEY (`ewt_id`),
  INDEX `fk_employee_work_types_work_types1_idx` (`ewt_work_type_id` ASC) VISIBLE,
  INDEX `fk_employee_work_types_users_part_employee1_idx` (`ewt_user_id_fk` ASC) VISIBLE,
  CONSTRAINT `fk_employee_work_types_work_types1`
    FOREIGN KEY (`ewt_work_type_id`)
    REFERENCES `hcs2508`.`work_types` (`work_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_employee_work_types_users_part_employee1`
    FOREIGN KEY (`ewt_user_id_fk`)
    REFERENCES `hcs2508`.`users_part_employee` (`part_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 23
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`request_statuses`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`request_statuses` (
  `request_status_id` TINYINT NOT NULL AUTO_INCREMENT,
  `request_status_name` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`request_status_id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`users_part_tenant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`users_part_tenant` (
  `city` VARCHAR(15) NOT NULL,
  `address` VARCHAR(30) NOT NULL,
  `part_user_id` INT NOT NULL,
  PRIMARY KEY (`part_user_id`),
  INDEX `fk_users_part_tenant_users1_idx` (`part_user_id` ASC) VISIBLE,
  CONSTRAINT `fk_users_part_tenant_users1`
    FOREIGN KEY (`part_user_id`)
    REFERENCES `hcs2508`.`users` (`user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`work_requests`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`work_requests` (
  `request_id` INT NOT NULL AUTO_INCREMENT,
  `filling_date` VARCHAR(20) NOT NULL,
  `planned_date` VARCHAR(15) NOT NULL,
  `request_status_id_fk` TINYINT NOT NULL,
  `tenant_user_id_fk` INT NOT NULL,
  PRIMARY KEY (`request_id`),
  INDEX `fk_work_requests_request_statuses1_idx1` (`request_status_id_fk` ASC) VISIBLE,
  INDEX `fk_work_requests_users_part_tenant1_idx` (`tenant_user_id_fk` ASC) VISIBLE,
  CONSTRAINT `fk_work_requests_request_statuses1`
    FOREIGN KEY (`request_status_id_fk`)
    REFERENCES `hcs2508`.`request_statuses` (`request_status_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_work_requests_users_part_tenant1`
    FOREIGN KEY (`tenant_user_id_fk`)
    REFERENCES `hcs2508`.`users_part_tenant` (`part_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 70
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`subqueries`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`subqueries` (
  `sub_id` INT NOT NULL AUTO_INCREMENT,
  `amount_of_work_in_hours` INT NULL DEFAULT NULL,
  `information` VARCHAR(100) NULL DEFAULT NULL,
  `sub_work_type_id` INT NOT NULL,
  `sub_work_request_id_fk` INT NOT NULL,
  PRIMARY KEY (`sub_id`),
  INDEX `fk_subqueries_work_types1_idx1` (`sub_work_type_id` ASC) VISIBLE,
  INDEX `fk_subqueries_work_requests1_idx1` (`sub_work_request_id_fk` ASC) VISIBLE,
  CONSTRAINT `fk_subqueries_work_types1`
    FOREIGN KEY (`sub_work_type_id`)
    REFERENCES `hcs2508`.`work_types` (`work_type_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_subqueries_work_requests1`
    FOREIGN KEY (`sub_work_request_id_fk`)
    REFERENCES `hcs2508`.`work_requests` (`request_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
AUTO_INCREMENT = 39
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `hcs2508`.`works_plan`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `hcs2508`.`works_plan` (
  `employee_user_id_fk` INT NOT NULL,
  `subquery_id` INT NOT NULL,
  PRIMARY KEY (`employee_user_id_fk`, `subquery_id`),
  INDEX `fk_works_plan_subqueries1_idx` (`subquery_id` ASC) VISIBLE,
  CONSTRAINT `fk_works_plan_users_part_employee1`
    FOREIGN KEY (`employee_user_id_fk`)
    REFERENCES `hcs2508`.`users_part_employee` (`part_user_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_works_plan_subqueries1`
    FOREIGN KEY (`subquery_id`)
    REFERENCES `hcs2508`.`subqueries` (`sub_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
