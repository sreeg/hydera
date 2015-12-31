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
INSERT INTO `attendance` (`staffid`, `date`, `daysinmonth`, `dayspresent`, `createdatetime`, `updatedatetime`, `month`, `year`) VALUES ('emp1','2015-11-08',30,24,'2015-11-08 12:00:26','2015-11-25 22:12:09','November','2015'),('STAFF_ID11','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID3','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID2','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID4','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID5','2015-11-08',30,26,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID6','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID10','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID7','2015-11-08',30,28,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID8','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('STAFF_ID9','2015-11-08',30,30,'2015-11-08 12:00:26','2015-11-25 22:12:10','November','2015'),('emp1','2015-11-08',31,22,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID3','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID11','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID2','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID4','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID5','2015-11-08',31,28,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID6','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID7','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID8','2015-11-08',31,29,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID9','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('STAFF_ID10','2015-11-08',31,31,'2015-11-08 12:01:47','2015-11-25 22:22:08','October','2015'),('emp1','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID3','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID11','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID2','2015-11-15',30,29,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID4','2015-11-15',30,28,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID5','2015-11-15',30,29,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID6','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID7','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID8','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID9','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('STAFF_ID10','2015-11-15',30,30,'2015-11-15 08:31:58','2015-11-25 22:24:05','September','2015'),('emp1','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID3','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID11','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID2','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID4','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID5','2015-11-15',31,29,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID6','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID7','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID8','2015-11-15',31,31,'2015-11-15 12:42:04','2015-11-26 05:59:48','August','2015'),('STAFF_ID9','2015-11-15',31,30,'2015-11-15 12:42:05','2015-11-26 05:59:48','August','2015'),('STAFF_ID10','2015-11-15',31,31,'2015-11-15 12:42:05','2015-11-26 05:59:48','August','2015'),('emp1','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID3','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID11','2015-11-25',31,30,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID12','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID13','2015-11-25',31,28,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID2','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID4','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID5','2015-11-25',31,27,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID6','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID7','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID8','2015-11-25',31,29,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID9','2015-11-25',31,29,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015'),('STAFF_ID10','2015-11-25',31,31,'2015-11-25 12:40:50','2015-11-26 06:15:21','December','2015');
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
-- Table structure for table `feedetails`
--

DROP TABLE IF EXISTS `feedetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feedetails` (
  `classname` varchar(100) NOT NULL,
  `term1amount` double DEFAULT NULL,
  `term2amount` double DEFAULT NULL,
  `term3amount` double DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `row` int(11) NOT NULL,
  `noofterms` int(11) DEFAULT NULL,
  `termamount` double DEFAULT NULL,
  PRIMARY KEY (`classname`,`row`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedetails`
--

LOCK TABLES `feedetails` WRITE;
/*!40000 ALTER TABLE `feedetails` DISABLE KEYS */;
INSERT INTO `feedetails` (`classname`, `term1amount`, `term2amount`, `term3amount`, `createdatetime`, `updatedatetime`, `row`, `noofterms`, `termamount`) VALUES ('Class I',16000,16000,16000,NULL,NULL,4,3,20000),('Class II',16000,16000,16000,NULL,NULL,5,3,20000),('Class III',17000,17000,17000,NULL,NULL,6,3,20000),('Class IV',17000,17000,17000,NULL,NULL,7,3,20000),('Class IX',35000,35000,35000,NULL,NULL,12,3,31000),('Class V',20000,20000,20000,NULL,NULL,8,3,22000),('Class VI',20000,20000,20000,NULL,NULL,9,3,22000),('Class VII',25000,25000,25000,NULL,NULL,10,3,27000),('Class VIII',30000,30000,30000,NULL,NULL,11,3,30000),('Class X',35000,35000,35000,NULL,NULL,13,3,35000),('LKG',12000,12000,12000,NULL,NULL,2,3,19000),('Nursery',12000,12000,12000,NULL,NULL,1,3,19000),('UKG',12000,12000,12000,NULL,NULL,3,3,19000);
/*!40000 ALTER TABLE `feedetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feepayment`
--

DROP TABLE IF EXISTS `feepayment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `feepayment` (
  `feereceiptid` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` varchar(45) NOT NULL,
  `amountpaid` double DEFAULT NULL,
  `paymentdate` date DEFAULT NULL,
  `paymentmode` varchar(45) DEFAULT NULL,
  `term1` tinyint(4) DEFAULT NULL,
  `term2` tinyint(4) DEFAULT NULL,
  `term3` tinyint(4) DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `paymentdetails` varchar(200) DEFAULT NULL,
  `bankname` varchar(200) DEFAULT NULL,
  `receivedfrom` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`feereceiptid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feepayment`
--

LOCK TABLES `feepayment` WRITE;
/*!40000 ALTER TABLE `feepayment` DISABLE KEYS */;
INSERT INTO `feepayment` (`feereceiptid`, `studentid`, `amountpaid`, `paymentdate`, `paymentmode`, `term1`, `term2`, `term3`, `createdatetime`, `updatedatetime`, `paymentdetails`, `bankname`, `receivedfrom`) VALUES (10,'STUDENT_ID8',240000,'2015-12-04','Cheque',1,1,0,'2015-12-13 12:31:41','2015-12-13 12:31:41','AXIS - 1980-9898-98','AXIS',NULL),(11,'STUDENT_ID8',65000,'2015-12-13','Cheque',0,0,1,'2015-12-13 13:34:16','2015-12-13 13:34:16','HDFC - 878-9898','HDFC',NULL),(14,'STUDENT_ID19',400000,'2015-12-13','Cheque',1,1,1,'2015-12-13 14:54:06','2015-12-13 14:54:06','HDFC -098-9989','HDFC',NULL),(15,'STUDENT_ID17',20000,'2015-12-13','Cash',1,0,0,'2015-12-13 15:14:26','2015-12-13 15:14:26','Paid thru bubly school','HDFC',NULL),(16,'STUDENT_ID1',190400,'2015-12-13','Cheque',1,1,0,'2015-12-13 18:32:13','2015-12-13 18:32:13','ICICI','ICICI',NULL),(17,'STUDENT_ID10',15000,'2015-12-13','Cash',1,0,0,'2015-12-13 19:02:56','2015-12-13 19:02:56','AXIS','AXIS',NULL),(18,'STUDENT_ID21',14090,'2015-12-13','Cheque',1,0,0,'2015-12-13 19:08:36','2015-12-13 19:08:36','ICICI','ICICI',NULL),(19,'STUDENT_ID2',28788,'2015-12-13','DD',1,0,0,'2015-12-13 20:49:38','2015-12-13 20:49:38','897979','HDFC',NULL),(20,'STUDENT_ID15',20000,'2015-12-13','DD',1,0,0,'2015-12-13 21:31:29','2015-12-13 21:31:29','868-8776','AXIS','Rama Swamy'),(21,'STUDENT_ID11',30000,'2015-12-15','Cheque',1,0,0,'2015-12-15 16:26:17','2015-12-15 16:26:17','2123123','HDFC','ASWER'),(22,'STUDENT_ID4',20000,'2015-12-16','Cheque',1,1,1,'2015-12-16 10:12:01','2015-12-16 10:12:01','87686','IDBI','Sree');
/*!40000 ALTER TABLE `feepayment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lists`
--

DROP TABLE IF EXISTS `lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `lists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lists`
--

LOCK TABLES `lists` WRITE;
/*!40000 ALTER TABLE `lists` DISABLE KEYS */;
INSERT INTO `lists` (`id`, `name`, `description`, `type`) VALUES (1,'IAS',NULL,'parent'),(2,'Doctor',NULL,'parent'),(3,'Software Engineer',NULL,'parent'),(5,'Lawyer',NULL,'parent'),(6,'Other',NULL,'parent'),(19,'Teacher',NULL,'parent'),(21,'Government Service',NULL,'parent'),(23,'Police',NULL,'parent'),(24,'Professor',NULL,'staff'),(25,'Teacher',NULL,'staff'),(26,'Professor',NULL,'parent'),(27,'HDFC',NULL,'banks'),(28,'ICICI',NULL,'banks'),(29,'SBI',NULL,'banks'),(30,'SBH',NULL,'banks'),(31,'AXIS',NULL,'banks'),(32,'Syndicate',NULL,'banks'),(33,'Bank of India',NULL,'banks'),(34,'Punjab National Bank',NULL,'banks'),(35,'HSBC',NULL,'banks'),(36,'Helper',NULL,'staff'),(37,'Cleaner',NULL,'staff'),(38,'Asst Professor',NULL,'staff'),(39,'Senior Teacher',NULL,'staff'),(40,'Andhra Bank',NULL,'banks'),(41,'IDBI',NULL,'banks'),(42,'City Bank',NULL,'banks'),(43,'Junior Teacher',NULL,'staff'),(44,'Politician',NULL,'parent'),(45,'Business',NULL,'parent'),(46,'Lab incharge',NULL,'staff');
/*!40000 ALTER TABLE `lists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payslip`
--

DROP TABLE IF EXISTS `payslip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `payslip` (
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
  `month` varchar(300) NOT NULL,
  `year` varchar(300) NOT NULL,
  `dayspresent` int(11) DEFAULT NULL,
  `daysinmonth` int(11) DEFAULT NULL,
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `iseligibleforpf` tinyint(4) DEFAULT NULL,
  `monthyeardate` datetime DEFAULT NULL,
  KEY `staffid_employeeid_idx` (`employeeid`),
  CONSTRAINT `staffid_pyslipemployeeid` FOREIGN KEY (`employeeid`) REFERENCES `staff` (`Id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payslip`
--

LOCK TABLES `payslip` WRITE;
/*!40000 ALTER TABLE `payslip` DISABLE KEYS */;
INSERT INTO `payslip` (`employeeid`, `basicsalary`, `fixedda`, `hra`, `conveyanceall`, `pfno`, `sbacno`, `pfrate`, `proftaxdeduction`, `otherdeduction`, `pfamount`, `loanamount`, `month`, `year`, `dayspresent`, `daysinmonth`, `createdatetime`, `updatedatetime`, `iseligibleforpf`, `monthyeardate`) VALUES ('emp1',9355,3742,4000,748.4,'2342342343','erw234234234',0.12,230,100,16.8,2000,'October','2015',29,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',1,'2015-10-01 00:00:00'),('STAFF_ID11',1806.4,1806.4,200,180.64,'3234234234234',NULL,0.12,100,200,480,0,'October','2015',28,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',1,'2015-10-01 00:00:00'),('STAFF_ID3',3000,909,9090,99,'iuuiu','',0.12,999,233,469.08,0,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',1,'2015-10-01 00:00:00'),('STAFF_ID5',7331.085,72.585,900,72.585,'000','',0.12,90,999,826.2,999,'October','2015',25,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',1,'2015-10-01 00:00:00'),('STAFF_ID2',2000,200,200,200,'-','22323',0.12,200,0,0,0,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',0.12,222,222,557.52,2323,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',1,'2015-10-01 00:00:00'),('STAFF_ID6',2000,900,999,9,'0909','09090',0.12,0,0,0,0,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('STAFF_ID8',2247.9671,2247.9671,2323,2247.9671,'2323','',0.12,22,233,0,0,'October','2015',30,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('STAFF_ID7',3333,33,33,33,'333','',0.12,33,3,0,33,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('STAFF_ID9',2999,999,999,99,'9999','',0.12,9,0,0,0,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('STAFF_ID10',5000,2000,2000,1212,'','',0.12,200,100,0,0,'October','2015',31,31,'2015-11-15 09:54:58','2015-11-15 09:54:58',0,'2015-10-01 00:00:00'),('emp1',10000,4000,4000,800,'2342342343','erw234234234',0.12,230,100,16.8,2000,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',1,'2015-09-01 00:00:00'),('STAFF_ID11',1933.4,1933.4,200,193.34,'3234234234234',NULL,0.12,100,200,480,0,'September','2015',29,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',1,'2015-09-01 00:00:00'),('STAFF_ID3',3000,909,9090,99,'iuuiu','',0.12,999,233,469.08,0,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',1,'2015-09-01 00:00:00'),('STAFF_ID5',9090,90,900,90,'000','',0.12,90,999,826.2,999,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',1,'2015-09-01 00:00:00'),('STAFF_ID2',2000,200,200,200,'-','22323',0.12,200,0,0,0,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('STAFF_ID4',1626.1,1626.1,2332,1626.1,'3223232','2232323',0.12,222,222,557.52,2323,'September','2015',21,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',1,'2015-09-01 00:00:00'),('STAFF_ID6',1933.4,870.03,999,8.7003,'0909','09090',0.12,0,0,0,0,'September','2015',29,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('STAFF_ID8',2323,2323,2323,2323,'2323','',0.12,22,233,0,0,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('STAFF_ID7',3333,33,33,33,'333','',0.12,33,3,0,33,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('STAFF_ID9',2699.1,899.1,999,89.10000000000001,'9999','',0.12,9,0,0,0,'September','2015',27,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('STAFF_ID10',5000,2000,2000,1212,'','',0.12,200,100,0,0,'September','2015',30,30,'2015-11-15 09:55:38','2015-11-15 09:55:38',0,'2015-09-01 00:00:00'),('emp1',10000,4000,4000,800,'2342342343','erw234234234',0.12,230,100,16.8,2000,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',1,'2015-08-01 00:00:00'),('STAFF_ID11',2000,2000,200,200,'3234234234234',NULL,0.12,100,200,480,0,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',1,'2015-08-01 00:00:00'),('STAFF_ID3',3000,909,9090,99,'iuuiu','',0.12,999,233,469.08,0,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',1,'2015-08-01 00:00:00'),('STAFF_ID2',2000,200,200,200,'-','22323',0.12,200,0,0,0,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',0,'2015-08-01 00:00:00'),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',0.12,222,222,557.52,2323,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',1,'2015-08-01 00:00:00'),('STAFF_ID6',2000,900,999,9,'0909','09090',0.12,0,0,0,0,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',0,'2015-08-01 00:00:00'),('STAFF_ID5',9090,90,900,90,'000','',0.12,90,999,826.2,999,'August','2015',31,31,'2015-11-15 12:42:23','2015-11-15 12:42:23',1,'2015-08-01 00:00:00'),('STAFF_ID7',3333,33,33,33,'333','',0.12,33,3,0,33,'August','2015',31,31,'2015-11-15 12:42:24','2015-11-15 12:42:24',0,'2015-08-01 00:00:00'),('STAFF_ID9',2999,999,999,99,'9999','',0.12,9,0,0,0,'August','2015',31,31,'2015-11-15 12:42:24','2015-11-15 12:42:24',0,'2015-08-01 00:00:00'),('STAFF_ID10',5000,2000,2000,1212,'','',0.12,200,100,0,0,'August','2015',31,31,'2015-11-15 12:42:24','2015-11-15 12:42:24',0,'2015-08-01 00:00:00'),('STAFF_ID8',2323,2323,2323,2323,'2323','',0.12,22,233,0,0,'August','2015',31,31,'2015-11-15 12:42:24','2015-11-15 12:42:24',0,'2015-08-01 00:00:00'),('STAFF_ID12',0,0,0,0,'','',0.12,200,0,0,0,'November','2015',0,0,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('emp1',8000,3200,3200,640,'2342342343','erw234234234',0.12,230,100,1344,2000,'November','2015',24,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID11',2000,2000,200,200,'3234234234234',NULL,0.12,100,200,480,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID13',0,0,0,0,'','',0.12,200,0,0,0,'November','2015',0,0,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID3',3000,909,9090,99,'iuuiu','',0.12,999,233,469.08,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID5',7878.303,78.003,780.03,78.003,'000','',0.12,90,999,954.7567199999999,999,'November','2015',26,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID2',2000,200,200,200,'-','22323',0.12,200,0,0,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',0.12,222,222,557.52,2323,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',1,'2015-11-01 00:00:00'),('STAFF_ID6',2000,900,999,9,'0909','09090',0.12,0,0,0,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID7',3110.6889,30.7989,30.7989,30.7989,'333','',0.12,33,3,0,33,'November','2015',28,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID9',2999,999,999,99,'9999','',0.12,9,0,0,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID10',5000,2000,2000,1212,'','',0.12,200,100,0,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID8',2323,2323,2323,2323,'2323','',0.12,22,233,0,0,'November','2015',30,30,'2015-11-30 09:01:14','2015-11-30 09:01:14',0,'2015-11-01 00:00:00'),('STAFF_ID14',0,0,0,0,'98798797','',0.12,200,100,0,0,'December','2015',0,0,'2015-12-31 16:26:42','2015-12-31 16:26:42',1,'2015-12-01 00:00:00'),('STAFF_ID9',2805.5645,934.5645,934.5645,92.6145,'9999','',0.12,9,0,0,0,'December','2015',29,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('emp1',10000,4000,4000,800,'2342342343','erw234234234',0.12,230,100,1680,2000,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',1,'2015-12-01 00:00:00'),('STAFF_ID11',1935.4,1935.4,193.54,193.54,'3234234234234',NULL,0.12,100,200,464.496,0,'December','2015',30,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',1,'2015-12-01 00:00:00'),('STAFF_ID10',5000,2000,2000,1212,'','',0.12,200,100,0,0,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID3',3000,909,9090,99,'iuuiu','',0.12,999,233,469.08,0,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',1,'2015-12-01 00:00:00'),('STAFF_ID5',7917.39,78.39,783.9,78.39,'000','',0.12,90,999,959.4936,999,'December','2015',27,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',1,'2015-12-01 00:00:00'),('STAFF_ID2',2000,200,200,200,'-','22323',0.12,200,0,0,0,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID8',2173.1665,2173.1665,2173.1665,2173.1665,'2323','',0.12,22,233,0,0,'December','2015',29,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID7',3333,33,33,33,'333','',0.12,33,3,0,33,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID13',9032,90.32,903.2,180.64,'','',0.12,200,0,1094.6784,0,'December','2015',28,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',0.12,222,222,557.52,2323,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID6',2000,900,999,9,'0909','09090',0.12,0,0,0,0,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00'),('STAFF_ID12',10000,4000,3000,800,'','',0.12,200,0,1680,0,'December','2015',31,31,'2015-12-31 16:26:42','2015-12-31 16:26:42',0,'2015-12-01 00:00:00');
/*!40000 ALTER TABLE `payslip` ENABLE KEYS */;
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
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `modeofpayment` varchar(45) DEFAULT NULL,
  `iseligibleforpf` tinyint(4) DEFAULT NULL,
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
INSERT INTO `salary` (`employeeid`, `basicsalary`, `fixedda`, `hra`, `conveyanceall`, `pfno`, `sbacno`, `pfrate`, `proftaxdeduction`, `otherdeduction`, `pfamount`, `loanamount`, `createdatetime`, `updatedatetime`, `modeofpayment`, `iseligibleforpf`) VALUES ('emp1',10000,4000,4000,800,'2342342343','erw234234234',0.12,230,100,16.8,2000,'2015-10-13 00:00:00','2015-11-14 11:48:59','3',1),('STAFF_ID10',5000,2000,2000,1212,'','',12,200,100,0,0,'2015-10-16 00:00:00','2015-10-16 00:00:00','1',0),('STAFF_ID11',2000,2000,200,200,'3234234234234',NULL,12,100,200,240,0,'2015-10-31 10:28:10','2015-11-07 10:15:45','2',1),('STAFF_ID12',10000,4000,3000,800,'','',0.12,200,0,16.8,0,'2015-11-23 11:59:41','2015-11-23 11:59:41','1',1),('STAFF_ID13',10000,100,1000,200,'','',0.12,200,0,1212,0,'2015-11-23 19:50:34','2015-11-23 19:50:34','2',1),('STAFF_ID14',1000,909,999,100,'98798797','',0.12,200,100,229.07999999999998,0,'2015-12-14 08:33:46','2015-12-14 08:33:46','1',1),('STAFF_ID2',2000,200,200,200,'-','22323',0,200,0,0,0,'2015-10-14 00:00:00','2015-10-14 00:00:00','3',0),('STAFF_ID3',3000,909,9090,99,'iuuiu','',12,999,233,3000,0,'2015-10-16 00:00:00','2015-10-16 00:00:00','1',1),('STAFF_ID4',2323,2323,2332,2323,'3223232','2232323',12,222,222,2222,2323,'2015-10-13 00:00:00','2015-10-14 00:00:00','3',1),('STAFF_ID5',9090,90,900,90,'000','',9,90,999,999,999,'2015-10-16 00:00:00','2015-10-16 00:00:00','1',1),('STAFF_ID6',2000,900,999,9,'0909','09090',12,0,0,0,0,'2015-10-16 00:00:00','2015-10-16 00:00:00','3',0),('STAFF_ID7',3333,33,33,33,'333','',3,33,3,0,33,'2015-10-17 00:00:00','2015-10-17 00:00:00','2',0),('STAFF_ID8',2323,2323,2323,2323,'2323','',12,22,233,0,0,'2015-10-17 00:00:00','2015-10-17 00:00:00','3',0),('STAFF_ID9',2999,999,999,99,'9999','',12,9,0,0,0,'2015-10-17 00:00:00','2015-10-17 00:00:00','2',0);
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
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
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
INSERT INTO `staff` (`Id`, `FirstName`, `LastName`, `CategoryId`, `Designation`, `SpouseName`, `SpouseOccupation`, `Phone`, `DateOfBirth`, `DateOfJoining`, `JoiningSalary`, `Gender`, `Mobile`, `Email`, `ProfilePic`, `houseno`, `street`, `city`, `postalcode`, `createdatetime`, `updatedatetime`, `isarchived`) VALUES ('emp1','Sreedhar','Ganduri','1','Professor','Sowjanya','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'1121212','Plot No. 303, Road No. 4,','Hyderabad','500056',NULL,'2015-12-11 19:52:07',0),('STAFF_ID10','Krishna','Potluri','1','Professor','','','9848050340','1983-04-26','2015-02-03',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-11 20:50:24',0),('STAFF_ID11','Vijay','Vaddem','1','Professor','','','9848050340','1983-04-26','2014-12-10',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-27 14:20:35','2015-12-11 20:50:12',0),('STAFF_ID12','Naga','Varma','3','Associate Professor','','','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-11-23 11:52:56','2015-12-11 19:59:06',0),('STAFF_ID13','Shahi','Naquash','3','Asst. Professor','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-11-23 19:50:04','2015-12-11 19:59:16',0),('STAFF_ID14','Sudha','G','1','Teacher','Bhaskar','','','1970-07-23','2015-09-01',12000.00,'Female','','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-12-14 08:32:33','2015-12-14 08:32:33',0),('STAFF_ID2','Sindhu','Tiwari','2','Office Boy','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056',NULL,'2015-12-13 19:12:27',0),('STAFF_ID3','Amit','Gupta','2','Professor','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056',NULL,'2015-12-11 19:57:33',0),('STAFF_ID4','Ravi','Kanukollu','3','Professor','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-11 00:00:00','2015-12-11 19:57:54',0),('STAFF_ID5','Seenu','Security','2','Security','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-13 19:12:42',0),('STAFF_ID6','Aasim','Sokal','3','Professor','','House Wife','9848050340','1983-04-26','2015-08-05',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-11 20:49:51',0),('STAFF_ID7','Raju','Security','3','Security Gaurd','','House Wife','9848050340','1983-04-26','2015-01-01',0.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-11 20:00:04',0),('STAFF_ID8','Rani','Amma','2','Helper','','','9848050340','1983-04-26','2015-01-01',0.00,'Female','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-13 19:12:48',0),('STAFF_ID9','Vikram','Muddya','1','Professor','','House Wife','9848050340','1983-04-26','2015-01-01',20000.00,'Male','9848050340','sreedhar.ganduri@gmail.com',NULL,'','Plot No. 303, Road No. 4,','Hyderabad','500056','2015-10-14 00:00:00','2015-12-11 20:00:56',0);
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
  `createdatetime` datetime DEFAULT NULL,
  `updatedatetime` datetime DEFAULT NULL,
  `isarchived` tinyint(4) DEFAULT '0',
  `fatherdetails` varchar(200) DEFAULT NULL,
  `motherdetails` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`Id`, `FirstName`, `LastName`, `Class`, `Section`, `FatherName`, `FatherOccupation`, `Phone`, `DateOfBirth`, `DateOfJoining`, `MotherName`, `MotherOccupation`, `Gender`, `GaurdianName`, `Mobile`, `Email`, `ProfilePic`, `houseno`, `street`, `city`, `postalcode`, `createdatetime`, `updatedatetime`, `isarchived`, `fatherdetails`, `motherdetails`) VALUES ('STUDENT_ID1','Sreedhar','Ganduri','Class X','Section-A','GVG','Government Service','09848050340','2015-10-01','2015-10-03','V','Other','Male','','6173060467','sreedhar.ganduri@gmail.com',NULL,'Plot No. 303','Road No. 4, Deen dayal Nagar, Neredmet','Hyderabad','500056',NULL,'2015-12-21 15:10:52',0,NULL,NULL),('STUDENT_ID10','Preethi','Kumari','Class II','Section-A','Prithanka','Doctor','','2015-10-27','2015-10-20','Priyanka','Doctor','Female','Pratheeka','','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-31 14:14:51',0,NULL,NULL),('STUDENT_ID11','Deepak','Agarwal','Class X','Section-B','Naresh Agarwal','Business','9080808000','1999-10-26','2015-10-26','Geeta Agarwal','','Male','','8080980000','vijayvaddem@gmail.com',NULL,'House No. 123','Agarwal Street','Hyderabad','500081',NULL,'2015-12-31 18:50:01',0,NULL,NULL),('STUDENT_ID12','Sindhu','Tiwari','Class X','Section B','Ashish Tiwari','Business','089809800','1999-12-12','2015-10-27','Asha Tiwari','House Wife','Male','','987987999','sreedhar.ganduri@gmail.com',NULL,'101-11','Begum Bazaar','Hyderabad','500081','2015-10-27 14:18:07','2015-12-31 18:56:11',0,NULL,NULL),('STUDENT_ID13','ads','ad','Class X','Section-A','asd','Other','','2015-11-03','2015-11-28','da','','Male','','','',NULL,'','','Hyderabad','','2015-11-28 07:07:26','2015-11-28 07:07:26',0,NULL,NULL),('STUDENT_ID14','wqe','qwe','Class X','Section-C','qwewqe','Other','','2015-11-04','2015-11-28','weq','','Male','','','',NULL,'','','Hyderabad','','2015-11-28 07:11:28','2015-12-31 18:56:11',0,NULL,NULL),('STUDENT_ID15','SreeVidya','Ganduri','UKG','Section-A','Rama Swamy','Software Engineer','','2013-12-03','2015-12-11','Gayathri','Software Engineer','Female','','','',NULL,'','','Hyderabad','500056','2015-12-11 20:42:59','2015-12-31 17:54:44',0,NULL,NULL),('STUDENT_ID16','SreeCharan','Ganduri','UKG','Section-B','Rama','Other','','2010-12-01','2015-12-11','Gayathri','','Male','','','',NULL,'','','Hyderabad','500056','2015-12-11 20:45:28','2015-12-11 20:45:28',0,NULL,NULL),('STUDENT_ID17','Chitkala','Ganduri','UKG','Section-A','Chandra Sekhar','Business','','2012-01-02','2015-12-11','Samyuktha','','Female','','','',NULL,'','','Hyderabad','','2015-12-11 20:46:06','2015-12-31 17:52:26',0,NULL,NULL),('STUDENT_ID18','Sreekala','Ganduri','LKG','Section-B','Chandra','Politician','','2013-10-02','2015-12-11','Samyuktha','Politician','Female','','','',NULL,'','','Hyderabad','','2015-12-11 20:47:11','2015-12-31 17:57:10',0,NULL,NULL),('STUDENT_ID19','Shakthidhar','Ganduri','Nursery','Section-C','GCS','Other','','2014-11-11','2015-12-12','GLS','','Male','','','',NULL,'','','Hyderabad','','2015-12-12 11:01:15','2015-12-12 11:01:15',0,NULL,NULL),('STUDENT_ID2','Vijay','Vaddem','Class X','Section-C','Ajay','Business','+447827974096','2015-10-29','2015-10-07','V','Other','Male','V','6173060467','vijayvaddem@gmail.com',NULL,NULL,'k nagar, road 2','Hyderabad','500056',NULL,'2015-12-31 17:49:23',0,NULL,NULL),('STUDENT_ID20','Vishnu Teja','Konduri','LKG','Section-C','Srinivas','Government Service','','2013-12-12','2015-12-12','Srilatha','','Male','','','',NULL,'','','Hyderabad','','2015-12-12 11:02:34','2015-12-21 15:09:42',0,NULL,NULL),('STUDENT_ID21','Sameer','Durga','Class X','Section-A','Raja','Government Service','','2000-10-10','2006-12-12','Gayathri','Teacher','Male','','','',NULL,'','','Hyderabad','','2015-12-12 11:05:48','2015-12-31 18:56:11',0,NULL,NULL),('STUDENT_ID3','Tarun','Bharti','LKG','Section-A','Madan','Business','09848050340','2015-10-01','2015-10-08','g','','Male','g','+914030555600','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-21 15:09:34',0,NULL,NULL),('STUDENT_ID4','Gulshan','Kumar','Class X','Section-A','Grover','Lawyer','+447827974096','2015-10-19','2015-10-28','Grovers','Police','Male','s','+447827974096','vijayvaddem@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-31 18:53:27',1,NULL,NULL),('STUDENT_ID5','Vandana','Upadhay','Class X','Section-A','Harish Upadhay','Professor','+447827974096','2015-10-19','2015-10-28','Harini Upadhay','Professor','Female','s','+447827974096','vijayvaddem@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-31 18:56:02',1,NULL,NULL),('STUDENT_ID6','Ajay','Kumar','Class X','Section-A','Sanjay Jah','Police','+447827974096','2015-10-19','2015-10-28','s','Other','Male','s','+447827974096','vijayvaddem@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-31 18:52:23',0,NULL,NULL),('STUDENT_ID7','Vishal','Singh','Class VI','Section-B','Krishna','Other','+447827974096','2015-10-20','2015-10-28','u','','Male','u','+447827974096','sreedhar.ganduri@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-12 11:06:38',0,NULL,NULL),('STUDENT_ID8','Swetha','Reddy','Class III','Section-C','Sunil','Software Engineer','','2015-10-05','2015-10-27','Swetha Mother','Software Engineer','Female','','','vijayvaddem@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-21 15:08:58',0,NULL,NULL),('STUDENT_ID9','Arshad ','Raza','Class II','Section-A','Javed','Lawyer','+447827974096','2015-10-20','2015-10-20','Mother','Other','Male','Gaurdian','09848050340','vijayvaddem@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,'2015-12-21 15:01:05',0,NULL,NULL);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `systemsettings`
--

DROP TABLE IF EXISTS `systemsettings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `systemsettings` (
  `email` varchar(200) NOT NULL,
  `password` varchar(45) DEFAULT NULL,
  `showwidget1` tinyint(4) DEFAULT NULL,
  `showwidget2` tinyint(4) DEFAULT NULL,
  `emailhost` varchar(200) DEFAULT NULL,
  `smtpport` varchar(45) DEFAULT NULL,
  `disableemail` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `systemsettings`
--

LOCK TABLES `systemsettings` WRITE;
/*!40000 ALTER TABLE `systemsettings` DISABLE KEYS */;
INSERT INTO `systemsettings` (`email`, `password`, `showwidget1`, `showwidget2`, `emailhost`, `smtpport`, `disableemail`) VALUES ('namovoter@gmail.com','Namo@1234',0,0,'smtp.gmail.com','465',1);
/*!40000 ALTER TABLE `systemsettings` ENABLE KEYS */;
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
  `lastlogindatetime` datetime DEFAULT NULL,
  `firstname` varchar(200) DEFAULT NULL,
  `lastname` varchar(200) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`username`),
  KEY `staffid_idx` (`staffid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `userinfo`
--

LOCK TABLES `userinfo` WRITE;
/*!40000 ALTER TABLE `userinfo` DISABLE KEYS */;
INSERT INTO `userinfo` (`username`, `email`, `password`, `createdatetime`, `staffid`, `issuperuser`, `lastlogindatetime`, `firstname`, `lastname`, `gender`) VALUES ('sree','sreedhar.ganduri@gmail.com','Sree@1234','2015-10-02 11:19:28','emp1',1,'2015-12-31 19:28:56','Sreedhar','Ganduri','Male'),('vijay','vijay.vaddem@gmail.com','vaddv','2015-10-07 17:18:37','STAFF_ID2',1,NULL,'Vijay','Vaddem','Male');
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

-- Dump completed on 2015-12-31 19:34:08
