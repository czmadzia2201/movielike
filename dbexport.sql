-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: movielike
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `celebrity`
--

DROP TABLE IF EXISTS `celebrity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `celebrity` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `director` tinyint(1) NOT NULL DEFAULT '0',
  `actor` tinyint(1) NOT NULL DEFAULT '0',
  `scriptwriter` tinyint(1) NOT NULL DEFAULT '0',
  `validationstatus` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=80 DEFAULT CHARSET=latin1 PACK_KEYS=0;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `celebrity`
--

LOCK TABLES `celebrity` WRITE;
/*!40000 ALTER TABLE `celebrity` DISABLE KEYS */;
INSERT INTO `celebrity` VALUES (1,'James Cameron',1,0,0,1),(2,'Michael Fassbender',0,1,0,1),(3,'Christopher Nolan',1,0,0,1),(4,'Quentin Tarantino',1,1,0,1),(5,'Sam Worthington',0,1,0,1),(6,'Zoe Saldana',0,1,0,1),(7,'Christian Bale',0,1,0,1),(8,'MCG',1,0,0,1),(9,'Doug Liman',1,0,0,1),(10,'Matt Damon',0,1,0,1),(11,'Tony Gilroy',1,0,0,1),(12,'Jeremy Renner',0,1,0,1),(13,'Jon Faverau',1,1,0,1),(14,'Robert Downey Jr.',0,1,0,1),(15,'Gwyneth Paltrow',0,1,0,1),(16,'Hugh Jackman',0,1,0,1),(17,'Anna Paquin',0,1,0,1),(18,'Antonio Banderas',0,1,0,1),(19,'Salma Hayek',0,1,0,1),(20,'Carlos Gallardo',0,1,0,1),(21,'Arnold Schwarzenegger',0,1,0,1),(22,'Linda Hamilton',0,1,0,1),(23,'Roy Schneider',0,1,0,1),(24,'Daniel Brühl',0,1,0,1),(25,'Chris Hemsworth',0,1,0,1),(26,'Joel Kinnaman',0,1,0,1),(27,'Julia Jentsch',0,1,0,1),(28,'Stipe Erceg',0,1,0,1),(29,'Marion Cotillard',0,1,0,1),(30,'Matthias Schoenaerts',0,1,0,1),(31,'Michael Biehn',0,1,0,1),(32,'Johnny Depp',0,1,0,1),(33,'George Clooney',0,1,0,1),(34,'Juliette Lewis',0,1,0,1),(35,'Kate Beckinsale',0,1,0,1),(36,'Vincent Pérez',1,1,0,1),(37,'Russel Crowe',0,1,0,1),(38,'Toshiro Mifune',0,1,0,1),(39,'Keanu Reeves',0,1,0,1),(40,'Patrick Swayze',0,1,0,1),(41,'Bryan Singer',1,0,0,1),(42,'Robert Rodriguez',1,0,0,1),(43,'Bob Fosse',1,0,0,1),(44,'Ron Howard',1,0,0,1),(45,'Kike Maillo',1,0,0,1),(46,'José Padilha',1,0,0,1),(47,'Hans Weingartner',1,0,0,1),(48,'Jacques Audiard',1,0,0,1),(49,'Len Wiseman',1,0,0,1),(50,'Tim Pope',1,0,0,1),(51,'Ridley Scott',1,0,0,1),(52,'Akira Kurosawa',1,0,0,1),(53,'Kathryn Bigelow',1,0,0,1),(67,'Sam Raimi',1,0,0,1),(68,'Tom Hiddlestone',0,1,0,1),(69,'Jakub Gierszał',0,1,0,1),(70,'Scott Eastwood',0,1,0,1),(71,'Jan Komasa',1,0,0,1);
/*!40000 ALTER TABLE `celebrity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie`
--

DROP TABLE IF EXISTS `movie`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `year` int(4) NOT NULL DEFAULT '0',
  `description` text COLLATE utf8_unicode_ci,
  `genre_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `added_by` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `rating_sum` int(10) NOT NULL DEFAULT '0',
  `voters` int(5) NOT NULL DEFAULT '0',
  `rating_avg_num` double(2,1) NOT NULL DEFAULT '0.0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_movie` (`title`,`year`)
) ENGINE=MyISAM AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie`
--

LOCK TABLES `movie` WRITE;
/*!40000 ALTER TABLE `movie` DISABLE KEYS */;
INSERT INTO `movie` VALUES (1,'Avatar',2008,'',NULL,NULL,1,'user2',0,0,0.0),(2,'Terminator Salvation',2009,'Fourth part of the Terminator franchise. ',NULL,NULL,1,'lena',7,1,7.0),(3,'Bourne Identity',2002,'Adaptation of the bestseller novel by Robert Ludlum.',NULL,NULL,1,'lena',0,0,0.0),(4,'Bourne Legacy',2012,'Fourth part of the Bourne franchise. After first three original novel adaptations the storyline takes up on another character from \'Bourne generation\'.',NULL,NULL,1,'lena',0,0,0.0),(5,'Iron Man',2008,'Witty and funny take on one of Marvel\'s iconic characters - Iron Man vel Tony Stark.',NULL,NULL,1,'user2',0,0,0.0),(6,'X-Men',2000,'Adaptation of one of Marvel\'s most popular comic book series - X-Men.',NULL,NULL,1,'lena',0,0,0.0),(7,'Desperado',1994,'A mysterious man arrives to a small mexican town with his guitar. But is he a musician or someone entirely else...','','',1,'czmadzia',0,0,0.0),(8,'El Mariachi',1992,'A young mariachi arrives to a small town looking for a job. By unfortunate coincidence, he is drawn into dramatic events that take place in the town.',NULL,NULL,1,'lena',0,0,0.0),(9,'Terminator',1984,'Killer machine goes back in time to kill Sarah Connor - a future mother of the lider of the resistance against the machines.',NULL,NULL,1,'lena',0,0,0.0),(10,'All That Jazz',1979,'',NULL,NULL,1,'user2',0,0,0.0),(11,'Rush',2013,'A story based on a real life rivalry of two F1 drivers: Niki Lauda and James Hunt.',NULL,NULL,1,'lena',0,0,0.0),(12,'Eva',2011,'',NULL,NULL,1,'lena',0,0,0.0),(13,'Robocop',2014,'A remake of a classic movie from 1987. Detroid, not far away future. A policeman is badly wounded in explosion. In order to safe his life he is turned into a robot in an experimental program.',NULL,NULL,1,'dwaynario',0,0,0.0),(14,'The Educators',2004,'Three young people have an interesting way to spend their free time and express their ideals.',NULL,NULL,1,'lena',0,0,0.0),(15,'Rust and Bone',2012,'A young trainer of killer whales loses her legs in an accident. When the life seems to have ended to her, it takes an unexpected turn.',NULL,NULL,1,'dwaynario',0,0,0.0),(16,'Once Upon a Time in Mexico',1996,'Continuation of Desperado.',NULL,NULL,1,'lena',0,0,0.0),(17,'From Dusk Till Dawn',2003,'',NULL,NULL,1,'user5_deleted',1,1,1.0),(18,'Underworld',2003,'Contemporary wampires fight with contemporary werewolfs. Thrown in the middle of it is a young man - the one that can bring the change.',NULL,NULL,1,'lena',0,0,0.0),(19,'Underworld: Evolution',2007,'Sequel to Underworld.',NULL,NULL,1,'czmadzia',2,1,2.0),(20,'The Crow: City of Angels',1996,'Vincent Perez in a very stupid continuation of a cool movie with Brandon Lee.',NULL,NULL,1,'lena',130,39,3.3),(47,'Gladiator',2000,'When a Roman general is betrayed and his family murdered by an emperor\'s corrupt son, he comes to Rome as a gladiator to seek revenge.',NULL,NULL,1,'dwaynario',8,1,8.0),(26,'Rashomon',1950,'To add',NULL,NULL,1,'czmadzia',21,4,5.3),(50,'The Hurt Locker',2008,'A story about addiction to war and danger.',NULL,NULL,1,'dwaynario',34,4,8.5),(31,'Point Break',1991,'Young FBI agents investigates a series of bank robberies.',NULL,NULL,1,'czmadzia',45,7,6.4);
/*!40000 ALTER TABLE `movie` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_country`
--

