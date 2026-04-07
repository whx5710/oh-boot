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

 Date: 07/04/2026 15:15:36
*/



-- ----------------------------
-- Table structure for online_table_column
-- ----------------------------
DROP TABLE IF EXISTS online_table_column;
CREATE TABLE online_table_column (
  id bigint NOT NULL,
  name varchar(100) NOT NULL,
  comments varchar(200) NOT NULL,
  "length" int NOT NULL,
  point_length int NOT NULL,
  default_value varchar(200) NULL DEFAULT NULL,
  "column_type" varchar(100) NULL DEFAULT NULL,
  column_pk smallint NULL DEFAULT NULL,
  column_null smallint NULL DEFAULT NULL,
  form_item smallint NULL DEFAULT NULL,
  form_required smallint NULL DEFAULT NULL,
  form_input varchar(100) NULL DEFAULT NULL,
  form_default varchar(100) NULL DEFAULT NULL,
  form_dict varchar(100) NULL DEFAULT NULL,
  grid_item smallint NULL DEFAULT NULL,
  grid_sort smallint NULL DEFAULT NULL,
  query_item smallint NULL DEFAULT NULL,
  query_type varchar(200) NULL DEFAULT NULL,
  query_input varchar(200) NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  table_id varchar(32) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE online_table_column IS 'Online表单字段';

-- ----------------------------
-- Records of online_table_column
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_blob_triggers;
CREATE TABLE qrtz_blob_triggers (
  sched_name varchar(120) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  blob_data bytea NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT QRTZ_BLOB_TRIGGERS_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group) ON DELETE RESTRICT ON UPDATE RESTRICT
);
COMMENT ON TABLE qrtz_blob_triggers IS 'Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS qrtz_calendars;
CREATE TABLE qrtz_calendars (
  sched_name varchar(120) NOT NULL,
  calendar_name varchar(200) NOT NULL,
  calendar bytea NOT NULL,
  PRIMARY KEY (sched_name, calendar_name)
);
COMMENT ON TABLE qrtz_calendars IS '日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_cron_triggers;
CREATE TABLE qrtz_cron_triggers (
  sched_name varchar(120) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  cron_expression varchar(200) NOT NULL,
  time_zone_id varchar(80) NULL DEFAULT NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT QRTZ_CRON_TRIGGERS_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group) ON DELETE RESTRICT ON UPDATE RESTRICT
);
COMMENT ON TABLE qrtz_cron_triggers IS 'Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO qrtz_cron_triggers VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_fired_triggers;
CREATE TABLE qrtz_fired_triggers (
  sched_name varchar(120) NOT NULL,
  entry_id varchar(95) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  instance_name varchar(200) NOT NULL,
  fired_time bigint NOT NULL,
  sched_time bigint NOT NULL,
  priority int NOT NULL,
  state varchar(16) NOT NULL,
  job_name varchar(200) NULL DEFAULT NULL,
  job_group varchar(200) NULL DEFAULT NULL,
  is_nonconcurrent varchar(1) NULL DEFAULT NULL,
  requests_recovery varchar(1) NULL DEFAULT NULL,
  PRIMARY KEY (sched_name, entry_id)
);
COMMENT ON TABLE qrtz_fired_triggers IS '已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS qrtz_job_details;
CREATE TABLE qrtz_job_details (
  sched_name varchar(120) NOT NULL,
  job_name varchar(200) NOT NULL,
  job_group varchar(200) NOT NULL,
  description varchar(250) NULL DEFAULT NULL,
  job_class_name varchar(250) NOT NULL,
  is_durable varchar(1) NOT NULL,
  is_nonconcurrent varchar(1) NOT NULL,
  is_update_data varchar(1) NOT NULL,
  requests_recovery varchar(1) NOT NULL,
  job_data bytea NULL,
  PRIMARY KEY (sched_name, job_name, job_group)
);
COMMENT ON TABLE qrtz_job_details IS '任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO qrtz_job_details VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.finn.sys.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002C636F6D2E697269732E7379732E71756172747A2E656E746974792E5363686564756C654A6F62456E746974791D0FDFE48785A8D702000A4C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00066D6574686F6471007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C000673746174757371007E000A78720024636F6D2E697269732E6672616D65776F726B2E656E746974792E42617365456E746974790CAC99DA4BA4272D0200064C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000763726561746F7271007E000B4C0008646253746174757371007E000A4C000874656E616E74496471007E00094C000A75706461746554696D6571007E000D4C00077570646174657271007E000B7872001D636F6D2E697269732E636F72652E656E746974792E4944456E74697479BB06028D6635A0130200014C0002696471007E000B78720020636F6D2E697269732E636F72652E656E746974792E5375706572456E746974791FD777AD7EB4DA400200007870707372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770A05000007E7060C1526DF787372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000002710737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E001400000001707371007E0011770A05000007E7060C1526DF787371007E00130000000000002710740008746573745461736B7371007E00160000000074000D30202A202A202A202A203F202A7371007E0013000000000000000174000673797374656D74000CE6B58BE8AF95E4BBBBE58AA174000372756E74000331323374000071007E001B7800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS qrtz_locks;
CREATE TABLE qrtz_locks (
  sched_name varchar(120) NOT NULL,
  lock_name varchar(40) NOT NULL,
  PRIMARY KEY (sched_name, lock_name)
);
COMMENT ON TABLE qrtz_locks IS '存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO qrtz_locks VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO qrtz_locks VALUES ('OhScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS qrtz_paused_trigger_grps;
CREATE TABLE qrtz_paused_trigger_grps (
  sched_name varchar(120) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  PRIMARY KEY (sched_name, trigger_group)
);
COMMENT ON TABLE qrtz_paused_trigger_grps IS '暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS qrtz_scheduler_state;
CREATE TABLE qrtz_scheduler_state (
  sched_name varchar(120) NOT NULL,
  instance_name varchar(200) NOT NULL,
  last_checkin_time bigint NOT NULL,
  checkin_interval bigint NOT NULL,
  PRIMARY KEY (sched_name, instance_name)
);
COMMENT ON TABLE qrtz_scheduler_state IS '调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO qrtz_scheduler_state VALUES ('OhScheduler', 'DESKTOP-IHJIP1I1737353556355', 1737353677179, 15000);

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_simple_triggers;
CREATE TABLE qrtz_simple_triggers (
  sched_name varchar(120) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  repeat_count bigint NOT NULL,
  repeat_interval bigint NOT NULL,
  times_triggered bigint NOT NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT QRTZ_SIMPLE_TRIGGERS_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group) ON DELETE RESTRICT ON UPDATE RESTRICT
);
COMMENT ON TABLE qrtz_simple_triggers IS '简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_simprop_triggers;
CREATE TABLE qrtz_simprop_triggers (
  sched_name varchar(120) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  str_prop_1 varchar(512) NULL DEFAULT NULL,
  str_prop_2 varchar(512) NULL DEFAULT NULL,
  str_prop_3 varchar(512) NULL DEFAULT NULL,
  int_prop_1 int NULL DEFAULT NULL,
  int_prop_2 int NULL DEFAULT NULL,
  long_prop_1 bigint NULL DEFAULT NULL,
  long_prop_2 bigint NULL DEFAULT NULL,
  dec_prop_1 decimal(13, 4) NULL DEFAULT NULL,
  dec_prop_2 decimal(13, 4) NULL DEFAULT NULL,
  bool_prop_1 varchar(1) NULL DEFAULT NULL,
  bool_prop_2 varchar(1) NULL DEFAULT NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT QRTZ_SIMPROP_TRIGGERS_ibfk_1 FOREIGN KEY (sched_name, trigger_name, trigger_group) REFERENCES qrtz_triggers (sched_name, trigger_name, trigger_group) ON DELETE RESTRICT ON UPDATE RESTRICT
);
COMMENT ON TABLE qrtz_simprop_triggers IS '同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS qrtz_triggers;
CREATE TABLE qrtz_triggers (
  sched_name varchar(120) NOT NULL,
  trigger_name varchar(200) NOT NULL,
  trigger_group varchar(200) NOT NULL,
  job_name varchar(200) NOT NULL,
  job_group varchar(200) NOT NULL,
  description varchar(250) NULL DEFAULT NULL,
  next_fire_time bigint NULL DEFAULT NULL,
  prev_fire_time bigint NULL DEFAULT NULL,
  priority int NULL DEFAULT NULL,
  trigger_state varchar(16) NOT NULL,
  trigger_type varchar(8) NOT NULL,
  start_time bigint NOT NULL,
  end_time bigint NULL DEFAULT NULL,
  calendar_name varchar(200) NULL DEFAULT NULL,
  misfire_instr smallint NULL DEFAULT NULL,
  job_data bytea NULL,
  PRIMARY KEY (sched_name, trigger_name, trigger_group),
  CONSTRAINT QRTZ_TRIGGERS_ibfk_1 FOREIGN KEY (sched_name, job_name, job_group) REFERENCES qrtz_job_details (sched_name, job_name, job_group) ON DELETE RESTRICT ON UPDATE RESTRICT
);
COMMENT ON TABLE qrtz_triggers IS '触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO qrtz_triggers VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1737353580000, -1, 5, 'PAUSED', 'CRON', 1737353556000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS schedule_job;
CREATE TABLE schedule_job (
  id bigint NOT NULL,
  job_name varchar(200) NULL DEFAULT NULL,
  job_group varchar(100) NULL DEFAULT NULL,
  bean_name varchar(200) NULL DEFAULT NULL,
  "method" varchar(100) NULL DEFAULT NULL,
  params varchar(2000) NULL DEFAULT NULL,
  cron_expression varchar(100) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  concurrent smallint NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE schedule_job IS '定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO schedule_job VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 1, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS schedule_job_log;
CREATE TABLE schedule_job_log (
  id bigint NOT NULL,
  job_id bigint NOT NULL,
  job_name varchar(200) NULL DEFAULT NULL,
  job_group varchar(100) NULL DEFAULT NULL,
  bean_name varchar(200) NULL DEFAULT NULL,
  "method" varchar(100) NULL DEFAULT NULL,
  params varchar(2000) NULL DEFAULT NULL,
  status smallint NOT NULL,
  error varchar(2000) NULL DEFAULT NULL,
  times bigint NOT NULL,
  create_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE schedule_job_log IS '定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS sms_log;
CREATE TABLE sms_log (
  id bigint NOT NULL,
  platform_id bigint NULL DEFAULT NULL,
  platform smallint NULL DEFAULT NULL,
  mobile varchar(20) NOT NULL,
  params varchar(200) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  error varchar(2000) NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sms_log IS '短信日志';

-- ----------------------------
-- Records of sms_log
-- ----------------------------

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS sms_platform;
CREATE TABLE sms_platform (
  id bigint NOT NULL,
  platform smallint NULL DEFAULT NULL,
  sign_name varchar(100) NOT NULL,
  template_id varchar(100) NOT NULL,
  app_id varchar(100) NOT NULL,
  sender_id varchar(100) NOT NULL,
  url varchar(200) NOT NULL,
  access_key varchar(100) NULL DEFAULT NULL,
  secret_key varchar(100) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sms_platform IS '短信平台';

-- ----------------------------
-- Records of sms_platform
-- ----------------------------

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS sys_attachment;
CREATE TABLE sys_attachment (
  id bigint NOT NULL,
  name varchar(255) NOT NULL,
  url varchar(255) NOT NULL,
  size bigint NULL DEFAULT NULL,
  platform varchar(50) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_attachment IS '附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS sys_dept;
CREATE TABLE sys_dept (
  id bigint NOT NULL,
  parent_id bigint NULL DEFAULT NULL,
  name varchar(50) NULL DEFAULT NULL,
  note varchar(800) NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_dept IS '机构管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_data;
CREATE TABLE sys_dict_data (
  id bigint NOT NULL,
  dict_type_id bigint NOT NULL,
  dict_label varchar(255) NOT NULL,
  dict_value varchar(255) NULL DEFAULT NULL,
  label_class varchar(30) NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dict_data IS '字典数据';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO sys_dict_data VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_data VALUES (32, 12, '开始', '1', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO sys_dict_data VALUES (33, 12, '暂停', '2', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO sys_dict_data VALUES (34, 12, '关闭', '3', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO sys_dict_data VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_data VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_data VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_data VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_data VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO sys_dict_data VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO sys_dict_data VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO sys_dict_data VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS sys_dict_type;
CREATE TABLE sys_dict_type (
  id bigint NOT NULL,
  dict_type varchar(100) NOT NULL,
  dict_name varchar(255) NOT NULL,
  dict_source smallint NULL DEFAULT 0,
  dict_sql varchar(500) NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_dict_type IS '字典类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO sys_dict_type VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_dict_type VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 1, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO sys_dict_type VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_type VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO sys_dict_type VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS sys_log_login;
CREATE TABLE sys_log_login (
  id bigint NOT NULL,
  username varchar(50) NULL DEFAULT NULL,
  ip varchar(32) NULL DEFAULT NULL,
  address varchar(32) NULL DEFAULT NULL,
  user_agent varchar(500) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  operation smallint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_log_login IS '登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS sys_log_operate;
CREATE TABLE sys_log_operate (
  id bigint NOT NULL,
  module varchar(100) NULL DEFAULT NULL,
  name varchar(100) NULL DEFAULT NULL,
  req_uri varchar(200) NULL DEFAULT NULL,
  req_method varchar(20) NULL DEFAULT NULL,
  req_params text NULL,
  ip varchar(32) NULL DEFAULT NULL,
  address varchar(32) NULL DEFAULT NULL,
  user_agent varchar(500) NULL DEFAULT NULL,
  operate_type smallint NULL DEFAULT NULL,
  duration int NOT NULL,
  status smallint NULL DEFAULT NULL,
  user_id bigint NULL DEFAULT NULL,
  real_name varchar(50) NULL DEFAULT NULL,
  result_msg varchar(500) NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_log_operate IS '操作日志';

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_menu;
CREATE TABLE sys_menu (
  id bigint NOT NULL,
  parent_id bigint NULL DEFAULT NULL,
  name varchar(200) NULL DEFAULT NULL,
  title varchar(50) NULL DEFAULT NULL,
  path varchar(200) NULL DEFAULT NULL,
  menu_path varchar(80) NULL DEFAULT NULL,
  status int NULL DEFAULT 1,
  hide_in_menu smallint NULL DEFAULT 0,
  hide_in_tab smallint NULL DEFAULT 0,
  authority varchar(500) NULL DEFAULT NULL,
  badge varchar(50) NULL DEFAULT NULL,
  badge_type varchar(20) NULL DEFAULT NULL,
  badge_variants varchar(30) NULL DEFAULT NULL,
  affix_tab smallint NULL DEFAULT 0,
  link varchar(255) NULL DEFAULT NULL,
  "type" varchar(10) NULL DEFAULT 'menu',
  open_style smallint NULL DEFAULT 0,
  icon varchar(50) NULL DEFAULT NULL,
  keep_alive smallint NULL DEFAULT 0,
  sort int NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  mark varchar(100) NULL DEFAULT NULL,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_menu IS '菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO sys_menu VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:layout-dashboard', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-26 13:39:51');
INSERT INTO sys_menu VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:workspace', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO sys_menu VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'lucide:area-chart', 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO sys_menu VALUES (5, 10, 'SystemSetting', '系统设置', '/system/setting', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:settings-check', 0, 0, 1, NULL, 10000, '2025-06-22 14:05:38', 10000, '2025-06-22 14:18:50');
INSERT INTO sys_menu VALUES (6, 10, 'SystemAuthority', '权限管理', '/system/authority', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-settings', 0, 0, 1, NULL, 10000, '2025-06-22 14:07:19', 10000, '2025-06-22 14:19:02');
INSERT INTO sys_menu VALUES (7, 10, 'SystemApp', '系统应用', '/system/app', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:app', 0, 0, 1, NULL, 10000, '2025-06-22 14:09:17', 10000, '2025-06-22 14:19:12');
INSERT INTO sys_menu VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 0, 0, '', NULL, 'dot', NULL, 0, NULL, 'menu', 0, 'carbon:settings', 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-07 10:16:33');
INSERT INTO sys_menu VALUES (11, 5, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 0, 0, NULL, 'new', 'normal', 'default', 0, NULL, 'menu', 0, 'carbon:menu', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:37');
INSERT INTO sys_menu VALUES (12, 11, '查询菜单', '查询菜单', '', NULL, 1, 0, 0, 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:12:33');
INSERT INTO sys_menu VALUES (13, 11, '新增修改菜单', '新增修改菜单', '', NULL, 1, 0, 0, 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 1, 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:11:39');
INSERT INTO sys_menu VALUES (15, 6, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'mdi:account-group', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:26');
INSERT INTO sys_menu VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 0, 0, 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO sys_menu VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 0, 0, 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO sys_menu VALUES (19, 6, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:container-services', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:49');
INSERT INTO sys_menu VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 0, 0, 'sys:dept:list,sys:dept:info', NULL, '', NULL, 0, NULL, 'button', 0, NULL, 1, 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO sys_menu VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 0, 0, 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO sys_menu VALUES (23, 5, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:delivery-parcel', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:47');
INSERT INTO sys_menu VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 0, 0, 'sys:params:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO sys_menu VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 0, 0, 'sys:params:save,sys:params:update,sys:params:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO sys_menu VALUES (27, 6, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:user-multiple', 1, 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:59');
INSERT INTO sys_menu VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 0, 0, 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO sys_menu VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 0, 0, 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO sys_menu VALUES (30, 5, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:align-horizontal-left', 0, 0, 1, NULL, 10000, '2025-05-19 09:30:58', 10000, '2025-06-22 14:20:57');
INSERT INTO sys_menu VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 0, 0, 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:32:48', NULL, NULL);
INSERT INTO sys_menu VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 0, 0, 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-19 09:33:38', NULL, NULL);
INSERT INTO sys_menu VALUES (33, 6, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-05-20 11:07:19', 10000, '2025-06-22 14:20:12');
INSERT INTO sys_menu VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 0, 0, 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:08:47', NULL, NULL);
INSERT INTO sys_menu VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 0, 0, 'tenant:member:save,tenant:member:update,sys:user:bindTenantUser,sys:user:unBindTenantUser', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 11:09:21', NULL, NULL);
INSERT INTO sys_menu VALUES (36, 6, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:calls-all', 1, 0, 1, NULL, 10000, '2025-05-20 17:54:23', 10000, '2025-06-22 14:20:26');
INSERT INTO sys_menu VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 0, 0, 'sys:post:page,sys:post:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:55:22', NULL, NULL);
INSERT INTO sys_menu VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 0, 0, 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-05-20 17:56:26', NULL, NULL);
INSERT INTO sys_menu VALUES (39, 7, 'Attachment', '附件管理', '/system/attachment', '/system/attachment/list', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:file-storage', 0, 0, 1, NULL, 10000, '2025-06-08 11:26:11', 10000, '2025-06-22 14:21:39');
INSERT INTO sys_menu VALUES (40, 39, '查询附件', '查询附件', '', NULL, 1, 0, 0, 'sys:attachment:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:27:23', NULL, NULL);
INSERT INTO sys_menu VALUES (41, 39, '新增修改附件', '新增修改附件', '', NULL, 1, 0, 0, 'sys:attachment:save,sys:attachment:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-08 11:28:09', NULL, NULL);
INSERT INTO sys_menu VALUES (42, 10, 'Log', '日志管理', '/system/log', NULL, 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:24:35', 10000, '2025-06-11 17:29:17');
INSERT INTO sys_menu VALUES (43, 42, 'LoginLog', '登录日志', '/system/loginLog', '/system/log/loginLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:change-catalog', 0, 0, 1, NULL, 10000, '2025-06-11 17:25:59', NULL, NULL);
INSERT INTO sys_menu VALUES (44, 43, '查询登录日志', '查询登录日志', '', NULL, 1, 0, 0, 'sys:log:login', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:35:32', NULL, NULL);
INSERT INTO sys_menu VALUES (45, 42, 'OpLog', '操作日志', '/system/opLog', '/system/log/opLog', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:border-top', 0, 0, 1, NULL, 10000, '2025-06-11 17:28:36', NULL, NULL);
INSERT INTO sys_menu VALUES (46, 45, '查询操作日志', '查询操作日志', '', NULL, 1, 0, 0, 'sys:operate:all', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-11 20:40:14', NULL, NULL);
INSERT INTO sys_menu VALUES (47, 7, 'SystemInterface', '接口管理', '/system/app/manage', '', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:ibm-cloud-vpc-client-vpn', 0, 0, 1, NULL, 10000, '2025-06-22 20:39:43', 10000, '2025-06-22 20:43:16');
INSERT INTO sys_menu VALUES (48, 47, 'SystemAppInterface', '客户端管理', '/system/app/list', '/system/app/list', 1, 0, 0, '', NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:multiuser-device', 0, 0, 1, NULL, 10000, '2025-06-22 20:42:06', 10000, '2025-06-22 20:46:05');
INSERT INTO sys_menu VALUES (49, 48, '查询客户端', '查询客户端', '', NULL, 1, 0, 0, 'sys:app:page,sys:app:info,sys:function:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-22 20:46:30', 10000, '2025-06-23 15:13:02');
INSERT INTO sys_menu VALUES (50, 48, '新增修改客户端', '新增修改客户端', '', NULL, 1, 0, 0, 'sys:app:save,sys:app:update,sys:app:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-24 14:27:08', NULL, NULL);
INSERT INTO sys_menu VALUES (51, 47, 'SystemFunc', '接口管理', '/system/app/func', '/system/app/funcList', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:interface-usage-1', 0, 0, 1, NULL, 10000, '2025-06-25 10:35:16', NULL, NULL);
INSERT INTO sys_menu VALUES (52, 51, '查询接口', '查询接口', '', NULL, 1, 0, 0, 'sys:function:page,sys:function:info', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 10:36:10', NULL, NULL);
INSERT INTO sys_menu VALUES (53, 51, '新增修改接口', '新增修改接口', '', NULL, 1, 0, 0, 'sys:function:save,sys:function:update,sys:function:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 11:00:12', NULL, NULL);
INSERT INTO sys_menu VALUES (54, 47, 'SystemAppLog', '接口日志', '/system/app/log', '/system/app/log', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:catalog-publish', 0, 0, 1, NULL, 10000, '2025-06-25 17:27:26', 10000, '2025-06-25 17:30:01');
INSERT INTO sys_menu VALUES (55, 54, '查询日志', '查询日志', '', NULL, 1, 0, 0, 'sys:app:page', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:11', NULL, NULL);
INSERT INTO sys_menu VALUES (56, 54, '删除日志', '删除日志', '', NULL, 1, 0, 0, 'sys:app:delete', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2025-06-25 17:28:37', NULL, NULL);
INSERT INTO sys_menu VALUES (57, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 0, 0, 'sys:log:login:delete', NULL, NULL, NULL, 0, NULL, 'menu', 0, NULL, 0, 0, 0, NULL, 10000, '2025-06-25 19:45:26', 10000, '2025-09-18 13:32:58');
INSERT INTO sys_menu VALUES (33304128612466688, 7, 'Flow', '绘制流程', '/system/flow', '/system/flow/draw', 1, 0, 0, NULL, NULL, NULL, NULL, 0, NULL, 'menu', 0, 'carbon:flow-logs-vpc', 0, 0, 1, NULL, 10000, '2026-04-02 21:38:43', 10000, '2026-04-06 18:10:43');
INSERT INTO sys_menu VALUES (34701521023139840, 33304128612466688, '流程新增修改', '流程新增修改', '', NULL, 1, 0, 0, 'flow:saveOrUpdate', NULL, NULL, NULL, 0, NULL, 'button', 0, NULL, 0, 0, 1, NULL, 10000, '2026-04-06 18:11:27', NULL, NULL);

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS sys_message;
CREATE TABLE sys_message (
  id int NOT NULL,
  title varchar(50) NULL DEFAULT NULL,
  content varchar(300) NOT NULL,
  "type" varchar(10) NULL DEFAULT 'success',
  state varchar(1) NOT NULL DEFAULT '1',
  from_id bigint NULL DEFAULT NULL,
  from_name varchar(50) NULL DEFAULT NULL,
  to_id bigint NOT NULL,
  to_name varchar(255) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_message IS '系统消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS sys_params;
CREATE TABLE sys_params (
  id bigint NOT NULL,
  param_name varchar(100) NULL DEFAULT NULL,
  param_type smallint NOT NULL,
  param_key varchar(100) NULL DEFAULT NULL,
  param_value varchar(2000) NULL DEFAULT NULL,
  remark varchar(200) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_params IS '参数管理';

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO sys_params VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO sys_params VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO sys_params VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO sys_params VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS sys_post;
CREATE TABLE sys_post (
  id bigint NOT NULL,
  post_code varchar(100) NOT NULL,
  post_name varchar(100) NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_post IS '岗位管理';

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role (
  id bigint NOT NULL,
  code varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  remark varchar(100) NULL DEFAULT NULL,
  data_scope smallint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  is_system smallint NOT NULL DEFAULT 0,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_role IS '角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO sys_role VALUES (1, 'Tenant', '租户角色', '系统内置', 0, 1, 1, 10000, '2025-01-01 01:23:45', 10000, '2025-06-07 21:53:07', '');

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS sys_role_data_scope;
CREATE TABLE sys_role_data_scope (
  id bigint NOT NULL,
  role_id bigint NULL DEFAULT NULL,
  dept_id bigint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_role_data_scope IS '角色数据权限';

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS sys_role_menu;
CREATE TABLE sys_role_menu (
  id bigint NOT NULL,
  role_id bigint NOT NULL,
  menu_id bigint NOT NULL,
  db_status smallint NOT NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_role_menu IS '角色菜单关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for sys_tenant_member
-- ----------------------------
DROP TABLE IF EXISTS sys_tenant_member;
CREATE TABLE sys_tenant_member (
  id bigint NOT NULL,
  tenant_id varchar(30) NOT NULL,
  tenant_name varchar(100) NOT NULL,
  dept_id bigint NULL DEFAULT NULL,
  note varchar(255) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  sort int NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_tenant_member IS '租户信息';

-- ----------------------------
-- Records of sys_tenant_member
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
  id bigint NOT NULL,
  username varchar(50) NOT NULL,
  password varchar(100) NULL DEFAULT NULL,
  real_name varchar(50) NULL DEFAULT NULL,
  avatar varchar(200) NULL DEFAULT NULL,
  gender smallint NULL DEFAULT NULL,
  email varchar(100) NULL DEFAULT NULL,
  mobile varchar(20) NULL DEFAULT NULL,
  dept_id bigint NULL DEFAULT NULL,
  super_admin smallint NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  note varchar(255) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  pwd_modify_time timestamp NULL DEFAULT NULL,
  user_key varchar(60) NULL DEFAULT NULL,
  tenant_id varchar(30) NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE sys_user IS '用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO sys_user VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, NULL, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15', '123', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS sys_user_post;
CREATE TABLE sys_user_post (
  id bigint NOT NULL,
  user_id bigint NOT NULL,
  post_id bigint NOT NULL,
  db_status smallint NOT NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_user_post IS '用户岗位关系';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
  id bigint NOT NULL,
  role_id bigint NOT NULL,
  user_id bigint NOT NULL,
  db_status smallint NOT NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_user_role IS '用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS sys_version_info;
CREATE TABLE sys_version_info (
  id bigint NOT NULL,
  version_num varchar(30) NOT NULL,
  title varchar(50) NULL DEFAULT NULL,
  content varchar(500) NOT NULL,
  release_time timestamp NOT NULL,
  is_curr_version smallint NOT NULL DEFAULT 0,
  cover_picture varchar(100) NULL DEFAULT NULL,
  re_login smallint NULL DEFAULT NULL,
  remark varchar(255) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE sys_version_info IS '版本信息';

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
INSERT INTO sys_version_info VALUES (1, '1.0.0', '初始版本', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1, '', 0, NULL, 1, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');


-- ----------------------------
-- Field Comments
-- ----------------------------
COMMENT ON COLUMN online_table_column.online_table_column IS 'id';
COMMENT ON COLUMN online_table_column.name IS '字段名称';
COMMENT ON COLUMN online_table_column.comments IS '字段描述';
COMMENT ON COLUMN online_table_column."length" IS '字段长度';
COMMENT ON COLUMN online_table_column.point_length IS '小数点';
COMMENT ON COLUMN online_table_column.default_value IS '默认值';
COMMENT ON COLUMN online_table_column."column_type" IS '字段类型';
COMMENT ON COLUMN online_table_column.column_pk IS '字段主键 0：否  1：是';
COMMENT ON COLUMN online_table_column.column_null IS '字段为空 0：否  1：是';
COMMENT ON COLUMN online_table_column.form_item IS '表单项 0：否  1：是';
COMMENT ON COLUMN online_table_column.form_required IS '表单必填 0：否  1：是';
COMMENT ON COLUMN online_table_column.form_input IS '表单控件';
COMMENT ON COLUMN online_table_column.form_default IS '表单控件默认值';
COMMENT ON COLUMN online_table_column.form_dict IS '表单字典';
COMMENT ON COLUMN online_table_column.grid_item IS '列表项 0：否  1：是';
COMMENT ON COLUMN online_table_column.grid_sort IS '列表排序 0：否  1：是';
COMMENT ON COLUMN online_table_column.query_item IS '查询项 0：否  1：是';
COMMENT ON COLUMN online_table_column.query_type IS '查询方式';
COMMENT ON COLUMN online_table_column.query_input IS '查询控件';
COMMENT ON COLUMN online_table_column.sort IS '排序';
COMMENT ON COLUMN online_table_column.table_id IS '表id';
COMMENT ON COLUMN qrtz_blob_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_blob_triggers.trigger_name IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN qrtz_blob_triggers.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_blob_triggers.blob_data IS '存放持久化Trigger对象';
COMMENT ON COLUMN qrtz_calendars.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_calendars.calendar_name IS '日历名称';
COMMENT ON COLUMN qrtz_calendars.calendar IS '存放持久化calendar对象';
COMMENT ON COLUMN qrtz_cron_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_cron_triggers.trigger_name IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN qrtz_cron_triggers.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_cron_triggers.cron_expression IS 'cron表达式';
COMMENT ON COLUMN qrtz_cron_triggers.time_zone_id IS '时区';
COMMENT ON COLUMN qrtz_fired_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_fired_triggers.entry_id IS '调度器实例id';
COMMENT ON COLUMN qrtz_fired_triggers.trigger_name IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN qrtz_fired_triggers.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_fired_triggers.instance_name IS '调度器实例名';
COMMENT ON COLUMN qrtz_fired_triggers.fired_time IS '触发的时间';
COMMENT ON COLUMN qrtz_fired_triggers.sched_time IS '定时器制定的时间';
COMMENT ON COLUMN qrtz_fired_triggers.priority IS '优先级';
COMMENT ON COLUMN qrtz_fired_triggers.state IS '状态';
COMMENT ON COLUMN qrtz_fired_triggers.job_name IS '任务名称';
COMMENT ON COLUMN qrtz_fired_triggers.job_group IS '任务组名';
COMMENT ON COLUMN qrtz_fired_triggers.is_nonconcurrent IS '是否并发';
COMMENT ON COLUMN qrtz_fired_triggers.requests_recovery IS '是否接受恢复执行';
COMMENT ON COLUMN qrtz_job_details.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_job_details.job_name IS '任务名称';
COMMENT ON COLUMN qrtz_job_details.job_group IS '任务组名';
COMMENT ON COLUMN qrtz_job_details.description IS '相关介绍';
COMMENT ON COLUMN qrtz_job_details.job_class_name IS '执行任务类名称';
COMMENT ON COLUMN qrtz_job_details.is_durable IS '是否持久化';
COMMENT ON COLUMN qrtz_job_details.is_nonconcurrent IS '是否并发';
COMMENT ON COLUMN qrtz_job_details.is_update_data IS '是否更新数据';
COMMENT ON COLUMN qrtz_job_details.requests_recovery IS '是否接受恢复执行';
COMMENT ON COLUMN qrtz_job_details.job_data IS '存放持久化job对象';
COMMENT ON COLUMN qrtz_locks.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_locks.lock_name IS '悲观锁名称';
COMMENT ON COLUMN qrtz_paused_trigger_grps.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_paused_trigger_grps.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_scheduler_state.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_scheduler_state.instance_name IS '实例名称';
COMMENT ON COLUMN qrtz_scheduler_state.last_checkin_time IS '上次检查时间';
COMMENT ON COLUMN qrtz_scheduler_state.checkin_interval IS '检查间隔时间';
COMMENT ON COLUMN qrtz_simple_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_simple_triggers.trigger_name IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN qrtz_simple_triggers.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_simple_triggers.repeat_count IS '重复的次数统计';
COMMENT ON COLUMN qrtz_simple_triggers.repeat_interval IS '重复的间隔时间';
COMMENT ON COLUMN qrtz_simple_triggers.times_triggered IS '已经触发的次数';
COMMENT ON COLUMN qrtz_simprop_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_simprop_triggers.trigger_name IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN qrtz_simprop_triggers.trigger_group IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN qrtz_simprop_triggers.str_prop_1 IS 'String类型的trigger的第一个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.str_prop_2 IS 'String类型的trigger的第二个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.str_prop_3 IS 'String类型的trigger的第三个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.int_prop_1 IS 'int类型的trigger的第一个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.int_prop_2 IS 'int类型的trigger的第二个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.long_prop_1 IS 'long类型的trigger的第一个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.long_prop_2 IS 'long类型的trigger的第二个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.dec_prop_1 IS 'decimal类型的trigger的第一个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.dec_prop_2 IS 'decimal类型的trigger的第二个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.bool_prop_1 IS 'Boolean类型的trigger的第一个参数';
COMMENT ON COLUMN qrtz_simprop_triggers.bool_prop_2 IS 'Boolean类型的trigger的第二个参数';
COMMENT ON COLUMN qrtz_triggers.sched_name IS '调度名称';
COMMENT ON COLUMN qrtz_triggers.trigger_name IS '触发器的名字';
COMMENT ON COLUMN qrtz_triggers.trigger_group IS '触发器所属组的名字';
COMMENT ON COLUMN qrtz_triggers.job_name IS 'qrtz_job_details表job_name的外键';
COMMENT ON COLUMN qrtz_triggers.job_group IS 'qrtz_job_details表job_group的外键';
COMMENT ON COLUMN qrtz_triggers.description IS '相关介绍';
COMMENT ON COLUMN qrtz_triggers.next_fire_time IS '上一次触发时间（毫秒）';
COMMENT ON COLUMN qrtz_triggers.prev_fire_time IS '下一次触发时间（默认为-1表示不触发）';
COMMENT ON COLUMN qrtz_triggers.priority IS '优先级';
COMMENT ON COLUMN qrtz_triggers.trigger_state IS '触发器状态';
COMMENT ON COLUMN qrtz_triggers.trigger_type IS '触发器的类型';
COMMENT ON COLUMN qrtz_triggers.start_time IS '开始时间';
COMMENT ON COLUMN qrtz_triggers.end_time IS '结束时间';
COMMENT ON COLUMN qrtz_triggers.calendar_name IS '日程表名称';
COMMENT ON COLUMN qrtz_triggers.misfire_instr IS '补偿执行的策略';
COMMENT ON COLUMN qrtz_triggers.job_data IS '存放持久化job对象';
COMMENT ON COLUMN schedule_job.schedule_job IS 'id';
COMMENT ON COLUMN schedule_job.job_name IS '名称';
COMMENT ON COLUMN schedule_job.job_group IS '分组';
COMMENT ON COLUMN schedule_job.bean_name IS 'spring bean名称';
COMMENT ON COLUMN schedule_job."method" IS '执行方法';
COMMENT ON COLUMN schedule_job.params IS '参数';
COMMENT ON COLUMN schedule_job.cron_expression IS 'cron表达式';
COMMENT ON COLUMN schedule_job.status IS '状态  0：暂停  1：正常';
COMMENT ON COLUMN schedule_job.concurrent IS '是否并发  0：禁止  1：允许';
COMMENT ON COLUMN schedule_job.remark IS '备注';
COMMENT ON COLUMN schedule_job.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN schedule_job.creator IS '创建者';
COMMENT ON COLUMN schedule_job.create_time IS '创建时间';
COMMENT ON COLUMN schedule_job.updater IS '更新者';
COMMENT ON COLUMN schedule_job.update_time IS '更新时间';
COMMENT ON COLUMN schedule_job_log.schedule_job_log IS 'id';
COMMENT ON COLUMN schedule_job_log.job_id IS '任务id';
COMMENT ON COLUMN schedule_job_log.job_name IS '任务名称';
COMMENT ON COLUMN schedule_job_log.job_group IS '任务组名';
COMMENT ON COLUMN schedule_job_log.bean_name IS 'spring bean名称';
COMMENT ON COLUMN schedule_job_log."method" IS '执行方法';
COMMENT ON COLUMN schedule_job_log.params IS '参数';
COMMENT ON COLUMN schedule_job_log.status IS '任务状态    0：失败    1：成功';
COMMENT ON COLUMN schedule_job_log.error IS '异常信息';
COMMENT ON COLUMN schedule_job_log.times IS '耗时(单位：毫秒)';
COMMENT ON COLUMN schedule_job_log.create_time IS '创建时间';
COMMENT ON COLUMN sms_log.sms_log IS 'id';
COMMENT ON COLUMN sms_log.platform_id IS '平台ID';
COMMENT ON COLUMN sms_log.platform IS '平台类型';
COMMENT ON COLUMN sms_log.mobile IS '手机号';
COMMENT ON COLUMN sms_log.params IS '参数';
COMMENT ON COLUMN sms_log.status IS '状态  0：失败   1：成功';
COMMENT ON COLUMN sms_log.error IS '异常信息';
COMMENT ON COLUMN sms_log.create_time IS '创建时间';
COMMENT ON COLUMN sms_platform.sms_platform IS 'id';
COMMENT ON COLUMN sms_platform.platform IS '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云';
COMMENT ON COLUMN sms_platform.sign_name IS '短信签名';
COMMENT ON COLUMN sms_platform.template_id IS '短信模板';
COMMENT ON COLUMN sms_platform.app_id IS '短信应用ID，如：腾讯云等';
COMMENT ON COLUMN sms_platform.sender_id IS '腾讯云国际短信、华为云等需要';
COMMENT ON COLUMN sms_platform.url IS '接入地址，如：华为云';
COMMENT ON COLUMN sms_platform.access_key IS 'AccessKey';
COMMENT ON COLUMN sms_platform.secret_key IS 'SecretKey';
COMMENT ON COLUMN sms_platform.status IS '状态  0：禁用   1：启用';
COMMENT ON COLUMN sms_platform.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sms_platform.creator IS '创建者';
COMMENT ON COLUMN sms_platform.create_time IS '创建时间';
COMMENT ON COLUMN sms_platform.updater IS '更新者';
COMMENT ON COLUMN sms_platform.update_time IS '更新时间';
COMMENT ON COLUMN sys_attachment.sys_attachment IS 'id';
COMMENT ON COLUMN sys_attachment.name IS '附件名称';
COMMENT ON COLUMN sys_attachment.url IS '附件地址';
COMMENT ON COLUMN sys_attachment.size IS '附件大小';
COMMENT ON COLUMN sys_attachment.platform IS '存储平台';
COMMENT ON COLUMN sys_attachment.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_attachment.creator IS '创建者';
COMMENT ON COLUMN sys_attachment.create_time IS '创建时间';
COMMENT ON COLUMN sys_attachment.updater IS '更新者';
COMMENT ON COLUMN sys_attachment.update_time IS '更新时间';
COMMENT ON COLUMN sys_attachment.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_dept.sys_dept IS 'id';
COMMENT ON COLUMN sys_dept.parent_id IS '上级ID';
COMMENT ON COLUMN sys_dept.name IS '机构名称';
COMMENT ON COLUMN sys_dept.note IS '备注';
COMMENT ON COLUMN sys_dept.sort IS '排序';
COMMENT ON COLUMN sys_dept.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_dept.creator IS '创建者';
COMMENT ON COLUMN sys_dept.create_time IS '创建时间';
COMMENT ON COLUMN sys_dept.updater IS '更新者';
COMMENT ON COLUMN sys_dept.update_time IS '更新时间';
COMMENT ON COLUMN sys_dept.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_dict_data.sys_dict_data IS 'id';
COMMENT ON COLUMN sys_dict_data.dict_type_id IS '字典类型ID';
COMMENT ON COLUMN sys_dict_data.dict_label IS '字典标签';
COMMENT ON COLUMN sys_dict_data.dict_value IS '字典值';
COMMENT ON COLUMN sys_dict_data.label_class IS '标签样式';
COMMENT ON COLUMN sys_dict_data.remark IS '备注';
COMMENT ON COLUMN sys_dict_data.sort IS '排序';
COMMENT ON COLUMN sys_dict_data.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_dict_data.creator IS '创建者';
COMMENT ON COLUMN sys_dict_data.create_time IS '创建时间';
COMMENT ON COLUMN sys_dict_data.updater IS '更新者';
COMMENT ON COLUMN sys_dict_data.update_time IS '更新时间';
COMMENT ON COLUMN sys_dict_type.sys_dict_type IS 'id';
COMMENT ON COLUMN sys_dict_type.dict_type IS '字典类型';
COMMENT ON COLUMN sys_dict_type.dict_name IS '字典名称';
COMMENT ON COLUMN sys_dict_type.dict_source IS '来源  0：字典数据  1：动态SQL';
COMMENT ON COLUMN sys_dict_type.dict_sql IS '动态SQL';
COMMENT ON COLUMN sys_dict_type.remark IS '备注';
COMMENT ON COLUMN sys_dict_type.sort IS '排序';
COMMENT ON COLUMN sys_dict_type.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_dict_type.creator IS '创建者';
COMMENT ON COLUMN sys_dict_type.create_time IS '创建时间';
COMMENT ON COLUMN sys_dict_type.updater IS '更新者';
COMMENT ON COLUMN sys_dict_type.update_time IS '更新时间';
COMMENT ON COLUMN sys_log_login.sys_log_login IS 'id';
COMMENT ON COLUMN sys_log_login.username IS '用户名';
COMMENT ON COLUMN sys_log_login.ip IS '登录IP';
COMMENT ON COLUMN sys_log_login.address IS '登录地点';
COMMENT ON COLUMN sys_log_login.user_agent IS 'User Agent';
COMMENT ON COLUMN sys_log_login.status IS '登录状态  0：失败   1：成功';
COMMENT ON COLUMN sys_log_login.operation IS '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误';
COMMENT ON COLUMN sys_log_login.create_time IS '创建时间';
COMMENT ON COLUMN sys_log_login.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_log_operate.sys_log_operate IS 'id';
COMMENT ON COLUMN sys_log_operate.module IS '模块名';
COMMENT ON COLUMN sys_log_operate.name IS '操作名';
COMMENT ON COLUMN sys_log_operate.req_uri IS '请求URI';
COMMENT ON COLUMN sys_log_operate.req_method IS '请求方法';
COMMENT ON COLUMN sys_log_operate.req_params IS '请求参数';
COMMENT ON COLUMN sys_log_operate.ip IS '操作IP';
COMMENT ON COLUMN sys_log_operate.address IS '登录地点';
COMMENT ON COLUMN sys_log_operate.user_agent IS 'User Agent';
COMMENT ON COLUMN sys_log_operate.operate_type IS '操作类型';
COMMENT ON COLUMN sys_log_operate.duration IS '执行时长';
COMMENT ON COLUMN sys_log_operate.status IS '操作状态  0：失败   1：成功';
COMMENT ON COLUMN sys_log_operate.user_id IS '用户ID';
COMMENT ON COLUMN sys_log_operate.real_name IS '操作人';
COMMENT ON COLUMN sys_log_operate.result_msg IS '返回消息';
COMMENT ON COLUMN sys_log_operate.create_time IS '创建时间';
COMMENT ON COLUMN sys_log_operate.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_menu.sys_menu IS 'id';
COMMENT ON COLUMN sys_menu.parent_id IS '上级ID，一级菜单为0';
COMMENT ON COLUMN sys_menu.name IS '菜单名称';
COMMENT ON COLUMN sys_menu.title IS '标题';
COMMENT ON COLUMN sys_menu.path IS '显示路径';
COMMENT ON COLUMN sys_menu.menu_path IS '页面组件路径';
COMMENT ON COLUMN sys_menu.status IS '状态 0停用 1有效';
COMMENT ON COLUMN sys_menu.hide_in_menu IS '菜单是否隐藏';
COMMENT ON COLUMN sys_menu.hide_in_tab IS '标签是否隐藏';
COMMENT ON COLUMN sys_menu.authority IS '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)';
COMMENT ON COLUMN sys_menu.badge IS '用于配置页面的徽标，会在菜单显示';
COMMENT ON COLUMN sys_menu.badge_type IS '用于配置页面的徽标类型，dot 为小红点，normal 为文本';
COMMENT ON COLUMN sys_menu.badge_variants IS '用于配置页面的徽标颜色';
COMMENT ON COLUMN sys_menu.affix_tab IS '用于配置页面是否固定标签页，固定后页面不可关闭';
COMMENT ON COLUMN sys_menu.link IS '用于配置外链跳转路径';
COMMENT ON COLUMN sys_menu."type" IS '类型:  catalog | menu | action';
COMMENT ON COLUMN sys_menu.open_style IS '打开方式   0：内部   1：外部';
COMMENT ON COLUMN sys_menu.icon IS '菜单图标';
COMMENT ON COLUMN sys_menu.keep_alive IS '菜单缓存';
COMMENT ON COLUMN sys_menu.sort IS '排序';
COMMENT ON COLUMN sys_menu.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_menu.mark IS '备注';
COMMENT ON COLUMN sys_menu.creator IS '创建者';
COMMENT ON COLUMN sys_menu.create_time IS '创建时间';
COMMENT ON COLUMN sys_menu.updater IS '更新者';
COMMENT ON COLUMN sys_menu.update_time IS '更新时间';
COMMENT ON COLUMN sys_message.title IS '标题';
COMMENT ON COLUMN sys_message.content IS '内容';
COMMENT ON COLUMN sys_message."type" IS '消息类别success普通消息warning警告error错误';
COMMENT ON COLUMN sys_message.state IS '状态1未读2已读';
COMMENT ON COLUMN sys_message.from_id IS '发送用户ID';
COMMENT ON COLUMN sys_message.from_name IS '发送人';
COMMENT ON COLUMN sys_message.to_id IS '接收用户ID';
COMMENT ON COLUMN sys_message.to_name IS '接收人';
COMMENT ON COLUMN sys_message.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_message.creator IS '创建者';
COMMENT ON COLUMN sys_message.create_time IS '创建时间';
COMMENT ON COLUMN sys_message.updater IS '更新者';
COMMENT ON COLUMN sys_message.update_time IS '更新时间';
COMMENT ON COLUMN sys_message.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_params.sys_params IS 'id';
COMMENT ON COLUMN sys_params.param_name IS '参数名称';
COMMENT ON COLUMN sys_params.param_type IS '系统参数   0：否   1：是';
COMMENT ON COLUMN sys_params.param_key IS '参数键';
COMMENT ON COLUMN sys_params.param_value IS '参数值';
COMMENT ON COLUMN sys_params.remark IS '备注';
COMMENT ON COLUMN sys_params.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_params.creator IS '创建者';
COMMENT ON COLUMN sys_params.create_time IS '创建时间';
COMMENT ON COLUMN sys_params.updater IS '更新者';
COMMENT ON COLUMN sys_params.update_time IS '更新时间';
COMMENT ON COLUMN sys_post.sys_post IS 'id';
COMMENT ON COLUMN sys_post.post_code IS '岗位编码';
COMMENT ON COLUMN sys_post.post_name IS '岗位名称';
COMMENT ON COLUMN sys_post.sort IS '排序';
COMMENT ON COLUMN sys_post.status IS '状态  0：停用   1：正常';
COMMENT ON COLUMN sys_post.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_post.creator IS '创建者';
COMMENT ON COLUMN sys_post.create_time IS '创建时间';
COMMENT ON COLUMN sys_post.updater IS '更新者';
COMMENT ON COLUMN sys_post.update_time IS '更新时间';
COMMENT ON COLUMN sys_post.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_role.sys_role IS 'id';
COMMENT ON COLUMN sys_role.code IS '角色编码';
COMMENT ON COLUMN sys_role.name IS '角色名称';
COMMENT ON COLUMN sys_role.remark IS '备注';
COMMENT ON COLUMN sys_role.data_scope IS '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据';
COMMENT ON COLUMN sys_role.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_role.is_system IS '是否系统内置角色1是';
COMMENT ON COLUMN sys_role.creator IS '创建者';
COMMENT ON COLUMN sys_role.create_time IS '创建时间';
COMMENT ON COLUMN sys_role.updater IS '更新者';
COMMENT ON COLUMN sys_role.update_time IS '更新时间';
COMMENT ON COLUMN sys_role.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_role_data_scope.sys_role_data_scope IS 'id';
COMMENT ON COLUMN sys_role_data_scope.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_data_scope.dept_id IS '部门ID';
COMMENT ON COLUMN sys_role_data_scope.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_role_data_scope.creator IS '创建者';
COMMENT ON COLUMN sys_role_data_scope.create_time IS '创建时间';
COMMENT ON COLUMN sys_role_data_scope.updater IS '更新者';
COMMENT ON COLUMN sys_role_data_scope.update_time IS '更新时间';
COMMENT ON COLUMN sys_role_data_scope.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_role_menu.sys_role_menu IS 'id';
COMMENT ON COLUMN sys_role_menu.role_id IS '角色ID';
COMMENT ON COLUMN sys_role_menu.menu_id IS '菜单ID';
COMMENT ON COLUMN sys_role_menu.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_role_menu.creator IS '创建者';
COMMENT ON COLUMN sys_role_menu.create_time IS '创建时间';
COMMENT ON COLUMN sys_role_menu.updater IS '更新者';
COMMENT ON COLUMN sys_role_menu.update_time IS '更新时间';
COMMENT ON COLUMN sys_tenant_member.sys_tenant_member IS 'id';
COMMENT ON COLUMN sys_tenant_member.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_tenant_member.tenant_name IS '租户名';
COMMENT ON COLUMN sys_tenant_member.dept_id IS '默认根部门';
COMMENT ON COLUMN sys_tenant_member.note IS '备注';
COMMENT ON COLUMN sys_tenant_member.status IS '状态  0：停用   1：正常';
COMMENT ON COLUMN sys_tenant_member.sort IS '排序';
COMMENT ON COLUMN sys_tenant_member.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_tenant_member.creator IS '创建者';
COMMENT ON COLUMN sys_tenant_member.create_time IS '创建时间';
COMMENT ON COLUMN sys_tenant_member.updater IS '更新者';
COMMENT ON COLUMN sys_tenant_member.update_time IS '更新时间';
COMMENT ON COLUMN sys_user.sys_user IS 'id';
COMMENT ON COLUMN sys_user.username IS '用户名';
COMMENT ON COLUMN sys_user.password IS '密码';
COMMENT ON COLUMN sys_user.real_name IS '姓名';
COMMENT ON COLUMN sys_user.avatar IS '头像';
COMMENT ON COLUMN sys_user.gender IS '性别   0：男   1：女   2：未知';
COMMENT ON COLUMN sys_user.email IS '邮箱';
COMMENT ON COLUMN sys_user.mobile IS '手机号';
COMMENT ON COLUMN sys_user.dept_id IS '部门ID';
COMMENT ON COLUMN sys_user.super_admin IS '超级管理员   0：否   1：是';
COMMENT ON COLUMN sys_user.status IS '状态  0：停用   1：正常';
COMMENT ON COLUMN sys_user.note IS '备注';
COMMENT ON COLUMN sys_user.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_user.creator IS '创建者';
COMMENT ON COLUMN sys_user.create_time IS '创建时间';
COMMENT ON COLUMN sys_user.updater IS '更新者';
COMMENT ON COLUMN sys_user.update_time IS '更新时间';
COMMENT ON COLUMN sys_user.pwd_modify_time IS '密码修改时间';
COMMENT ON COLUMN sys_user.user_key IS '用户密钥，用于第三方系统登录';
COMMENT ON COLUMN sys_user.tenant_id IS '租户ID';
COMMENT ON COLUMN sys_user_post.sys_user_post IS 'id';
COMMENT ON COLUMN sys_user_post.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_post.post_id IS '岗位ID';
COMMENT ON COLUMN sys_user_post.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_user_post.creator IS '创建者';
COMMENT ON COLUMN sys_user_post.create_time IS '创建时间';
COMMENT ON COLUMN sys_user_post.updater IS '更新者';
COMMENT ON COLUMN sys_user_post.update_time IS '更新时间';
COMMENT ON COLUMN sys_user_role.sys_user_role IS 'id';
COMMENT ON COLUMN sys_user_role.role_id IS '角色ID';
COMMENT ON COLUMN sys_user_role.user_id IS '用户ID';
COMMENT ON COLUMN sys_user_role.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_user_role.creator IS '创建者';
COMMENT ON COLUMN sys_user_role.create_time IS '创建时间';
COMMENT ON COLUMN sys_user_role.updater IS '更新者';
COMMENT ON COLUMN sys_user_role.update_time IS '更新时间';
COMMENT ON COLUMN sys_version_info.version_num IS '版本号';
COMMENT ON COLUMN sys_version_info.title IS '标题';
COMMENT ON COLUMN sys_version_info.content IS '发布内容';
COMMENT ON COLUMN sys_version_info.release_time IS '发布时间';
COMMENT ON COLUMN sys_version_info.is_curr_version IS '是否当前版本';
COMMENT ON COLUMN sys_version_info.cover_picture IS '封面图片';
COMMENT ON COLUMN sys_version_info.re_login IS '是否需要重新登录';
COMMENT ON COLUMN sys_version_info.remark IS '备注';
COMMENT ON COLUMN sys_version_info.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN sys_version_info.creator IS '创建者';
COMMENT ON COLUMN sys_version_info.create_time IS '创建时间';
COMMENT ON COLUMN sys_version_info.updater IS '更新者';
COMMENT ON COLUMN sys_version_info.update_time IS '更新时间';



-- ----------------------------
-- Indexes
-- ----------------------------
CREATE INDEX sched_name ON qrtz_triggers(sched_name, job_name, job_group);
CREATE INDEX idx_job_id ON schedule_job_log(job_id);
CREATE INDEX idx_pid ON sys_dept(parent_id);
CREATE INDEX idx_pid ON sys_menu(parent_id);
CREATE INDEX idx_01 ON sys_message(from_id);
CREATE INDEX idx_02 ON sys_message(to_id);
CREATE UNIQUE INDEX sys_params_key ON sys_params(param_key);
CREATE INDEX sys_post_post_code_IDX ON sys_post(post_code);
CREATE INDEX idx_role_id ON sys_role_data_scope(role_id);
CREATE INDEX idx_role_id ON sys_role_menu(role_id);
CREATE INDEX idx_menu_id ON sys_role_menu(menu_id);
CREATE UNIQUE INDEX idx_01 ON sys_tenant_member(tenant_id);
CREATE INDEX idx_user_id ON sys_user_post(user_id);
CREATE INDEX idx_post_id ON sys_user_post(post_id);
CREATE INDEX idx_role_id ON sys_user_role(role_id);
CREATE INDEX idx_user_id ON sys_user_role(user_id);
CREATE INDEX idx_01 ON sys_version_info(is_curr_version);
