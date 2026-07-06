/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : oh-sys

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 06/07/2026 16:55:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`  (
  `id` bigint NOT NULL COMMENT 'id',
  `platform_id` bigint NULL DEFAULT NULL COMMENT '平台ID',
  `platform` smallint NULL DEFAULT NULL COMMENT '平台类型',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `params` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` smallint NULL DEFAULT NULL COMMENT '状态  0：失败   1：成功',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform`  (
  `id` bigint NOT NULL COMMENT 'id',
  `platform` smallint NULL DEFAULT NULL COMMENT '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云',
  `sign_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信签名',
  `template_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信模板',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信应用ID，如：腾讯云等',
  `sender_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '腾讯云国际短信、华为云等需要',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接入地址，如：华为云',
  `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SecretKey',
  `status` smallint NULL DEFAULT NULL COMMENT '状态  0：禁用   1：启用',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信平台' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sms_platform
-- ----------------------------

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件地址',
  `size` bigint NULL DEFAULT NULL COMMENT '附件大小',
  `content_type` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `tmp_flag` smallint NOT NULL DEFAULT 0 COMMENT '临时文件标识，1为临时文件',
  `platform` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储平台',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附件管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept`  (
  `id` bigint NOT NULL COMMENT 'id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `note` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_dept_parent_id`(`parent_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `label_class` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_dict_data_type`(`dict_type_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_data` VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_data` VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_data` VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_data` VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_data` VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41.000000', 10000, '2023-06-12 13:47:41.000000');
