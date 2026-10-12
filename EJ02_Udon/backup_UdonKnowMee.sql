-- MySQL dump 10.13  Distrib 9.6.0, for Win64 (x86_64)
--
-- Host: localhost    Database: UdonKnowMee
-- ------------------------------------------------------
-- Server version	9.6.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `UdonKnowMee`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `UdonKnowMee` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `UdonKnowMee`;

--
-- Table structure for table `noodles`
--

DROP TABLE IF EXISTS `noodles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `noodles` (
  `id` int NOT NULL,
  `flavour` varchar(50) DEFAULT NULL,
  `brand` varchar(50) DEFAULT NULL,
  `price` float DEFAULT NULL,
  `qty` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `noodles`
--

LOCK TABLES `noodles` WRITE;
/*!40000 ALTER TABLE `noodles` DISABLE KEYS */;
INSERT INTO `noodles` VALUES (1001,'Carbonara','Samyang',2.1,120),(1002,'Curry','Maggi',0.8,420),(1003,'Chicken','Mamee',0.8,120),(1004,'Mi Goreng','Indomie',0.5,150),(1005,'Original','Shin Ramyun',1.2,100),(1006,'Original','Samyang',2,250),(1007,'Chicken','Maggi',0.7,350),(1008,'Vegetarian','Mamee',0.6,200),(1009,'Satay','Indomie',0.6,100),(1010,'Stir Fry','Shin Ramyun',1.3,150),(1011,'Cheese','Samyang',2.3,200),(1012,'Asam Laksa','Maggi',0.9,300),(1013,'Curry','Mamee',1,350),(1014,'Rendang','Indomie',0.6,110),(1015,'Kimchi','Shin Ramyun',1.5,160);
/*!40000 ALTER TABLE `noodles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_records`
--

DROP TABLE IF EXISTS `order_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_records` (
  `id` int DEFAULT NULL,
  `qty_ordered` int DEFAULT NULL,
  `cust_name` varchar(30) DEFAULT NULL,
  `cust_email` varchar(30) DEFAULT NULL,
  `cust_phone` char(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_records`
--

LOCK TABLES `order_records` WRITE;
/*!40000 ALTER TABLE `order_records` DISABLE KEYS */;
/*!40000 ALTER TABLE `order_records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-03-19 21:06:03
