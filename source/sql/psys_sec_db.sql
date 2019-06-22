/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50718
 Source Host           : localhost:3306
 Source Schema         : psys_sec_db

 Target Server Type    : MySQL
 Target Server Version : 50718
 File Encoding         : 65001

 Date: 14/07/2018 15:44:53
*/

CREATE DATABASE /*!32312 IF NOT EXISTS*/`psys_sec_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `psys_sec_db`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sec_group
-- ----------------------------
DROP TABLE IF EXISTS `sec_group`;
CREATE TABLE `sec_group`  (
  `group_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '组织ID',
  `group_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织code',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '组织名称',
  `parent_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父组织ID',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '组织地址',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_sys_res` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内置资源：Y-是；N或其它-否',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`group_id`) USING BTREE,
  UNIQUE INDEX `uq_group_code`(`group_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_group
-- ----------------------------
INSERT INTO `sec_group` VALUES (1, 'G-20170101-001', '根组织', '-1', NULL, NULL, '根组织', 'Y', 'admin', '2017-12-13 23:38:08', 'admin', '2017-12-13 23:38:08', 'Y');

-- ----------------------------
-- Table structure for sec_group_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_group_role`;
CREATE TABLE `sec_group_role`  (
  `group_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`group_code`, `role_code`) USING BTREE,
  INDEX `fk_sec_group_has_sec_role_sec_role1_idx`(`role_code`) USING BTREE,
  INDEX `fk_sec_group_has_sec_role_sec_group1_idx`(`group_code`) USING BTREE,
  CONSTRAINT `sec_group_role_ibfk_1` FOREIGN KEY (`group_code`) REFERENCES `sec_group` (`group_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sec_group_role_ibfk_2` FOREIGN KEY (`role_code`) REFERENCES `sec_role` (`role_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组织角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sec_operation
-- ----------------------------
DROP TABLE IF EXISTS `sec_operation`;
CREATE TABLE `sec_operation`  (
  `operation_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '操作ID',
  `operation_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作code',
  `resource_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '操作名称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`operation_id`) USING BTREE,
  UNIQUE INDEX `uq_operation_code`(`operation_code`) USING BTREE,
  INDEX `fk_sec_operation_sec_resource1_idx`(`resource_code`) USING BTREE,
  INDEX `uq_resource_id_idx`(`resource_code`) USING BTREE,
  CONSTRAINT `sec_operation_ibfk_1` FOREIGN KEY (`resource_code`) REFERENCES `sec_resource` (`resource_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源操作权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_operation
-- ----------------------------
INSERT INTO `sec_operation` VALUES (1, 'O-20170101-001', 'M-20170101-001', '系统管理', '系统管理');
INSERT INTO `sec_operation` VALUES (2, 'O-20170101-002', 'M-20170101-002', '用户管理查询', '用户管理查询');
INSERT INTO `sec_operation` VALUES (3, 'O-20170101-003', 'M-20170101-003', '角色管理查询', '角色管理查询');
INSERT INTO `sec_operation` VALUES (4, 'O-20170101-004', 'M-20170101-004', '菜单管理查询', '菜单管理查询');
INSERT INTO `sec_operation` VALUES (5, 'O-20170101-005', 'M-20170101-005', '组织管理查询', '组织管理查询');
INSERT INTO `sec_operation` VALUES (6, 'O-20170101-006', 'M-20170101-006', '数据字典查询', '数据字典查询');
INSERT INTO `sec_operation` VALUES (11, 'O-20180331-001', 'M-20180331-001', '博客demo', '博客demo');
INSERT INTO `sec_operation` VALUES (12, 'O-20180331-002', 'M-20180331-002', '博客中心查询', '博客中心查询');
INSERT INTO `sec_operation` VALUES (13, 'O-20180331-003', 'M-20180331-003', '博客群组查询', '博客群组查询');
INSERT INTO `sec_operation` VALUES (14, 'O-20180714-001', 'M-20180714-003', '游客菜单管理查询', '游客菜单管理查询');

-- ----------------------------
-- Table structure for sec_permission
-- ----------------------------
DROP TABLE IF EXISTS `sec_permission`;
CREATE TABLE `sec_permission`  (
  `permission_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '权限ID',
  `permission_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问许可code',
  `operation_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '访问许可名称',
  `address` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '访问许可地址',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`permission_id`) USING BTREE,
  UNIQUE INDEX `uq_permission_code`(`permission_code`) USING BTREE,
  INDEX `fk_sec_permission_sec_operation1_idx`(`operation_code`) USING BTREE,
  CONSTRAINT `sec_permission_ibfk_1` FOREIGN KEY (`operation_code`) REFERENCES `sec_operation` (`operation_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源允许操作权限关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_permission
-- ----------------------------
INSERT INTO `sec_permission` VALUES (1, 'P-20170101-001', 'O-20170101-001', '系统管理一级菜单', NULL, '系统管理一级菜单');
INSERT INTO `sec_permission` VALUES (2, 'P-20170101-002', 'O-20170101-002', '用户管理权限', 'sys/user/to/list', '用户管理权限');
INSERT INTO `sec_permission` VALUES (3, 'P-20170101-003', 'O-20170101-003', '角色管理权限', 'sys/role/to/list', '角色管理权限');
INSERT INTO `sec_permission` VALUES (4, 'P-20170101-004', 'O-20170101-004', '菜单管理权限', 'sys/resource/to/list', '菜单管理权限');
INSERT INTO `sec_permission` VALUES (5, 'P-20170101-005', 'O-20170101-005', '组织管理权限', 'sys/group/to/list', '组织管理权限');
INSERT INTO `sec_permission` VALUES (6, 'P-20170101-006', 'O-20170101-006', '数据字典权限', 'sys/code/to/list', '数据字典权限');
INSERT INTO `sec_permission` VALUES (11, 'P-20180331-001', 'O-20180331-001', '博客demo权限', NULL, '博客demo权限');
INSERT INTO `sec_permission` VALUES (12, 'P-20180331-002', 'O-20180331-002', '博客中心权限', 'blog/center/to/list', '博客中心权限');
INSERT INTO `sec_permission` VALUES (13, 'P-20180331-003', 'O-20180331-003', '博客群组权限', 'blog/group/to/list', '博客群组权限');
INSERT INTO `sec_permission` VALUES (14, 'P-20180714-001', 'O-20180714-001', '游客菜单管理权限', 'sys/resourcetourist/to/list', '游客菜单管理权限');

-- ----------------------------
-- Table structure for sec_resource
-- ----------------------------
DROP TABLE IF EXISTS `sec_resource`;
CREATE TABLE `sec_resource`  (
  `resource_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源code',
  `parent_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父资源',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `url` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源（菜单）链接',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标名称',
  `new_window` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否打开新窗口',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '_blank' COMMENT '资源target,新窗口打开生效',
  `is_sys_res` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内置资源：Y-是；N或其它-否',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`resource_id`) USING BTREE,
  UNIQUE INDEX `uq_resource_code`(`resource_code`) USING BTREE,
  UNIQUE INDEX `uq_url`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1004 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_resource
-- ----------------------------
INSERT INTO `sec_resource` VALUES (1, 'M-20170101-001', '-1', '系统管理', NULL, '系统管理', 1, '', 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (2, 'M-20170101-002', 'M-20170101-001', '用户管理', 'sys/user/to/list', '用户管理', 1, NULL, 'N', NULL, 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (3, 'M-20170101-003', 'M-20170101-001', '角色管理', 'sys/role/to/list', '角色管理', 2, NULL, 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (4, 'M-20170101-004', 'M-20170101-001', '菜单管理', 'sys/resource/to/list', '菜单管理', 5, NULL, 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (5, 'M-20170101-005', 'M-20170101-001', '组织管理', 'sys/group/to/list', '组织管理', 3, NULL, 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (6, 'M-20170101-006', 'M-20170101-001', '数据字典', 'sys/code/to/list', '数据字典', 4, NULL, 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (13, 'M-20180331-001', '-1', '博客demo', NULL, '博客demo', 2, '', 'N', NULL, 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (14, 'M-20180331-002', 'M-20180331-001', '博客中心', 'blog/center/to/list', '博客中心', 1, NULL, 'N', NULL, 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (15, 'M-20180331-003', 'M-20180331-001', '博客群组', 'blog/group/to/list', '博客群组', 2, NULL, 'N', NULL, 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource` VALUES (16, 'M-20180714-003', 'M-20170101-001', '游客菜单管理', 'sys/resourcetourist/to/list', '游客菜单管理', 6, NULL, 'N', NULL, 'Y', 'admin', '2018-07-14 13:13:57', 'admin', '2018-07-14 13:14:17', 'Y');

-- ----------------------------
-- Table structure for sec_resource_tourist
-- ----------------------------
DROP TABLE IF EXISTS `sec_resource_tourist`;
CREATE TABLE `sec_resource_tourist`  (
  `resource_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `resource_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源code',
  `parent_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父资源',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源名称',
  `url` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '资源（菜单）链接',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int(2) DEFAULT NULL COMMENT '排序',
  `icon` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标名称',
  `new_window` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否打开新窗口',
  `target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '_blank' COMMENT '资源target,新窗口打开生效',
  `is_sys_res` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内置资源：Y-是；N或其它-否',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`resource_id`) USING BTREE,
  UNIQUE INDEX `uq_tourist_resource_code`(`resource_code`) USING BTREE,
  UNIQUE INDEX `uq_tourist_url`(`url`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1003 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '游客菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_resource_tourist
-- ----------------------------
INSERT INTO `sec_resource_tourist` VALUES (1, 'TM-20170101-001', '-1', '游客菜单', NULL, '游客菜单', 1, '', 'N', '', 'Y', 'admin', '2018-07-13 00:21:19', 'admin', '2018-07-13 00:21:19', 'Y');
INSERT INTO `sec_resource_tourist` VALUES (1000, 'TM-20180714-001', 'TM-20170101-001', '我的京东云首页', 'http://www.hanjieshao.com.cn/', '我的京东云首页', 1, NULL, 'N', NULL, NULL, 'admin', '2018-07-14 13:16:30', 'admin', '2018-07-14 13:16:30', 'Y');
INSERT INTO `sec_resource_tourist` VALUES (1001, 'TM-20180714-002', 'TM-20170101-001', '伟哥预留模板页', 'http://www.hanjieshao.com.cn/tongwei/index.html', '伟哥预留模板页', 2, NULL, 'N', NULL, NULL, 'admin', '2018-07-14 13:16:52', 'admin', '2018-07-14 13:16:52', 'Y');
INSERT INTO `sec_resource_tourist` VALUES (1002, 'TM-20180714-003', 'TM-20170101-001', '表白首页', 'http://www.hanjieshao.com.cn/biaobai/index.html', '表白首页', 3, NULL, 'N', NULL, NULL, 'admin', '2018-07-14 13:17:08', 'admin', '2018-07-14 13:17:08', 'Y');

-- ----------------------------
-- Table structure for sec_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_role`;
CREATE TABLE `sec_role`  (
  `role_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'code',
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`role_id`) USING BTREE,
  UNIQUE INDEX `uq_role_code`(`role_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sec_role_operation
-- ----------------------------
DROP TABLE IF EXISTS `sec_role_operation`;
CREATE TABLE `sec_role_operation`  (
  `role_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `operation_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`role_code`, `operation_code`) USING BTREE,
  INDEX `fk_sec_operation_has_sec_role_sec_role1_idx`(`role_code`) USING BTREE,
  INDEX `fk_sec_operation_has_sec_role_sec_operation1_idx`(`operation_code`) USING BTREE,
  CONSTRAINT `sec_role_operation_ibfk_1` FOREIGN KEY (`operation_code`) REFERENCES `sec_operation` (`operation_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sec_role_operation_ibfk_2` FOREIGN KEY (`role_code`) REFERENCES `sec_role` (`role_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色操作关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sec_user
-- ----------------------------
DROP TABLE IF EXISTS `sec_user`;
CREATE TABLE `sec_user`  (
  `user_id` int(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `user_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户code',
  `user_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `real_name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '姓名',
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '邮箱',
  `mobile` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机',
  `status` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `sex` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别：1-男；2-女；3-其他',
  `birthday` datetime(0) DEFAULT NULL COMMENT '生日',
  `group_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `is_admin` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内置管理员：Y-是；N或其它-否',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`user_id`) USING BTREE,
  UNIQUE INDEX `uq_user_code`(`user_code`) USING BTREE,
  INDEX `fk_sec_user_sec_group1_idx`(`group_code`) USING BTREE,
  CONSTRAINT `sec_user_ibfk_1` FOREIGN KEY (`group_code`) REFERENCES `sec_group` (`group_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB AUTO_INCREMENT = 1000 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sec_user
-- ----------------------------
INSERT INTO `sec_user` VALUES (1, 'U-20170101-001', 'admin', '管理员', '21232f297a57a5a743894a0e4a801fc3', '313627222@qq.com', '13388888888', 'user_state_normal', '1', '2017-12-13 23:06:08', 'G-20170101-001', 'Y', 'admin', '2018-07-13 00:04:58', 'admin', '2018-07-13 00:04:58', 'Y');

-- ----------------------------
-- Table structure for sec_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sec_user_role`;
CREATE TABLE `sec_user_role`  (
  `user_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `role_code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`user_code`, `role_code`) USING BTREE,
  INDEX `fk_sec_user_has_sec_role_sec_role2_idx`(`role_code`) USING BTREE,
  INDEX `fk_sec_user_has_sec_role_sec_user1_idx`(`user_code`) USING BTREE,
  CONSTRAINT `sec_user_role_ibfk_1` FOREIGN KEY (`role_code`) REFERENCES `sec_role` (`role_code`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sec_user_role_ibfk_2` FOREIGN KEY (`user_code`) REFERENCES `sec_user` (`user_code`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关系表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sn
-- ----------------------------
DROP TABLE IF EXISTS `sn`;
CREATE TABLE `sn`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '序号类型',
  `expression` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表达式格式',
  `sn` bigint(20) DEFAULT NULL COMMENT '当前序号值',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uq_type`(`type`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1001 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'code序号管理表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sn
-- ----------------------------
INSERT INTO `sn` VALUES (1, 'tb_code_sn', '\'C-\'T{yyyyMMdd}-SN{%03d}', 2, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-14 15:14:52', 'Y');
INSERT INTO `sn` VALUES (2, 'user_sn', '\'U-\'T{yyyyMMdd}-SN{%03d}', 1, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-13 00:08:11', 'Y');
INSERT INTO `sn` VALUES (3, 'group_sn', '\'G-\'T{yyyyMMdd}-SN{%03d}', 1, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-13 00:08:11', 'Y');
INSERT INTO `sn` VALUES (4, 'role_sn', '\'R-\'T{yyyyMMdd}-SN{%03d}', 5, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-13 00:08:11', 'Y');
INSERT INTO `sn` VALUES (5, 'operation_sn', '\'O-\'T{yyyyMMdd}-SN{%03d}', 2, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-14 15:09:54', 'Y');
INSERT INTO `sn` VALUES (6, 'permission_sn', '\'P-\'T{yyyyMMdd}-SN{%03d}', 2, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-14 15:09:54', 'Y');
INSERT INTO `sn` VALUES (7, 'menu_sn', '\'M-\'T{yyyyMMdd}-SN{%03d}', 4, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-14 15:09:54', 'Y');
INSERT INTO `sn` VALUES (8, 'menu_tour_sn', '\'TM-\'T{yyyyMMdd}-SN{%03d}', 3, 'admin', '2018-07-13 00:08:11', 'admin', '2018-07-14 13:17:08', 'Y');

-- ----------------------------
-- Table structure for tb_code
-- ----------------------------
DROP TABLE IF EXISTS `tb_code`;
CREATE TABLE `tb_code`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `code` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码',
  `parent_id` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父字典编码',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典类型',
  `code_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编码类别',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '值',
  `no` int(11) DEFAULT NULL COMMENT '序号',
  `is_leaf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否是叶子节点',
  `remark` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `is_sys_res` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内置资源：Y-是；N或其它-否',
  `creator` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  `create_time` datetime(0) DEFAULT NULL COMMENT '创建时间',
  `last_updater` varchar(36) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后更新人',
  `last_update_time` datetime(0) DEFAULT NULL COMMENT '最后更新时间',
  `enabled_flag` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '有效性.Y:有效;N:无效',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据字典表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_code
-- ----------------------------
INSERT INTO `tb_code` VALUES (1, 'C-20170101-001', '-1', 'system', 'root_code', '系统编码', '-', 1, NULL, '系统编码', 'Y', 'admin', '2018-07-13 00:00:00', 'admin', '2018-07-13 00:00:00', 'Y');
INSERT INTO `tb_code` VALUES (2, 'C-20170101-002', 'C-20170101-001', 'system', 'user_state', '管理用户状态', '-', 1, NULL, '管理用户状态', 'Y', 'admin', '2018-07-13 00:00:00', 'admin', '2018-07-13 00:00:00', 'Y');
INSERT INTO `tb_code` VALUES (3, 'C-20170101-003', 'C-20170101-002', 'system', 'user_state_normal', '启用', '1', 1, NULL, '正常', 'Y', 'admin', '2018-07-13 00:00:00', 'admin', '2018-07-13 00:00:00', 'Y');
INSERT INTO `tb_code` VALUES (4, 'C-20170101-004', 'C-20170101-002', 'system', 'user_state_disabled', '禁用', '2', 2, NULL, '禁用1122', 'Y', 'admin', '2018-07-13 00:00:00', 'admin', '2018-07-13 00:00:00', 'Y');
INSERT INTO `tb_code` VALUES (5, 'C-20180714-001', 'C-20170101-001', 'system', 'sys_url', '系统url配置', '2', 1, NULL, '系统url配置', 'Y', 'admin', '2018-07-14 15:12:55', 'admin', '2018-07-14 15:12:55', 'Y');
INSERT INTO `tb_code` VALUES (6, 'C-20180714-002', 'C-20180714-001', 'system', 'login_success_url', '登录成功首页', 'http://localhost:18091/sys-web-server/index', 1, NULL, '登录成功首页', 'Y', 'admin', '2018-07-14 15:14:52', 'admin', '2018-07-14 15:14:52', 'Y');

-- ----------------------------
-- View structure for sec_user_view
-- ----------------------------
DROP VIEW IF EXISTS `sec_user_view`;
CREATE ALGORITHM = UNDEFINED DEFINER = `root`@`%` SQL SECURITY DEFINER VIEW `sec_user_view` AS select distinct `u`.`user_id` AS `user_id`,`u`.`user_code` AS `user_code`,`u`.`user_name` AS `user_name`,`u`.`real_name` AS `real_name`,`u`.`email` AS `email`,`u`.`mobile` AS `mobile`,`u`.`status` AS `status`,`u`.`sex` AS `sex`,`u`.`birthday` AS `birthday`,`u`.`is_admin` AS `is_admin`,`u`.`enabled_flag` AS `enabled_flag`,`u`.`group_code` AS `group_code`,(select group_concat(`t2`.`name` separator ',') from (`sec_role` `t2` join `sec_user_role` `t3` on((`t2`.`role_code` = `t3`.`role_code`))) where (`t3`.`user_code` = `u`.`user_code`) group by `t3`.`user_code`) AS `roleNames` from (`sec_user` `u` left join `sec_user_role` `ur` on((`ur`.`user_code` = `u`.`user_code`)));

-- ----------------------------
-- Procedure structure for create_code_child_lst
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_code_child_lst`;
delimiter ;;
CREATE DEFINER=`root`@`*` PROCEDURE `create_code_child_lst`(
	IN root_code VARCHAR(36),
	IN n_depth INT
)
BEGIN
	DECLARE step INT DEFAULT 0;
	DECLARE tmp varchar(36);
	DECLARE cur1 CURSOR FOR SELECT `code` FROM tb_code WHERE parent_id = root_code and enabled_flag = 'Y';
	DECLARE CONTINUE HANDLER FOR NOT FOUND SET step = 1;
	INSERT INTO tb_code_tmp VALUES (NULL, root_code, n_depth);
	OPEN cur1;
		FETCH cur1 INTO tmp;
		WHILE step = 0 DO
			CALL create_code_child_lst(tmp, n_depth+1);
			FETCH cur1 INTO tmp;
		END WHILE;
	CLOSE cur1;
    END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for create_group_parent_lst
-- ----------------------------
DROP PROCEDURE IF EXISTS `create_group_parent_lst`;
delimiter ;;
CREATE DEFINER=`root`@`*` PROCEDURE `create_group_parent_lst`(
	IN group_code varchar(45),
	IN n_depth INT
)
BEGIN
	DECLARE step INT DEFAULT 0;
	DECLARE tmp VARCHAR(45);
	DECLARE cur1 CURSOR FOR SELECT parent_id FROM sec_group s WHERE s.enabled_flag = 'Y' and s.group_code = group_code;

	DECLARE CONTINUE HANDLER FOR NOT FOUND SET step = 1;
	INSERT INTO sec_group_tmp VALUES (NULL, group_code, n_depth); /** 插入当前数据 */
	OPEN cur1;
		FETCH cur1 INTO tmp;
		IF(step = 0) THEN
			CALL create_group_parent_lst(tmp, n_depth + 1);
		END IF;
	CLOSE cur1;
    END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for show_code_child_lst
-- ----------------------------
DROP PROCEDURE IF EXISTS `show_code_child_lst`;
delimiter ;;
CREATE DEFINER=`root`@`*` PROCEDURE `show_code_child_lst`(
	IN root_code VARCHAR(36)
)
BEGIN
	SET max_sp_recursion_depth=10;
	CREATE TEMPORARY TABLE IF NOT EXISTS tb_code_tmp
	  (sno INT PRIMARY KEY AUTO_INCREMENT,
	   `code` VARCHAR(36),
	   depth INT);
	DELETE FROM tb_code_tmp;

	CALL create_code_child_lst(root_code,0);

	SELECT ct.sno, ct.depth, c.id, c.code, c.parent_id, c.type,
		c.code_type, c.name, c.value, c.no, c.is_leaf,
		c.remark, c.is_sys_res, c.creator, c.create_time, c.last_updater, c.last_update_time, c.enabled_flag
	FROM tb_code_tmp ct,tb_code c WHERE ct.code=c.code ORDER BY ct.sno;
    END
;;
delimiter ;

-- ----------------------------
-- Procedure structure for show_group_fullname_lst
-- ----------------------------
DROP PROCEDURE IF EXISTS `show_group_fullname_lst`;
delimiter ;;
CREATE DEFINER=`root`@`*` PROCEDURE `show_group_fullname_lst`(
	IN group_code varchar(45)
)
BEGIN
	SET max_sp_recursion_depth = 5;
	CREATE TEMPORARY TABLE IF NOT EXISTS sec_group_tmp 
	  (sno INT PRIMARY KEY AUTO_INCREMENT,
	   group_code varchar(45),
	   depth INT);
	DELETE FROM sec_group_tmp;
	
	CALL create_group_parent_lst(group_code,0);
	
	SELECT GROUP_CONCAT(g.name ORDER BY gt.sno DESC SEPARATOR '/') AS full_name
	FROM sec_group_tmp gt
	LEFT JOIN sec_group g ON gt.group_code = g.group_code;
    END
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
