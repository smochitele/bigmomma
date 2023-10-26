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
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_col` varchar(225) NOT NULL,
  `description_col` varchar(225) NOT NULL,
  `instructions` varchar(225) DEFAULT NULL,
  `color` varchar(25) NOT NULL,
  `price` float NOT NULL,
  `best_before` varchar(25) DEFAULT NULL,
  `quantity` int NOT NULL,
  `category` int NOT NULL,
  `rating` float DEFAULT '5',
  `product_owner` int NOT NULL,
  `is_active` tinyint DEFAULT '1',
  `date_added` datetime DEFAULT CURRENT_TIMESTAMP,
  `last_updated` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'bread','to be eaten','apply buttter','white',22,'1696257843',3,2,5,1,1,NULL,'2023-10-03 18:28:58'),(2,'butter','to be eaten','apply buttter','yellow',22,'2023-09-09',3,1,5,1,0,'2023-10-02 18:30:09','2023-10-03 18:59:02'),(3,'butter','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-02 00:00:00','2023-10-03 21:06:19'),(4,'butter','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 14:59:36','2023-10-04 14:59:36'),(5,'sugar','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 15:00:41','2023-10-04 15:00:41'),(6,'sugar','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 15:01:27','2023-10-04 15:01:27'),(7,'spice','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 15:01:38','2023-10-04 15:01:38'),(8,'fish','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 15:03:22','2023-10-04 15:03:22'),(9,'meat','to be drank','apply oil','yellow',22,'2023-09-09',3,1,5,1,1,'2023-10-04 15:03:22','2023-10-04 15:03:22'),(10,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:29:53','2023-10-16 11:29:53'),(11,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:31:12','2023-10-16 11:31:12'),(12,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:31:36','2023-10-16 11:31:36'),(13,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:35:48','2023-10-16 11:35:48'),(14,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:41:31','2023-10-16 11:41:31'),(15,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 11:51:01','2023-10-16 11:51:01'),(16,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:03:52','2023-10-16 12:03:52'),(17,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:05:51','2023-10-16 12:05:51'),(18,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:07:06','2023-10-16 12:07:06'),(19,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:08:44','2023-10-16 12:08:44'),(20,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:09:47','2023-10-16 12:09:47'),(21,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:11:29','2023-10-16 12:11:29'),(22,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:39:20','2023-10-16 12:39:20'),(23,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:40:55','2023-10-16 12:40:55'),(24,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:43:16','2023-10-16 12:43:16'),(25,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:45:07','2023-10-16 12:45:07'),(26,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-16 12:46:25','2023-10-16 12:46:25'),(27,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 09:39:37','2023-10-18 09:39:37'),(28,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 09:40:14','2023-10-18 09:40:14'),(29,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 09:41:12','2023-10-18 09:41:12'),(30,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 09:52:48','2023-10-18 09:52:48'),(31,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 09:54:20','2023-10-18 09:54:20'),(32,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 10:18:18','2023-10-18 10:18:18'),(33,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 10:21:50','2023-10-18 10:21:50'),(34,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 10:34:44','2023-10-18 10:34:44'),(35,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 10:35:46','2023-10-18 10:35:46'),(36,'bread','to be eaten','apply buttter','white',22,'1970-01-20',3,2,5,1,1,'2023-10-18 10:43:03','2023-10-18 10:43:03');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
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
