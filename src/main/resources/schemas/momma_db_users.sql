-- MySQL dump 10.13  Distrib 8.0.21, for macos10.15 (x86_64)
--
-- Host: 127.0.0.1    Database: momma_db
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `first_name` varchar(225) NOT NULL,
  `last_name` varchar(225) NOT NULL,
  `email_address` varchar(225) NOT NULL,
  `user_type` int NOT NULL,
  `date_added` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `cellphone_number` varchar(20) NOT NULL,
  `last_updated` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_address` (`email_address`),
  UNIQUE KEY `user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Tebs','FNB','mail@mail.co.za',0,'2023-10-25 09:40:49','0112235510','2023-10-25 14:25:03',1),(2,'Mavis','Mokou','mavis@mail.co.za',0,'2023-10-25 10:33:56','0152235515','2023-10-25 10:34:00',1),(3,'Lungz','FNB','lungz@mail.co.za',0,'2023-10-25 10:50:37','0152235515','2023-10-25 10:50:44',1),(4,'Tebs','FNB','tebs@mail.co.za',0,'2023-10-25 11:04:13','0152235515','2023-10-25 11:04:30',1),(5,'Tebs','FNB','tebs1@mail.co.za',0,'2023-10-25 11:13:22','0152235515','2023-10-25 11:13:22',1),(6,'Tebs','FNB','tebs2@mail.co.za',0,'2023-10-25 11:19:52','0152235515','2023-10-25 11:19:55',1),(8,'Tebs','FNB','tebs4@mail.co.za',0,'2023-10-25 11:34:54','0152235515','2023-10-25 11:34:56',1),(9,'Tebs','FNB','tebs5@mail.co.za',0,'2023-10-25 11:36:43','0152235515','2023-10-25 11:36:46',1),(10,'Tebs','FNB','tebs7@mail.co.za',0,'2023-10-25 11:44:26','0152235515','2023-10-25 11:44:30',1),(12,'Tebza','ATM','tebs8@mail.co.za',0,'2023-10-25 14:12:41','0000000000','2023-10-25 14:38:15',1),(14,'Mavis','Mokou','mavis2@mail.co.za',0,'2023-10-25 20:23:25','0152235515','2023-10-25 20:23:25',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-26  9:29:57
