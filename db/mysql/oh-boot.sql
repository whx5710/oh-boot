/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.239.131
 Source Server Type    : MySQL
 Source Server Version : 80032
 Source Host           : 192.168.239.131:3306
 Source Schema         : oh-boot

 Target Server Type    : MySQL
 Target Server Version : 80032
 File Encoding         : 65001

 Date: 24/09/2023 20:50:27
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint(0) NOT NULL COMMENT '触发的时间',
  `sched_time` bigint(0) NOT NULL COMMENT '定时器制定的时间',
  `priority` int(0) NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.iris.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720028636F6D2E697269732E71756172747A2E656E746974792E5363686564756C654A6F62456E74697479000000000000000102000A4C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00066D6574686F6471007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C000673746174757371007E000A7872002B636F6D2E697269732E6672616D65776F726B2E636F6D6D6F6E2E656E746974792E42617365456E74697479CD4A8DB6F97A32E80200014C000776657273696F6E71007E000A7872002B636F6D2E697269732E6672616D65776F726B2E636F6D6D6F6E2E656E746974792E4D657461456E74697479F42121726FFA889D0200054C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000763726561746F7271007E000B4C000764656C6574656471007E000A4C000A75706461746554696D6571007E000E4C00077570646174657271007E000B78720029636F6D2E697269732E6672616D65776F726B2E636F6D6D6F6E2E656E746974792E4944456E746974794A13878033DDE8AA0200014C0002696471007E000B7870707372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000188AFD463C0787372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000002710737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0014000000007371007E0011770800000188AFD463C0787371007E001300000000000027107371007E00160000000E740008746573745461736B71007E001774000D30202A202A202A202A203F202A7371007E0013000000000000000174000673797374656D74000CE6B58BE8AF95E4BBBBE58AA174000372756E74000331323374000071007E00177800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` VALUES ('OhScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint(0) NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint(0) NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
INSERT INTO `QRTZ_SCHEDULER_STATE` VALUES ('OhScheduler', 'DESKTOP-1LF75BV1695559162053', 1695559819502, 15000);

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint(0) NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint(0) NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint(0) NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int(0) NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint(0) NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint(0) NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int(0) NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint(0) NOT NULL COMMENT '开始时间',
  `end_time` bigint(0) NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint(0) NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1695559200000, -1, 5, 'PAUSED', 'CRON', 1695559162000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for gen_base_class
-- ----------------------------
DROP TABLE IF EXISTS `gen_base_class`;
CREATE TABLE `gen_base_class`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基类包名',
  `code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基类编码',
  `fields` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基类字段，多个用英文逗号分隔',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '基类管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_base_class
-- ----------------------------
INSERT INTO `gen_base_class` VALUES (1, 'com.iris.framework.mybatis.entity', 'BaseEntity', 'id,creator,create_time,updater,update_time,version,deleted', '使用该基类，则需要表里有这些字段', '2023-06-15 05:10:26');

-- ----------------------------
-- Table structure for gen_datasource
-- ----------------------------
DROP TABLE IF EXISTS `gen_datasource`;
CREATE TABLE `gen_datasource`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `db_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库类型',
  `conn_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '连接名',
  `conn_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'URL',
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据源管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_field_type
-- ----------------------------
DROP TABLE IF EXISTS `gen_field_type`;
CREATE TABLE `gen_field_type`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `column_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `attr_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性类型',
  `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性包名',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `column_type`(`column_type`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字段类型管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_field_type
