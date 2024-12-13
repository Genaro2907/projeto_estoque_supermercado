CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `entry_date` datetime(6) DEFAULT NULL,
  `department_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8vqjj32x97p0xtnbe504aku1c` (`department_id`),
  CONSTRAINT `FK8vqjj32x97p0xtnbe504aku1c` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
);


	