INSERT INTO `sys_dict_data` VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41.000000', 10000, '2023-06-12 13:47:41.000000');
INSERT INTO `sys_dict_data` VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41.000000', 10000, '2023-06-12 13:47:41.000000');
INSERT INTO `sys_dict_data` VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41.000000', 10000, '2023-06-12 13:47:41.000000');
INSERT INTO `sys_dict_data` VALUES (32, 12, '设施损坏', 'facility', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01.000000', 10000, '2026-06-18 23:08:33.839675');
INSERT INTO `sys_dict_data` VALUES (33, 12, '环境卫生', 'hygiene', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16.000000', 10000, '2026-06-18 23:08:52.339996');
INSERT INTO `sys_dict_data` VALUES (34, 12, '安全隐患', 'safety', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29.000000', 10000, '2026-06-18 23:09:04.405446');
INSERT INTO `sys_dict_data` VALUES (61231154140807168, 12, '秩序问题', 'order', NULL, NULL, 4, 1, 10000, '2026-06-18 23:10:44.767269', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (61231247732506624, 12, '服务质量', 'service', NULL, NULL, 5, 1, 10000, '2026-06-18 23:11:07.081686', NULL, NULL);
INSERT INTO `sys_dict_data` VALUES (61231295530795008, 12, '其他问题', 'other', NULL, NULL, 1, 1, 10000, '2026-06-18 23:11:18.477354', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL COMMENT 'id',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_source` smallint NULL DEFAULT 0 COMMENT '来源  0：字典数据  1：动态SQL',
  `dict_sql` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '动态SQL',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_dict_type`(`dict_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_dict_type` VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_type` VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54.000000', 10000, '2023-06-12 13:45:54.000000');
INSERT INTO `sys_dict_type` VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41.000000', 10000, '2023-06-12 13:47:41.000000');
INSERT INTO `sys_dict_type` VALUES (12, 'event_type', '事件类型', 0, '', '事件类型', 12, 1, 10000, '2022-11-27 19:19:50.000000', 10000, '2022-11-27 19:19:50.000000');

-- ----------------------------
-- Table structure for sys_error_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_error_log`;
CREATE TABLE `sys_error_log`  (
  `id` bigint NOT NULL,
  `err_code` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误编码',
  `msg` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '错误提示',
  `stack_info` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '堆栈信息',
  `err_time` datetime(6) NOT NULL COMMENT '错误发生时间',
  `trace_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '链路跟踪ID',
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `score` decimal(5, 2) NULL DEFAULT NULL COMMENT '队列拥挤程度0-10',
  `queue_size` int NULL DEFAULT NULL COMMENT '队列大小',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `err_log_01`(`trace_id`) USING BTREE,
  INDEX `err_log_02`(`err_time`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统错误日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` bigint NOT NULL COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'User Agent',
  `status` smallint NULL DEFAULT NULL COMMENT '登录状态  0：失败   1：成功',
  `operation` smallint NULL DEFAULT NULL COMMENT '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_operate`;
CREATE TABLE `sys_log_operate`  (
  `id` bigint NOT NULL COMMENT 'id',
  `module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作名',
  `req_uri` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `req_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `req_params` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'User Agent',
  `operate_type` smallint NULL DEFAULT NULL COMMENT '操作类型',
  `duration` int NOT NULL COMMENT '执行时长',
  `status` smallint NULL DEFAULT NULL COMMENT '操作状态  0：失败   1：成功',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `result_msg` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回消息',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint NOT NULL COMMENT 'id',
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
  `open_style` smallint NULL DEFAULT 0 COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) NULL DEFAULT 0 COMMENT '菜单缓存',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (15, 6, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'mdi:account-group', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59.000000', 10000, '2025-06-22 14:19:26.000000');
INSERT INTO `sys_menu` VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 0, 0, 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 0, 0, 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (19, 6, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:container-services', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59.000000', 10000, '2025-06-22 14:19:49.000000');
INSERT INTO `sys_menu` VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 0, 0, 'sys:dept:list,sys:dept:info', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (23, 5, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-parcel', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59.000000', 10000, '2025-06-22 14:20:47.000000');
INSERT INTO `sys_menu` VALUES (27, 6, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-multiple', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59.000000', 10000, '2025-06-22 14:19:59.000000');
INSERT INTO `sys_menu` VALUES (36, 6, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:calls-all', 1, 0, 1, NULL, 10000, '2025-05-20 17:54:23.000000', 10000, '2025-06-22 14:20:26.000000');
INSERT INTO `sys_menu` VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:workspace', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (13, 11, '新增修改菜单', '新增修改菜单', '', NULL, 1, 0, 0, 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47.000000', 10000, '2025-06-06 10:11:39.000000');
INSERT INTO `sys_menu` VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:layout-dashboard', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47.000000', 10000, '2025-05-26 13:39:51.000000');
INSERT INTO `sys_menu` VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:area-chart', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (11, 5, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 0, 0, NULL, 'new', 'normal', 'default', 0, NULL, 'menu', 0, 'carbon:menu', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59.000000', 10000, '2025-06-22 14:20:37.000000');
INSERT INTO `sys_menu` VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 0, 0, '', NULL, 'dot', NULL, 0, NULL, 'menu', 0, 'carbon:settings', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47.000000', 10000, '2025-05-07 10:16:33.000000');
INSERT INTO `sys_menu` VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 0, 0, 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58.000000', 10000, '2025-05-08 14:23:29.000000');
INSERT INTO `sys_menu` VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 0, 0, 'sys:params:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58.000000', 10000, '2025-05-08 14:23:29.000000');
INSERT INTO `sys_menu` VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 0, 0, 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58.000000', 10000, '2025-05-08 14:23:29.000000');
INSERT INTO `sys_menu` VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 0, 0, 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58.000000', 10000, '2025-05-08 14:23:29.000000');
INSERT INTO `sys_menu` VALUES (30, 5, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:align-horizontal-left', 0, 0, 1, NULL, 10000, '2025-05-19 09:30:58.000000', 10000, '2025-06-22 14:20:57.000000');
INSERT INTO `sys_menu` VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 0, 0, 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:32:48.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 0, 0, 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:33:38.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 0, 0, 'sys:post:page,sys:post:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:55:22.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (39, 7, 'Attachment', '附件管理', '/system/attachment', '/system/attachment/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:file-storage', 0, 0, 1, NULL, 10000, '2025-06-08 11:26:11.000000', 10000, '2025-06-22 14:21:39.000000');
INSERT INTO `sys_menu` VALUES (40, 39, '查询附件', '查询附件', '', NULL, 1, 0, 0, 'sys:attachment:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:27:23.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (41, 39, '新增修改附件', '新增修改附件', '', NULL, 1, 0, 0, 'sys:attachment:save,sys:attachment:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:28:09.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (42, 10, 'Log', '日志管理', '/system/log', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:24:35.000000', 10000, '2025-06-11 17:29:17.000000');
INSERT INTO `sys_menu` VALUES (43, 42, 'LoginLog', '登录日志', '/system/loginLog', '/system/log/loginLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:change-catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:25:59.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (44, 43, '查询登录日志', '查询登录日志', '', NULL, 1, 0, 0, 'sys:log:login', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:35:32.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (45, 42, 'OpLog', '操作日志', '/system/opLog', '/system/log/opLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:border-top', 0, 0, 1, NULL, 10000, '2025-06-11 17:28:36.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (46, 45, '查询操作日志', '查询操作日志', '', NULL, 1, 0, 0, 'sys:operate:all', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:40:14.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (48, 47, 'SystemAppInterface', '客户端管理', '/system/app/list', '/system/app/list', 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:multiuser-device', 0, 0, 1, NULL, 10000, '2025-06-22 20:42:06.000000', 10000, '2025-06-22 20:46:05.000000');
INSERT INTO `sys_menu` VALUES (49, 48, '查询客户端', '查询客户端', '', NULL, 1, 0, 0, 'sys:app:page,sys:app:info,sys:function:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-22 20:46:30.000000', 10000, '2025-06-23 15:13:02.000000');
INSERT INTO `sys_menu` VALUES (50, 48, '新增修改客户端', '新增修改客户端', '', NULL, 1, 0, 0, 'sys:app:save,sys:app:update,sys:app:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-24 14:27:08.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (51, 47, 'SystemFunc', '接口管理', '/system/app/func', '/system/app/funcList', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:interface-usage-1', 0, 0, 1, NULL, 10000, '2025-06-25 10:35:16.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (5, 10, 'SystemSetting', '系统设置', '/system/setting', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:settings-check', 0, 0, 1, NULL, 10000, '2025-06-22 14:05:38.000000', 10000, '2025-06-22 14:18:50.000000');
INSERT INTO `sys_menu` VALUES (47, 7, 'SystemInterface', '接口管理', '/system/app/manage', '', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:ibm-cloud-vpc-client-vpn', 0, 0, 1, NULL, 10000, '2025-06-22 20:39:43.000000', 10000, '2025-06-22 20:43:16.000000');
INSERT INTO `sys_menu` VALUES (7, 10, 'SystemApp', '系统应用', '/system/app', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:app', 0, 0, 1, NULL, 10000, '2025-06-22 14:09:17.000000', 10000, '2025-06-22 14:19:12.000000');
INSERT INTO `sys_menu` VALUES (6, 10, 'SystemAuthority', '权限管理', '/system/authority', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-settings', 0, 0, 1, NULL, 10000, '2025-06-22 14:07:19.000000', 10000, '2025-06-22 14:19:02.000000');
INSERT INTO `sys_menu` VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 0, 0, 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:56:26.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (52, 51, '查询接口', '查询接口', '', NULL, 1, 0, 0, 'sys:function:page,sys:function:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 10:36:10.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (12, 11, '查询菜单', '查询菜单', '', NULL, 1, 0, 0, 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47.000000', 10000, '2025-06-06 10:12:33.000000');
INSERT INTO `sys_menu` VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 0, 0, 'sys:params:save,sys:params:update,sys:params:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58.000000', 10000, '2025-05-08 14:23:29.000000');
INSERT INTO `sys_menu` VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 0, 0, 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 0, NULL, 'menu', 0, NULL, 0, 0, 0, NULL, 10000, '2025-05-20 11:08:47.000000', 10000, '2026-06-13 21:34:24.165547');
INSERT INTO `sys_menu` VALUES (33, 6, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 0, NULL, 10000, '2025-05-20 11:07:19.000000', 10000, '2026-06-13 21:34:27.411261');
INSERT INTO `sys_menu` VALUES (53, 51, '新增修改接口', '新增修改接口', '', NULL, 1, 0, 0, 'sys:function:save,sys:function:update,sys:function:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 11:00:12.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (54, 47, 'SystemAppLog', '接口日志', '/system/app/log', '/system/app/log', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-06-25 17:27:26.000000', 10000, '2025-06-25 17:30:01.000000');
INSERT INTO `sys_menu` VALUES (55, 54, '查询日志', '查询日志', '', NULL, 1, 0, 0, 'sys:app:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:11.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (56, 54, '删除日志', '删除日志', '', NULL, 1, 0, 0, 'sys:app:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:37.000000', NULL, NULL);
INSERT INTO `sys_menu` VALUES (57, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 0, 0, 'sys:log:login:delete', NULL, NULL, NULL, 0, NULL, 'menu', 0, NULL, 0, 0, 0, NULL, 10000, '2025-06-25 19:45:26.000000', 10000, '2025-09-18 13:32:58.000000');
INSERT INTO `sys_menu` VALUES (33304128612466688, 7, 'Flow', '绘制流程', '/system/flow', '/system/flow/draw', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:flow-logs-vpc', 0, 0, 1, NULL, 10000, '2026-04-02 21:38:43.022556', 10000, '2026-04-06 18:10:42.981877');
INSERT INTO `sys_menu` VALUES (36768990499962880, 33304128612466688, '流程查询', '流程查询', '', NULL, 1, 0, 0, 'flow:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-12 11:06:50.469297', NULL, NULL);
INSERT INTO `sys_menu` VALUES (36769889070874624, 7, 'FlowManage', '流程管理', '/system/flow-manage', '/system/flow/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:workflow-automation', 0, 0, 1, NULL, 10000, '2026-04-12 11:10:24.706571', 10000, '2026-04-12 11:15:00.574170');
INSERT INTO `sys_menu` VALUES (36770623787106304, 36769889070874624, '流程管理新增修改', '流程管理新增修改', '', NULL, 1, 0, 0, 'flow:task:saveOrUpdate,flow:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-12 11:13:19.876445', 10000, '2026-04-12 11:17:29.143403');
INSERT INTO `sys_menu` VALUES (34701521023139840, 33304128612466688, '流程新增修改', '流程新增修改', '', NULL, 1, 0, 0, 'flow:saveOrUpdate,flow:update', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-06 18:11:27.335070', 10000, '2026-04-15 21:21:57.624954');
INSERT INTO `sys_menu` VALUES (38012448816496640, 36769889070874624, '环节查看', '环节查看', '', NULL, 1, 0, 0, 'flow:node:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-15 21:27:54.035171', NULL, NULL);
INSERT INTO `sys_menu` VALUES (38012908344442880, 36769889070874624, '环节修改', '环节修改', '', NULL, 1, 0, 0, 'flow:node:update', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-15 21:29:43.593548', NULL, NULL);
INSERT INTO `sys_menu` VALUES (43462997489745920, 42, 'ErrorLog', '错误日志', '/system/errLog', '/system/log/errorLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:ibm-knowledge-catalog-premium', 0, 0, 1, NULL, 10000, '2026-04-30 22:26:26.104883', NULL, NULL);
INSERT INTO `sys_menu` VALUES (43463159524098048, 43462997489745920, '查询错误日志', '查询错误日志', '', NULL, 1, 0, 0, 'sys:error:log', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-30 22:27:04.737104', NULL, NULL);
INSERT INTO `sys_menu` VALUES (45101480209809408, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 0, 0, 'sys:log:login:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-05-05 10:57:10.827503', NULL, NULL);
INSERT INTO `sys_menu` VALUES (45108924046639104, 43462997489745920, '删除错误日志', '删除错误日志', '', NULL, 1, 0, 0, 'sys:error:log:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-05-05 11:26:45.576264', NULL, NULL);
INSERT INTO `sys_menu` VALUES (51801209756975104, 6, 'LockUser', '用户锁定', '/system/lockUser', '/system/user/lock', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-access-locked', 0, 1, 1, NULL, 10000, '2026-05-23 22:39:30.825550', 10000, '2026-05-24 10:59:42.103162');
INSERT INTO `sys_menu` VALUES (53949658837286912, 53949269693956096, '查询在线用户', '查询在线用户', '', NULL, 1, 0, 0, 'monitor:user:all,monitor:user:tokens', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 1, 1, NULL, 10000, '2026-05-29 20:56:41.003309', 10000, '2026-05-29 20:59:55.515648');
INSERT INTO `sys_menu` VALUES (53951928605868032, 53949269693956096, '下线操作', '下线操作', '', NULL, 1, 0, 0, 'monitor:user:logout', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-05-29 21:05:42.157191', NULL, NULL);
INSERT INTO `sys_menu` VALUES (53949269693956096, 6, 'OnlineUser', '在线用户', '/system/online-user', '/system/user/monitor', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-online', 0, 1, 1, NULL, 10000, '2026-05-29 20:55:08.223114', 10000, '2026-05-29 21:07:06.986522');
INSERT INTO `sys_menu` VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 0, 0, 'tenant:member:save,tenant:member:update,sys:user:bindTenantUser,sys:user:unBindTenantUser', NULL, NULL, NULL, 0, NULL, 'menu', 0, NULL, 0, 0, 0, NULL, 10000, '2025-05-20 11:09:21.000000', 10000, '2026-06-13 21:34:20.350493');

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message`  (
  `id` int NOT NULL COMMENT 'id',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'success' COMMENT '消息类别success普通消息warning警告error错误',
  `state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态1未读2已读',
  `from_id` bigint NULL DEFAULT NULL COMMENT '发送用户ID',
  `from_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `to_id` bigint NOT NULL COMMENT '接收用户ID',
  `to_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统消息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_message
-- ----------------------------

-- ----------------------------
-- Table structure for sys_open_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_open_user`;
CREATE TABLE `sys_open_user`  (
  `id` bigint NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密钥',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` smallint NULL DEFAULT NULL COMMENT '性别   0：男   1：女   2：未知',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `status` smallint NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `open_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '外部用户ID',
  `user_type` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '用户类型，1微信小程序用户；2支付宝',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_third_party_01`(`open_id`) USING BTREE,
  INDEX `idx_third_party_02`(`user_type`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '第三方平台用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_open_user
-- ----------------------------

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS `sys_params`;
CREATE TABLE `sys_params`  (
  `id` bigint NOT NULL COMMENT 'id',
  `param_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `param_type` smallint NOT NULL COMMENT '系统参数   0：否   1：是',
  `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_params_key`(`param_key`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO `sys_params` VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-04 21:03:59.000000');
INSERT INTO `sys_params` VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06.000000', 10000, '2023-09-09 16:25:06.000000');
INSERT INTO `sys_params` VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07.000000', 10000, '2023-10-04 12:15:07.000000');
INSERT INTO `sys_params` VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55.000000', 10000, '2023-10-06 15:07:55.000000');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint NOT NULL COMMENT 'id',
  `post_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `status` smallint NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_post_code`(`post_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint NOT NULL COMMENT 'id',
  `code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色编码',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_scope` smallint NULL DEFAULT NULL COMMENT '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `is_system` smallint NOT NULL DEFAULT 0 COMMENT '是否系统内置角色1是',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  INDEX `idx_role_code`(`code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `db_status` smallint NOT NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint NOT NULL COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` smallint NULL DEFAULT NULL COMMENT '性别   0：男   1：女   2：未知',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `super_admin` smallint NULL DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` smallint NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间',
  `pwd_modify_time` datetime(6) NULL DEFAULT NULL COMMENT '密码修改时间',
  `user_key` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户密钥，用于第三方系统登录',
  `open_id` bigint NULL DEFAULT NULL COMMENT '外部用户ID，对应sys_open_user.id',
  INDEX `idx_user_name`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, NULL, 1, 10000, '2023-06-04 21:03:59.000000', 10000, '2023-06-24 21:14:15.000000', '2023-06-24 21:14:15.000000', '123', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  `db_status` smallint NOT NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint NOT NULL COMMENT 'id',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `db_status` smallint NOT NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关系' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_version_info`;
CREATE TABLE `sys_version_info`  (
  `id` bigint NOT NULL COMMENT 'id',
  `version_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布内容',
  `release_time` datetime(6) NOT NULL COMMENT '发布时间',
  `is_curr_version` tinyint(1) NULL DEFAULT NULL COMMENT '是否当前版本',
  `cover_picture` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面图片',
  `re_login` smallint NULL DEFAULT NULL COMMENT '是否需要重新登录',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` smallint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(6) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(6) NULL DEFAULT NULL COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '版本信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
INSERT INTO `sys_version_info` VALUES (1, '1.0.0', '初始版本', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27.000000', 1, '', 0, NULL, 1, 10000, '2023-09-24 20:42:39.000000', 10000, '2023-09-24 20:42:39.000000');

SET FOREIGN_KEY_CHECKS = 1;
