-- MySQL dump 10.13  Distrib 8.0.35, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: childrenmaster
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `math_exercise_scores`
--

DROP TABLE IF EXISTS `math_exercise_scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `math_exercise_scores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `correct_rate` float NOT NULL,
  `date_recorded` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `math_exercise_scores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `math_exercise_scores`
--

LOCK TABLES `math_exercise_scores` WRITE;
/*!40000 ALTER TABLE `math_exercise_scores` DISABLE KEYS */;
INSERT INTO `math_exercise_scores` VALUES (9,1,0.8,'2024-05-01 09:00:00'),(10,1,0.85,'2024-05-01 14:00:00'),(11,1,0.9,'2024-05-08 10:00:00'),(12,1,0.95,'2024-05-08 15:00:00');
/*!40000 ALTER TABLE `math_exercise_scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poem_kid`
--

DROP TABLE IF EXISTS `poem_kid`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poem_kid` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `verse1` varchar(255) NOT NULL,
  `verse2` varchar(255) NOT NULL,
  `verse3` varchar(255) NOT NULL,
  `verse4` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poem_kid`
--

LOCK TABLES `poem_kid` WRITE;
/*!40000 ALTER TABLE `poem_kid` DISABLE KEYS */;
INSERT INTO `poem_kid` VALUES (1,'静夜思','床前明月光','疑是地上霜','举头望明月','低头思故乡'),(2,'春晓','春眠不觉晓','处处闻啼鸟','夜来风雨声','花落知多少'),(3,'悯农','锄禾日当午','汗滴禾下土','谁知盘中餐','粒粒皆辛苦'),(4,'咏鹅','鹅鹅鹅','曲项向天歌','白毛浮绿水','红掌拨清波'),(5,'风','解落三秋叶','能开二月花','过江千尺浪','入竹万竿斜'),(6,'静夜思','床前明月光','疑是地上霜','举头望明月','低头思故乡'),(7,'游子吟','慈母手中线','游子身上衣','临行密密缝','意恐迟迟归'),(8,'所见','牧童骑黄牛','歌声振林樾','意欲捕鸣蝉','忽然闭口立'),(9,'回乡偶书','少小离家老大回','乡音无改鬓毛衰','儿童相见不相识','笑问客从何处来'),(10,'早发白帝城','朝辞白帝彩云间','千里江陵一日还','两岸猿声啼不住','轻舟已过万重山');
/*!40000 ALTER TABLE `poem_kid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poem_teen`
--

DROP TABLE IF EXISTS `poem_teen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poem_teen` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `verse1` varchar(255) NOT NULL,
  `verse2` varchar(255) NOT NULL,
  `verse3` varchar(255) NOT NULL,
  `verse4` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poem_teen`
--

LOCK TABLES `poem_teen` WRITE;
/*!40000 ALTER TABLE `poem_teen` DISABLE KEYS */;
INSERT INTO `poem_teen` VALUES (1,'黄鹤楼送孟浩然之广陵','故人西辞黄鹤楼','烟花三月下扬州','孤帆远影碧空尽','唯见长江天际流'),(2,'望庐山瀑布','日照香炉生紫烟','遥看瀑布挂前川','飞流直下三千尺','疑是银河落九天'),(3,'登鹳雀楼','白日依山尽','黄河入海流','欲穷千里目','更上一层楼'),(4,'题西林壁','横看成岭侧成峰','远近高低各不同','不识庐山真面目','只缘身在此山中'),(5,'江雪','千山鸟飞绝','万径人踪灭','孤舟蓑笠翁','独钓寒江雪'),(6,'春望','国破山河在','城春草木深','感时花溅泪','恨别鸟惊心'),(7,'凉州词','黄河远上白云间','一片孤城万仞山','羌笛何须怨杨柳','春风不度玉门关'),(8,'早春呈水部张十八员外','天街小雨润如酥','草色遥看近却无','最是一年春好处','绝胜烟柳满皇都'),(9,'芙蓉楼送辛渐','寒雨连江夜入吴','平明送客楚山孤','洛阳亲友如相问','一片冰心在玉壶'),(10,'赠汪伦','李白乘舟将欲行','忽闻岸上踏歌声','桃花潭水深千尺','不及汪伦送我情');
/*!40000 ALTER TABLE `poem_teen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `poetry_exercise_scores`
--

DROP TABLE IF EXISTS `poetry_exercise_scores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `poetry_exercise_scores` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `correct_rate` float NOT NULL,
  `date_recorded` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `poetry_exercise_scores_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `poetry_exercise_scores`
--

LOCK TABLES `poetry_exercise_scores` WRITE;
/*!40000 ALTER TABLE `poetry_exercise_scores` DISABLE KEYS */;
INSERT INTO `poetry_exercise_scores` VALUES (1,1,0.75,'2024-05-01 08:00:00'),(2,1,0.78,'2024-05-01 12:00:00'),(3,1,0.82,'2024-05-08 09:00:00'),(4,1,0.85,'2024-05-08 11:00:00');
/*!40000 ALTER TABLE `poetry_exercise_scores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(254) DEFAULT NULL,
  `age` int NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nick_name` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'christianlsl@foxmail.com',9,'111','ch');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-06 18:54:38
