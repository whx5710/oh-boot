/*
 Navicat Premium Dump SQL

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42-0ubuntu0.24.04.2)
 Source Host           : localhost:3306
 Source Schema         : oh-sys3.0

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42-0ubuntu0.24.04.2)
 File Encoding         : 65001

 Date: 16/08/2025 11:04:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

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
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`,`calendar_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_cron_triggers` (`sched_name`, `trigger_name`, `trigger_group`, `cron_expression`, `time_zone_id`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
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
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
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
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_job_details` (`sched_name`, `job_name`, `job_group`, `description`, `job_class_name`, `is_durable`, `is_nonconcurrent`, `is_update_data`, `requests_recovery`, `job_data`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.finn.sys.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002C636F6D2E697269732E7379732E71756172747A2E656E746974792E5363686564756C654A6F62456E746974791D0FDFE48785A8D702000A4C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00066D6574686F6471007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C000673746174757371007E000A78720024636F6D2E697269732E6672616D65776F726B2E656E746974792E42617365456E746974790CAC99DA4BA4272D0200064C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000763726561746F7271007E000B4C0008646253746174757371007E000A4C000874656E616E74496471007E00094C000A75706461746554696D6571007E000D4C00077570646174657271007E000B7872001D636F6D2E697269732E636F72652E656E746974792E4944456E74697479BB06028D6635A0130200014C0002696471007E000B78720020636F6D2E697269732E636F72652E656E746974792E5375706572456E746974791FD777AD7EB4DA400200007870707372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770A05000007E7060C1526DF787372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000002710737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E001400000001707371007E0011770A05000007E7060C1526DF787371007E00130000000000002710740008746573745461736B7371007E00160000000074000D30202A202A202A202A203F202A7371007E0013000000000000000174000673797374656D74000CE6B58BE8AF95E4BBBBE58AA174000372756E74000331323374000071007E001B7800);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`,`lock_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_locks` (`sched_name`, `lock_name`) VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` (`sched_name`, `lock_name`) VALUES ('OhScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`,`trigger_group`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`,`instance_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_scheduler_state` (`sched_name`, `instance_name`, `last_checkin_time`, `checkin_interval`) VALUES ('OhScheduler', 'DESKTOP-IHJIP1I1737353556355', 1737353677179, 15000);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`,`trigger_name`,`trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
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
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
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
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `qrtz_job_details` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO `qrtz_triggers` (`sched_name`, `trigger_name`, `trigger_group`, `job_name`, `job_group`, `description`, `next_fire_time`, `prev_fire_time`, `priority`, `trigger_state`, `trigger_type`, `start_time`, `end_time`, `calendar_name`, `misfire_instr`, `job_data`) VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1737353580000, -1, 5, 'PAUSED', 'CRON', 1737353556000, 0, NULL, 2, '');
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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `sys_dept`;
CREATE TABLE `sys_dept` (
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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='机构管理';

-- ----------------------------
-- Records of sys_dept
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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='登录日志';

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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志';

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
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '显示路径',
  `menu_path` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '页面组件路径',
  `status` int DEFAULT '1' COMMENT '状态 0停用 1有效',
  `hide_in_menu` tinyint(1) DEFAULT '0' COMMENT '菜单是否隐藏',
  `hide_in_tab` tinyint(1) DEFAULT '0' COMMENT '标签是否隐藏',
  `authority` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)',
  `badge` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于配置页面的徽标，会在菜单显示',
  `badge_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于配置页面的徽标类型，dot 为小红点，normal 为文本',
  `badge_variants` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于配置页面的徽标颜色',
  `affix_tab` tinyint(1) DEFAULT '0' COMMENT '用于配置页面是否固定标签页，固定后页面不可关闭',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用于配置外链跳转路径',
  `type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT 'menu' COMMENT '类型:  catalog | menu | action',
  `open_style` tinyint DEFAULT '0' COMMENT '打开方式   0：内部   1：外部',
  `icon` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单图标',
  `keep_alive` tinyint(1) DEFAULT '0' COMMENT '菜单缓存',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `mark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_pid` (`parent_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:layout-dashboard', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-26 13:39:51');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:workspace', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:area-chart', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (5, 10, 'SystemSetting', '系统设置', '/system/setting', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:settings-check', 0, 0, 1, NULL, 10000, '2025-06-22 14:05:38', 10000, '2025-06-22 14:18:50');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (6, 10, 'SystemAuthority', '权限管理', '/system/authority', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-settings', 0, 0, 1, NULL, 10000, '2025-06-22 14:07:19', 10000, '2025-06-22 14:19:02');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (7, 10, 'SystemApp', '系统应用', '/system/app', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:app', 0, 0, 1, NULL, 10000, '2025-06-22 14:09:17', 10000, '2025-06-22 14:19:12');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 0, 0, '', NULL, 'dot', NULL, 0, NULL, 'menu', 0, 'carbon:settings', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-07 10:16:33');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (11, 5, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 0, 0, NULL, 'new', 'normal', 'default', 0, NULL, 'menu', 0, 'carbon:menu', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:37');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (12, 11, '查询菜单', '查询菜单', '', NULL, 1, 0, 0, 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:12:33');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (13, 11, '新增修改菜单', '新增修改菜单', '', NULL, 1, 0, 0, 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:11:39');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (15, 6, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'mdi:account-group', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:26');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 0, 0, 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 0, 0, 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (19, 6, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:container-services', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:49');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 0, 0, 'sys:dept:list,sys:dept:info', NULL, '', NULL, 0, NULL, 'action', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 0, 0, 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (23, 5, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-parcel', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:47');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 0, 0, 'sys:params:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 0, 0, 'sys:params:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (27, 6, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-multiple', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:59');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 0, 0, 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 0, 0, 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (30, 5, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:align-horizontal-left', 0, 0, 1, NULL, 10000, '2025-05-19 09:30:58', 10000, '2025-06-22 14:20:57');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 0, 0, 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:32:48', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 0, 0, 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:33:38', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (33, 6, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-05-20 11:07:19', 10000, '2025-06-22 14:20:12');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 0, 0, 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:08:47', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 0, 0, 'tenant:member:save,tenant:member:update', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:09:21', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (36, 6, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:calls-all', 1, 0, 1, NULL, 10000, '2025-05-20 17:54:23', 10000, '2025-06-22 14:20:26');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 0, 0, 'sys:post:page,sys:post:info', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:55:22', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 0, 0, 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:56:26', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (39, 7, 'Attachment', '附件管理', '/system/attachment', '/system/attachment/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:file-storage', 0, 0, 1, NULL, 10000, '2025-06-08 11:26:11', 10000, '2025-06-22 14:21:39');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (40, 39, '查询附件', '查询附件', '', NULL, 1, 0, 0, 'sys:attachment:page', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:27:23', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (41, 39, '新增修改附件', '新增修改附件', '', NULL, 1, 0, 0, 'sys:attachment:save,sys:attachment:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:28:09', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (42, 10, 'Log', '日志管理', '/system/log', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:24:35', 10000, '2025-06-11 17:29:17');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (43, 42, 'LoginLog', '登录日志', '/system/loginLog', '/system/log/loginLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:change-catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:25:59', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (44, 43, '查询登录日志', '查询登录日志', '', NULL, 1, 0, 0, 'sys:log:login', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:35:32', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (45, 42, 'OpLog', '操作日志', '/system/opLog', '/system/log/opLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:border-top', 0, 0, 1, NULL, 10000, '2025-06-11 17:28:36', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (46, 45, '查询操作日志', '查询操作日志', '', NULL, 1, 0, 0, 'sys:operate:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:40:14', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (47, 7, 'SystemInterface', '接口管理', '/system/app/manage', '', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:ibm-cloud-vpc-client-vpn', 0, 0, 1, NULL, 10000, '2025-06-22 20:39:43', 10000, '2025-06-22 20:43:16');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (48, 47, 'SystemAppInterface', '客户端管理', '/system/app/list', '/system/app/list', 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:multiuser-device', 0, 0, 1, NULL, 10000, '2025-06-22 20:42:06', 10000, '2025-06-22 20:46:05');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (49, 48, '查询客户端', '查询客户端', '', NULL, 1, 0, 0, 'sys:app:page,sys:app:info,sys:function:page', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-22 20:46:30', 10000, '2025-06-23 15:13:02');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (50, 48, '新增修改客户端', '新增修改客户端', '', NULL, 1, 0, 0, 'sys:app:save,sys:app:update,sys:app:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-24 14:27:08', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (51, 47, 'SystemFunc', '接口管理', '/system/app/func', '/system/app/funcList', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:interface-usage-1', 0, 0, 1, NULL, 10000, '2025-06-25 10:35:16', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (52, 51, '查询接口', '查询接口', '', NULL, 1, 0, 0, 'sys:function:page,sys:function:info', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 10:36:10', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (53, 51, '新增修改接口', '新增修改接口', '', NULL, 1, 0, 0, 'sys:function:save,sys:function:update,sys:function:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 11:00:12', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (54, 47, 'SystemAppLog', '接口日志', '/system/app/log', '/system/app/log', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-06-25 17:27:26', 10000, '2025-06-25 17:30:01');
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (55, 54, '查询日志', '查询日志', '', NULL, 1, 0, 0, 'sys:app:page', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:11', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (56, 54, '删除日志', '删除日志', '', NULL, 1, 0, 0, 'sys:app:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:37', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (57, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 0, 0, 'sys:log:login:delete', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 19:45:26', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (58, 7, 'UserMonitor', '用户监控', '/system/userMonitor', '/system/user/monitor', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:cloud-monitoring', 0, 0, 1, NULL, 10000, '2025-07-13 19:25:32', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (59, 58, '在线用户', '在线用户', '', NULL, 1, 0, 0, 'monitor:user:all', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-07-13 19:34:04', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (60, 58, '强制退出', '强制退出', '', NULL, 1, 0, 0, 'monitor:user:logout', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-07-13 19:34:36', NULL, NULL);
INSERT INTO `sys_menu` (`id`, `parent_id`, `name`, `title`, `path`, `menu_path`, `status`, `hide_in_menu`, `hide_in_tab`, `authority`, `badge`, `badge_type`, `badge_variants`, `affix_tab`, `link`, `type`, `open_style`, `icon`, `keep_alive`, `sort`, `db_status`, `mark`, `creator`, `create_time`, `updater`, `update_time`) VALUES (61, 58, '用户token列表', '用户token列表', '', NULL, 1, 0, 0, 'monitor:user:tokens', NULL, NULL, NULL, 0, NULL, 'action', 0, NULL, 0, 0, 1, NULL, 10000, '2025-07-26 22:47:10', NULL, NULL);
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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
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
INSERT INTO `sys_params` (`id`, `param_name`, `param_type`, `param_key`, `param_value`, `remark`, `db_status`, `creator`, `create_time`, `updater`, `update_time`) VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
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
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
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
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `is_system` tinyint(1) NOT NULL DEFAULT '0' COMMENT '是否系统内置角色1是',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` (`id`, `name`, `remark`, `data_scope`, `db_status`, `is_system`, `creator`, `create_time`, `updater`, `update_time`, `tenant_id`) VALUES (1, '租户角色', '系统内置', 0, 1, 1, 10000, '2025-01-01 01:23:45', 10000, '2025-06-07 21:53:07', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_data_scope`;
CREATE TABLE `sys_role_data_scope` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
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
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  `db_status` tinyint NOT NULL DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
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
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `super_admin` tinyint DEFAULT NULL COMMENT '超级管理员   0：否   1：是',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `pwd_modify_time` datetime DEFAULT NULL COMMENT '密码修改时间',
  `user_key` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户密钥，用于第三方系统登录',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '租户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10001 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` (`id`, `username`, `password`, `real_name`, `avatar`, `gender`, `email`, `mobile`, `dept_id`, `super_admin`, `status`, `note`, `db_status`, `creator`, `create_time`, `updater`, `update_time`, `pwd_modify_time`, `user_key`, `tenant_id`) VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, NULL, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15', '123', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_post`;
CREATE TABLE `sys_user_post` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  `db_status` tinyint NOT NULL DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
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
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `db_status` tinyint NOT NULL DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_role_id` (`role_id`) USING BTREE,
  KEY `idx_user_id` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
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

-- ----------------------------
-- Table structure for tenant_member
-- ----------------------------
DROP TABLE IF EXISTS `tenant_member`;
CREATE TABLE `tenant_member` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `tenant_id` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户ID',
  `tenant_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '租户名',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint DEFAULT NULL COMMENT '状态  0：停用   1：正常',
  `sort` int DEFAULT NULL COMMENT '排序',
  `db_status` tinyint DEFAULT '1' COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_01` (`tenant_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='租户信息';

-- ----------------------------
-- Records of tenant_member
-- ----------------------------
BEGIN;
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
