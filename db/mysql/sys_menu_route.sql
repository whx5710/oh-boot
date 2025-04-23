/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80405 (8.4.5)
 Source Host           : 127.0.0.1:3306
 Source Schema         : oh-sys3.0

 Target Server Type    : MySQL
 Target Server Version : 80405 (8.4.5)
 File Encoding         : 65001

 Date: 23/04/2025 22:10:36
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu_route
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu_route`;
CREATE TABLE `sys_menu_route` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint DEFAULT NULL COMMENT '上级ID，一级菜单为0',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单URL',
  `menu_path` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示路径',
  `authority` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
  `type` tinyint DEFAULT NULL COMMENT '类型   0：菜单   1：按钮   2：接口',
  `open_style` tinyint DEFAULT NULL COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
  `no_cache` tinyint(1) DEFAULT NULL COMMENT '菜单缓存',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu_route
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 0, '概览', 'BasicLayout', '/', '', 0, 0, 'lucide:layout-dashboard', 1, -1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '工作台', '/dashboard/workspace/index', '/workspace', NULL, 0, 0, 'carbon:workspace', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, '分析页', '/dashboard/analytics/index', '/analytics', NULL, 0, 0, 'lucide:area-chart', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 0, '系统管理', 'BasicLayout', '/sys', '', 0, 0, 'carbon:settings', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2024-06-29 11:26:07');
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 10, '菜单管理', '/sys/menu/index', '/sys/menu', '', 0, 0, 'carbon:menu', 1, 0, 1, '', 10000, '2023-06-04 21:03:59', 10000, '2024-06-30 16:52:44');
INSERT INTO `sys_menu_route` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 10, '参数管理', '/sys/params/index', '/sys/params', 'sys:params:all', 0, 0, 'carbon:delivery-parcel', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
