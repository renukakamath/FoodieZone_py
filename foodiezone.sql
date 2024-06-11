/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.24-MariaDB : Database - dbfoodapp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`dbfoodapp` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `dbfoodapp`;

/*Table structure for table `tblassign` */

DROP TABLE IF EXISTS `tblassign`;

CREATE TABLE `tblassign` (
  `assign_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `d_boy_id` int(11) DEFAULT NULL,
  `booking_id` int(11) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`assign_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tblassign` */

insert  into `tblassign`(`assign_id`,`hotel_id`,`d_boy_id`,`booking_id`,`date_time`,`status`) values (1,2,1,1,'2022-10-04','pending');

/*Table structure for table `tblbooking` */

DROP TABLE IF EXISTS `tblbooking`;

CREATE TABLE `tblbooking` (
  `booking_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `total` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`booking_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tblbooking` */

insert  into `tblbooking`(`booking_id`,`user_id`,`menu_id`,`quantity`,`total`,`date_time`,`status`) values (1,1,3,'1','100','12/5/22','assign');

/*Table structure for table `tbldelivery_boys` */

DROP TABLE IF EXISTS `tbldelivery_boys`;

CREATE TABLE `tbldelivery_boys` (
  `delivery_boy_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `house_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`delivery_boy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tbldelivery_boys` */

insert  into `tbldelivery_boys`(`delivery_boy_id`,`hotel_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`phone`,`email`) values (1,2,11,'Sara','mary','kalarikkal','9812345678','8987878787','marysara@gmail.com');

/*Table structure for table `tblentrepreneur` */

DROP TABLE IF EXISTS `tblentrepreneur`;

CREATE TABLE `tblentrepreneur` (
  `hotel_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `hotel_name` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `license_number` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`hotel_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `tblentrepreneur` */

insert  into `tblentrepreneur`(`hotel_id`,`login_id`,`hotel_name`,`phone`,`email`,`license_number`,`status`) values (2,4,'Sagar','9857631085','sagar123@gmail.com','TL123458','accepted');

/*Table structure for table `tblfood_category` */

DROP TABLE IF EXISTS `tblfood_category`;

CREATE TABLE `tblfood_category` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `tblfood_category` */

insert  into `tblfood_category`(`category_id`,`category_name`) values (1,'sswwww'),(8,'dsfsafdf'),(9,'sswwww');

/*Table structure for table `tbllogin` */

DROP TABLE IF EXISTS `tbllogin`;

CREATE TABLE `tbllogin` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `tbllogin` */

insert  into `tbllogin`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin123','admin'),(5,'ambily','1234','user'),(4,'Sagar','1234','entrepreneur'),(6,'Ammu','Ammu','user');

/*Table structure for table `tblmenu` */

DROP TABLE IF EXISTS `tblmenu`;

CREATE TABLE `tblmenu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `food_name` varchar(100) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `tblmenu` */

insert  into `tblmenu`(`menu_id`,`hotel_id`,`category_id`,`food_name`,`image`,`price`,`quantity`,`status`) values (3,2,1,'yyyyyyy','static/5bb32777-1f56-42f4-88a1-d79c4cb5a4a0bg-hero.jpg','100','100','pending');

/*Table structure for table `tblpayment` */

DROP TABLE IF EXISTS `tblpayment`;

CREATE TABLE `tblpayment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tblpayment` */

insert  into `tblpayment`(`payment_id`,`booking_id`,`date_time`) values (1,1,'12/5/22');

/*Table structure for table `tblrating` */

DROP TABLE IF EXISTS `tblrating`;

CREATE TABLE `tblrating` (
  `rate_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `hotel_id` int(11) DEFAULT NULL,
  `rate` varchar(100) DEFAULT NULL,
  `review` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rate_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tblrating` */

insert  into `tblrating`(`rate_id`,`user_id`,`hotel_id`,`rate`,`review`,`date_time`) values (1,1,2,'5','good','12/5/22');

/*Table structure for table `tblreported_hotel` */

DROP TABLE IF EXISTS `tblreported_hotel`;

CREATE TABLE `tblreported_hotel` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `hotel_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `reason` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `tblreported_hotel` */

insert  into `tblreported_hotel`(`report_id`,`hotel_id`,`user_id`,`reason`,`date_time`) values (1,2,1,'fdsfsf','12/2/22');

/*Table structure for table `tbluser` */

DROP TABLE IF EXISTS `tbluser`;

CREATE TABLE `tbluser` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `house_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `pincode` varchar(100) DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `age` varchar(100) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `tbluser` */

insert  into `tbluser`(`user_id`,`login_id`,`first_name`,`last_name`,`house_name`,`place`,`pincode`,`gender`,`age`,`phone`,`email`) values (1,5,'Ambily','Sajan','Kuravakattu','Thodupuzha','686671','Female','22','9497533003','ambilysajan@gmail.com'),(2,6,'Ammu','R','Pullickal','Trivandrum','686648','Female','20','9845671235','ammuz98@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
