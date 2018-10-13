/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50560
Source Host           : localhost:3306
Source Database       : IMR

Target Server Type    : MYSQL
Target Server Version : 50560
File Encoding         : 65001

Date: 2018-08-06 10:02:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for data_file
-- ----------------------------
DROP TABLE IF EXISTS `data_file`;
CREATE TABLE `data_file` (
  `id` varchar(30) CHARACTER SET utf8mb4 NOT NULL,
  `meeting_id` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '对应的会议id',
  `upload_user` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  `file_path` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `file_size` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for metting
-- ----------------------------
DROP TABLE IF EXISTS `metting`;
CREATE TABLE `metting` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `topic` varchar(100) COLLATE utf8_bin DEFAULT NULL COMMENT '会议主题',
  `description` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '会议描述',
  `originator` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '会议发起者',
  `plan_start_time` datetime DEFAULT NULL COMMENT '计划开始时间',
  `plan_duration` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '计划持续时长',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '会议室地址',
  `password` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT '会议室密码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `menu_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `menu_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `parent_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `menu_url` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单链接地址',
  `sort_num` int(11) DEFAULT NULL COMMENT '排序字段',
  `menu_status` int(11) DEFAULT NULL COMMENT '菜单状态(1:启用;0:停用)',
  `menu_icon` varchar(20) COLLATE utf8_bin DEFAULT NULL COMMENT '菜单图标',
  `create_time` datetime DEFAULT NULL,
  `menu_type` varchar(30) COLLATE utf8_bin DEFAULT NULL COMMENT 'Mobile，PC，Other',
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `role_code` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `role_name` varchar(200) COLLATE utf8_bin DEFAULT NULL,
  `sort_num` int(11) DEFAULT NULL COMMENT '排序字段',
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='角色';

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `role_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `menu_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `menu_id` (`menu_id`),
  CONSTRAINT `menuid` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `roleid` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='角色菜单';

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `name` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `password` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `mobile` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `email` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `head_image` varchar(120) COLLATE utf8_bin DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  `quit_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`) USING BTREE,
  KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户';

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` varchar(30) COLLATE utf8_bin NOT NULL,
  `user_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `role_id` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `role_id` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `sys_users` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin ROW_FORMAT=DYNAMIC COMMENT='用户角色';
