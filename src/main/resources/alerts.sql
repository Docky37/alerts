CREATE DATABASE `alerts_prod` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `alerts_prod`;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

CREATE TABLE `medical_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allergies` tinyblob,
  `birthdate` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `medications` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `persons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `med_rec_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91lhgyogjpj1rlwiwwsuy21bo` (`address_id`),
  KEY `FKq3hfwjty7xmwnnli4sq5241tn` (`med_rec_id`),
  CONSTRAINT `FK91lhgyogjpj1rlwiwwsuy21bo` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKq3hfwjty7xmwnnli4sq5241tn` FOREIGN KEY (`med_rec_id`) REFERENCES `medical_records` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE DATABASE `alerts_tests` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE  `alerts_tests`;

CREATE TABLE `address` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `station` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `medical_records` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `allergies` tinyblob,
  `birthdate` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `medications` tinyblob,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

CREATE TABLE `persons` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `address_id` bigint DEFAULT NULL,
  `med_rec_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91lhgyogjpj1rlwiwwsuy21bo` (`address_id`),
  KEY `FKq3hfwjty7xmwnnli4sq5241tn` (`med_rec_id`),
  CONSTRAINT `FK91lhgyogjpj1rlwiwwsuy21bo` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`),
  CONSTRAINT `FKq3hfwjty7xmwnnli4sq5241tn` FOREIGN KEY (`med_rec_id`) REFERENCES `medical_records` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

COMMIT;
