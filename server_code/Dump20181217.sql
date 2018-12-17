-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: myymdb.cbs4aawfycnj.ap-northeast-2.rds.amazonaws.com    Database: bbang5368
-- ------------------------------------------------------
-- Server version	5.6.41-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `building`
--

DROP TABLE IF EXISTS `building`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `building` (
  `bid` int(11) NOT NULL,
  `bname` varchar(45) DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL,
  `altitude` double DEFAULT NULL,
  `content` text,
  PRIMARY KEY (`bid`),
  UNIQUE KEY `bname_UNIQUE` (`bname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `building`
--

LOCK TABLES `building` WRITE;
/*!40000 ALTER TABLE `building` DISABLE KEYS */;
INSERT INTO `building` VALUES (1,'원흥별관',37.55865,126.99872,69.2,'학군단, ROTC');
INSERT INTO `indoor` VALUES (2,'신공학관',37.55815,126.99845,67.4,'1층에는 편의점, 카페, 식당이 위치해 있으며 3층부터 9층까지 각 학과의 강의실, 실습실, 연구실 등이 있습니다. 10층에는 연구실이 있습니다.');
INSERT INTO `indoor` VALUES (3,'팔정도',37.55823,127.00016,86.4,'중앙 광장을 중심으로 여덟 개의 길이 뻗어져 나가는 구조로 불교에서의 팔정도를 상징합니다. 중앙에는 큰 부처님(석가여래) 석상과 코끼리 동상 3마리가 있습니다.');
INSERT INTO `indoor` VALUES (4,'중앙도서관',37.55792,126.99909,81.4,'자료실의 운영시간은 (평일) 오전 아홉시에서 오후 아홉시, (토요일) 오전 아홉시에서 오후 다섯시, (일요일) 휴관 입니다.');
INSERT INTO `indoor` VALUES (5,'다향관',37.55868,127.00037,81.6,'안경원, 서점');
INSERT INTO `indoor` VALUES (6,'본관',37.55851,126.99951,79.8,'학교 행정에 관한  시설이 있고, 1층에는 카페 세리오가 있습니다.');
INSERT INTO `indoor` VALUES (7,'명진관',37.55767,126.99994,89.1,'문과대와 이과대가 사용하고 있습니다. 신한은행, 우체국, 동아리방이 있습니다.');
INSERT INTO `indoor` VALUES (8,'상록원',37.55699,126.99963,89.3,'학생 식당'),(9,'정각원',37.55744,127.00114,84.1,'절');
INSERT INTO `indoor` VALUES (10,'혜화관',37.55765,127.0018,76,'혜화'),(11,'카페쎄리오',37.55886,126.99962,77.4,'카페');
INSERT INTO `indoor` VALUES (12,'학생회관',37.5601,126.99842,58.5,'학생회관');
INSERT INTO `indoor` VALUES (13,'원흥관',37.55903,126.9987,67.6,'융합sw교육원');
INSERT INTO `indoor` VALUES (14,'남산학사',37.5583,126.99802,63.2,'기숙사');
INSERT INTO `indoor` VALUES (15,'대운동장',37.55652,127.00058,84.6,'잔디밭');
INSERT INTO `indoor` VALUES (16,'만해광장',37.55957,126.99945,71.6,'농구코드, 족구장');
/*!40000 ALTER TABLE `building` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `indoor`
--

DROP TABLE IF EXISTS `indoor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `indoor` (
  `floor` varchar(45) DEFAULT NULL,
  `content` text,
  `macAddr` varchar(45) NOT NULL,
  PRIMARY KEY (`macAddr`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `indoor`
--

LOCK TABLES `indoor` WRITE;
/*!40000 ALTER TABLE `indoor` DISABLE KEYS */;
INSERT INTO `indoor` VALUES ('원흥관2층','소나무방, 공과대 학생회실','34:FC:B9:25:B6:12');
INSERT INTO `indoor` VALUES ('원흥관3층','융합SW교육원','34:FC:B9:25:BA:72');
INSERT INTO `indoor` VALUES ('신공학관 3층','알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실','70:3A:0E:AD:02:12');
INSERT INTO `indoor` VALUES ('신공학관 3층','알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실','A8:BD:27:80:15:92');
INSERT INTO `indoor` VALUES ('신공학관 6층','실습실(6144)','A8:BD:27:80:34:F2');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:1A:52');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:1A:D2');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:25:92');
INSERT INTO `indoor` VALUES ('원흥관 2층','소나무방, 공과대 학생회실','B4:5D:50:6A:29:E2');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:2A:12');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:2E:22');
INSERT INTO `indoor` VALUES ('원흥관2층','실험실','B4:5D:50:6A:2E:32');
INSERT INTO `indoor` VALUES ('신공학관 3층','알파실, 컴퓨터공학과 실습실 1,2, 컴퓨터공학과 학생회실','B4:5D:50:6A:43:E2');
INSERT INTO `indoor` VALUES ('신공학관 3층','알파실(3173호) , 운영시간은 오전 아홉시에서 오후 아홉시까지입니다.','B4:5D:50:6A:43:F2');
INSERT INTO `indoor` VALUES ('신공학관 3층','3183호, 컴퓨터공학과 실습실','B4:5D:50:6A:44:02');
INSERT INTO `indoor` VALUES ('신공학관 3층','3183호, 컴퓨터공학과 실습실','B4:5D:50:6A:44:12');
/*!40000 ALTER TABLE `indoor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bbang5368'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-17 19:40:54
