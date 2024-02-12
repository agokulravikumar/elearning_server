--
-- Database: `E Learning`
--
DROP DATABASE IF EXISTS `elearning_dev`;
CREATE DATABASE IF NOT EXISTS `elearning_dev`;
USE `elearning_dev`;

# CREATING USER FOR DB
CREATE USER 'elearn'@'localhost' IDENTIFIED BY 'Elearn@2024';

GRANT ALL PRIVILEGES ON elearning_dev.* TO 'elearn'@'localhost';

FLUSH PRIVILEGES;