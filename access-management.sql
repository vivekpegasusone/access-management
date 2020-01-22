DROP DATABASE IF EXISTS `access_management`;

CREATE DATABASE IF NOT EXISTS `access_management`;

use `access_management`;

-- -----------------------------------------------------------------------------
CREATE TABLE `applications` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `description` varchar(25) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Applications_Name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `loginId` varchar(25) NOT NULL,
  `firstName` varchar(25) DEFAULT NULL,
  `lastName` varchar(25) DEFAULT NULL,
  `password` varchar(25) NOT NULL,
  `emailId` varchar(50) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Users_Login_id` (`loginId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `roles` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Roles_Name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `resources` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `active` bit(1) DEFAULT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Resources_Name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `actions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Action_Name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `permissions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `actionId` bigint(20) NOT NULL,
  `resourceId` bigint(20) NOT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_Permissions_Name` (`name`),
  UNIQUE KEY `UK_Resource_Action` (`resourceId`,`actionId`),
  KEY `FK_Permissions_Action_Id` (`actionId`),
  KEY `FK_Permissions_Resource_Id` (`resourceId`),
  CONSTRAINT `FK_Permissions_Action_Id` FOREIGN KEY (`actionId`) REFERENCES `actions` (`id`),
  CONSTRAINT `FK_Permissions_Resource_Id` FOREIGN KEY (`resourceId`) REFERENCES `resources` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `user_roles` (
  `userId` bigint(20) NOT NULL,
  `roleId` bigint(20) NOT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  KEY `FK_Role_Id` (`roleId`),
  KEY `FK_User_Id` (`userId`),
  CONSTRAINT `FK_Users_Roles_Role_Id` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK_Users_Roles_User_Id` FOREIGN KEY (`userId`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `permission_mappings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleId` bigint(20) NOT NULL,
  `permissionId` bigint(20) NOT NULL,
  `createdBy` varchar(25) NOT NULL,
  `createdOn` timestamp NOT NULL,
  `updatedBy` varchar(25) NOT NULL,
  `updatedOn` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Permission_Mappings_Role_Id` (`roleId`),
  KEY `FK_Permission_Mappings_Permission_Id` (`permissionId`),
  CONSTRAINT `FK_Permission_Mappings_Role_Id` FOREIGN KEY (`roleId`) REFERENCES `roles` (`id`),
  CONSTRAINT `FK_Permission_Mappings_Permission_Id` FOREIGN KEY (`permissionId`) REFERENCES `permissions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;