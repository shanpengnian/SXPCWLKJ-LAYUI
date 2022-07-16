/*
 Navicat Premium Data Transfer

 Source Server         : shanpengnian
 Source Server Type    : MySQL
 Source Server Version : 50562
 Source Host           : bt.sxpcwlkj.com:3306
 Source Schema         : sxpcwlkj-father

 Target Server Type    : MySQL
 Target Server Version : 50562
 File Encoding         : 65001

 Date: 12/04/2021 14:44:49
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of member
-- ----------------------------
BEGIN;
INSERT INTO `member` VALUES (1, 'xijue', '2020-11-18 14:25:51', '123456');
COMMIT;

-- ----------------------------
-- Table structure for p_alipay_config
-- ----------------------------
DROP TABLE IF EXISTS `p_alipay_config`;
CREATE TABLE `p_alipay_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `app_id` varchar(500) DEFAULT NULL,
  `merchant_private_key` text,
  `alipay_public_key` text,
  `notify_url` varchar(500) DEFAULT NULL,
  `return_url` varchar(500) DEFAULT NULL,
  `sign_type` varchar(200) DEFAULT NULL,
  `alipay_charset` varchar(200) DEFAULT NULL,
  `gateway_url` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_alipay_config
-- ----------------------------
BEGIN;
INSERT INTO `p_alipay_config` VALUES (1, 1, '2018062360383926', 'MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCuvXuiHvL3bKwbnRGXgz06vkg4v9L7lJX06PFRYSGm+GPstgq1kqUkNo/6zEErqQR3WUKtf52kosKakTkAFYCE6oFdhQKv1ij2STdfB5G/GVPB+uZAafXP4L3HIVYSVs65nznXcYHj32YmscfSoihFxBOemZNzhFxCLzBiTlShaLUdV7/2M8KIRpRV4LkuAxfTw++TnOM6Ipbvzt8/PfPYM91miizQIqmGIvsokzPnvf4fLSoVOECkGp63P5tIpk/89tCHgnbOTtaxcrQj6K4NglDEZNAlqwv2HVtfRgMOKyhl6A++L3Wpf/BWe5Ango5j3HN8Yk/LVaJJjx4uLzv1AgMBAAECggEAUNuReCWGHIQG6Ag4ebna/XHLjacGBDBva5LXB+dAYTkTVBewwPCIxkcRPOaDSaa+UFPXjN0+n05JaqjEjYtlmxvAnPvNkHwzHDQpESo5gQpBPcPSBACEJJtEdf6xC29r3W0WoOD/T+iyJjmh05ABvrbKtVsvZgbbgiy/4N2KP92lES+Gzli/YT7oCHi3W4IvbzGohoxzUBbDWDCDcs459UhhFtb8hiOmiLOtdZd1vCzYwDBBO/o1fgnXGTBbdcWkkQB5lXS9IkdMe1cg+9f8Y2Ry0zdxmHjyOgpbu6jE651ZoYnIcSYnyKXhvHRd4YkTXYQf1EXYZ2VaKu4PrHAywQKBgQDq1Jg3EK3ANG+dza4qrXkfkIofaOw8IfsDxU2m3gEsd2+oxT76kRqxAdDZ5c4faVqQUMuVvTlETv9tEqoD8cBymiPRlnb/M4BaFUDtvpVvUv2VP6OjGv2tXc127Gcjb26Ij/PUZMWRuZg8SLVDr3+4y2TU8LN1aRq3RbrX8M6enQKBgQC+fiC7PkyTIoErLdlhik0hswATMPqvzTFJmQ6jEg7VSzzMleFLFrq9xvlSqtAx4/PsqGcA/DTulmHf0Xborn7bhgAAZOUE6JV3PGKzJAeW5d4Tmd+KvoM0zPuUXxzuaBEUm/+6vfcGrQzpIbrMZMXlMM8HeKzfPFACQsA+7NQnOQKBgQCaOwS57b9gJFBGgUbpisOpgoHk/UFigSLeqCBG/zogHVV9sAacBN9V8A5efjsxkD1F2XrMntnUzlmeJor5SjcOTcRGrB79n7Kl952MSAbXdddMxd5QirKfwaLnf36B3HbpRDptfb3w5sdgmJRMnyAwm41e+bOz4lMEmcyMdQEPVQKBgG3tJQBIjDHgGIANXkNuZuJKhIGOoytuEsIw5ARweETXvQcmerM1M6AuQVRWI/yWWf24lHA6GmWQPHzHSIIvqB8QLdIMZyi0+wOqwh40bXjDv6q6AgjztY1zuL4/QNfHEAw/lYxy8SlWFXw3La+je5ut1dqu5buQ927GjGADlM3pAoGBAK4jSaKbETya5X1crUwLz1JGas3gFOqg1lvn8iD5CUUoub8Q7z5SL1UM1uRPE0s1AIkCyqfmUrvd3lU8PG/WOHlGQku2wN9OaqfhPUzDTfBG42J81LGRbnVRNemVHa8mZ1niZW9oqQHsFfmyBfWHaQhbS02li3sJyhaz3x/8SxVt', 'MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzmnPaVtRCilghVzkAEaIMYY+wiipUECi0QDViRNNESsUH3e0PLkl4ZwjIPDsyw5cM8vNj+OzYNYqfIcCbgNWafBu3hCM7l6Jz7mBTbd6EZzjgz5r3gF1XQ5GfzpxdwkAyxOgH7/E//d608YY7BqPNjDIX3KgEz3tJdQdEdwanWMujRpQsrItFA2hN5cAkP/wfMfVoaoKLb7WYSldGR8mZSci2NHyGGP/ASyB1RClR5BBNqo1UGM79UL1UnrJcmj3Ln900ODK8M79QAD2TZ4A4DEXXxAv3lQIcs25OAZEpo6AlZ6VajY+99xzRGWB+8QoOMidasC9LOHPzehXf0R0qwIDAQAB', 'http://jfgapp.top/front/views/inform/succeed.html', 'http://localhost:8089/Plugin/alipayReturnNotice', 'RSA2', 'utf-8', 'https://openapi.alipay.com/gateway.do');
COMMIT;

-- ----------------------------
-- Table structure for p_api
-- ----------------------------
DROP TABLE IF EXISTS `p_api`;
CREATE TABLE `p_api` (
  `api_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `task_code` varchar(300) DEFAULT NULL,
  `api_name` varchar(200) DEFAULT NULL,
  `api_url` varchar(300) DEFAULT NULL,
  `api_site` varchar(200) DEFAULT NULL,
  `api_key` varchar(200) DEFAULT NULL,
  `api_one` varchar(200) DEFAULT NULL,
  `api_two` varchar(200) DEFAULT NULL,
  `api_three` varchar(200) DEFAULT NULL,
  `api_four` varchar(200) DEFAULT NULL,
  `api_five` varchar(200) DEFAULT NULL,
  `api_six` varchar(200) DEFAULT NULL,
  `api_seven` varchar(200) DEFAULT NULL,
  `api_eight` varchar(200) DEFAULT NULL,
  `api_nine` varchar(200) DEFAULT NULL,
  `api_ten` varchar(200) DEFAULT NULL,
  `api_ten_one` varchar(200) DEFAULT NULL,
  `api_ten_two` varchar(200) DEFAULT NULL,
  `api_ten_three` varchar(200) DEFAULT NULL,
  `api_ten_four` varchar(200) DEFAULT NULL,
  `api_ten_five` varchar(200) DEFAULT NULL,
  `api_ten_six` varchar(200) DEFAULT NULL,
  `api_ten_seven` varchar(200) DEFAULT NULL,
  `api_ten_eight` varchar(200) DEFAULT NULL,
  `api_ten_nine` varchar(200) DEFAULT NULL,
  `api_twenty` varchar(200) DEFAULT NULL,
  `api_twenty_one` varchar(200) DEFAULT NULL,
  `api_twenty_two` varchar(200) DEFAULT NULL,
  `api_twenty_three` varchar(200) DEFAULT NULL,
  `api_twenty_four` varchar(200) DEFAULT NULL,
  `api_twenty_five` varchar(200) DEFAULT NULL,
  `api_twenty_six` varchar(200) DEFAULT NULL,
  `api_twenty_seven` varchar(200) DEFAULT NULL,
  `api_twenty_eight` varchar(200) DEFAULT NULL,
  `api_twenty_nine` varchar(200) DEFAULT NULL,
  `api_thirty` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`api_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_api
-- ----------------------------
BEGIN;
INSERT INTO `p_api` VALUES (1, 1, NULL, '秒滴验证码接口', 'https://openapi.miaodiyun.com', '/distributor/sendSMS', 'fddea177634748d4a7dc2d3cf92c5914', '4c525c6a3d2c45bc9a3c85a1b8da7ceb', '256619', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `p_api` VALUES (2, 1, NULL, '秒滴营销接口', 'https://openapi.miaodiyun.com', '/distributor/sendSMS', 'fddea177634748d4a7dc2d3cf92c5914', '4c525c6a3d2c45bc9a3c85a1b8da7ceb', '233996', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for p_code
-- ----------------------------
DROP TABLE IF EXISTS `p_code`;
CREATE TABLE `p_code` (
  `code_id` int(11) NOT NULL AUTO_INCREMENT,
  `code_name` varchar(100) DEFAULT NULL,
  `code_code` varchar(200) DEFAULT NULL,
  `code_type` int(11) DEFAULT NULL,
  PRIMARY KEY (`code_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_code
-- ----------------------------
BEGIN;
INSERT INTO `p_code` VALUES (1, '超级管理员', 'admin', 1);
INSERT INTO `p_code` VALUES (2, '管理员', 'userSuper', 1);
INSERT INTO `p_code` VALUES (3, '普通商家', 'user', 1);
COMMIT;

-- ----------------------------
-- Table structure for p_department
-- ----------------------------
DROP TABLE IF EXISTS `p_department`;
CREATE TABLE `p_department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_name` varchar(500) DEFAULT NULL,
  `department_desc` varchar(500) DEFAULT NULL,
  `department_fathe` int(11) DEFAULT NULL,
  `department_state` int(11) DEFAULT NULL COMMENT '1：正常\n            2：禁用',
  `department_sort` int(11) DEFAULT NULL,
  `department_outher` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_department
-- ----------------------------
BEGIN;
INSERT INTO `p_department` VALUES (1, '总经办', '总经理办公室拥有最高权限', 0, 1, 1, NULL);
INSERT INTO `p_department` VALUES (2, '用户组', '后台用户', 1, 1, 2, NULL);
COMMIT;

-- ----------------------------
-- Table structure for p_emai_config
-- ----------------------------
DROP TABLE IF EXISTS `p_emai_config`;
CREATE TABLE `p_emai_config` (
  `email_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `smtp_server` varchar(100) DEFAULT NULL,
  `port` int(11) DEFAULT NULL,
  `send_email` varchar(100) DEFAULT NULL,
  `send_nickname` varchar(100) DEFAULT NULL,
  `send_password` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`email_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_emai_config
-- ----------------------------
BEGIN;
INSERT INTO `p_emai_config` VALUES (3, 1, 'smtp.163.com', 465, 'sxpcwlkj@163.com', '西决', 'spn570104');
COMMIT;

-- ----------------------------
-- Table structure for p_function
-- ----------------------------
DROP TABLE IF EXISTS `p_function`;
CREATE TABLE `p_function` (
  `fun_id` int(11) NOT NULL AUTO_INCREMENT,
  `fun_name` varchar(50) DEFAULT NULL,
  `fun_code` varchar(50) DEFAULT NULL,
  `father_id` int(11) DEFAULT NULL,
  `depth` int(11) DEFAULT NULL,
  `left_node` int(11) DEFAULT NULL,
  `right_node` int(11) DEFAULT NULL,
  `level` int(11) DEFAULT NULL,
  `machine_path` varchar(100) DEFAULT NULL,
  `link_path` varchar(100) DEFAULT NULL,
  `external_path` varchar(100) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `path_type` int(11) DEFAULT NULL,
  `icon` varchar(200) DEFAULT NULL,
  `open_type` int(11) DEFAULT NULL COMMENT '1：iframe窗口打开\n            2：web打开，_blank\n            3：web打开',
  `is_open` varchar(200) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT 'false',
  PRIMARY KEY (`fun_id`)
) ENGINE=InnoDB AUTO_INCREMENT=96 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_function
-- ----------------------------
BEGIN;
INSERT INTO `p_function` VALUES (1, '权限管理', '', 0, 0, 0, 0, 1, '0', NULL, NULL, 0, NULL, 'layui-icon layui-icon-util', NULL, 'false');
INSERT INTO `p_function` VALUES (2, '系统管理', '', 1, 0, 0, 0, 2, '0', NULL, NULL, 9999, NULL, 'layui-icon layui-icon-util', NULL, 'false');
INSERT INTO `p_function` VALUES (3, '会员管理', '/user/user', 2, 0, 0, 0, 3, '', 'user/user.html', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (4, '角色管理', '/user/role', 2, 0, 0, 0, 3, '', 'user/role.html', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (5, '资源管理', '/user/function', 2, 0, 0, 0, 3, '', 'user/function.html', '', 3, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (6, '公共权限', '', 1, 0, 0, 0, 2, '', '', '', 1, 0, 'layui-icon-upload', 1, 'false');
INSERT INTO `p_function` VALUES (7, '上传单图到阿里云存储', '/fileUploadToQiniuyun', 6, 0, 0, 0, 3, '', '', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (8, '富文本图片上传', '/txtFileUpload', 6, 0, 0, 0, 3, '', '', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (9, '后台树菜单', '/user/getNumeTree', 6, 0, 0, 0, 3, '', '/user/getNumeTree', '', 3, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (10, '会员列表', '/user/queryUserInfoPage', 3, 0, 0, 0, 4, '', '/user/queryUserInfoPage', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (11, '角色列表分页', '/user/queryRolePage', 4, 0, 0, 0, 4, '', '/user/queryRolePage', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (12, '添加角色页面', '/user/addRole', 4, 0, 0, 0, 4, '0', '/user/addRole', NULL, 1, NULL, NULL, NULL, 'false');
INSERT INTO `p_function` VALUES (13, '编辑角色页面', '/user/editRole', 4, 0, 0, 0, 4, '0', '/user/editRole', NULL, 2, NULL, NULL, NULL, 'false');
INSERT INTO `p_function` VALUES (15, '查询权限树', '/user/loadTree', 5, NULL, NULL, NULL, 4, '', '/user/loadTree', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (16, '新增资源', '/user/addFunction', 5, NULL, NULL, NULL, 3, '', '/user/addFunction', '', 2, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (17, '删除资源', '/user/deleteFunction', 5, NULL, NULL, NULL, 3, '', '/user/deleteFunction', '', 3, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (18, '资源回显', '/user/queryFunctionByFunId', 5, NULL, NULL, NULL, 3, '', '/user/queryFunctionByFunId', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (19, '编辑资源', '/user/updateFunction', 5, NULL, NULL, NULL, 3, '', '/user/updateFunction', '', 5, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (20, '本地图片上传', '/fileUpload', 6, NULL, NULL, NULL, 3, '', '', '', 4, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (21, '修改密码页面', '/user/userPassWord', 6, NULL, NULL, NULL, 3, '', '/user/userPassWord', '', 6, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (23, '新增会员页面', '/user/addUser', 3, NULL, NULL, NULL, 4, '', '/user/addUser', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (24, '编辑会员页面', '/user/updateUser', 3, NULL, NULL, NULL, 4, '', '/user/updateUser', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (25, '查询所有角色', '/user/selectAllRole', 6, NULL, NULL, NULL, 3, '', '/user/selectAllRole', '', 6, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (26, '查询角色编码', '/user/getRoleCode', 4, NULL, NULL, NULL, 4, '', '/user/getRoleCode', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (27, '查询全部资源', '/user/getNumeTreeAll', 4, NULL, NULL, NULL, 4, '', '/user/getNumeTreeAll', '', 5, 0, '查询全部资源', NULL, 'false');
INSERT INTO `p_function` VALUES (28, '添加角色', '/user/addRoleInfo', 4, NULL, NULL, NULL, 4, '', '/user/addRoleInfo', '', 3, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (29, '删除角色', '/user/deleteRole', 4, NULL, NULL, NULL, 4, '', '/user/deleteRole', '', 6, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (30, '查询角色', '/user/queryRoleByRoleId', 4, NULL, NULL, NULL, 4, '', '/user/queryRoleByRoleId', '', 8, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (31, '更新角色', '/user/updateRole', 4, NULL, NULL, NULL, 4, '', '/user/updateRole', '', 9, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (32, '编辑资料', '/user/updateUserSelf', 6, NULL, NULL, NULL, 3, '', '/user/updateUserSelf', '', 6, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (33, '修改密码', '/user/updatePassword', 6, NULL, NULL, NULL, 3, '', '/user/updatePassword', '', 9, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (34, '添加会员', '/user/addUserInfo', 3, NULL, NULL, NULL, 4, '', '/user/addUserInfo', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (35, '查询会员', '/user/queryUserByUserId', 3, NULL, NULL, NULL, 4, '', '/user/queryUserByUserId', '', 6, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (36, '角色授权', '/user/updateUserRole', 3, NULL, NULL, NULL, 4, '', '/user/updateUserRole', '', 7, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (37, '重置密码', '/user/updateUserPass', 3, NULL, NULL, NULL, 4, '', '/user/updateUserPass', '', 8, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (38, '编辑会员', '/user/updateUserInfo', 3, NULL, NULL, NULL, 4, '', '/user/updateUserInfo', '', 9, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (39, '改变会员状态', '/user/updateUserByUserId', 3, NULL, NULL, NULL, 4, '', '/user/updateUserByUserId', '', 10, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (40, '日志管理', '', 1, NULL, NULL, NULL, 2, '', '', '', 70, 0, 'layui-icon-date', 1, 'false');
INSERT INTO `p_function` VALUES (41, '日志列表', '/log/logList', 40, NULL, NULL, NULL, 3, '', 'log/log.html', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (42, '日志分页', '/log/queryLogPage', 41, NULL, NULL, NULL, 4, '', '/log/queryLogPage', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (43, '系统首页', '', 1, NULL, NULL, NULL, 2, '', '', '', 1, 0, 'layui-icon-app', 1, 'false');
INSERT INTO `p_function` VALUES (45, '扩展插件', '', 1, NULL, NULL, NULL, 2, '', '', '', 2, 0, 'layui-icon-unlink', 1, 'false');
INSERT INTO `p_function` VALUES (46, '富文本', '/OpenRichTxt', 45, NULL, NULL, NULL, 3, '', 'plugin/richTxt.html', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (47, '定时任务', '', 1, NULL, NULL, NULL, 2, '', '', '', 99, 0, 'layui-icon-log', NULL, 'false');
INSERT INTO `p_function` VALUES (48, '自动任务', '/openTask', 47, NULL, NULL, NULL, 3, '', 'task/task.html', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (50, '查询列表分页', '/task/listPage', 48, NULL, NULL, NULL, 4, '', '/task/listPage', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (51, '添加定时任务页面', '/task/OpenAddUTask', 48, NULL, NULL, NULL, 4, '', '/task/OpenAddUTask', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (52, '添加定时', '/task/addtask', 48, NULL, NULL, NULL, 4, '', '/task/addtask', '', 3, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (53, '定时器回显', '/task/selectTaskOne', 48, NULL, NULL, NULL, 4, '', '/task/selectTaskOne', '', 4, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (54, '打开编辑页面', '/task/OpenUpdateTask', 48, NULL, NULL, NULL, 4, '', '/task/OpenUpdateTask', '', 6, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (55, '更新定时器', '/task/updateTask', 48, NULL, NULL, NULL, 4, '', '/task/updateTask', '', 7, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (56, '删除定时器', '/task/deleteTask', 48, NULL, NULL, NULL, 4, '', '/task/deleteTask', '', 8, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (57, '更改定时器状态', '/task/updateTaskState', 48, NULL, NULL, NULL, 4, '', '/task/updateTaskState', '', 9, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (58, '邮件发送', '/openEmail', 45, NULL, NULL, NULL, 3, '', 'plugin/email.html', '', 2, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (59, '更新配置', '/email/updateEmail', 58, NULL, NULL, NULL, 4, '', '/email/updateEmail', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (60, '发送邮件', '/email/sendEmail', 58, NULL, NULL, NULL, 4, '', '/email/sendEmail', '', 2, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (61, '接口管理', '', 1, NULL, NULL, NULL, 2, '', '', '', 80, 0, 'layui-icon-auz', NULL, 'false');
INSERT INTO `p_function` VALUES (62, 'API列表', '/api/api', 61, NULL, NULL, NULL, 3, '', 'api/api.html', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (63, '增加接口页面', '/api/addapi', 62, NULL, NULL, NULL, 4, '', '/api/addapi', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (64, '编辑接口页面', '/api/updateapi', 62, NULL, NULL, NULL, 4, '', '/api/updateapi', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (65, '查询接口列表', '/api/queryApiPage', 62, NULL, NULL, NULL, 4, '', '/api/queryApiPage', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (66, '接口回显查询', '/api/selectApiByApiId', 62, NULL, NULL, NULL, 4, '', '/api/selectApiByApiId', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (67, '修改接口', '/api/updateApiByApiId', 62, NULL, NULL, NULL, 4, '', '/api/updateApiByApiId', '', 5, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (68, '删除接口', '/api/deleteApiByApiId', 62, NULL, NULL, NULL, 4, '', '/api/deleteApiByApiId', '', 6, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (69, '增加接口', '/api/insertApi', 62, NULL, NULL, NULL, 4, '', '/api/insertApi', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (70, '支付宝支付配置', '/api/alipayConfig', 61, NULL, NULL, NULL, 3, '', 'api/alipayConfig.html', '', 2, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (71, '更新配置', '/api/setAlipay', 70, NULL, NULL, NULL, 4, '', '/api/setAlipay', '', 1, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (72, '秒滴短信', '/OpenMiaodi', 45, NULL, NULL, NULL, 3, '', 'plugin/miaodi.html', '', 3, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (73, '发短信验证码', '/Plugin/sendPhone', 72, NULL, NULL, NULL, 4, '', '/Plugin/sendPhone', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (74, '发营销短信', '/Plugin/sendYinxiao', 72, NULL, NULL, NULL, 4, '', '/Plugin/sendYinxiao', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (75, '微信支付配置', '/api/weixinConfig', 61, NULL, NULL, NULL, 3, '', 'api/wxPayConfig.html', '', 3, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (76, '更新配置', '/api/setwxPayConfig', 75, NULL, NULL, NULL, 4, '', '/api/setwxPayConfig', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (77, '充值下单', '/commonPay/voucher', 6, NULL, NULL, NULL, 3, '', '/commonPay/voucher', '', 13, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (78, '微信下单', '/wxPay/getQRCode', 6, NULL, NULL, NULL, 3, '', '/wxPay/getQRCode', '', 14, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (79, '微信支付回调', '/wxPay/notifyWeChatPnPay', 6, NULL, NULL, NULL, 3, '', '/wxPay/notifyWeChatPnPay', '', 15, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (80, '项目管理', '', 1, NULL, NULL, NULL, 2, '', '', '', 85, 0, 'layui-icon-website', 1, 'false');
INSERT INTO `p_function` VALUES (81, '项目列表', '/openObject', 80, NULL, NULL, NULL, 3, '', 'obj/obj.html', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (82, '列表查询', '/obj/queryPage', 81, NULL, NULL, NULL, 4, '', '/obj/queryPage', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (83, '打开新增页面', '/obj/addobj', 81, NULL, NULL, NULL, 4, '', '/obj/addobj', '', 1, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (84, '打开编辑页面', '/obj/updateobj', 81, NULL, NULL, NULL, 4, '', '/obj/updateobj', '', 2, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (85, '删除项目', '/obj/deleteById', 81, NULL, NULL, NULL, 4, '', '/obj/deleteById', '', 3, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (86, '新增项目', '/obj/insertObject', 81, NULL, NULL, NULL, 4, '', '/obj/insertObject', '', 4, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (87, '回显项目', '/obj/selectById', 81, NULL, NULL, NULL, 4, '', '/obj/selectById', '', 5, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (88, '更新项目', '/obj/updateById', 81, NULL, NULL, NULL, 4, '', '/obj/updateById', '', 6, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (89, '更新秘钥', '/obj/updateKey', 81, NULL, NULL, NULL, 4, '', '/obj/updateKey', '', 10, 0, '', 1, 'false');
INSERT INTO `p_function` VALUES (90, '退出登录', '/user/userLogout', 6, NULL, NULL, NULL, 3, '', '', '', 0, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (91, '查询邮件', '/email/selectEmail', 58, NULL, NULL, NULL, 4, '', '', '', 0, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (92, '控制台', '', 43, NULL, NULL, NULL, 3, '', 'home/index.html', '', 0, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (93, '查询系统信息', '/home/selectSysinfo', 92, NULL, NULL, NULL, 4, '', '', '', 0, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (94, '查询配置', '/api/getAlipay', 70, NULL, NULL, NULL, 4, '', '', '', 0, 0, '', NULL, 'false');
INSERT INTO `p_function` VALUES (95, '查询配置', '/api/getwxPayConfig', 75, NULL, NULL, NULL, 4, '', '', '', 0, 0, '', NULL, 'false');
COMMIT;

-- ----------------------------
-- Table structure for p_log
-- ----------------------------
DROP TABLE IF EXISTS `p_log`;
CREATE TABLE `p_log` (
  `log_id` int(11) NOT NULL AUTO_INCREMENT,
  `log_type` int(11) DEFAULT NULL,
  `log_desc` varchar(500) DEFAULT NULL,
  `log_time` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `log_level` int(11) DEFAULT NULL,
  `log_ip` varchar(500) DEFAULT NULL,
  `is_collect` int(11) DEFAULT NULL,
  PRIMARY KEY (`log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=759 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_log
-- ----------------------------
BEGIN;
INSERT INTO `p_log` VALUES (626, 1, 'admin登录成功', '2021-01-19 07:37:06', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (627, 1, '新增权限控制台', '2021-01-19 07:37:52', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (628, 2, '删除权限', '2021-01-19 07:41:08', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (629, 1, '新增权限查询系统信息', '2021-01-19 07:41:31', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (630, 3, '修改权限', '2021-01-19 07:41:50', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (631, 3, '修改角色', '2021-01-19 07:42:06', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (632, 1, 'admin登录成功', '2021-01-19 07:49:14', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (633, 3, '修改角色', '2021-01-19 07:49:59', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (634, 1, 'admin登录成功', '2021-01-19 07:50:59', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (635, 1, '新增权限查询配置', '2021-01-19 07:52:44', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (636, 1, '新增权限查询配置', '2021-01-19 07:53:20', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (637, 3, '修改角色', '2021-01-19 07:53:35', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (638, 3, '修改角色', '2021-01-19 07:55:40', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (639, 1, 'admin登录成功', '2021-01-19 07:55:53', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (640, 1, '系统(qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun)充值:1.00元', '2021-01-19 08:25:25', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (641, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:28:51', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (642, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:28:51', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (643, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:28:51', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (644, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:28:51', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (645, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:43:49', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (646, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:43:49', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (647, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:43:49', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (648, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:43:49', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (649, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:44:53', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (650, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:44:53', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (651, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:44:53', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (652, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:44:53', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (653, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:47:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (654, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:47:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (655, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:47:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (656, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:47:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (657, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:17', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (658, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:17', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (659, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:17', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (660, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:17', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (661, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:48', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (662, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:48', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (663, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:48', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (664, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:48:48', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (665, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:49:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (666, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:49:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (667, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:49:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (668, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:49:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (669, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:50:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (670, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:50:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (671, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:50:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (672, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:50:02', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (673, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:51:50', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (674, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:51:50', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (675, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:51:50', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (676, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:51:50', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (677, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:55:15', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (678, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:55:15', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (679, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:55:15', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (680, 1, 'Failed to parse multipart servlet request; nested exception is java.io.IOException: org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException: Processing of multipart/form-data request failed. java.io.EOFException: Unexpected EOF read on the socket', '2021-01-19 08:55:15', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (681, 1, '子系统(qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun)微信充值:1.00元', '2021-01-19 15:56:46', 1, 1, '192.168.31.168', 0);
INSERT INTO `p_log` VALUES (682, 1, '子系统(qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun)支付宝充值:1.00元', '2021-01-19 16:02:31', 1, 1, '192.168.31.168', 0);
INSERT INTO `p_log` VALUES (683, 1, 'admin登录成功', '2021-01-22 06:23:02', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (684, 1, 'admin登录成功', '2021-01-22 07:01:44', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (685, 1, '非法请求', '2021-01-22 07:09:34', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (686, 1, '非法请求', '2021-01-22 07:09:45', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (687, 1, '非法请求', '2021-01-22 07:10:35', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (688, 1, '非法请求', '2021-01-22 07:10:35', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (689, 1, '非法请求', '2021-01-22 07:10:48', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (690, 1, '非法请求', '2021-01-22 07:10:48', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (691, 1, '非法请求', '2021-01-22 07:11:51', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (692, 1, 'admin登录成功', '2021-01-22 07:12:11', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (693, 1, '非法请求', '2021-01-22 07:12:47', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (694, 1, '非法请求', '2021-01-22 07:19:00', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (695, 1, 'admin登录成功', '2021-01-22 07:19:19', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (696, 3, '修改权限', '2021-01-22 07:20:10', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (697, 3, '修改角色', '2021-01-22 07:20:29', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (698, 1, 'admin登录成功', '2021-01-22 07:21:02', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (699, 1, 'admin登录成功', '2021-01-22 07:22:09', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (700, 1, '非法请求', '2021-01-22 07:30:46', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (701, 1, '非法请求', '2021-01-22 07:44:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (702, 1, 'java.io.IOException: 你的主机中的软件中止了一个已建立的连接。', '2021-01-22 07:44:38', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (703, 1, 'admin登录成功', '2021-01-22 07:44:47', 1, 1, '192.168.8.1', 0);
INSERT INTO `p_log` VALUES (704, 1, 'admin登录成功', '2021-01-22 07:59:19', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (705, 1, '子系统(DPWFIEXupoESsBVqecDjU7FpG1oJO192)微信充值:1.00元', '2021-01-22 08:04:53', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (706, 1, 'admin登录成功', '2021-01-22 08:15:45', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (707, 1, 'admin登录成功', '2021-01-22 08:16:57', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (708, 1, 'admin登录成功', '2021-01-22 08:23:00', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (709, 1, '手机号为13389186557短信验证:332356信息请求成功。smsId:0122174333010495639808', '2021-01-22 09:43:33', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (710, 1, '手机号为13389186557短信验证:522511信息请求成功。smsId:0122174606010495640845', '2021-01-22 09:46:07', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (711, 1, '手机号为13389186557短信验证:167946信息请求成功。smsId:0122174635010495641012', '2021-01-22 09:46:35', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (712, 1, '手机号为13389186557短信验证:589519信息请求成功。smsId:0122180420010495648231', '2021-01-22 10:04:21', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (713, 1, '手机号为13389186557短信验证:970349信息请求成功。smsId:0122180630010495649070', '2021-01-22 10:06:31', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (714, 1, '手机号为13389186557短信验证:696575信息请求成功。smsId:0123143731010495644263', '2021-01-23 06:37:32', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (715, 1, '手机号为13389186557短信验证:177982信息请求成功。smsId:0123143927010495644938', '2021-01-23 06:39:28', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (716, 1, '手机号为13389186557短信验证:869114信息请求成功。smsId:0123145404010495649620', '2021-01-23 06:54:05', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (717, 1, '手机号为13389186557短信验证:356925信息请求成功。smsId:0123145610010495650345', '2021-01-23 06:56:11', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (718, 1, '手机号为13389186557短信验证:926031信息请求成功。smsId:0123150056010495652038', '2021-01-23 07:00:57', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (719, 1, 'admin登录成功', '2021-01-25 01:46:00', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (720, 1, 'admin登录成功', '2021-01-28 10:00:43', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (721, 1, '手机号为18629066557短信验证:380432信息请求成功。smsId:0128200701010495653220', '2021-01-28 12:07:01', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (722, 1, '手机号为13389186557短信验证:540636信息请求成功。smsId:0129084218010495619458', '2021-01-29 00:42:18', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (723, 1, '手机号为15594167954短信验证:265519信息请求成功。smsId:0206154026017599651080', '2021-02-06 07:40:26', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (724, 1, '手机号为15594167954短信验证:518341信息请求成功。smsId:0206154245017599653227', '2021-02-06 07:42:46', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (725, 1, '手机号为15594167954短信验证:806780信息请求成功。smsId:0208125229017599646885', '2021-02-08 04:52:30', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (726, 1, '手机号为13572702222短信验证:723115信息请求成功。smsId:0208130403017599654680', '2021-02-08 05:04:04', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (727, 1, '手机号为13572702222短信验证:995931信息请求成功。smsId:0208131710017599661437', '2021-02-08 05:17:11', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (728, 1, '手机号为13572702222短信验证:484045信息请求成功。smsId:0208135121017599616281', '2021-02-08 05:51:22', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (729, 1, '手机号为13572702222短信验证:745598信息请求成功。smsId:0208135600017599619343', '2021-02-08 05:56:01', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (730, 1, '手机号为13363906104短信验证:760564信息请求成功。smsId:0209180636017599665522', '2021-02-09 10:06:37', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (731, 1, 'Index: 0, Size: 0', '2021-02-09 14:36:58', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (732, 1, 'Index: 0, Size: 0', '2021-02-09 14:37:04', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (733, 1, 'admin登录成功', '2021-02-21 13:04:35', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (734, 1, '手机号为15594167954短信验证:761689信息请求成功。smsId:0225094815017599602304', '2021-02-25 01:48:16', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (735, 1, 'Index: 0, Size: 0', '2021-02-25 02:38:28', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (736, 1, 'Index: 0, Size: 0', '2021-02-25 02:38:54', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (737, 1, 'Index: 0, Size: 0', '2021-02-25 02:41:51', 1, 3, '172.26.0.130:8082', 0);
INSERT INTO `p_log` VALUES (738, 1, 'Index: 0, Size: 0', '2021-02-25 02:50:01', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (739, 1, 'A JSONObject text must begin with \'{\' at character 1 of <response>\n  <status>0</status>\n  <info>INVALID_USER_KEY</info>\n  <infocode>10001</infocode>\n</response>', '2021-02-25 03:29:22', 1, 3, '192.168.8.1:-1', 0);
INSERT INTO `p_log` VALUES (740, 1, 'admin登录成功', '2021-02-25 07:18:59', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (741, 1, '手机号为18220375111短信验证:248574信息请求成功。smsId:0310121655017599604962', '2021-03-10 04:16:56', 1, 1, '172.26.0.130', 0);
INSERT INTO `p_log` VALUES (742, 1, '手机号为18978143678短信验证:819391信息请求成功。smsId:0313093848017599627981', '2021-03-13 01:38:49', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (743, 1, '手机号为13991814992短信验证:412550信息请求成功。smsId:0313191925017599629061', '2021-03-13 11:19:25', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (744, 1, 'admin登录成功', '2021-03-17 08:48:42', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (745, 1, 'admin登录成功', '2021-03-27 07:37:52', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (746, 1, 'Optional int parameter \'objectId\' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.', '2021-03-27 07:39:57', 1, 3, '172.17.0.1:8082', 0);
INSERT INTO `p_log` VALUES (747, 1, 'admin登录成功', '2021-03-27 09:03:11', 1, 1, '192.168.31.240', 0);
INSERT INTO `p_log` VALUES (748, 3, '自己资料', '2021-03-27 09:23:11', 1, 1, '192.168.31.240', 0);
INSERT INTO `p_log` VALUES (749, 3, '自己资料', '2021-03-27 09:35:56', 1, 1, '192.168.31.240', 0);
INSERT INTO `p_log` VALUES (750, 1, 'admin登录成功', '2021-03-27 09:37:24', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (751, 1, 'admin登录成功', '2021-03-27 10:07:28', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (752, 1, 'admin登录成功', '2021-03-28 11:03:28', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (753, 1, 'admin登录成功', '2021-03-28 11:23:46', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (754, 3, '自己资料', '2021-03-28 11:29:45', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (755, 1, 'admin登录成功', '2021-03-29 01:08:21', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (756, 1, '子系统(TRYBb1esLAFcQhQnWTvT745RxRH89c7N)微信充值:1.00元', '2021-03-29 03:52:50', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (757, 1, 'admin登录成功', '2021-03-29 06:34:38', 1, 1, '172.17.0.1', 0);
INSERT INTO `p_log` VALUES (758, 1, 'admin登录成功', '2021-04-12 06:21:05', 1, 1, '172.17.0.1', 0);
COMMIT;

-- ----------------------------
-- Table structure for p_object
-- ----------------------------
DROP TABLE IF EXISTS `p_object`;
CREATE TABLE `p_object` (
  `object_id` int(11) NOT NULL AUTO_INCREMENT,
  `object_name` varchar(100) DEFAULT NULL,
  `object_code` varchar(100) DEFAULT NULL,
  `object_key` text,
  `object_end_time` datetime DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `object_add_time` datetime DEFAULT NULL,
  `alipay_info` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`object_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_object
-- ----------------------------
BEGIN;
INSERT INTO `p_object` VALUES (2, '路阿路项目', 'DPWFIEXupoESsBVqecDjU7FpG1oJO192', 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIyIiwiaWF0IjoxNjExMzAxNjcxLCJjb2RlIjp7Im9iamVjdENvZGUiOiJEUFdGSUVYdXBvRVNzQlZxZWNEalU3RnBHMW9KTzE5MiIsIm9iamVjdElkIjoyfSwic3ViIjoi5ZOB5Yib572R57ucIiwiZXhwIjoyMjEwODMxMTA4fQ.sMf9cKYdOwiGBUf_Ofgf18aNb8QorZ9t_88PfeFEl8U', '2040-01-22 07:45:08', 1, 2, '路阿路App项目', '2021-01-09 23:30:40', NULL);
INSERT INTO `p_object` VALUES (3, '品创网络子系统', 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIzIiwiaWF0IjoxNjEwMjEwMDYxLCJjb2RlIjp7Im9iamVjdENvZGUiOiJxUmViT3JXV01QNmNXdjZYUlROcFRJaTZsaDluN3N1biIsIm9iamVjdElkIjozfSwic3ViIjoi5ZOB5Yib572R57ucIiwiZXhwIjoxODMxMDQ0OTUzfQ.PfLgvYkBnwDZCOfSHrysPjCzjRqbiUMdnOC0lV6yttY', '2028-01-09 15:35:53', 1, 1, '品创网络子系统', '2021-01-19 22:58:19', 'http://127.0.0.1:8848/front/views/inform/succeed.html');
INSERT INTO `p_object` VALUES (4, '集福购项目', 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', 'eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI0IiwiaWF0IjoxNjE2ODM2MTIwLCJjb2RlIjp7Im9iamVjdENvZGUiOiJUUllCYjFlc0xBRmNRaFFuV1R2VDc0NVJ4Ukg4OWM3TiIsIm9iamVjdElkIjo0fSwic3ViIjoi5ZOB5Yib572R57ucIiwiZXhwIjoyNTMxOTg0ODc0fQ.ZOnZh9s9heEITmnG7mdsMpgZ95jSDnaiv3tNluFMB04', '2050-03-27 09:07:54', 1, 3, '集福购项目', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for p_pay_order
-- ----------------------------
DROP TABLE IF EXISTS `p_pay_order`;
CREATE TABLE `p_pay_order` (
  `order_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `sys_code` varchar(100) DEFAULT NULL,
  `order_num` varchar(200) DEFAULT NULL,
  `order_state` int(11) DEFAULT NULL COMMENT '1:待支付\n            2:已支付',
  `order_pice` decimal(10,2) DEFAULT NULL,
  `order_paid_pice` decimal(10,2) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  `order_count` int(11) DEFAULT NULL,
  `order_add_time` datetime DEFAULT NULL,
  `order_pay_time` datetime DEFAULT NULL,
  `trade_no` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `FK_Reference_13` (`user_id`),
  KEY `FK_Reference_54` (`product_id`),
  CONSTRAINT `FK_Reference_13` FOREIGN KEY (`user_id`) REFERENCES `p_user` (`user_id`),
  CONSTRAINT `FK_Reference_54` FOREIGN KEY (`product_id`) REFERENCES `p_pay_product` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_pay_order
-- ----------------------------
BEGIN;
INSERT INTO `p_pay_order` VALUES (127, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000678773470', 2, 1.00, 1.00, 1, 1, '2021-01-19 07:57:11', '2021-01-19 07:58:27', NULL);
INSERT INTO `p_pay_order` VALUES (128, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000259591431', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:11:10', '2021-01-19 08:11:23', NULL);
INSERT INTO `p_pay_order` VALUES (129, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000479417199', 1, 10.00, NULL, 1, 1, '2021-01-19 08:11:52', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (130, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000421719116', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:12:02', '2021-01-19 08:12:13', NULL);
INSERT INTO `p_pay_order` VALUES (131, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000002143364424', 1, 10.00, NULL, 1, 1, '2021-01-19 08:15:04', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (132, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000315283003', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:15:09', '2021-01-19 08:15:19', NULL);
INSERT INTO `p_pay_order` VALUES (133, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000849313875', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:16:24', '2021-01-19 08:16:38', NULL);
INSERT INTO `p_pay_order` VALUES (134, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000021526144', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:20:01', '2021-01-19 08:20:12', NULL);
INSERT INTO `p_pay_order` VALUES (135, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000545514014', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:20:35', '2021-01-19 08:20:50', NULL);
INSERT INTO `p_pay_order` VALUES (136, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000002064810029', 2, 1.00, 1.00, 1, 1, '2021-01-19 08:25:14', '2021-01-19 08:25:26', NULL);
INSERT INTO `p_pay_order` VALUES (137, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001320959574', 1, 10.00, NULL, 1, 1, '2021-01-19 09:22:56', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (138, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000962821275', 1, 10.00, NULL, 1, 1, '2021-01-19 09:23:44', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (139, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001851265239', 1, 1.00, NULL, 1, 1, '2021-01-19 09:30:18', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (140, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000002139277163', 1, 1.00, NULL, 1, 1, '2021-01-19 09:37:11', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (141, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000562879524', 1, 1.00, NULL, 1, 1, '2021-01-19 10:01:52', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (142, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000443503270', 1, 1.00, NULL, 1, 1, '2021-01-19 10:15:18', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (143, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001998419932', 1, 1.00, NULL, 1, 1, '2021-01-19 10:21:24', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (144, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000276249432', 1, 1.00, NULL, 1, 1, '2021-01-19 10:22:47', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (145, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001573482336', 1, 1.00, NULL, 1, 1, '2021-01-19 10:36:50', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (146, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000782129787', 2, 1.00, 1.00, 1, 1, '2021-01-19 15:02:59', '2021-01-19 15:03:34', '2021011922001459531411239096');
INSERT INTO `p_pay_order` VALUES (147, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000000300701651', 2, 1.00, 1.00, 1, 1, '2021-01-19 15:56:21', '2021-01-19 15:56:47', NULL);
INSERT INTO `p_pay_order` VALUES (148, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001482726067', 1, 1.00, NULL, 1, 1, '2021-01-19 15:56:59', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (149, 1, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', '1000001673094930', 2, 1.00, 1.00, 1, 1, '2021-01-19 15:57:59', '2021-01-19 16:02:32', '2021012022001459531411389028');
INSERT INTO `p_pay_order` VALUES (150, 1, 'DPWFIEXupoESsBVqecDjU7FpG1oJO192', '1000000395636093', 1, 1.00, NULL, 1, 1, '2021-01-22 08:02:23', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (151, 1, 'DPWFIEXupoESsBVqecDjU7FpG1oJO192', '1000000348058372', 2, 1.00, 1.00, 1, 1, '2021-01-22 08:04:38', '2021-01-22 08:04:53', NULL);
INSERT INTO `p_pay_order` VALUES (152, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000000838815038', 2, 1.00, 1.00, 1, 1, '2021-03-29 03:52:26', '2021-03-29 03:52:50', NULL);
INSERT INTO `p_pay_order` VALUES (153, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000001247491059', 1, 10.00, NULL, 1, 1, '2021-03-29 03:53:01', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (154, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000002010352992', 1, 10.00, NULL, 1, 1, '2021-03-29 03:53:17', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (155, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000000262778284', 1, 10.00, NULL, 1, 1, '2021-03-29 03:53:25', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (156, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000001502243637', 1, 10.00, NULL, 1, 1, '2021-03-29 03:57:00', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (157, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000001579836586', 1, 10.00, NULL, 1, 1, '2021-03-29 06:40:31', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (158, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000000957968869', 1, 10.00, NULL, 1, 1, '2021-03-29 06:40:34', NULL, NULL);
INSERT INTO `p_pay_order` VALUES (159, 1, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', '1000001785658699', 1, 10.00, NULL, 1, 1, '2021-04-07 15:24:39', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for p_pay_product
-- ----------------------------
DROP TABLE IF EXISTS `p_pay_product`;
CREATE TABLE `p_pay_product` (
  `product_id` int(11) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(500) DEFAULT NULL,
  `product_pice` decimal(10,2) DEFAULT NULL,
  `product_level` int(11) DEFAULT NULL,
  `num_day` int(11) DEFAULT NULL,
  PRIMARY KEY (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_pay_product
-- ----------------------------
BEGIN;
INSERT INTO `p_pay_product` VALUES (1, '系统余额充值', 1.00, 1, 0);
COMMIT;

-- ----------------------------
-- Table structure for p_role
-- ----------------------------
DROP TABLE IF EXISTS `p_role`;
CREATE TABLE `p_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  `role_desc` varchar(500) DEFAULT NULL,
  `role_type` int(11) DEFAULT NULL COMMENT '1:管理角色\n            2：用户角色\n            3：其他',
  `role_sort` int(11) DEFAULT NULL,
  `role_rank` int(11) DEFAULT NULL COMMENT '越大级别越高',
  `code_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_role
-- ----------------------------
BEGIN;
INSERT INTO `p_role` VALUES (1, '超级管理员', '无所不能', 1, 1, 100, 1);
INSERT INTO `p_role` VALUES (3, '管理员', '商家管理员也是很牛逼的..........', 1, 2, 50, 2);
INSERT INTO `p_role` VALUES (8, '普通商家', '普通商家会员', 2, 3, 10, 3);
COMMIT;

-- ----------------------------
-- Table structure for p_role_fun
-- ----------------------------
DROP TABLE IF EXISTS `p_role_fun`;
CREATE TABLE `p_role_fun` (
  `fun_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  KEY `FK_Reference_4` (`fun_id`),
  KEY `FK_Reference_7` (`role_id`),
  CONSTRAINT `FK_Reference_4` FOREIGN KEY (`fun_id`) REFERENCES `p_function` (`fun_id`),
  CONSTRAINT `FK_Reference_7` FOREIGN KEY (`role_id`) REFERENCES `p_role` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_role_fun
-- ----------------------------
BEGIN;
INSERT INTO `p_role_fun` VALUES (6, 8);
INSERT INTO `p_role_fun` VALUES (7, 8);
INSERT INTO `p_role_fun` VALUES (8, 8);
INSERT INTO `p_role_fun` VALUES (9, 8);
INSERT INTO `p_role_fun` VALUES (6, 3);
INSERT INTO `p_role_fun` VALUES (7, 3);
INSERT INTO `p_role_fun` VALUES (8, 3);
INSERT INTO `p_role_fun` VALUES (9, 3);
INSERT INTO `p_role_fun` VALUES (6, 1);
INSERT INTO `p_role_fun` VALUES (90, 1);
INSERT INTO `p_role_fun` VALUES (7, 1);
INSERT INTO `p_role_fun` VALUES (8, 1);
INSERT INTO `p_role_fun` VALUES (9, 1);
INSERT INTO `p_role_fun` VALUES (20, 1);
INSERT INTO `p_role_fun` VALUES (21, 1);
INSERT INTO `p_role_fun` VALUES (25, 1);
INSERT INTO `p_role_fun` VALUES (32, 1);
INSERT INTO `p_role_fun` VALUES (33, 1);
INSERT INTO `p_role_fun` VALUES (77, 1);
INSERT INTO `p_role_fun` VALUES (78, 1);
INSERT INTO `p_role_fun` VALUES (79, 1);
INSERT INTO `p_role_fun` VALUES (43, 1);
INSERT INTO `p_role_fun` VALUES (92, 1);
INSERT INTO `p_role_fun` VALUES (93, 1);
INSERT INTO `p_role_fun` VALUES (46, 1);
INSERT INTO `p_role_fun` VALUES (58, 1);
INSERT INTO `p_role_fun` VALUES (91, 1);
INSERT INTO `p_role_fun` VALUES (59, 1);
INSERT INTO `p_role_fun` VALUES (60, 1);
INSERT INTO `p_role_fun` VALUES (72, 1);
INSERT INTO `p_role_fun` VALUES (73, 1);
INSERT INTO `p_role_fun` VALUES (74, 1);
INSERT INTO `p_role_fun` VALUES (40, 1);
INSERT INTO `p_role_fun` VALUES (41, 1);
INSERT INTO `p_role_fun` VALUES (42, 1);
INSERT INTO `p_role_fun` VALUES (61, 1);
INSERT INTO `p_role_fun` VALUES (62, 1);
INSERT INTO `p_role_fun` VALUES (65, 1);
INSERT INTO `p_role_fun` VALUES (63, 1);
INSERT INTO `p_role_fun` VALUES (64, 1);
INSERT INTO `p_role_fun` VALUES (66, 1);
INSERT INTO `p_role_fun` VALUES (69, 1);
INSERT INTO `p_role_fun` VALUES (67, 1);
INSERT INTO `p_role_fun` VALUES (68, 1);
INSERT INTO `p_role_fun` VALUES (70, 1);
INSERT INTO `p_role_fun` VALUES (94, 1);
INSERT INTO `p_role_fun` VALUES (71, 1);
INSERT INTO `p_role_fun` VALUES (75, 1);
INSERT INTO `p_role_fun` VALUES (95, 1);
INSERT INTO `p_role_fun` VALUES (76, 1);
INSERT INTO `p_role_fun` VALUES (80, 1);
INSERT INTO `p_role_fun` VALUES (81, 1);
INSERT INTO `p_role_fun` VALUES (82, 1);
INSERT INTO `p_role_fun` VALUES (83, 1);
INSERT INTO `p_role_fun` VALUES (84, 1);
INSERT INTO `p_role_fun` VALUES (85, 1);
INSERT INTO `p_role_fun` VALUES (86, 1);
INSERT INTO `p_role_fun` VALUES (87, 1);
INSERT INTO `p_role_fun` VALUES (88, 1);
INSERT INTO `p_role_fun` VALUES (89, 1);
INSERT INTO `p_role_fun` VALUES (47, 1);
INSERT INTO `p_role_fun` VALUES (48, 1);
INSERT INTO `p_role_fun` VALUES (50, 1);
INSERT INTO `p_role_fun` VALUES (51, 1);
INSERT INTO `p_role_fun` VALUES (52, 1);
INSERT INTO `p_role_fun` VALUES (53, 1);
INSERT INTO `p_role_fun` VALUES (54, 1);
INSERT INTO `p_role_fun` VALUES (55, 1);
INSERT INTO `p_role_fun` VALUES (56, 1);
INSERT INTO `p_role_fun` VALUES (57, 1);
INSERT INTO `p_role_fun` VALUES (2, 1);
INSERT INTO `p_role_fun` VALUES (3, 1);
INSERT INTO `p_role_fun` VALUES (10, 1);
INSERT INTO `p_role_fun` VALUES (23, 1);
INSERT INTO `p_role_fun` VALUES (24, 1);
INSERT INTO `p_role_fun` VALUES (34, 1);
INSERT INTO `p_role_fun` VALUES (35, 1);
INSERT INTO `p_role_fun` VALUES (36, 1);
INSERT INTO `p_role_fun` VALUES (37, 1);
INSERT INTO `p_role_fun` VALUES (38, 1);
INSERT INTO `p_role_fun` VALUES (39, 1);
INSERT INTO `p_role_fun` VALUES (4, 1);
INSERT INTO `p_role_fun` VALUES (11, 1);
INSERT INTO `p_role_fun` VALUES (12, 1);
INSERT INTO `p_role_fun` VALUES (13, 1);
INSERT INTO `p_role_fun` VALUES (26, 1);
INSERT INTO `p_role_fun` VALUES (28, 1);
INSERT INTO `p_role_fun` VALUES (27, 1);
INSERT INTO `p_role_fun` VALUES (29, 1);
INSERT INTO `p_role_fun` VALUES (30, 1);
INSERT INTO `p_role_fun` VALUES (31, 1);
INSERT INTO `p_role_fun` VALUES (5, 1);
INSERT INTO `p_role_fun` VALUES (15, 1);
INSERT INTO `p_role_fun` VALUES (16, 1);
INSERT INTO `p_role_fun` VALUES (17, 1);
INSERT INTO `p_role_fun` VALUES (18, 1);
INSERT INTO `p_role_fun` VALUES (19, 1);
COMMIT;

-- ----------------------------
-- Table structure for p_sys_money
-- ----------------------------
DROP TABLE IF EXISTS `p_sys_money`;
CREATE TABLE `p_sys_money` (
  `sys_id` int(11) NOT NULL AUTO_INCREMENT,
  `sys_code` varchar(200) DEFAULT NULL,
  `sys_pice` decimal(10,2) DEFAULT NULL,
  `sys_allpice` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`sys_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_sys_money
-- ----------------------------
BEGIN;
INSERT INTO `p_sys_money` VALUES (2, 'qRebOrWWMP6cWv6XRTNpTIi6lh9n7sun', 7.00, 7.00);
INSERT INTO `p_sys_money` VALUES (3, 'DPWFIEXupoESsBVqecDjU7FpG1oJO192', 1500.00, 1500.00);
INSERT INTO `p_sys_money` VALUES (4, 'TRYBb1esLAFcQhQnWTvT745RxRH89c7N', 1.00, 1.00);
COMMIT;

-- ----------------------------
-- Table structure for p_tasks
-- ----------------------------
DROP TABLE IF EXISTS `p_tasks`;
CREATE TABLE `p_tasks` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_name` varchar(200) DEFAULT NULL,
  `task_code` varchar(200) DEFAULT NULL,
  `task_state` int(11) DEFAULT NULL,
  `task_function` varchar(100) DEFAULT NULL,
  `task_num` int(11) DEFAULT NULL,
  `task_sort` int(11) DEFAULT NULL,
  `task_start_time` datetime DEFAULT NULL,
  `task_end_time` datetime DEFAULT NULL,
  `task_update_time` datetime DEFAULT NULL,
  `task_note` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_tasks
-- ----------------------------
BEGIN;
INSERT INTO `p_tasks` VALUES (1, '每2秒执行一次', '0/2 * * * * ? ', 2, 'test', 9973, 1, '2020-12-28 11:26:34', '2020-12-31 11:26:36', '2020-12-30 03:31:56', '及时测试一下.........');
INSERT INTO `p_tasks` VALUES (4, '一分钟执行5次', '0/1 * * * * ? ', 1, 'test2', 9874, 1, '2020-12-28 06:25:44', '2020-12-30 16:00:00', '2020-12-30 03:31:59', '一分钟执行2次');
COMMIT;

-- ----------------------------
-- Table structure for p_user
-- ----------------------------
DROP TABLE IF EXISTS `p_user`;
CREATE TABLE `p_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `department_id` int(11) DEFAULT NULL,
  `user_phone` varchar(50) DEFAULT NULL,
  `user_account` varchar(50) DEFAULT NULL,
  `user_password` varchar(50) DEFAULT NULL,
  `user_email` varchar(50) DEFAULT NULL,
  `user_name` varchar(50) DEFAULT NULL,
  `user_check` int(11) DEFAULT NULL COMMENT '1：正常\n            2：禁用\n            3：停用',
  `is_super` int(11) DEFAULT NULL,
  `icon` varchar(500) DEFAULT NULL,
  `user_qq` varchar(50) DEFAULT NULL,
  `user_describe` text,
  `creation_time` datetime DEFAULT NULL,
  `wechat_uid` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FK_Reference_6` (`department_id`),
  CONSTRAINT `FK_Reference_6` FOREIGN KEY (`department_id`) REFERENCES `p_department` (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user
-- ----------------------------
BEGIN;
INSERT INTO `p_user` VALUES (1, 1, '13389186557', 'admin', 'bFwbUgdQ4/KkRMP6sP37bw==', '942879858@qq.com', '西决', 1, 1, 'https://jifugou.oss-cn-zhangjiakou.aliyuncs.com/1000000152003937.png', '942879858', '我很酷,我知道!', '2020-12-07 11:54:13', NULL);
INSERT INTO `p_user` VALUES (4, 2, '13389186559', 'xijue', 'bFwbUgdQ4/KkRMP6sP37bw==', '1802221702@qq.com', 'xijue', 1, NULL, 'http://file.jfgapp.com/1000000557644776.jpg', '1802221702', NULL, '2020-12-15 09:24:08', NULL);
INSERT INTO `p_user` VALUES (5, 2, '13389185556', '2222222', 'bFwbUgdQ4/KkRMP6sP37bw==', '13389185556@qq.com', '2222222', 1, NULL, '111111', '13389185556', NULL, '2021-01-12 14:50:44', NULL);
COMMIT;

-- ----------------------------
-- Table structure for p_user_money
-- ----------------------------
DROP TABLE IF EXISTS `p_user_money`;
CREATE TABLE `p_user_money` (
  `user_money_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `available_money` decimal(10,2) DEFAULT NULL,
  `total_money` decimal(10,2) DEFAULT NULL,
  `has_been_money` decimal(10,2) DEFAULT NULL,
  `add_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_money_id`),
  KEY `FK_Reference_10` (`user_id`),
  CONSTRAINT `FK_Reference_10` FOREIGN KEY (`user_id`) REFERENCES `p_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user_money
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for p_user_role
-- ----------------------------
DROP TABLE IF EXISTS `p_user_role`;
CREATE TABLE `p_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reference_5` (`user_id`),
  KEY `FK_Reference_8` (`role_id`),
  CONSTRAINT `FK_Reference_5` FOREIGN KEY (`user_id`) REFERENCES `p_user` (`user_id`),
  CONSTRAINT `FK_Reference_8` FOREIGN KEY (`role_id`) REFERENCES `p_role` (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user_role
-- ----------------------------
BEGIN;
INSERT INTO `p_user_role` VALUES (1, 1, 1);
INSERT INTO `p_user_role` VALUES (2, 4, 3);
INSERT INTO `p_user_role` VALUES (3, 5, 8);
COMMIT;

-- ----------------------------
-- Table structure for p_user_water_money
-- ----------------------------
DROP TABLE IF EXISTS `p_user_water_money`;
CREATE TABLE `p_user_water_money` (
  `user_water_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `user_water_money` decimal(10,2) DEFAULT NULL,
  `user_water_type` int(11) DEFAULT NULL COMMENT '1支出\n            2收入',
  `user_water_time` datetime DEFAULT NULL,
  `user_water_ip` varchar(500) DEFAULT NULL,
  `user_water_desc` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`user_water_id`),
  KEY `FK_Reference_11` (`user_id`),
  CONSTRAINT `FK_Reference_11` FOREIGN KEY (`user_id`) REFERENCES `p_user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_user_water_money
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for p_wx_pay_config
-- ----------------------------
DROP TABLE IF EXISTS `p_wx_pay_config`;
CREATE TABLE `p_wx_pay_config` (
  `wx_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(100) DEFAULT NULL,
  `app_id` varchar(100) DEFAULT NULL,
  `mch_id` varchar(100) DEFAULT NULL,
  `wx_key` varchar(100) DEFAULT NULL,
  `notiey_url` varchar(150) DEFAULT NULL,
  PRIMARY KEY (`wx_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of p_wx_pay_config
-- ----------------------------
BEGIN;
INSERT INTO `p_wx_pay_config` VALUES (2, '1', 'wxbb4fa1c0b01c822f', '1502764891', 'o31H520SOav4vw3Lv8LCwxY6gLqODbKU', 'https://www.sxpcwlkj.com/');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `crate_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `user_sex` int(11) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1321732391973847053 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, '西决', 18, 'test1@baomidou.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (2, '单鹏年', 20, 'test2@baomidou.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (3, 'Tom', 28, 'test3@baomidou.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (4, 'Sandy', 21, 'test4@baomidou.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (5, 'gcc', 12, 'test5@baomidou.com', '2020-10-29 16:48:30', '2020-11-03 02:04:47', 0);
INSERT INTO `user` VALUES (1321732391973847041, 'zsr', 20, '1241@qq.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (1321732391973847042, 'zsr', 20, '1241@qq.com', '2020-10-29 16:48:30', '2020-10-29 16:48:30', 1);
INSERT INTO `user` VALUES (1321732391973847043, 'xxx', 20, '1241@qq.com', '2020-10-29 16:54:38', '2020-10-29 16:54:38', 1);
INSERT INTO `user` VALUES (1321732391973847044, 'uiopc', 30, '1241@qq.com', '2020-10-29 09:21:22', '2020-10-29 09:21:22', 1);
INSERT INTO `user` VALUES (1321732391973847045, 'uiopc', 38, '1241@qq.com', '2020-10-29 09:23:15', '2020-10-29 09:23:15', 1);
INSERT INTO `user` VALUES (1321732391973847046, 'uiopc', 38, '1241@qq.com', '2020-10-31 02:42:52', '2020-10-31 02:42:52', 1);
INSERT INTO `user` VALUES (1321732391973847047, 'uiopc', 38, '1241@qq.com', '2020-10-31 02:49:05', '2020-10-31 02:49:05', 1);
INSERT INTO `user` VALUES (1321732391973847048, 'uiopc', 38, '1241@qq.com', '2020-11-02 04:48:37', '2020-11-02 04:48:37', 1);
INSERT INTO `user` VALUES (1321732391973847049, 'uiopc', 38, '1241@qq.com', '2020-11-02 05:58:29', '2020-11-02 05:58:29', 0);
INSERT INTO `user` VALUES (1321732391973847051, 'uiopc', 38, '1241@qq.com', '2020-11-02 06:51:03', '2020-11-02 06:51:03', 0);
INSERT INTO `user` VALUES (1321732391973847052, 'uiopc', 38, '1241@qq.com', '2020-11-03 01:29:41', '2020-11-03 01:29:41', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
