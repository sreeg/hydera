CREATE DATABASE  IF NOT EXISTS `school` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `school`;
-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: localhost    Database: school
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `attendance`
--

DROP TABLE IF EXISTS `attendance`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `attendance` (
  `staffid` varchar(45) NOT NULL,
  `date` date DEFAULT NULL,
  `daysinmonth` int(11) DEFAULT NULL,
  `dayspresent` int(11) DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `month` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  KEY `staff_idx` (`staffid`),
  CONSTRAINT `staff` FOREIGN KEY (`staffid`) REFERENCES `staff` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `attendance`
--

LOCK TABLES `attendance` WRITE;
/*!40000 ALTER TABLE `attendance` DISABLE KEYS */;
/*!40000 ALTER TABLE `attendance` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `category` (
  `Id` varchar(45) NOT NULL,
  `Name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` (`Id`, `Name`) VALUES ('1','Permanant'),('2','Temporary'),('3','Visiting');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feepayment`
--

DROP TABLE IF EXISTS `feepayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feepayment` (
  `studentid` varchar(45) NOT NULL,
  `term1paymentamount` double DEFAULT NULL,
  `term2paymentamount` double DEFAULT NULL,
  `term3paymentamount` double DEFAULT NULL,
  `term1paiddate` date DEFAULT NULL,
  `term2paiddate` date DEFAULT NULL,
  `term3paiddate` date DEFAULT NULL,
  `term1cheque` varchar(200) DEFAULT NULL,
  `term2cheque` varchar(200) DEFAULT NULL,
  `term3cheque` varchar(200) DEFAULT NULL,
  `createdatetime` date DEFAULT NULL,
  `updatedatetime` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feepayment`
--

LOCK TABLES `feepayment` WRITE;
/*!40000 ALTER TABLE `feepayment` DISABLE KEYS */;
/*!40000 ALTER TABLE `feepayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary` (
  `employeeid` varchar(45) NOT NULL,
  `basicsalary` double DEFAULT NULL,
  `fixedda` double DEFAULT NULL,
  `hra` double DEFAULT NULL,
  `conveyanceall` double DEFAULT NULL,
  `pfno` varchar(300) DEFAULT NULL,
  `sbacno` varchar(300) DEFAULT NULL,
  `pfrate` double DEFAULT NULL,
  `proftaxdeduction` double DEFAULT NULL,
  `otherdeduction` double DEFAULT NULL,
  `pfamount` double DEFAULT NULL,
  `loanamount` double DEFAULT NULL,
  `createdatetime` date DEFAULT NULL,
  `updatedatetime` date DEFAULT NULL,
  UNIQUE KEY `employeeid_UNIQUE` (`employeeid`),
  KEY `staffid_employeeid_idx` (`employeeid`),
  CONSTRAINT `staffid_employeeid` FOREIGN KEY (`employeeid`) REFERENCES `staff` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary`
--

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` (`employeeid`, `basicsalary`, `fixedda`, `hra`, `conveyanceall`, `pfno`, `sbacno`, `pfrate`, `proftaxdeduction`, `otherdeduction`, `pfamount`, `loanamount`, `createdatetime`, `updatedatetime`) VALUES ('emp1',10000,4000,4000,800,'9899-98989-9888','0004100075217',12,230,100,2000,2000,'2015-10-13','2015-10-13'),('STAFF_ID10',5000,2000,2000,1212,'iouou','ouoiu',12,200,100,838,0,'2015-10-16','2015-10-16'),('STAFF_ID2',2000,200,200,200,'-','-',0,200,0,0,0,'2015-10-14','2015-10-14'),('STAFF_ID3',3000,909,9090,99,'iuuiu','uiui',12,999,233,3000,0,'2015-10-16','2015-10-16'),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',12,222,222,2222,2323,'2015-10-13','2015-10-14'),('STAFF_ID5',9090,90,900,90,'000','000',9,90,999,999,999,'2015-10-16','2015-10-16'),('STAFF_ID6',2000,900,999,9,'0909','09090',12,0,0,0,0,'2015-10-16','2015-10-16'),('STAFF_ID7',3333,33,33,33,'333','333',3,33,3,333,33,'2015-10-17','2015-10-17'),('STAFF_ID8',2323,2323,2323,2323,'2323','2323',12,22,233,3333,0,'2015-10-17','2015-10-17'),('STAFF_ID9',2999,999,999,99,'9999','9999',12,9,0,999,0,'2015-10-17','2015-10-17');
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `staff`
--

DROP TABLE IF EXISTS `staff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `staff` (
  `Id` varchar(45) NOT NULL,
  `FirstName` varchar(200) DEFAULT NULL,
  `LastName` varchar(200) DEFAULT NULL,
  `CategoryId` varchar(45) NOT NULL,
  `Designation` varchar(200) DEFAULT NULL,
  `SpouseName` varchar(200) DEFAULT NULL,
  `SpouseOccupation` varchar(200) DEFAULT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `DateOfJoining` date DEFAULT NULL,
  `JoiningSalary` decimal(20,2) DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `Mobile` varchar(45) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `ProfilePic` longblob,
  `houseno` varchar(200) DEFAULT NULL,
  `street` varchar(200) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `postalcode` varchar(200) DEFAULT NULL,
  `createdatetime` date DEFAULT NULL,
  `updatedatetime` date DEFAULT NULL,
  `isarchived` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`Id`),
  KEY `categoryId_category.Id_idx` (`CategoryId`),
  KEY `categoryid_category_id_idx` (`CategoryId`),
  CONSTRAINT `categoryid` FOREIGN KEY (`CategoryId`) REFERENCES `category` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `staff`
--

LOCK TABLES `staff` WRITE;
/*!40000 ALTER TABLE `staff` DISABLE KEYS */;
INSERT INTO `staff` (`Id`, `FirstName`, `LastName`, `CategoryId`, `Designation`, `SpouseName`, `SpouseOccupation`, `Phone`, `DateOfBirth`, `DateOfJoining`, `JoiningSalary`, `Gender`, `Mobile`, `Email`, `ProfilePic`, `houseno`, `street`, `city`, `postalcode`, `createdatetime`, `updatedatetime`, `isarchived`) VALUES ('emp1','Sreedhar','Ganduri','1','Professor','Sowjanya','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('STAFF_ID10','Amrut','Raj','3',NULL,'m','','','1981-10-10','2015-10-14',6097.00,'Male','','m',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0),('STAFF_ID2','Padma','T','2','','Malathi','House Wife','09848050340','2015-10-29','2015-10-13',5000.00,'Female','6173060467','raghuk@gmail.com',NULL,'23232','Plot No.303, Road No.4, Deendayal Nagar, Neredmet','Hyderabad','500056',NULL,NULL,0),('STAFF_ID3','Vimala','Kanth','1','Teacher','Vikram','Private Employee','898989898','1976-04-26','2015-10-11',40000.00,'Female','898989899','vimla@gmail.com',NULL,'201','Vimala Apts, Vimala Street','Hyderabad','500032',NULL,NULL,0),('STAFF_ID4','Balamani','G','2',NULL,'Raja','Rajiv','798798799','1984-03-12','2015-10-11',5000.00,'Female','797978989','jyothi@gmail.com',NULL,'100, 3rd floor','Chaitanya puri, Habsiguda','Hyderabad','500042','2015-10-11','2015-10-11',0),('STAFF_ID5','Balraj','B','2',NULL,'M','','','1972-10-12','2015-10-14',5200.00,'Male','','x',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0),('STAFF_ID6','Eshwar','J','2',NULL,'b','','','1978-10-10','2015-10-14',8700.00,'Male','','x',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0),('STAFF_ID7','Srinivas','M','3',NULL,'M','','','1977-10-10','2015-10-14',8565.00,'Male','','msrinivas@gmail.com',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0),('STAFF_ID8','Angela','Eastlyn','3',NULL,'M','','','1983-10-10','2015-10-14',18350.00,'Female','','Eastlyn@gmail.com',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0),('STAFF_ID9','Rekha','B Lagu','3',NULL,'M','','','1983-10-10','2015-10-14',15000.00,'Female','','r',NULL,'','','Hyderabad','','2015-10-14','2015-10-14',0);
/*!40000 ALTER TABLE `staff` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `Id` varchar(60) NOT NULL,
  `FirstName` varchar(200) DEFAULT NULL,
  `LastName` varchar(200) DEFAULT NULL,
  `Class` varchar(45) DEFAULT NULL,
  `Section` varchar(200) DEFAULT NULL,
  `FatherName` varchar(200) DEFAULT NULL,
  `FatherOccupation` varchar(200) DEFAULT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `DateOfBirth` date DEFAULT NULL,
  `DateOfJoining` date DEFAULT NULL,
  `MotherName` varchar(200) DEFAULT NULL,
  `MotherOccupation` varchar(200) DEFAULT NULL,
  `Gender` varchar(45) DEFAULT NULL,
  `GaurdianName` varchar(200) DEFAULT NULL,
  `Mobile` varchar(45) DEFAULT NULL,
  `Email` varchar(200) DEFAULT NULL,
  `ProfilePic` longblob,
  `houseno` varchar(200) DEFAULT NULL,
  `street` varchar(200) DEFAULT NULL,
  `city` varchar(200) DEFAULT NULL,
  `postalcode` varchar(45) DEFAULT NULL,
  `createdatetime` date DEFAULT NULL,
  `updatedatetime` date DEFAULT NULL,
  `isarchived` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`Id`, `FirstName`, `LastName`, `Class`, `Section`, `FatherName`, `FatherOccupation`, `Phone`, `DateOfBirth`, `DateOfJoining`, `MotherName`, `MotherOccupation`, `Gender`, `GaurdianName`, `Mobile`, `Email`, `ProfilePic`, `houseno`, `street`, `city`, `postalcode`, `createdatetime`, `updatedatetime`, `isarchived`) VALUES ('EMPID1','Sreedhar','Ganduri','Class IX','Section B','G','','09848050340','2015-10-01','2015-10-03','V','','Male','','6173060467','sreedhar.ganduri@gmail.com',NULL,'Plot No. 303','Road No. 4, Deen dayal Nagar, Neredmet','Hyderabad','500056',NULL,NULL,0),('EMPID10','Vijay','Vaddem','Class X','Section B','V','V','+447827974096','2015-10-29','2015-10-07','V','202','Male','V','6173060467','sreedhar.ganduri@gmail.com',NULL,NULL,'k nagar, road 2','Hyderabad','500056',NULL,NULL,0),('EMPID2','Sreedhar','Ganduri','Class LKG','Section B','g','g','09848050340','2015-10-01','2015-10-08','g','g','Male','g','+914030555600','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID3','Nageswara','Ganduri','Class X','Section B','s','s','+447827974096','2015-10-19','2015-10-28','s','','Male','s','+447827974096','nageswarag@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID4','Nageswara','Ganduri','Class X','Section B','s','s','+447827974096','2015-10-19','2015-10-28','s','','Male','s','+447827974096','nageswarag@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID5','Nageswara','Ganduri','Class X','Section B','s','s','+447827974096','2015-10-19','2015-10-28','s','','Male','s','+447827974096','nageswarag@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID6','Nageswara','Ganduri','Class VI','Section B','u','','+447827974096','2015-10-20','2015-10-28','u','','','u','+447827974096','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID7','Nageswara','Sastry','','','h','','','2015-10-05','2015-10-27','h','','','','','nageswarag@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID8','Nageswara','Sastry','Class IX','Section A','g','g','+447827974096','2015-10-20','2015-10-20','g','','','g','09848050340','nageswarag@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),('EMPID9','Sreedhar','Ganduri','','','d','','','2015-10-27','2015-10-20','d','','','','','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `userinfo`
--

DROP TABLE IF EXISTS `userinfo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `userinfo` (
  `username` varchar(40) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(40) NOT NULL,
  `createdatetime` datetime DEFAULT CURRENT_TIMESTAMP,
  `staffid` varchar(45) DEFAULT NULL,
  `issuperuser` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`username`),
  KEY `staffid_idx` (`staffid`),
  CONSTRAINT `staffid` FOREIGN KEY (`staffid`) REFERENCES `staff` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` (`username`, `email`, `password`, `createdatetime`, `staffid`, `issuperuser`) VALUES ('raghu','raghu@gmail.com','aa','2015-10-07 17:18:37','STAFF_ID2',0),('sree','sreedhar.ganduri@gmail.com','Sree@1234','2015-10-02 11:19:28','emp1',1);
/*!40000 ALTER TABLE `userinfo` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-10-17 11:56:10
