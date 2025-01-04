/*
 Navicat MySQL Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80040 (8.0.40-0ubuntu0.24.04.1)
 Source Host           : localhost:3306
 Source Schema         : oh-sys

 Target Server Type    : MySQL
 Target Server Version : 80040 (8.0.40-0ubuntu0.24.04.1)
 File Encoding         : 65001

 Date: 04/01/2025 22:45:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='日历信息表';

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_CRON_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`, `cron_expression`, `time_zone_id`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`,`entry_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='已触发的触发器表';

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='任务详细信息表';

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`, `description`, `job_class_name`, `is_durable`, `is_nonconcurrent`, `is_update_data`, `requests_recovery`, `job_data`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.iris.sys.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002C636F6D2E697269732E7379732E71756172747A2E656E746974792E5363686564756C654A6F62456E746974791D0FDFE48785A8D702000A4C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00066D6574686F6471007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C000673746174757371007E000A7872001F636F6D2E697269732E636F72652E656E746974792E42617365456E74697479D68F7A13C5FF2C2B0200054C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000763726561746F7271007E000B4C0008646253746174757371007E000A4C000A75706461746554696D6571007E000D4C00077570646174657271007E000B7872001D636F6D2E697269732E636F72652E656E746974792E4944456E74697479B53BABF913E644280200014C0002696471007E000B7870707372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770A05000007E7060C1526DF787372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000002710737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017371007E0010770A05000007E7060C1526DF787371007E00120000000000002710740008746573745461736B7371007E00150000000074000D30202A202A202A202A203F202A7371007E0012000000000000000174000673797374656D74000CE6B58BE8AF95E4BBBBE58AA174000372756E74000331323374000071007E001A7800);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_LOCKS` (`sched_name`, `lock_name`) VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO `QRTZ_LOCKS` (`sched_name`, `lock_name`) VALUES ('OhScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='调度器状态表';

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_SCHEDULER_STATE` (`sched_name`, `instance_name`, `last_checkin_time`, `checkin_interval`) VALUES ('OhScheduler', 'DESKTOP-IHJIP1I1729825736858', 1729825768675, 15000);
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13,4) DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  KEY `sched_name` (`sched_name`,`job_name`,`job_group`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
BEGIN;
INSERT INTO `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`, `job_name`, `job_group`, `description`, `next_fire_time`, `prev_fire_time`, `priority`, `trigger_state`, `trigger_type`, `start_time`, `end_time`, `calendar_name`, `misfire_instr`, `job_data`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1729825740000, -1, 5, 'PAUSED', 'CRON', 1729825737000, 0, NULL, 2, '');
COMMIT;

-- ----------------------------
-- Table structure for online_table_column
-- ----------------------------
DROP TABLE IF EXISTS `online_table_column`;
CREATE TABLE `online_table_column` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段名称',
  `comments` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字段描述',
  `length` int NOT NULL COMMENT '字段长度',
  `point_length` int NOT NULL COMMENT '小数点',
  `default_value` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '默认值',
  `column_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字段类型',
  `column_pk` tinyint DEFAULT NULL COMMENT '字段主键 0：否  1：是',
  `column_null` tinyint DEFAULT NULL COMMENT '字段为空 0：否  1：是',
  `form_item` tinyint DEFAULT NULL COMMENT '表单项 0：否  1：是',
  `form_required` tinyint DEFAULT NULL COMMENT '表单必填 0：否  1：是',
  `form_input` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单控件',
  `form_default` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单控件默认值',
  `form_dict` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表单字典',
  `grid_item` tinyint DEFAULT NULL COMMENT '列表项 0：否  1：是',
  `grid_sort` tinyint DEFAULT NULL COMMENT '列表排序 0：否  1：是',
  `query_item` tinyint DEFAULT NULL COMMENT '查询项 0：否  1：是',
  `query_type` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '查询方式',
  `query_input` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '查询控件',
  `sort` int DEFAULT NULL COMMENT '排序',
  `table_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '表id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Online表单字段';

-- ----------------------------
-- Records of online_table_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `job_group` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '分组',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint unsigned DEFAULT NULL COMMENT '状态  0：暂停  1：正常',
  `concurrent` tinyint unsigned DEFAULT NULL COMMENT '是否并发  0：禁止  1：允许',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
BEGIN;
INSERT INTO `schedule_job` (`id`, `job_name`, `job_group`, `bean_name`, `method`, `params`, `cron_expression`, `status`, `concurrent`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 1, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');
COMMIT;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `job_id` bigint NOT NULL COMMENT '任务id',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务组名',
  `bean_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'spring bean名称',
  `method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '执行方法',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
  `status` tinyint unsigned NOT NULL COMMENT '任务状态    0：失败    1：成功',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '异常信息',
  `times` bigint NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_job_id` (`job_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS `sms_log`;
CREATE TABLE `sms_log` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform_id` bigint DEFAULT NULL COMMENT '平台ID',
  `platform` tinyint DEFAULT NULL COMMENT '平台类型',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号',
  `params` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：失败   1：成功',
  `error` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信日志';

-- ----------------------------
-- Records of sms_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS `sms_platform`;
CREATE TABLE `sms_platform` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `platform` tinyint DEFAULT NULL COMMENT '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云',
  `sign_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信签名',
  `template_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信模板',
  `app_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '短信应用ID，如：腾讯云等',
  `sender_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '腾讯云国际短信、华为云等需要',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接入地址，如：华为云',
  `access_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'AccessKey',
  `secret_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'SecretKey',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：禁用   1：启用',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='短信平台';

-- ----------------------------
-- Records of sms_platform
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS `sys_attachment`;
CREATE TABLE `sys_attachment` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '附件地址',
  `size` bigint DEFAULT NULL COMMENT '附件大小',
  `platform` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '存储平台',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型ID',
  `dict_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典值',
  `label_class` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签样式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 12, '开始', '1', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, 12, '暂停', '2', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, 12, '关闭', '3', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` (`id`, `dict_type_id`, `dict_label`, `dict_value`, `label_class`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `dict_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_source` tinyint DEFAULT '0' COMMENT '来源  0：字典数据  1：动态SQL',
  `dict_sql` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '动态SQL',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 1, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` (`id`, `dict_type`, `dict_name`, `dict_source`, `dict_sql`, `remark`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_login`;
CREATE TABLE `sys_log_login` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'User Agent',
  `status` tinyint DEFAULT NULL COMMENT '登录状态  0：失败   1：成功',
  `operation` tinyint unsigned DEFAULT NULL COMMENT '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS `sys_log_operate`;
CREATE TABLE `sys_log_operate` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `module` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '模块名',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作名',
  `req_uri` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求URI',
  `req_method` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '请求方法',
  `req_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '请求参数',
  `ip` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作IP',
  `address` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录地点',
  `user_agent` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'User Agent',
  `operate_type` tinyint DEFAULT NULL COMMENT '操作类型',
  `duration` int NOT NULL COMMENT '执行时长',
  `status` tinyint DEFAULT NULL COMMENT '操作状态  0：失败   1：成功',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作人',
  `result_msg` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '返回消息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志';

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
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
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 0, '系统管理', 'sys', NULL, '', 0, 0, 'icon-Report', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2024-06-29 11:26:07');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '系统设置', 'sys/set', NULL, '', 0, 0, 'icon-setting', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:26:19');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 2, '菜单管理', 'sys/menu/index', NULL, '', 0, 0, 'icon-menu', 1, 0, 1, '', 10000, '2023-06-04 21:03:59', 10000, '2024-06-30 16:52:44');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 3, '查看', '', NULL, 'sys:menu:list', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 3, '新增', '', NULL, 'sys:menu:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 3, '修改', '', NULL, 'sys:menu:update,sys:menu:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 3, '删除', '', NULL, 'sys:menu:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 2, '数据字典', 'sys/dict/type', NULL, '', 0, 0, 'icon-insertrowabove', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (9, 8, '查询', '', NULL, 'sys:dict:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 8, '新增', '', NULL, 'sys:dict:save', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 8, '修改', '', NULL, 'sys:dict:update,sys:dict:info', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 8, '删除', '', NULL, 'sys:dict:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 1, '权限管理', 'sys/authority', NULL, '', 0, 0, 'icon-safetycertificate', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:26:56');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (14, 13, '岗位管理', 'sys/post/index', NULL, '', 0, 0, 'icon-solution', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 14, '查询', '', NULL, 'sys:post:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 14, '新增', '', NULL, 'sys:post:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 14, '修改', '', NULL, 'sys:post:update,sys:post:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (18, 14, '删除', '', NULL, 'sys:post:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 13, '机构管理', 'sys/org/index', NULL, '', 0, 0, 'icon-cluster', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 19, '查询', '', NULL, 'sys:org:list', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 19, '新增', '', NULL, 'sys:org:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (22, 19, '修改', '', NULL, 'sys:org:update,sys:org:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 19, '删除', '', NULL, 'sys:org:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 13, '角色管理', 'sys/role/index', NULL, '', 0, 0, 'icon-team', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 24, '查询', '', NULL, 'sys:role:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (26, 24, '新增', '', NULL, 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 24, '修改', '', NULL, 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 24, '删除', '', NULL, 'sys:role:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 13, '用户管理', 'sys/user/index', NULL, '', 0, 0, 'icon-user', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 29, '查询', '', NULL, 'sys:user:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 29, '新增', '', NULL, 'sys:user:save,sys:role:list', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 29, '修改', '', NULL, 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, 29, '删除', '', NULL, 'sys:user:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, 1, '应用管理', 'sys/app', NULL, '', 0, 0, 'icon-appstore', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:27:30');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 2, '附件管理', 'sys/attachment/index', NULL, NULL, 0, 0, 'icon-folder-fill', 1, 3, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 35, '查看', '', NULL, 'sys:attachment:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 35, '上传', '', NULL, 'sys:attachment:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 35, '删除', '', NULL, 'sys:attachment:delete', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 1, '日志管理', 'sys/log', NULL, '', 0, 0, 'icon-filedone', 1, 4, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:28:36');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 39, '登录日志', 'sys/log/login', NULL, 'sys:log:login', 0, 0, 'icon-solution', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 29, '导入', '', NULL, 'sys:user:import', 1, 0, '', 1, 5, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 29, '导出', '', NULL, 'sys:user:export', 1, 0, '', 1, 6, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (43, 2, '参数管理', 'sys/params/index', NULL, 'sys:params:all', 0, 0, 'icon-filedone', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (44, 2, '接口文档', 'http://localhost:8080/doc.html', NULL, '', 0, 1, 'icon-file-text-fill', 1, 10, 1, 'http://localhost:8080/doc.html', 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:45:34');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (45, 0, '在线开发', 'online', NULL, '', 0, 0, 'icon-cloud', 1, 2, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2024-06-29 11:28:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (46, 45, 'Online表单开发', 'online/table/index', NULL, 'online:table:all', 0, 0, 'icon-table', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (47, 39, '操作日志', 'sys/log/operate', NULL, 'sys:operate:all', 0, 0, 'icon-file-text', 1, 1, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (48, 69, '踢出', '', NULL, 'monitor:user:user', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-09-25 21:16:17', 10000, '2023-09-25 21:16:17');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (49, 34, '流程管理', 'sys/flow', NULL, '', 0, 0, 'icon-switchuser', 1, 1, 1, NULL, 10000, '2023-12-23 16:02:38', 10000, '2024-06-29 11:27:43');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (50, 49, '流程设计', 'workflow/index', NULL, 'flow:saveOrUpdate', 0, 0, 'icon-expand', 1, 0, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-07-16 11:54:26');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (51, 49, '自定义流程', 'workflow/index-list', NULL, 'flow:page,flow:delete', 0, 0, 'icon-menu', 1, 0, 1, NULL, 10000, '2023-12-18 04:48:26', 10000, '2023-12-18 04:48:26');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (52, 66, '数据库监控', 'http://localhost:8080/druid/login.html', NULL, '', 0, 0, 'icon-Console-SQL', 1, 1, 1, '', 10000, '2024-08-18 13:40:48', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (59, 2, '定时任务', 'quartz/schedule/index', NULL, NULL, 0, 0, 'icon-reloadtime', 1, 0, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (60, 59, '查看', '', NULL, 'schedule:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (61, 59, '新增', '', NULL, 'schedule:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (62, 59, '修改', '', NULL, 'schedule:update,schedule:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (63, 59, '删除', '', NULL, 'schedule:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (64, 59, '立即运行', '', NULL, 'schedule:run', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (65, 59, '日志', '', NULL, 'schedule:log', 1, 0, '', 1, 4, 1, NULL, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (66, 34, '系统监控', 'sys/monitor', NULL, '', 0, 0, 'icon-Report', 1, 10, 1, NULL, 10000, '2023-06-12 13:46:12', 10000, '2024-06-29 11:28:20');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (67, 66, '服务监控', 'monitor/server/index', NULL, 'monitor:server:all', 0, 0, 'icon-sever', 1, 0, 1, NULL, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (68, 66, '缓存监控', 'monitor/cache/index', NULL, 'monitor:cache:all', 0, 0, 'icon-fund-fill', 1, 2, 1, NULL, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (69, 66, '用户监控', 'monitor/user/index', NULL, 'monitor:user:all', 0, 0, 'icon-user', 1, 3, 1, NULL, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (70, 34, '消息管理', 'sys/msg', NULL, '', 0, 0, 'icon-message', 1, 2, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2024-06-29 11:27:52');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (71, 70, '短信日志', 'message/sms/log/index', NULL, 'sms:log', 0, 0, 'icon-detail', 1, 1, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (72, 70, '短信平台', 'message/sms/platform/index', NULL, NULL, 0, 0, 'icon-whatsapp', 1, 0, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (73, 72, '查看', '', NULL, 'sms:platform:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (74, 72, '新增', '', NULL, 'sms:platform:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (75, 72, '修改', '', NULL, 'sms:platform:update,sms:platform:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (76, 72, '删除', '', NULL, 'sms:platform:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (77, 34, '代码生成器', '{{apiUrl}}/sysApi/oh-generator/index.html', NULL, '', 0, 1, 'icon-rocket', 1, 2, 1, NULL, 10000, '2023-06-12 13:47:50', 10000, '2023-06-24 21:35:28');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (80, 84, '查看', '', NULL, 'sys:app:page,sys:function:page,sys:authority:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:05:51');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (81, 84, '新增', '', NULL, 'sys:app:save,sys:function:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:04');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (82, 84, '修改', '', NULL, 'sys:app:update,sys:app:info,sys:function:update,sys:function:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:24');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (83, 84, '删除', '', NULL, 'sys:app:delete,sys:function:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:34');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (84, 34, '接口管理', 'sys/app/index', NULL, '', 0, 0, 'icon-drag', 1, 5, 1, NULL, 10000, '2023-06-12 13:47:41', 10000, '2024-04-21 20:03:08');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (85, 2, '版本信息', 'sys/info/index', NULL, '', 0, 0, 'icon-menu', 1, 11, 1, NULL, 10000, '2023-08-13 10:35:40', 10000, '2023-09-16 16:02:27');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (86, 85, '查看', '', NULL, 'system:info:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (87, 85, '新增', '', NULL, 'system:info:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (88, 85, '修改', '', NULL, 'system:info:update,system:info:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (89, 85, '删除', '', NULL, 'system:info:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (100, 0, '协同办公', 'team', NULL, '', 0, 0, 'icon-insertrowleft', 1, 4, 1, NULL, 10000, '2022-11-27 17:21:33', 10000, '2024-06-29 11:28:58');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (101, 100, '项目信息表', 'team/project/index', NULL, NULL, 0, 0, 'icon-menu', 1, 1, 1, NULL, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:44:38');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (102, 101, '查看', '', NULL, 'team:project:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (103, 101, '新增', '', NULL, 'team:project:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (104, 101, '修改', '', NULL, 'team:project:update,team:project:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (105, 101, '删除', '', NULL, 'team:project:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (106, 100, '任务表', 'team/task/index', NULL, NULL, 0, 0, 'icon-menu', 1, 2, 1, NULL, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:44:30');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (107, 106, '查看', '', NULL, 'team:task:page', 1, 0, '', 1, 0, 1, NULL, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (108, 106, '新增', '', NULL, 'team:task:save', 1, 0, '', 1, 1, 1, NULL, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (109, 106, '修改', '', NULL, 'team:task:update,oneHill:task:info', 1, 0, '', 1, 2, 1, NULL, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (110, 106, '删除', '', NULL, 'team:task:delete', 1, 0, '', 1, 3, 1, NULL, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `url`, `menu_path`, `authority`, `type`, `open_style`, `icon`, `no_cache`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (111, 49, '流程绘制', 'workflow/draw-index', NULL, '', 0, 0, 'icon-formatpainter', 1, 3, 1, '', 10000, '2024-07-06 15:29:51', NULL, NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `content` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '内容',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'success' COMMENT '消息类别success普通消息warning警告error错误',
  `state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '1' COMMENT '状态1未读2已读',
  `from_id` bigint DEFAULT NULL COMMENT '发送用户ID',
  `from_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发送人',
  `to_id` bigint NOT NULL COMMENT '接收用户ID',
  `to_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '接收人',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_01` (`from_id`) USING BTREE,
  KEY `idx_02` (`to_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='系统消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_org
-- ----------------------------
DROP TABLE IF EXISTS `sys_org`;
CREATE TABLE `sys_org` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `parent_id` bigint DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '机构名称',
  `note` varchar(800) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='机构管理';

-- ----------------------------
-- Records of sys_org
-- ----------------------------
BEGIN;
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 0, '长沙总部', '公司总部', 1, 1, 10000, '2022-12-03 15:48:18', 10000, '2023-06-27 22:35:57');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, '研发部', NULL, 1, 1, 10000, '2022-12-03 15:48:39', 10000, '2022-12-03 15:48:39');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, '销售部', NULL, 2, 1, 10000, '2022-12-03 15:48:57', 10000, '2022-12-03 15:48:57');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 1, '设计部', NULL, 3, 1, 10000, '2022-12-08 21:22:48', 10000, '2022-12-08 21:22:48');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 0, '合作伙伴', '重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司', 2, 1, 10000, '2022-12-08 21:31:18', 10000, '2022-12-08 22:06:30');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 5, '阿里哒', '总部在杭州的大公司。', 1, 1, 10000, '2022-12-08 21:31:48', 10000, '2023-06-27 22:52:48');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 5, '长沙银行', NULL, 2, 1, 10000, '2022-12-08 21:32:25', 10000, '2022-12-08 21:32:25');
INSERT INTO `sys_org` (`id`, `parent_id`, `name`, `note`, `sort`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (8, 1, '财务部', '', 4, 1, 10000, '2022-12-08 22:29:10', 10000, '2022-12-08 22:29:10');
COMMIT;

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS `sys_params`;
CREATE TABLE `sys_params` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `param_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数名称',
  `param_type` tinyint NOT NULL COMMENT '系统参数   0：否   1：是',
  `param_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数键',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '参数值',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `sys_params_key` (`param_key`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='参数管理';

-- ----------------------------
-- Records of sys_params
-- ----------------------------
BEGIN;
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'true', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_post`;
CREATE TABLE `sys_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `post_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '岗位编码',
  `post_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '岗位名称',
  `sort` int DEFAULT NULL COMMENT '排序',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `sys_post_post_code_IDX` (`post_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='岗位管理';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `data_scope` tinyint DEFAULT NULL COMMENT '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据',
  `org_id` bigint DEFAULT NULL COMMENT '机构ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_org_id` (`org_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `remark`, `data_scope`, `org_id`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '业务员', '', 3, 0, 1, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `org_id` bigint DEFAULT NULL COMMENT '机构ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色数据权限';

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_menu_id` (`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色菜单关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '姓名',
  `avatar` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `gender` tinyint DEFAULT NULL COMMENT '性别   0：男   1：女   2：未知',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `org_id` bigint DEFAULT NULL COMMENT '机构ID',
  `super_admin` tinyint DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `pwd_modify_time` datetime DEFAULT NULL COMMENT '密码修改时间',
  `user_key` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密钥，用于第三方系统登录',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10002 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `db_status`, `creator`, `create_time`, `updater`, `update_time`, `pwd_modify_time`, `user_key`) VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15', NULL);
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `org_id`, `super_admin`, `status`, `db_status`, `creator`, `create_time`, `updater`, `update_time`, `pwd_modify_time`, `user_key`) VALUES (10001, 'whx', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '王小费', 'http://localhost:8080/upload/20230717/1671258609873_77092.jpg', 0, 'whx5710@qq.com', '15088885710', 1, 0, 1, 1, 10000, '2023-07-17 21:15:47', 10001, '2023-07-17 21:24:54', '2023-06-24 21:14:15', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `post_id` bigint DEFAULT NULL COMMENT '岗位ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE,
  KEY `idx_post_id` (`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户岗位关系';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `user_id` bigint DEFAULT NULL COMMENT '用户ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` (`id`, `role_id`, `user_id`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 1, 10001, 1, 10000, '2023-07-17 21:16:57', 10000, '2023-07-17 21:16:57');
COMMIT;

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS `sys_version_info`;
CREATE TABLE `sys_version_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `version_num` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '版本号',
  `content` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布内容',
  `release_time` datetime NOT NULL COMMENT '发布时间',
  `is_curr_version` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否当前版本',
  `cover_picture` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图片',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_01` (`is_curr_version`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='版本信息';

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
BEGIN;
INSERT INTO `sys_version_info` (`id`, `version_num`, `content`, `release_time`, `is_curr_version`, `cover_picture`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '1.0.0', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1, '', 1, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
