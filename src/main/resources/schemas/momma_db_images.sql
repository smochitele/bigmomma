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
-- Table structure for table `images`
--

DROP TABLE IF EXISTS `images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name_col` varchar(225) NOT NULL,
  `url` varchar(225) NOT NULL,
  `extension` varchar(10) DEFAULT NULL,
  `size_in_bytes` double DEFAULT NULL,
  `date_added` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `entity_id` int DEFAULT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `last_updated` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`),
  KEY `entity_id` (`entity_id`),
  CONSTRAINT `images_ibfk_1` FOREIGN KEY (`entity_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `images`
--

LOCK TABLES `images` WRITE;
/*!40000 ALTER TABLE `images` DISABLE KEYS */;
INSERT INTO `images` VALUES (1,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-14 19:40:46',1,1,'2023-10-16 11:18:48'),(2,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-14 21:17:51',1,1,'2023-10-16 11:18:48'),(3,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 11:51:01',1,1,'2023-10-16 11:51:01'),(4,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 11:51:01',1,1,'2023-10-16 11:51:01'),(5,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:03:53',1,1,'2023-10-16 12:03:53'),(6,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:03:53',1,1,'2023-10-16 12:03:53'),(7,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:05:51',1,1,'2023-10-16 12:05:51'),(8,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:05:51',1,1,'2023-10-16 12:05:51'),(9,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:07:44',1,1,'2023-10-16 12:07:44'),(10,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:07:44',1,1,'2023-10-16 12:07:44'),(11,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:09:20',1,1,'2023-10-16 12:09:20'),(12,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:09:20',1,1,'2023-10-16 12:09:20'),(13,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:10:36',1,1,'2023-10-16 12:10:36'),(14,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:10:36',1,1,'2023-10-16 12:10:36'),(15,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:13:53',1,1,'2023-10-16 12:13:53'),(16,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:13:53',1,1,'2023-10-16 12:13:53'),(17,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:39:20',1,1,'2023-10-16 12:39:20'),(18,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:39:20',1,1,'2023-10-16 12:39:20'),(19,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:40:56',1,1,'2023-10-16 12:40:56'),(20,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:40:56',1,1,'2023-10-16 12:40:56'),(21,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:43:16',1,1,'2023-10-16 12:43:16'),(22,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:43:16',1,1,'2023-10-16 12:43:16'),(23,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:45:07',1,1,'2023-10-16 12:45:07'),(24,'pic','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:45:07',1,1,'2023-10-16 12:45:07'),(25,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:46:25',1,1,'2023-10-16 12:46:25'),(26,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-16 12:46:25',1,1,'2023-10-16 12:46:25'),(27,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 09:39:37',1,1,'2023-10-18 09:39:37'),(28,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 09:39:37',1,1,'2023-10-18 09:39:37'),(29,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 09:40:47',1,1,'2023-10-18 09:40:47'),(30,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 09:40:47',1,1,'2023-10-18 09:40:47'),(31,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 10:43:03',36,1,'2023-10-18 10:43:03'),(32,'pic.jpeg','./src/main/resources/static/public/img/download.jpeg','jpeg',3000,'2023-10-18 10:43:03',36,1,'2023-10-18 10:43:03');
/*!40000 ALTER TABLE `images` ENABLE KEYS */;
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