-- ----------------------------
INSERT INTO `gen_field_type` VALUES (1, 'datetime', 'Date', 'java.util.Date', '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (2, 'date', 'Date', 'java.util.Date', '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (3, 'tinyint', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (4, 'smallint', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (5, 'mediumint', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (6, 'int', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (7, 'integer', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (8, 'bigint', 'Long', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (9, 'float', 'Float', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (10, 'double', 'Double', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (11, 'decimal', 'BigDecimal', 'java.math.BigDecimal', '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (12, 'bit', 'Boolean', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (13, 'char', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (14, 'varchar', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (15, 'tinytext', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (16, 'text', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (17, 'mediumtext', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (18, 'longtext', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (19, 'timestamp', 'Date', 'java.util.Date', '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (20, 'NUMBER', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (21, 'BINARY_INTEGER', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (22, 'BINARY_FLOAT', 'Float', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (23, 'BINARY_DOUBLE', 'Double', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (24, 'VARCHAR2', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (25, 'NVARCHAR', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (26, 'NVARCHAR2', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (27, 'CLOB', 'String', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (28, 'int8', 'Long', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (29, 'int4', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (30, 'int2', 'Integer', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_field_type` VALUES (31, 'numeric', 'BigDecimal', 'java.math.BigDecimal', '2023-06-15 05:10:26');

-- ----------------------------
-- Table structure for gen_project_modify
-- ----------------------------
DROP TABLE IF EXISTS `gen_project_modify`;
CREATE TABLE `gen_project_modify`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `project_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名',
  `project_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目标识',
  `project_package` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目包名',
  `project_path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目路径',
  `modify_project_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更项目名',
  `modify_project_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更标识',
  `modify_project_package` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更包名',
  `exclusions` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '排除文件',
  `modify_suffix` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更文件',
  `modify_tmp_path` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变更临时路径',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目名变更' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of gen_project_modify
-- ----------------------------
INSERT INTO `gen_project_modify` VALUES (1, 'oh-boot', 'oh', 'com.iris', 'D:/oh/oh-boot', 'oh-boot', 'baba', 'com.iris', '.git,.idea,target,logs', 'java,xml,yml,txt', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_project_modify` VALUES (2, 'oh-cloud', 'oh', 'com.iris', 'D:/oh/oh-cloud', 'oh-cloud', 'baba', 'com.iris', '.git,.idea,target,logs', 'java,xml,yml,txt', NULL, '2023-06-15 05:10:26');

-- ----------------------------
-- Table structure for gen_table
-- ----------------------------
DROP TABLE IF EXISTS `gen_table`;
CREATE TABLE `gen_table`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `class_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类名',
  `table_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `author` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `email` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目包名',
  `version` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目版本号',
  `generator_type` tinyint(0) NULL DEFAULT NULL COMMENT '生成方式  0：zip压缩包   1：自定义目录',
  `backend_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端生成路径',
  `frontend_path` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端生成路径',
  `module_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `function_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '功能名',
  `form_layout` tinyint(0) NULL DEFAULT NULL COMMENT '表单布局  1：一列   2：两列',
  `datasource_id` bigint(0) NULL DEFAULT NULL COMMENT '数据源ID',
  `baseclass_id` bigint(0) NULL DEFAULT NULL COMMENT '基类ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `table_name`(`table_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for gen_table_field
-- ----------------------------
DROP TABLE IF EXISTS `gen_table_field`;
CREATE TABLE `gen_table_field`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `table_id` bigint(0) NULL DEFAULT NULL COMMENT '表ID',
  `field_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名称',
  `field_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `field_comment` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段说明',
  `attr_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `attr_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性类型',
  `package_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性包名',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `auto_fill` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '自动填充  DEFAULT、INSERT、UPDATE、INSERT_UPDATE',
  `primary_pk` tinyint(0) NULL DEFAULT NULL COMMENT '主键 0：否  1：是',
  `base_field` tinyint(0) NULL DEFAULT NULL COMMENT '基类字段 0：否  1：是',
  `form_item` tinyint(0) NULL DEFAULT NULL COMMENT '表单项 0：否  1：是',
  `form_required` tinyint(0) NULL DEFAULT NULL COMMENT '表单必填 0：否  1：是',
  `form_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单类型',
  `form_dict` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单字典类型',
  `form_validator` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单效验',
  `grid_item` tinyint(0) NULL DEFAULT NULL COMMENT '列表项 0：否  1：是',
  `grid_sort` tinyint(0) NULL DEFAULT NULL COMMENT '列表排序 0：否  1：是',
  `query_item` tinyint(0) NULL DEFAULT NULL COMMENT '查询项 0：否  1：是',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询方式',
  `query_form_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询表单类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 59 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_project
-- ----------------------------
DROP TABLE IF EXISTS `oh_project`;
CREATE TABLE `oh_project`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编码',
  `project_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `project_alias` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `director` bigint(0) NULL DEFAULT NULL COMMENT '负责人ID',
  `director_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态（1开始2暂停3关闭）',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of oh_project
-- ----------------------------
INSERT INTO `oh_project` VALUES (2, 'P1001', '垫脚石计划', '垫脚石', '2022-12-01', NULL, 10001, '王小费', 1, 0, NULL, '2022-12-04 14:09:33', NULL, '2022-12-04 14:09:37');

-- ----------------------------
-- Table structure for oh_project_log
-- ----------------------------
DROP TABLE IF EXISTS `oh_project_log`;
CREATE TABLE `oh_project_log`  (
  `id` bigint(0) NOT NULL,
  `project_id` int(0) NULL DEFAULT NULL COMMENT '项目ID',
  `task_id` bigint(0) NULL DEFAULT NULL COMMENT '任务ID',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目、任务操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_task
-- ----------------------------
DROP TABLE IF EXISTS `oh_task`;
CREATE TABLE `oh_task`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '序号',
  `project_id` int(0) NOT NULL COMMENT '所属项目',
  `task_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务标题',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `task_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '1任务2需求3设计4缺陷9其他',
  `parent_id` bigint(0) NOT NULL DEFAULT 0 COMMENT '父级任务ID',
  `has_child` tinyint(1) NULL DEFAULT NULL COMMENT '是否有子任务',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '计划开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '计划结束时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（1待办项2进行中3已完成）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_task_user
-- ----------------------------
DROP TABLE IF EXISTS `oh_task_user`;
CREATE TABLE `oh_task_user`  (
  `id` bigint(0) NOT NULL,
  `task_id` bigint(0) NOT NULL COMMENT '任务ID',
  `user_id` bigint(0) NOT NULL COMMENT '人员ID',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `person_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员类型1负责人2协作人',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`task_id`, `user_id`) USING BTREE,
  INDEX `idx_02`(`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for online_table
-- ----------------------------
DROP TABLE IF EXISTS `online_table`;
CREATE TABLE `online_table`  (
  `id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表名',
  `comments` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '表描述',
  `form_layout` tinyint(0) NULL DEFAULT NULL COMMENT '表单布局',
  `tree` tinyint(0) NULL DEFAULT NULL COMMENT '是否树  0：否   1：是',
  `tree_pid` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树父id',
  `tree_label` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树展示列',
  `table_type` tinyint(0) NULL DEFAULT NULL COMMENT '表类型  0：单表',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '是否更新  0：否   1：是',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Online表单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for online_table_column
-- ----------------------------
DROP TABLE IF EXISTS `online_table_column`;
CREATE TABLE `online_table_column`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `comments` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段描述',
  `length` int(0) NOT NULL COMMENT '字段长度',
  `point_length` int(0) NOT NULL COMMENT '小数点',
  `default_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '默认值',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `column_pk` tinyint(0) NULL DEFAULT NULL COMMENT '字段主键 0：否  1：是',
  `column_null` tinyint(0) NULL DEFAULT NULL COMMENT '字段为空 0：否  1：是',
  `form_item` tinyint(0) NULL DEFAULT NULL COMMENT '表单项 0：否  1：是',
  `form_required` tinyint(0) NULL DEFAULT NULL COMMENT '表单必填 0：否  1：是',
  `form_input` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单控件',
  `form_default` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单控件默认值',
  `form_dict` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单字典',
  `grid_item` tinyint(0) NULL DEFAULT NULL COMMENT '列表项 0：否  1：是',
  `grid_sort` tinyint(0) NULL DEFAULT NULL COMMENT '列表排序 0：否  1：是',
  `query_item` tinyint(0) NULL DEFAULT NULL COMMENT '查询项 0：否  1：是',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询方式',
  `query_input` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '查询控件',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Online表单字段' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `job_group` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分组',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '状态  0：暂停  1：正常',
  `concurrent` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '是否并发  0：禁止  1：允许',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 14, 0, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_id` bigint(0) NOT NULL COMMENT '任务id',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(0) UNSIGNED NOT NULL COMMENT '任务状态    0：失败    1：成功',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `times` bigint(0) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_job_id`(`job_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_id` bigint(0) NULL DEFAULT NULL COMMENT '平台ID',
  `platform` tinyint(0) NULL DEFAULT NULL COMMENT '平台类型',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `params` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：失败   1：成功',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform` tinyint(0) NULL DEFAULT NULL COMMENT '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云',
  `sign_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信签名',
  `template_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信模板',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信应用ID，如：腾讯云等',
  `sender_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '腾讯云国际短信、华为云等需要',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接入地址，如：华为云',
  `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'SecretKey',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：禁用   1：启用',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信平台' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件地址',
  `size` bigint(0) NULL DEFAULT NULL COMMENT '附件大小',
  `platform` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '存储平台',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '附件管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type_id` bigint(0) NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `label_class` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '停用', '0', 'danger', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (2, 1, '正常', '1', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (3, 2, '男', '0', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (4, 2, '女', '1', 'success', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (5, 2, '未知', '2', 'warning', '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (6, 3, '正常', '1', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (7, 3, '停用', '0', 'danger', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (8, 4, '全部数据', '0', '', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (10, 4, '本机构数据', '2', '', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (11, 4, '本人数据', '3', '', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (12, 4, '自定义数据', '4', '', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (13, 5, '禁用', '0', 'danger', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (14, 5, '启用', '1', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (15, 6, '失败', '0', 'danger', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (16, 6, '成功', '1', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (21, 8, '否', '0', 'primary', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (22, 8, '是', '1', 'danger', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (23, 9, '是', '1', 'danger', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (24, 9, '否', '0', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (25, 10, '其它', '0', 'info', '', 10, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (26, 10, '查询', '1', 'primary', '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (27, 10, '新增', '2', 'success', '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (28, 10, '修改', '3', 'warning', '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (29, 10, '删除', '4', 'danger', '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (30, 10, '导出', '5', 'info', '', 4, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (31, 10, '导入', '6', 'info', '', 5, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (32, 12, '开始', '1', NULL, '', 1, 0, 0, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO `sys_dict_data` VALUES (33, 12, '暂停', '2', NULL, '', 2, 0, 0, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO `sys_dict_data` VALUES (34, 12, '关闭', '3', NULL, '', 3, 0, 0, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO `sys_dict_data` VALUES (35, 13, '默认', 'default', '', '', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (36, 13, '系统', 'system', '', '', 1, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (37, 14, '暂停', '0', 'danger', '', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (38, 14, '正常', '1', 'primary', '', 1, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (39, 15, '阿里云', '0', '', '', 0, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (40, 15, '腾讯云', '1', '', '', 1, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (41, 15, '七牛云', '2', '', '', 2, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (42, 15, '华为云', '3', '', '', 3, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_source` tinyint(0) NULL DEFAULT 0 COMMENT '来源  0：字典数据  1：动态SQL',
  `dict_sql` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '动态SQL',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 0, 0, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO `sys_dict_type` VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'User Agent',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '登录状态  0：失败   1：成功',
  `operation` tinyint(0) UNSIGNED NULL DEFAULT NULL COMMENT '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
INSERT INTO `sys_log_login` VALUES (90, 'admin', '127.0.0.1', '内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 1, 1, '2023-09-24 20:39:48');
INSERT INTO `sys_log_login` VALUES (91, 'admin', '127.0.0.1', '内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 1, 0, '2023-09-24 20:39:55');
INSERT INTO `sys_log_login` VALUES (92, 'admin', '127.0.0.1', '内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 1, 1, '2023-09-24 20:44:54');
INSERT INTO `sys_log_login` VALUES (93, 'admin', '127.0.0.1', '内网IP', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/116.0.0.0 Safari/537.36', 1, 0, '2023-09-24 20:45:22');

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_operate`;
CREATE TABLE `sys_log_operate`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作名',
  `req_uri` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求URI',
  `req_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求方法',
  `req_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '请求参数',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'User Agent',
  `operate_type` tinyint(0) NULL DEFAULT NULL COMMENT '操作类型',
  `duration` int(0) NOT NULL COMMENT '执行时长',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '操作状态  0：失败   1：成功',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作人',
  `result_msg` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '返回消息',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级ID，一级菜单为0',
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `authority` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '类型   0：菜单   1：按钮   2：接口',
  `open_style` tinyint(0) NULL DEFAULT NULL COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 90 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', '', 0, 0, 'icon-Report', 1, 0, 0, 10000, '2023-09-24 20:13:47', 10000, '2023-09-24 20:13:47');
INSERT INTO `sys_menu` VALUES (2, 1, '系统设置', '', '', 0, 0, 'icon-setting', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:15:58');
INSERT INTO `sys_menu` VALUES (3, 2, '菜单管理', 'sys/menu/index', NULL, 0, 0, 'icon-menu', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (4, 3, '查看', '', 'sys:menu:list', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (5, 3, '新增', '', 'sys:menu:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (6, 3, '修改', '', 'sys:menu:update,sys:menu:info', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (7, 3, '删除', '', 'sys:menu:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (8, 2, '数据字典', 'sys/dict/type', '', 0, 0, 'icon-insertrowabove', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (9, 8, '查询', '', 'sys:dict:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (10, 8, '新增', '', 'sys:dict:save', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (11, 8, '修改', '', 'sys:dict:update,sys:dict:info', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (12, 8, '删除', '', 'sys:dict:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (13, 1, '权限管理', '', '', 0, 0, 'icon-safetycertificate', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:08');
INSERT INTO `sys_menu` VALUES (14, 13, '岗位管理', 'sys/post/index', '', 0, 0, 'icon-solution', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (15, 14, '查询', '', 'sys:post:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (16, 14, '新增', '', 'sys:post:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (17, 14, '修改', '', 'sys:post:update,sys:post:info', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (18, 14, '删除', '', 'sys:post:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (19, 13, '机构管理', 'sys/org/index', '', 0, 0, 'icon-cluster', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (20, 19, '查询', '', 'sys:org:list', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (21, 19, '新增', '', 'sys:org:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (22, 19, '修改', '', 'sys:org:update,sys:org:info', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (23, 19, '删除', '', 'sys:org:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (24, 13, '角色管理', 'sys/role/index', '', 0, 0, 'icon-team', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (25, 24, '查询', '', 'sys:role:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (26, 24, '新增', '', 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (27, 24, '修改', '', 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (28, 24, '删除', '', 'sys:role:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (29, 13, '用户管理', 'sys/user/index', '', 0, 0, 'icon-user', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (30, 29, '查询', '', 'sys:user:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (31, 29, '新增', '', 'sys:user:save,sys:role:list', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (32, 29, '修改', '', 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (33, 29, '删除', '', 'sys:user:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (34, 1, '应用管理', '', '', 0, 0, 'icon-appstore', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:16');
INSERT INTO `sys_menu` VALUES (35, 2, '附件管理', 'sys/attachment/index', NULL, 0, 0, 'icon-folder-fill', 3, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (36, 35, '查看', '', 'sys:attachment:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (37, 35, '上传', '', 'sys:attachment:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (38, 35, '删除', '', 'sys:attachment:delete', 1, 0, '', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (39, 1, '日志管理', '', '', 0, 0, 'icon-filedone', 4, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:24');
INSERT INTO `sys_menu` VALUES (40, 39, '登录日志', 'sys/log/login', 'sys:log:login', 0, 0, 'icon-solution', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (41, 29, '导入', '', 'sys:user:import', 1, 0, '', 5, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (42, 29, '导出', '', 'sys:user:export', 1, 0, '', 6, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (43, 2, '参数管理', 'sys/params/index', 'sys:params:all', 0, 0, 'icon-filedone', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (44, 2, '接口文档', '{{apiUrl}}/doc.html', NULL, 0, 1, 'icon-file-text-fill', 10, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (45, 0, '在线开发', '', '', 0, 0, 'icon-cloud', 2, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (46, 45, 'Online表单开发', 'online/table/index', 'online:table:all', 0, 0, 'icon-table', 0, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (47, 39, '操作日志', 'sys/log/operate', 'sys:operate:all', 0, 0, 'icon-file-text', 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (48, 0, '协同办公', '', '', 0, 0, 'icon-insertrowleft', 4, 0, 0, 10000, '2022-11-27 17:21:33', 10000, '2022-11-27 17:21:54');
INSERT INTO `sys_menu` VALUES (49, 48, '项目信息表', 'team/project/index', NULL, 0, 0, 'icon-menu', 1, 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:44:38');
INSERT INTO `sys_menu` VALUES (50, 49, '查看', '', 'team:project:page', 1, 0, '', 0, 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (51, 49, '新增', '', 'team:project:save', 1, 0, '', 1, 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (52, 49, '修改', '', 'team:project:update,team:project:info', 1, 0, '', 2, 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (53, 49, '删除', '', 'team:project:delete', 1, 0, '', 3, 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (54, 48, '任务表', 'team/task/index', NULL, 0, 0, 'icon-menu', 2, 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:44:30');
INSERT INTO `sys_menu` VALUES (55, 54, '查看', '', 'team:task:page', 1, 0, '', 0, 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (56, 54, '新增', '', 'team:task:save', 1, 0, '', 1, 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (57, 54, '修改', '', 'team:task:update,oneHill:task:info', 1, 0, '', 2, 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (58, 54, '删除', '', 'team:task:delete', 1, 0, '', 3, 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (59, 2, '定时任务', 'quartz/schedule/index', NULL, 0, 0, 'icon-reloadtime', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (60, 59, '查看', '', 'schedule:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (61, 59, '新增', '', 'schedule:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (62, 59, '修改', '', 'schedule:update,schedule:info', 1, 0, '', 2, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (63, 59, '删除', '', 'schedule:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (64, 59, '立即运行', '', 'schedule:run', 1, 0, '', 2, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (65, 59, '日志', '', 'schedule:log', 1, 0, '', 4, 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (66, 34, '系统监控', '', '', 0, 0, 'icon-Report', 10, 0, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (67, 66, '服务监控', 'monitor/server/index', 'monitor:server:all', 0, 0, 'icon-sever', 0, 0, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (68, 66, '缓存监控', 'monitor/cache/index', 'monitor:cache:all', 0, 0, 'icon-fund-fill', 2, 0, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (69, 66, '在线用户', 'monitor/user/index', 'monitor:user:all', 0, 0, 'icon-user', 3, 0, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (70, 34, '消息管理', '', '', 0, 0, 'icon-message', 2, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (71, 70, '短信日志', 'message/sms/log/index', 'sms:log', 0, 0, 'icon-detail', 1, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (72, 70, '短信平台', 'message/sms/platform/index', NULL, 0, 0, 'icon-whatsapp', 0, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (73, 72, '查看', '', 'sms:platform:page', 1, 0, '', 0, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (74, 72, '新增', '', 'sms:platform:save', 1, 0, '', 1, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (75, 72, '修改', '', 'sms:platform:update,sms:platform:info', 1, 0, '', 2, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (76, 72, '删除', '', 'sms:platform:delete', 1, 0, '', 3, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (77, 34, '代码生成器', '{{apiUrl}}/sysApi/oh-generator/index.html', '', 0, 1, 'icon-rocket', 2, 0, 0, 10000, '2023-06-12 13:47:50', 10000, '2023-06-24 21:35:28');
INSERT INTO `sys_menu` VALUES (78, 34, '流程设计', 'workflow/index', '', 0, 0, 'icon-expand', 0, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-07-16 11:54:26');
INSERT INTO `sys_menu` VALUES (80, 84, '查看', '', 'external:app:page,external:function:page,external:authority:page', 1, 0, '', 0, 0, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:00');
INSERT INTO `sys_menu` VALUES (81, 84, '新增', '', 'external:app:save,external:function:save', 1, 0, '', 1, 0, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:09');
INSERT INTO `sys_menu` VALUES (82, 84, '修改', '', 'external:app:update,external:app:info,external:function:update,external:function:info', 1, 0, '', 2, 0, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:21');
INSERT INTO `sys_menu` VALUES (83, 84, '删除', '', 'external:app:delete,external:function:delete', 1, 0, '', 3, 0, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:33');
INSERT INTO `sys_menu` VALUES (84, 34, '接口管理', 'external/app/index', '', 0, 0, 'icon-drag', 5, 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-08-09 21:49:46');
INSERT INTO `sys_menu` VALUES (85, 2, '版本信息', 'sys/info/index', '', 0, 0, 'icon-menu', 11, 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-09-16 16:02:27');
INSERT INTO `sys_menu` VALUES (86, 85, '查看', '', 'system:info:page', 1, 0, '', 0, 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (87, 85, '新增', '', 'system:info:save', 1, 0, '', 1, 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (88, 85, '修改', '', 'system:info:update,system:info:info', 1, 0, '', 2, 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (89, 85, '删除', '', 'system:info:delete', 1, 0, '', 3, 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '机构名称',
  `note` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_pid`(`pid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '机构管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1, 0, '长沙总部', '公司总部', 1, 0, 0, 10000, '2022-12-03 15:48:18', 10000, '2023-06-27 22:35:57');
INSERT INTO `sys_org` VALUES (2, 1, '研发部', NULL, 1, 0, 0, 10000, '2022-12-03 15:48:39', 10000, '2022-12-03 15:48:39');
INSERT INTO `sys_org` VALUES (3, 1, '销售部', NULL, 2, 0, 0, 10000, '2022-12-03 15:48:57', 10000, '2022-12-03 15:48:57');
INSERT INTO `sys_org` VALUES (4, 1, '设计部', NULL, 3, 0, 0, 10000, '2022-12-08 21:22:48', 10000, '2022-12-08 21:22:48');
INSERT INTO `sys_org` VALUES (5, 0, '合作伙伴', '重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司', 2, 0, 0, 10000, '2022-12-08 21:31:18', 10000, '2022-12-08 22:06:30');
INSERT INTO `sys_org` VALUES (6, 5, '阿里哒', '总部在杭州的大公司。', 1, 0, 0, 10000, '2022-12-08 21:31:48', 10000, '2023-06-27 22:52:48');
INSERT INTO `sys_org` VALUES (7, 5, '长沙银行', NULL, 2, 0, 0, 10000, '2022-12-08 21:32:25', 10000, '2022-12-08 21:32:25');
INSERT INTO `sys_org` VALUES (8, 1, '财务部', '', 4, 0, 0, 10000, '2022-12-08 22:29:10', 10000, '2022-12-08 22:29:10');

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS `sys_params`;
CREATE TABLE `sys_params`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `param_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数名称',
  `param_type` tinyint(0) NOT NULL COMMENT '系统参数   0：否   1：是',
  `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `sys_params_key`(`param_key`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '参数管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO `sys_params` VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'true', '是否开启验证码（true：开启，false：关闭）', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_params` VALUES (2, '验证码字符类型', 1, 'CAPTCHA_CHAR_TYPE', '3', '类型：1数字和字母混合、2纯数字、3纯字母、4纯大写字母、5纯小写字母、6数字和大写字母', 1, 0, 10000, '2023-09-08 22:15:36', NULL, NULL);
INSERT INTO `sys_params` VALUES (3, '验证码类型', 1, 'CAPTCHA_TYPE', '1', '1-png类型、2-gif类型、3-中文类型、4-中文gif类型', 2, 0, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `post_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位编码',
  `post_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '岗位名称',
  `sort` int(0) NULL DEFAULT NULL COMMENT '排序',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '岗位管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `data_scope` tinyint(0) NULL DEFAULT NULL COMMENT '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
  `org_id` bigint(0) NULL DEFAULT NULL COMMENT '机构ID',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_org_id`(`org_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '业务员', '', 3, 0, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `org_id` bigint(0) NULL DEFAULT NULL COMMENT '机构ID',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(0) NULL DEFAULT NULL COMMENT '菜单ID',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_menu_id`(`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 65 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色菜单关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 1, 12, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (2, 1, 28, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (3, 1, 29, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (4, 1, 30, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (5, 1, 31, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (6, 1, 32, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (7, 1, 40, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (8, 1, 41, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (9, 1, 18, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (10, 1, 19, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (11, 1, 20, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (12, 1, 21, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (13, 1, 22, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (14, 1, 13, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (15, 1, 14, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (16, 1, 15, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (17, 1, 16, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (18, 1, 17, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (19, 1, 23, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (20, 1, 24, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (21, 1, 25, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (22, 1, 26, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (23, 1, 27, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (24, 1, 1, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (25, 1, 69, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (26, 1, 70, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (27, 1, 71, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (28, 1, 74, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (29, 1, 72, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (30, 1, 73, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (31, 1, 75, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (32, 1, 2, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (33, 1, 3, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (34, 1, 4, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (35, 1, 5, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (36, 1, 6, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (37, 1, 7, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (38, 1, 8, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (39, 1, 10, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (40, 1, 9, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (41, 1, 11, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (42, 1, 42, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (43, 1, 34, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (44, 1, 35, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (45, 1, 37, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (46, 1, 36, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (47, 1, 43, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (48, 1, 33, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (49, 1, 88, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (50, 1, 80, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (51, 1, 82, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (52, 1, 83, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (53, 1, 84, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (54, 1, 85, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (55, 1, 86, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (56, 1, 81, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (57, 1, 87, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (58, 1, 76, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (59, 1, 77, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (60, 1, 78, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (61, 1, 79, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (62, 1, 38, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (63, 1, 39, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
INSERT INTO `sys_role_menu` VALUES (64, 1, 46, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint(0) NULL DEFAULT NULL COMMENT '性别   0：男   1：女   2：未知',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `org_id` bigint(0) NULL DEFAULT NULL COMMENT '机构ID',
  `super_admin` tinyint(0) NULL DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint(0) NULL DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10002 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户管理' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, 'admin', '{bcrypt}$2a$10$mW/yJPHjyueQ1g26WNBz0uxVPa0GQdJO1fFZmqdkqgMTGnyszlXxu', 'admin', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15');
INSERT INTO `sys_user` VALUES (10001, 'whx', '{bcrypt}$2a$10$xDGPuGsNWV4hIdXVyndwyeLEBxv1Z8ULLbDTWuMWBz.mDXTeHxfbi', '王小费', 'http://localhost:8080/upload/20230717/1671258609873_77092.jpg', 0, 'whx5710@qq.com', '15074825710', 1, 0, 1, 0, 0, 10000, '2023-07-17 21:15:47', 10001, '2023-07-17 21:24:54');

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `post_id` bigint(0) NULL DEFAULT NULL COMMENT '岗位ID',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE,
  INDEX `idx_post_id`(`post_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户岗位关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint(0) NULL DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `version` int(0) NULL DEFAULT NULL COMMENT '版本号',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_role_id`(`role_id`) USING BTREE,
  INDEX `idx_user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 10001, 0, 0, 10000, '2023-07-17 21:16:57', 10000, '2023-07-17 21:16:57');

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_version_info`;
CREATE TABLE `sys_version_info`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `version_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布内容',
  `release_time` datetime(0) NOT NULL COMMENT '发布时间',
  `is_curr_version` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否当前版本',
  `deleted` tinyint(0) NULL DEFAULT NULL COMMENT '删除标识  0：正常   1：已删除',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_01`(`is_curr_version`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '版本信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
INSERT INTO `sys_version_info` VALUES (1, '1.0.0', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis-Plus，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1, 0, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');

SET FOREIGN_KEY_CHECKS = 1;
