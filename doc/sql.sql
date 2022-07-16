/*
SQLyog  v12.2.6 (64 bit)
MySQL - 8.0.20 : Database - mybatis_plus
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mybatis_plus` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `mybatis_plus`;

/*Table structure for table `member` */

DROP TABLE IF EXISTS `member`;

CREATE TABLE `member` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `member` */

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `age` int DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `crate_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_sex` int DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1321732391973847052 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

/*Data for the table `user` */

insert  into `user`(`id`,`name`,`age`,`email`,`crate_time`,`update_time`,`user_sex`) values 
(1,'西决',18,'test1@baomidou.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(2,'单鹏年',20,'test2@baomidou.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(3,'Tom',28,'test3@baomidou.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(4,'Sandy',21,'test4@baomidou.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(5,'gcc',12,'test5@baomidou.com','2020-10-29 16:48:30','2020-11-02 06:23:33',0),
(1321732391973847041,'zsr',20,'1241@qq.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(1321732391973847042,'zsr',20,'1241@qq.com','2020-10-29 16:48:30','2020-10-29 16:48:30',1),
(1321732391973847043,'xxx',20,'1241@qq.com','2020-10-29 16:54:38','2020-10-29 16:54:38',1),
(1321732391973847044,'uiopc',30,'1241@qq.com','2020-10-29 09:21:22','2020-10-29 09:21:22',1),
(1321732391973847045,'uiopc',38,'1241@qq.com','2020-10-29 09:23:15','2020-10-29 09:23:15',1),
(1321732391973847046,'uiopc',38,'1241@qq.com','2020-10-31 02:42:52','2020-10-31 02:42:52',1),
(1321732391973847047,'uiopc',38,'1241@qq.com','2020-10-31 02:49:05','2020-10-31 02:49:05',1),
(1321732391973847048,'uiopc',38,'1241@qq.com','2020-11-02 04:48:37','2020-11-02 04:48:37',1),
(1321732391973847049,'uiopc',38,'1241@qq.com','2020-11-02 05:58:29','2020-11-02 05:58:29',0),
(1321732391973847051,'uiopc',38,'1241@qq.com','2020-11-02 06:51:03','2020-11-02 06:51:03',0);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
