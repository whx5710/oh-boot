/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : oh-sys3.0

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 20/05/2025 17:58:41
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级ID，一级菜单为0',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '显示路径',
  `menu_path` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面组件路径',
  `status` int NULL DEFAULT 1 COMMENT '状态 0停用 1有效',
  `hide_in_menu` tinyint(1) NULL DEFAULT 0 COMMENT '菜单是否隐藏',
  `hide_in_tab` tinyint(1) NULL DEFAULT 0 COMMENT '标签是否隐藏',
  `authority` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
  `badge` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用于配置页面的徽标，会在菜单显示',
  `badge_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用于配置页面的徽标类型，dot 为小红点，normal 为文本',
  `badge_variants` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用于配置页面的徽标颜色',
  `affix_tab` tinyint(1) NULL DEFAULT 0 COMMENT '用于配置页面是否固定标签页，固定后页面不可关闭',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用于配置外链跳转路径',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'menu' COMMENT '类型:  catalog | menu | action',
  `open_style` tinyint NULL DEFAULT 0 COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '菜单缓存',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`parent_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:layout-dashboard', 1, -1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:workspace', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:area-chart', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 0, 0, '', NULL, 'dot', NULL, 0, NULL, 'menu', 0, 'carbon:settings', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-07 10:16:33');
INSERT INTO `sys_menu` VALUES (11, 10, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 0, 0, NULL, 'new', 'normal', 'default', 0, NULL, 'menu', 0, 'carbon:menu', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-05-19 09:53:17');
INSERT INTO `sys_menu` VALUES (12, 11, '查询菜单', '查询菜单', NULL, NULL, 1, 0, 0, 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 11, '新增修改菜单', '新增修改菜单', NULL, NULL, 1, 0, 0, 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (15, 10, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'mdi:account-group', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-05-08 16:46:19');
INSERT INTO `sys_menu` VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 0, 0, 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 0, 0, 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 10, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:container-services', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-05-08 16:46:25');
INSERT INTO `sys_menu` VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 0, 0, 'sys:dept:list,sys:dept:info', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 0, 0, 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` VALUES (23, 10, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-parcel', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-05-12 11:32:45');
INSERT INTO `sys_menu` VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 0, 0, 'sys:params:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 0, 0, 'sys:params:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` VALUES (27, 10, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-multiple', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-05-13 17:01:07');
INSERT INTO `sys_menu` VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 0, 0, 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 0, 0, 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` VALUES (30, 10, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:align-horizontal-left', 0, 0, 1, NULL, 10000, '2025-05-19 09:30:58', 10000, '2025-05-19 09:31:43');
INSERT INTO `sys_menu` VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 0, 0, 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:32:48', NULL, NULL);
INSERT INTO `sys_menu` VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 0, 0, 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:33:38', NULL, NULL);
INSERT INTO `sys_menu` VALUES (33, 10, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-05-20 11:07:19', 10000, '2025-05-20 11:08:02');
INSERT INTO `sys_menu` VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 0, 0, 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:08:47', NULL, NULL);
INSERT INTO `sys_menu` VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 0, 0, 'tenant:member:save,tenant:member:update', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:09:21', NULL, NULL);
INSERT INTO `sys_menu` VALUES (36, 10, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:calls-all', 1, 0, 1, NULL, 10000, '2025-05-20 17:54:23', NULL, NULL);
INSERT INTO `sys_menu` VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 0, 0, 'sys:post:page,sys:post:info', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:55:22', NULL, NULL);
INSERT INTO `sys_menu` VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 0, 0, 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:56:26', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