DROP TABLE IF EXISTS `movie_country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_country` (
  `movie_id` int(11) NOT NULL DEFAULT '0',
  `countrylist` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  KEY `FK_o63u2922xbu7l9mf6usbclmmm` (`movie_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_country`
--

LOCK TABLES `movie_country` WRITE;
/*!40000 ALTER TABLE `movie_country` DISABLE KEYS */;
INSERT INTO `movie_country` VALUES (2,'USA'),(1,'USA'),(3,'USA'),(4,'USA'),(5,'USA'),(6,'USA'),(7,'USA'),(8,'Mexico'),(9,'USA'),(10,'USA'),(11,'USA'),(12,'Spain'),(13,'USA'),(14,'Germany'),(15,'France'),(16,'USA'),(17,'USA'),(18,'USA'),(19,'USA'),(20,'USA'),(47,'USA'),(26,'Japan'),(50,'USA'),(31,'USA'),(7,'Mexico');
/*!40000 ALTER TABLE `movie_country` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_director`
--

DROP TABLE IF EXISTS `movie_director`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_director` (
  `movie_id` int(10) NOT NULL DEFAULT '0',
  `director_id` int(10) NOT NULL DEFAULT '0',
  KEY `FK_otppr1xkqn7jadlvk90qpwtxu` (`movie_id`),
  KEY `FK_tj4fr91wgocno9c749puxacre` (`director_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_director`
--

LOCK TABLES `movie_director` WRITE;
/*!40000 ALTER TABLE `movie_director` DISABLE KEYS */;
INSERT INTO `movie_director` VALUES (1,1),(2,8),(3,9),(4,11),(5,13),(6,41),(8,42),(9,1),(10,43),(11,44),(12,45),(13,46),(14,47),(15,48),(16,42),(17,42),(17,4),(18,49),(19,49),(20,50),(26,52),(31,53),(47,51),(50,53),(7,42);
/*!40000 ALTER TABLE `movie_director` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_genre` (
  `movie_id` int(10) NOT NULL DEFAULT '0',
  `genrelist` varchar(15) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  KEY `FK_o8ayd24knrkkjfuiugi2skgjc` (`movie_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES (1,'SF'),(1,'Adventure'),(2,'SF'),(3,'Action'),(4,'Action'),(5,'SF'),(6,'SF'),(7,'Adventure'),(7,'Action'),(8,'Action'),(8,'Adventure'),(9,'SF'),(10,'Musical'),(10,'Drama'),(10,'Biography'),(11,'Biography'),(11,'Sport'),(11,'Drama'),(12,'SF'),(13,'Action'),(13,'SF'),(14,'Romance'),(14,'Drama'),(14,'Comedy'),(15,'Drama'),(15,'Romance'),(16,'Action'),(16,'Adventure'),(17,'Horror'),(18,'Fantasy'),(19,'Fantasy'),(20,'Thriller'),(26,'Drama'),(31,'Action'),(47,'Drama'),(47,'Historical'),(50,'Drama'),(50,'War'),(19,'Thriller'),(19,'Mystery');
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_leadactors`
--

DROP TABLE IF EXISTS `movie_leadactors`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_leadactors` (
  `movie_id` int(10) NOT NULL DEFAULT '0',
  `actor_id` int(10) NOT NULL DEFAULT '0',
  KEY `FK_97p68ge0s67k8sm5w7ub0kcvt` (`movie_id`),
  KEY `FK_mugxos64ivw24fhvoothsy5mt` (`actor_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_leadactors`
--

LOCK TABLES `movie_leadactors` WRITE;
/*!40000 ALTER TABLE `movie_leadactors` DISABLE KEYS */;
INSERT INTO `movie_leadactors` VALUES (1,5),(1,6),(2,5),(2,7),(3,10),(4,12),(5,14),(5,15),(6,16),(6,17),(8,20),(9,21),(9,22),(9,31),(10,23),(12,24),(11,24),(11,25),(13,26),(14,24),(14,27),(14,28),(15,29),(15,30),(16,18),(16,32),(17,33),(17,34),(18,35),(19,35),(20,36),(26,38),(31,39),(31,40),(47,37),(50,12),(7,18),(7,19);
/*!40000 ALTER TABLE `movie_leadactors` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_rejected`
--

DROP TABLE IF EXISTS `movie_rejected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_rejected` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `year` int(4) NOT NULL DEFAULT '0',
  `description` text COLLATE utf8_unicode_ci,
  `genre_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `country_other` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `status` tinyint(1) NOT NULL DEFAULT '0',
  `added_by` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `rating_sum` int(10) NOT NULL DEFAULT '0',
  `voters` int(5) NOT NULL DEFAULT '0',
  `rating_avg_num` double(2,1) NOT NULL DEFAULT '0.0',
  `reason` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `directorstring` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `leadactorsstring` varchar(150) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_movie` (`title`,`year`)
) ENGINE=MyISAM AUTO_INCREMENT=207 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci PACK_KEYS=1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_rejected`
--

LOCK TABLES `movie_rejected` WRITE;
/*!40000 ALTER TABLE `movie_rejected` DISABLE KEYS */;
/*!40000 ALTER TABLE `movie_rejected` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `review` (
  `id` int(5) NOT NULL AUTO_INCREMENT,
  `author` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '0',
  `movie_id` int(4) NOT NULL DEFAULT '0',
  `content` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (162,'user2',5,'gfvfdvfdfvfvfdd'),(7,'dwaynario',5,'&#379;&#377;&#378;&#263;&#347;&#322;ó'),(8,'dwaynario',7,'&#263;&#378;&#380;&#261;&#281;&#322;ó&#379;&#262;\r\n&#379;ó&#322;&#263;'),(9,'dwaynario',7,'&#379;ó&#322;&#263;'),(12,'czmadzia',8,'Zó&#322;&#263;'),(92,'user4_deleted',9,'anuibpnfiupvmsdiofmp'),(25,'lena',5,'żłóść'),(164,'user2',11,'rshsbfgvrevffdv'),(163,'dwaynario',6,'udu6tnyff bygytu'),(31,'lena',6,'żądłądź'),(85,'lena',3,'gmieom[haqqrevmfdkr'),(90,'lena',3,'ju tyjtdsdtgbgf'),(91,'lena',6,'XMenXMenXMen'),(94,'dwaynario',8,'yjyhgsrtvthy33333'),(96,'dwaynario',5,'jhbikftrffffff'),(111,'lena',17,'hpditmprisopgmoipgm'),(165,'lena',12,'fnjghmjchgmchgnc'),(125,'lena',8,'vsvfxvbtxdfb'),(157,'lena',5,'fffffffffffddddddddd'),(129,'dwaynario',7,'rbyhujygkuhjkv'),(130,'lena',9,'fxgbn vcbcfcfxd'),(159,'dwaynario',9,'new review by Dwaynarioooooo'),(134,'lena',5,'Yet another trial with redirect'),(136,'lena',5,'eeeeeeeeeee'),(161,'dwaynario',8,'vregvdsfvfvvvvvv'),(146,'czmadzia',18,'dsgdfbhgccvfd'),(147,'dwaynario',11,'vvvvvvvvvvvvvvvvvvvvv'),(148,'dwaynario',11,'vvvvvvvvvvvvvvvvvvvvv'),(149,'dwaynario',12,'Evaevaevaevaeva evaevaeva'),(150,'dwaynario',12,'fjfthrgfdbffgmjh'),(151,'dwaynario',12,'ggggggggggggggggggggggggg'),(154,'czmadzia',12,'juyikyfhrdghuyljum fdtu87kuymj<br>\r\nsrtmigopsmvfiodp'),(155,'dwaynario',15,'hytnjrhyhrdtgrgrjyu'),(156,'dwaynario',15,'hythdhdbgfj,uyjfgftesttest'),(167,'czmadzia',14,'umtiyf7ikyfuiuygjyg');
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `password` varchar(20) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `role` varchar(5) COLLATE utf8_unicode_ci NOT NULL DEFAULT 'user',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`username`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('lena','lady_dev','admin',1),('czmadzia','raoul7katze','user',1),('user2','pass2','user',1),('dwaynario','magdalena','user',1),('user3','pass3','user',1),('user4','pass4','user',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_movie_link`
--

DROP TABLE IF EXISTS `user_movie_link`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_movie_link` (
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `movie_id` int(4) NOT NULL DEFAULT '0',
  `liked` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`,`movie_id`,`liked`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_movie_link`
--

LOCK TABLES `user_movie_link` WRITE;
/*!40000 ALTER TABLE `user_movie_link` DISABLE KEYS */;
INSERT INTO `user_movie_link` VALUES ('czmadzia',1,1),('czmadzia',2,1),('czmadzia',3,1),('czmadzia',5,1),('czmadzia',7,1),('czmadzia',8,1),('czmadzia',9,-1),('czmadzia',11,1),('czmadzia',12,1),('czmadzia',13,1),('czmadzia',14,1),('czmadzia',15,1),('czmadzia',17,-1),('czmadzia',18,-1),('czmadzia',19,-1),('czmadzia',20,-1),('czmadzia',26,1),('czmadzia',47,1),('lena',2,1),('lena',3,1),('lena',6,1),('lena',8,1),('lena',14,-1),('lena',15,1),('lena',19,-1),('lena',20,-1),('lena',31,1);
/*!40000 ALTER TABLE `user_movie_link` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_movie_rate`
--

DROP TABLE IF EXISTS `user_movie_rate`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_movie_rate` (
  `username` varchar(20) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT '',
  `movie_id` int(4) NOT NULL DEFAULT '0',
  `last_rated` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `rating_sum` tinyint(2) NOT NULL DEFAULT '0',
  `votes` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`,`movie_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_movie_rate`
--

LOCK TABLES `user_movie_rate` WRITE;
/*!40000 ALTER TABLE `user_movie_rate` DISABLE KEYS */;
INSERT INTO `user_movie_rate` VALUES ('czmadzia',31,'2016-04-03 12:45:27',15,2),('czmadzia',26,'2016-04-02 15:31:09',11,2),('user2',31,'2016-04-03 15:25:06',21,4),('czmadzia',50,'2016-04-03 15:30:24',8,1),('czmadzia',47,'2016-04-03 16:53:27',8,1),('czmadzia',17,'2016-04-03 17:11:05',1,1),('lena',2,'2016-04-24 13:19:09',7,1),('lena',31,'2016-04-24 13:37:00',9,1),('lena',50,'2016-04-24 13:57:03',8,1);
/*!40000 ALTER TABLE `user_movie_rate` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-09-25 14:23:35
