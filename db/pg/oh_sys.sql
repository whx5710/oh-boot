/*
 Navicat Premium Dump SQL

 Source Server         : pg
 Source Server Type    : PostgreSQL
 Source Server Version : 180003 (180003)
 Source Host           : localhost:5432
 Source Catalog        : stepping-stone
 Source Schema         : oh_sys

 Target Server Type    : PostgreSQL
 Target Server Version : 180003 (180003)
 File Encoding         : 65001

 Date: 27/03/2026 18:22:47
*/


-- ----------------------------
-- Sequence structure for online_table_column_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."online_table_column_id_seq";
CREATE SEQUENCE "oh_sys"."online_table_column_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."online_table_column_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for schedule_job_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."schedule_job_id_seq";
CREATE SEQUENCE "oh_sys"."schedule_job_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."schedule_job_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for schedule_job_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."schedule_job_log_id_seq";
CREATE SEQUENCE "oh_sys"."schedule_job_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."schedule_job_log_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sms_log_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sms_log_id_seq";
CREATE SEQUENCE "oh_sys"."sms_log_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sms_log_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sms_platform_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sms_platform_id_seq";
CREATE SEQUENCE "oh_sys"."sms_platform_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sms_platform_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_attachment_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_attachment_id_seq";
CREATE SEQUENCE "oh_sys"."sys_attachment_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_attachment_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_dept_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_dept_id_seq";
CREATE SEQUENCE "oh_sys"."sys_dept_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_dept_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_dict_data_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_dict_data_id_seq";
CREATE SEQUENCE "oh_sys"."sys_dict_data_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_dict_data_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_dict_type_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_dict_type_id_seq";
CREATE SEQUENCE "oh_sys"."sys_dict_type_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_dict_type_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_log_login_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_log_login_id_seq";
CREATE SEQUENCE "oh_sys"."sys_log_login_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_log_login_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_log_operate_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_log_operate_id_seq";
CREATE SEQUENCE "oh_sys"."sys_log_operate_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_log_operate_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_menu_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_menu_id_seq";
CREATE SEQUENCE "oh_sys"."sys_menu_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_menu_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_message_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_message_id_seq";
CREATE SEQUENCE "oh_sys"."sys_message_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_message_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_params_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_params_id_seq";
CREATE SEQUENCE "oh_sys"."sys_params_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_params_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_post_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_post_id_seq";
CREATE SEQUENCE "oh_sys"."sys_post_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_post_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_role_data_scope_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_role_data_scope_id_seq";
CREATE SEQUENCE "oh_sys"."sys_role_data_scope_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_role_data_scope_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_role_id_seq";
CREATE SEQUENCE "oh_sys"."sys_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_role_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_role_menu_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_role_menu_id_seq";
CREATE SEQUENCE "oh_sys"."sys_role_menu_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_role_menu_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_tenant_member_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_tenant_member_id_seq";
CREATE SEQUENCE "oh_sys"."sys_tenant_member_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_tenant_member_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_user_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_user_id_seq";
CREATE SEQUENCE "oh_sys"."sys_user_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_user_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_user_post_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_user_post_id_seq";
CREATE SEQUENCE "oh_sys"."sys_user_post_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_user_post_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_user_role_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_user_role_id_seq";
CREATE SEQUENCE "oh_sys"."sys_user_role_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_user_role_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Sequence structure for sys_version_info_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "oh_sys"."sys_version_info_id_seq";
CREATE SEQUENCE "oh_sys"."sys_version_info_id_seq" 
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1;
ALTER SEQUENCE "oh_sys"."sys_version_info_id_seq" OWNER TO "postgres";

-- ----------------------------
-- Table structure for online_table_column
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."online_table_column";
CREATE TABLE "oh_sys"."online_table_column" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "comments" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "length" int4 NOT NULL,
  "point_length" int4 NOT NULL,
  "default_value" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "column_type" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "column_pk" int2,
  "column_null" int2,
  "form_item" int2,
  "form_required" int2,
  "form_input" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "form_default" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "form_dict" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "grid_item" int2,
  "grid_sort" int2,
  "query_item" int2,
  "query_type" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "query_input" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "table_id" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."online_table_column" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."online_table_column"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."online_table_column"."name" IS '字段名称';
COMMENT ON COLUMN "oh_sys"."online_table_column"."comments" IS '字段描述';
COMMENT ON COLUMN "oh_sys"."online_table_column"."length" IS '字段长度';
COMMENT ON COLUMN "oh_sys"."online_table_column"."point_length" IS '小数点';
COMMENT ON COLUMN "oh_sys"."online_table_column"."default_value" IS '默认值';
COMMENT ON COLUMN "oh_sys"."online_table_column"."column_type" IS '字段类型';
COMMENT ON COLUMN "oh_sys"."online_table_column"."column_pk" IS '字段主键 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."column_null" IS '字段为空 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."form_item" IS '表单项 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."form_required" IS '表单必填 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."form_input" IS '表单控件';
COMMENT ON COLUMN "oh_sys"."online_table_column"."form_default" IS '表单控件默认值';
COMMENT ON COLUMN "oh_sys"."online_table_column"."form_dict" IS '表单字典';
COMMENT ON COLUMN "oh_sys"."online_table_column"."grid_item" IS '列表项 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."grid_sort" IS '列表排序 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."query_item" IS '查询项 0：否  1：是';
COMMENT ON COLUMN "oh_sys"."online_table_column"."query_type" IS '查询方式';
COMMENT ON COLUMN "oh_sys"."online_table_column"."query_input" IS '查询控件';
COMMENT ON COLUMN "oh_sys"."online_table_column"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."online_table_column"."table_id" IS '表id';
COMMENT ON TABLE "oh_sys"."online_table_column" IS 'Online表单字段';

-- ----------------------------
-- Records of online_table_column
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_blob_triggers";
CREATE TABLE "oh_sys"."qrtz_blob_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "blob_data" bytea
)
;
ALTER TABLE "oh_sys"."qrtz_blob_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_blob_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_blob_triggers"."trigger_name" IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_blob_triggers"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_blob_triggers"."blob_data" IS '存放持久化Trigger对象';
COMMENT ON TABLE "oh_sys"."qrtz_blob_triggers" IS 'Blob类型的触发器表';

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_calendars";
CREATE TABLE "oh_sys"."qrtz_calendars" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "calendar" bytea NOT NULL
)
;
ALTER TABLE "oh_sys"."qrtz_calendars" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_calendars"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_calendars"."calendar_name" IS '日历名称';
COMMENT ON COLUMN "oh_sys"."qrtz_calendars"."calendar" IS '存放持久化calendar对象';
COMMENT ON TABLE "oh_sys"."qrtz_calendars" IS '日历信息表';

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_cron_triggers";
CREATE TABLE "oh_sys"."qrtz_cron_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "cron_expression" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "time_zone_id" varchar(80) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."qrtz_cron_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_cron_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_cron_triggers"."trigger_name" IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_cron_triggers"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_cron_triggers"."cron_expression" IS 'cron表达式';
COMMENT ON COLUMN "oh_sys"."qrtz_cron_triggers"."time_zone_id" IS '时区';
COMMENT ON TABLE "oh_sys"."qrtz_cron_triggers" IS 'Cron类型的触发器表';

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."qrtz_cron_triggers" ("sched_name", "trigger_name", "trigger_group", "cron_expression", "time_zone_id") VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_fired_triggers";
CREATE TABLE "oh_sys"."qrtz_fired_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "entry_id" varchar(95) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "fired_time" int8 NOT NULL,
  "sched_time" int8 NOT NULL,
  "priority" int4 NOT NULL,
  "state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "is_nonconcurrent" varchar(1) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "requests_recovery" varchar(1) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."qrtz_fired_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."entry_id" IS '调度器实例id';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."trigger_name" IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."instance_name" IS '调度器实例名';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."fired_time" IS '触发的时间';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."sched_time" IS '定时器制定的时间';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."priority" IS '优先级';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."state" IS '状态';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."job_name" IS '任务名称';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."job_group" IS '任务组名';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."is_nonconcurrent" IS '是否并发';
COMMENT ON COLUMN "oh_sys"."qrtz_fired_triggers"."requests_recovery" IS '是否接受恢复执行';
COMMENT ON TABLE "oh_sys"."qrtz_fired_triggers" IS '已触发的触发器表';

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_job_details";
CREATE TABLE "oh_sys"."qrtz_job_details" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "job_class_name" varchar(250) COLLATE "pg_catalog"."default" NOT NULL,
  "is_durable" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "is_nonconcurrent" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "is_update_data" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "requests_recovery" varchar(1) COLLATE "pg_catalog"."default" NOT NULL,
  "job_data" bytea
)
;
ALTER TABLE "oh_sys"."qrtz_job_details" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."job_name" IS '任务名称';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."job_group" IS '任务组名';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."description" IS '相关介绍';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."job_class_name" IS '执行任务类名称';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."is_durable" IS '是否持久化';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."is_nonconcurrent" IS '是否并发';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."is_update_data" IS '是否更新数据';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."requests_recovery" IS '是否接受恢复执行';
COMMENT ON COLUMN "oh_sys"."qrtz_job_details"."job_data" IS '存放持久化job对象';
COMMENT ON TABLE "oh_sys"."qrtz_job_details" IS '任务详细信息表';

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."qrtz_job_details" ("sched_name", "job_name", "job_group", "description", "job_class_name", "is_durable", "is_nonconcurrent", "is_update_data", "requests_recovery", "job_data") VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.finn.sys.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', E'\\254\\355\\000\\005sr\\000\\025org.quartz.JobDataMap\\237\\260\\203\\350\\277\\251\\260\\313\\002\\000\\000xr\\000&org.quartz.utils.StringKeyDirtyFlagMap\\202\\010\\350\\303\\373\\305](\\002\\000\\001Z\\000\\023allowsTransientDataxr\\000\\035org.quartz.utils.DirtyFlagMap\\023\\346.\\255(v\\012\\316\\002\\000\\002Z\\000\\005dirtyL\\000\\003mapt\\000\\017Ljava/util/Map;xp\\001sr\\000\\021java.util.HashMap\\005\\007\\332\\301\\303\\026`\\321\\003\\000\\002F\\000\\012loadFactorI\\000\\011thresholdxp?@\\000\\000\\000\\000\\000\\014w\\010\\000\\000\\000\\020\\000\\000\\000\\001t\\000\\015JOB_PARAM_KEYsr\\000,com.iris.sys.quartz.entity.ScheduleJobEntity\\035\\017\\337\\344\\207\\205\\250\\327\\002\\000\\012L\\000\\010beanNamet\\000\\022Ljava/lang/String;L\\000\\012concurrentt\\000\\023Ljava/lang/Integer;L\\000\\016cronExpressionq\\000~\\000\\011L\\000\\002idt\\000\\020Ljava/lang/Long;L\\000\\010jobGroupq\\000~\\000\\011L\\000\\007jobNameq\\000~\\000\\011L\\000\\006methodq\\000~\\000\\011L\\000\\006paramsq\\000~\\000\\011L\\000\\006remarkq\\000~\\000\\011L\\000\\006statusq\\000~\\000\\012xr\\000$com.iris.framework.entity.BaseEntity\\014\\254\\231\\332K\\244''-\\002\\000\\006L\\000\\012createTimet\\000\\031Ljava/time/LocalDateTime;L\\000\\007creatorq\\000~\\000\\013L\\000\\010dbStatusq\\000~\\000\\012L\\000\\010tenantIdq\\000~\\000\\011L\\000\\012updateTimeq\\000~\\000\\015L\\000\\007updaterq\\000~\\000\\013xr\\000\\035com.iris.core.entity.IDEntity\\273\\006\\002\\215f5\\240\\023\\002\\000\\001L\\000\\002idq\\000~\\000\\013xr\\000 com.iris.core.entity.SuperEntity\\037\\327w\\255~\\264\\332@\\002\\000\\000xppsr\\000\\015java.time');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_locks";
CREATE TABLE "oh_sys"."qrtz_locks" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "lock_name" varchar(40) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "oh_sys"."qrtz_locks" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_locks"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_locks"."lock_name" IS '悲观锁名称';
COMMENT ON TABLE "oh_sys"."qrtz_locks" IS '存储的悲观锁信息表';

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."qrtz_locks" ("sched_name", "lock_name") VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO "oh_sys"."qrtz_locks" ("sched_name", "lock_name") VALUES ('OhScheduler', 'TRIGGER_ACCESS');
COMMIT;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_paused_trigger_grps";
CREATE TABLE "oh_sys"."qrtz_paused_trigger_grps" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL
)
;
ALTER TABLE "oh_sys"."qrtz_paused_trigger_grps" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_paused_trigger_grps"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_paused_trigger_grps"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON TABLE "oh_sys"."qrtz_paused_trigger_grps" IS '暂停的触发器表';

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_scheduler_state";
CREATE TABLE "oh_sys"."qrtz_scheduler_state" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "instance_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "last_checkin_time" int8 NOT NULL,
  "checkin_interval" int8 NOT NULL
)
;
ALTER TABLE "oh_sys"."qrtz_scheduler_state" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_scheduler_state"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_scheduler_state"."instance_name" IS '实例名称';
COMMENT ON COLUMN "oh_sys"."qrtz_scheduler_state"."last_checkin_time" IS '上次检查时间';
COMMENT ON COLUMN "oh_sys"."qrtz_scheduler_state"."checkin_interval" IS '检查间隔时间';
COMMENT ON TABLE "oh_sys"."qrtz_scheduler_state" IS '调度器状态表';

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."qrtz_scheduler_state" ("sched_name", "instance_name", "last_checkin_time", "checkin_interval") VALUES ('OhScheduler', 'DESKTOP-IHJIP1I1737353556355', 1737353677179, 15000);
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_simple_triggers";
CREATE TABLE "oh_sys"."qrtz_simple_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "repeat_count" int8 NOT NULL,
  "repeat_interval" int8 NOT NULL,
  "times_triggered" int8 NOT NULL
)
;
ALTER TABLE "oh_sys"."qrtz_simple_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."trigger_name" IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."repeat_count" IS '重复的次数统计';
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."repeat_interval" IS '重复的间隔时间';
COMMENT ON COLUMN "oh_sys"."qrtz_simple_triggers"."times_triggered" IS '已经触发的次数';
COMMENT ON TABLE "oh_sys"."qrtz_simple_triggers" IS '简单触发器的信息表';

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_simprop_triggers";
CREATE TABLE "oh_sys"."qrtz_simprop_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "str_prop_1" varchar(512) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "str_prop_2" varchar(512) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "str_prop_3" varchar(512) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "int_prop_1" int4,
  "int_prop_2" int4,
  "long_prop_1" int8,
  "long_prop_2" int8,
  "dec_prop_1" numeric(13,4) DEFAULT NULL::numeric,
  "dec_prop_2" numeric(13,4) DEFAULT NULL::numeric,
  "bool_prop_1" varchar(1) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "bool_prop_2" varchar(1) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."qrtz_simprop_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."trigger_name" IS 'qrtz_triggers表trigger_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."trigger_group" IS 'qrtz_triggers表trigger_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."str_prop_1" IS 'String类型的trigger的第一个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."str_prop_2" IS 'String类型的trigger的第二个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."str_prop_3" IS 'String类型的trigger的第三个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."int_prop_1" IS 'int类型的trigger的第一个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."int_prop_2" IS 'int类型的trigger的第二个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."long_prop_1" IS 'long类型的trigger的第一个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."long_prop_2" IS 'long类型的trigger的第二个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."dec_prop_1" IS 'decimal类型的trigger的第一个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."dec_prop_2" IS 'decimal类型的trigger的第二个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."bool_prop_1" IS 'Boolean类型的trigger的第一个参数';
COMMENT ON COLUMN "oh_sys"."qrtz_simprop_triggers"."bool_prop_2" IS 'Boolean类型的trigger的第二个参数';
COMMENT ON TABLE "oh_sys"."qrtz_simprop_triggers" IS '同步机制的行锁表';

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."qrtz_triggers";
CREATE TABLE "oh_sys"."qrtz_triggers" (
  "sched_name" varchar(120) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "job_group" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "description" varchar(250) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "next_fire_time" int8,
  "prev_fire_time" int8,
  "priority" int4,
  "trigger_state" varchar(16) COLLATE "pg_catalog"."default" NOT NULL,
  "trigger_type" varchar(8) COLLATE "pg_catalog"."default" NOT NULL,
  "start_time" int8 NOT NULL,
  "end_time" int8,
  "calendar_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "misfire_instr" int2,
  "job_data" bytea
)
;
ALTER TABLE "oh_sys"."qrtz_triggers" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."sched_name" IS '调度名称';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."trigger_name" IS '触发器的名字';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."trigger_group" IS '触发器所属组的名字';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."job_name" IS 'qrtz_job_details表job_name的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."job_group" IS 'qrtz_job_details表job_group的外键';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."description" IS '相关介绍';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."next_fire_time" IS '上一次触发时间（毫秒）';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."prev_fire_time" IS '下一次触发时间（默认为-1表示不触发）';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."priority" IS '优先级';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."trigger_state" IS '触发器状态';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."trigger_type" IS '触发器的类型';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."start_time" IS '开始时间';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."end_time" IS '结束时间';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."calendar_name" IS '日程表名称';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."misfire_instr" IS '补偿执行的策略';
COMMENT ON COLUMN "oh_sys"."qrtz_triggers"."job_data" IS '存放持久化job对象';
COMMENT ON TABLE "oh_sys"."qrtz_triggers" IS '触发器详细信息表';

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group", "job_name", "job_group", "description", "next_fire_time", "prev_fire_time", "priority", "trigger_state", "trigger_type", "start_time", "end_time", "calendar_name", "misfire_instr", "job_data") VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1737353580000, -1, 5, 'PAUSED', 'CRON', 1737353556000, 0, NULL, 2, '');
COMMIT;

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."schedule_job";
CREATE TABLE "oh_sys"."schedule_job" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "job_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "job_group" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "bean_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "method" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "params" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "cron_expression" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "concurrent" int2,
  "remark" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."schedule_job" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."schedule_job"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."schedule_job"."job_name" IS '名称';
COMMENT ON COLUMN "oh_sys"."schedule_job"."job_group" IS '分组';
COMMENT ON COLUMN "oh_sys"."schedule_job"."bean_name" IS 'spring bean名称';
COMMENT ON COLUMN "oh_sys"."schedule_job"."method" IS '执行方法';
COMMENT ON COLUMN "oh_sys"."schedule_job"."params" IS '参数';
COMMENT ON COLUMN "oh_sys"."schedule_job"."cron_expression" IS 'cron表达式';
COMMENT ON COLUMN "oh_sys"."schedule_job"."status" IS '状态  0：暂停  1：正常';
COMMENT ON COLUMN "oh_sys"."schedule_job"."concurrent" IS '是否并发  0：禁止  1：允许';
COMMENT ON COLUMN "oh_sys"."schedule_job"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."schedule_job"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."schedule_job"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."schedule_job"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."schedule_job"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."schedule_job"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."schedule_job" IS '定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."schedule_job" ("id", "job_name", "job_group", "bean_name", "method", "params", "cron_expression", "status", "concurrent", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 1, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');
COMMIT;

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."schedule_job_log";
CREATE TABLE "oh_sys"."schedule_job_log" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "job_id" int8 NOT NULL,
  "job_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "job_group" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "bean_name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "method" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "params" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2 NOT NULL,
  "error" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "times" int8 NOT NULL,
  "create_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."schedule_job_log" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."job_id" IS '任务id';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."job_name" IS '任务名称';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."job_group" IS '任务组名';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."bean_name" IS 'spring bean名称';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."method" IS '执行方法';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."params" IS '参数';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."status" IS '任务状态    0：失败    1：成功';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."error" IS '异常信息';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."times" IS '耗时(单位：毫秒)';
COMMENT ON COLUMN "oh_sys"."schedule_job_log"."create_time" IS '创建时间';
COMMENT ON TABLE "oh_sys"."schedule_job_log" IS '定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sms_log";
CREATE TABLE "oh_sys"."sms_log" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "platform_id" int8,
  "platform" int2,
  "mobile" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "params" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "error" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "create_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sms_log" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sms_log"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sms_log"."platform_id" IS '平台ID';
COMMENT ON COLUMN "oh_sys"."sms_log"."platform" IS '平台类型';
COMMENT ON COLUMN "oh_sys"."sms_log"."mobile" IS '手机号';
COMMENT ON COLUMN "oh_sys"."sms_log"."params" IS '参数';
COMMENT ON COLUMN "oh_sys"."sms_log"."status" IS '状态  0：失败   1：成功';
COMMENT ON COLUMN "oh_sys"."sms_log"."error" IS '异常信息';
COMMENT ON COLUMN "oh_sys"."sms_log"."create_time" IS '创建时间';
COMMENT ON TABLE "oh_sys"."sms_log" IS '短信日志';

-- ----------------------------
-- Records of sms_log
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sms_platform";
CREATE TABLE "oh_sys"."sms_platform" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "platform" int2,
  "sign_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "template_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "app_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "sender_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "access_key" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "secret_key" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sms_platform" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sms_platform"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sms_platform"."platform" IS '平台类型  0：阿里云   1：腾讯云   2：七牛云   3：华为云';
COMMENT ON COLUMN "oh_sys"."sms_platform"."sign_name" IS '短信签名';
COMMENT ON COLUMN "oh_sys"."sms_platform"."template_id" IS '短信模板';
COMMENT ON COLUMN "oh_sys"."sms_platform"."app_id" IS '短信应用ID，如：腾讯云等';
COMMENT ON COLUMN "oh_sys"."sms_platform"."sender_id" IS '腾讯云国际短信、华为云等需要';
COMMENT ON COLUMN "oh_sys"."sms_platform"."url" IS '接入地址，如：华为云';
COMMENT ON COLUMN "oh_sys"."sms_platform"."access_key" IS 'AccessKey';
COMMENT ON COLUMN "oh_sys"."sms_platform"."secret_key" IS 'SecretKey';
COMMENT ON COLUMN "oh_sys"."sms_platform"."status" IS '状态  0：禁用   1：启用';
COMMENT ON COLUMN "oh_sys"."sms_platform"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sms_platform"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sms_platform"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sms_platform"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sms_platform"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sms_platform" IS '短信平台';

-- ----------------------------
-- Records of sms_platform
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_attachment";
CREATE TABLE "oh_sys"."sys_attachment" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "size" int8,
  "platform" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_attachment" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_attachment"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."name" IS '附件名称';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."url" IS '附件地址';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."size" IS '附件大小';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."platform" IS '存储平台';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_attachment" IS '附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dept";
CREATE TABLE "oh_sys"."sys_dept" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "parent_id" int8,
  "name" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(800) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_dept" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_dept"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_dept"."parent_id" IS '上级ID';
COMMENT ON COLUMN "oh_sys"."sys_dept"."name" IS '机构名称';
COMMENT ON COLUMN "oh_sys"."sys_dept"."note" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_dept"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_dept"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_dept"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_dept"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_dept"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_dept"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_dept"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_dept" IS '机构管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dict_data";
CREATE TABLE "oh_sys"."sys_dict_data" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "dict_type_id" int8 NOT NULL,
  "dict_label" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_value" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "label_class" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "remark" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_dict_data" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."dict_type_id" IS '字典类型ID';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."dict_label" IS '字典标签';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."dict_value" IS '字典值';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."label_class" IS '标签样式';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_dict_data"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_dict_data" IS '字典数据';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (32, 12, '开始', '1', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (33, 12, '暂停', '2', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (34, 12, '关闭', '3', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" ("id", "dict_type_id", "dict_label", "dict_value", "label_class", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dict_type";
CREATE TABLE "oh_sys"."sys_dict_type" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "dict_type" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "dict_source" int2 DEFAULT '0'::smallint,
  "dict_sql" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "remark" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_dict_type" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."dict_type" IS '字典类型';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."dict_name" IS '字典名称';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."dict_source" IS '来源  0：字典数据  1：动态SQL';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."dict_sql" IS '动态SQL';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_dict_type"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_dict_type" IS '字典类型';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 1, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_type" ("id", "dict_type", "dict_name", "dict_source", "dict_sql", "remark", "sort", "db_status", "creator", "create_time", "updater", "update_time") VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
COMMIT;

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_log_login";
CREATE TABLE "oh_sys"."sys_log_login" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "username" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "ip" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "address" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "user_agent" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "operation" int2,
  "create_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_log_login" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_log_login"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."username" IS '用户名';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."ip" IS '登录IP';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."address" IS '登录地点';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."user_agent" IS 'User Agent';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."status" IS '登录状态  0：失败   1：成功';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."operation" IS '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_log_login" IS '登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_log_operate";
CREATE TABLE "oh_sys"."sys_log_operate" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "module" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "req_uri" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "req_method" varchar(20) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "req_params" text COLLATE "pg_catalog"."default",
  "ip" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "address" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "user_agent" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "operate_type" int2,
  "duration" int4 NOT NULL,
  "status" int2,
  "user_id" int8,
  "real_name" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "result_msg" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "create_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_log_operate" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."module" IS '模块名';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."name" IS '操作名';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."req_uri" IS '请求URI';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."req_method" IS '请求方法';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."req_params" IS '请求参数';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."ip" IS '操作IP';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."address" IS '登录地点';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."user_agent" IS 'User Agent';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."operate_type" IS '操作类型';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."duration" IS '执行时长';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."status" IS '操作状态  0：失败   1：成功';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."user_id" IS '用户ID';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."real_name" IS '操作人';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."result_msg" IS '返回消息';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_log_operate"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_log_operate" IS '操作日志';

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_menu";
CREATE TABLE "oh_sys"."sys_menu" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "parent_id" int8,
  "name" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "title" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "path" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "menu_path" varchar(80) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int4 DEFAULT 1,
  "hide_in_menu" bool,
  "hide_in_tab" bool,
  "authority" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "badge" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "badge_type" varchar(20) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "badge_variants" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "affix_tab" bool,
  "link" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "type" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'menu'::character varying,
  "open_style" int2 DEFAULT 0,
  "icon" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "keep_alive" bool,
  "sort" int4,
  "db_status" int2 DEFAULT 1,
  "mark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_menu" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_menu"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_menu"."parent_id" IS '上级ID，一级菜单为0';
COMMENT ON COLUMN "oh_sys"."sys_menu"."name" IS '菜单名称';
COMMENT ON COLUMN "oh_sys"."sys_menu"."title" IS '标题';
COMMENT ON COLUMN "oh_sys"."sys_menu"."path" IS '显示路径';
COMMENT ON COLUMN "oh_sys"."sys_menu"."menu_path" IS '页面组件路径';
COMMENT ON COLUMN "oh_sys"."sys_menu"."status" IS '状态 0停用 1有效';
COMMENT ON COLUMN "oh_sys"."sys_menu"."hide_in_menu" IS '菜单是否隐藏';
COMMENT ON COLUMN "oh_sys"."sys_menu"."hide_in_tab" IS '标签是否隐藏';
COMMENT ON COLUMN "oh_sys"."sys_menu"."authority" IS '授权标识(多个用逗号分隔，如：sys:menu:list,sys:menu:save)';
COMMENT ON COLUMN "oh_sys"."sys_menu"."badge" IS '用于配置页面的徽标，会在菜单显示';
COMMENT ON COLUMN "oh_sys"."sys_menu"."badge_type" IS '用于配置页面的徽标类型，dot 为小红点，normal 为文本';
COMMENT ON COLUMN "oh_sys"."sys_menu"."badge_variants" IS '用于配置页面的徽标颜色';
COMMENT ON COLUMN "oh_sys"."sys_menu"."affix_tab" IS '用于配置页面是否固定标签页，固定后页面不可关闭';
COMMENT ON COLUMN "oh_sys"."sys_menu"."link" IS '用于配置外链跳转路径';
COMMENT ON COLUMN "oh_sys"."sys_menu"."type" IS '类型:  catalog | menu | action';
COMMENT ON COLUMN "oh_sys"."sys_menu"."open_style" IS '打开方式   0：内部   1：外部';
COMMENT ON COLUMN "oh_sys"."sys_menu"."icon" IS '菜单图标';
COMMENT ON COLUMN "oh_sys"."sys_menu"."keep_alive" IS '菜单缓存';
COMMENT ON COLUMN "oh_sys"."sys_menu"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_menu"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_menu"."mark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_menu"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_menu"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_menu"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_menu" IS '菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (15, 6, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'mdi:account-group', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:26');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 'f', 'f', 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 'f', 'f', 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (19, 6, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:container-services', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:49');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 'f', 'f', 'sys:dept:list,sys:dept:info', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (23, 5, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:delivery-parcel', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:47');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (27, 6, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:user-multiple', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:59');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (36, 6, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:calls-all', 't', 0, 1, NULL, 10000, '2025-05-20 17:54:23', 10000, '2025-06-22 14:20:26');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:workspace', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (13, 11, '新增修改菜单', '新增修改菜单', '', NULL, 1, 'f', 'f', 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:11:39');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 'f', 'f', '', NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'lucide:layout-dashboard', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-26 13:39:51');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'lucide:area-chart', 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (11, 5, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 'f', 'f', NULL, 'new', 'normal', 'default', 'f', NULL, 'menu', 0, 'carbon:menu', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:37');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 'f', 'f', '', NULL, 'dot', NULL, 'f', NULL, 'menu', 0, 'carbon:settings', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-07 10:16:33');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 'f', 'f', 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 'f', 'f', 'sys:params:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 'f', 'f', 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 'f', 'f', 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (30, 5, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:align-horizontal-left', 'f', 0, 1, NULL, 10000, '2025-05-19 09:30:58', 10000, '2025-06-22 14:20:57');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 'f', 'f', 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-19 09:32:48', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 'f', 'f', 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-19 09:33:38', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (33, 6, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog-publish', 'f', 0, 1, NULL, 10000, '2025-05-20 11:07:19', 10000, '2025-06-22 14:20:12');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 'f', 'f', 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 11:08:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 'f', 'f', 'sys:post:page,sys:post:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 17:55:22', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (39, 7, 'Attachment', '附件管理', '/system/attachment', '/system/attachment/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:file-storage', 'f', 0, 1, NULL, 10000, '2025-06-08 11:26:11', 10000, '2025-06-22 14:21:39');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (40, 39, '查询附件', '查询附件', '', NULL, 1, 'f', 'f', 'sys:attachment:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-08 11:27:23', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (41, 39, '新增修改附件', '新增修改附件', '', NULL, 1, 'f', 'f', 'sys:attachment:save,sys:attachment:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-08 11:28:09', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (42, 10, 'Log', '日志管理', '/system/log', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog', 'f', 0, 1, NULL, 10000, '2025-06-11 17:24:35', 10000, '2025-06-11 17:29:17');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (43, 42, 'LoginLog', '登录日志', '/system/loginLog', '/system/log/loginLog', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:change-catalog', 'f', 0, 1, NULL, 10000, '2025-06-11 17:25:59', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (44, 43, '查询登录日志', '查询登录日志', '', NULL, 1, 'f', 'f', 'sys:log:login', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-11 20:35:32', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (45, 42, 'OpLog', '操作日志', '/system/opLog', '/system/log/opLog', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:border-top', 'f', 0, 1, NULL, 10000, '2025-06-11 17:28:36', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (46, 45, '查询操作日志', '查询操作日志', '', NULL, 1, 'f', 'f', 'sys:operate:all', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-11 20:40:14', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (48, 47, 'SystemAppInterface', '客户端管理', '/system/app/list', '/system/app/list', 1, 'f', 'f', '', NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:multiuser-device', 'f', 0, 1, NULL, 10000, '2025-06-22 20:42:06', 10000, '2025-06-22 20:46:05');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (49, 48, '查询客户端', '查询客户端', '', NULL, 1, 'f', 'f', 'sys:app:page,sys:app:info,sys:function:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-22 20:46:30', 10000, '2025-06-23 15:13:02');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (50, 48, '新增修改客户端', '新增修改客户端', '', NULL, 1, 'f', 'f', 'sys:app:save,sys:app:update,sys:app:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-24 14:27:08', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (51, 47, 'SystemFunc', '接口管理', '/system/app/func', '/system/app/funcList', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:interface-usage-1', 'f', 0, 1, NULL, 10000, '2025-06-25 10:35:16', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (5, 10, 'SystemSetting', '系统设置', '/system/setting', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:settings-check', 'f', 0, 1, NULL, 10000, '2025-06-22 14:05:38', 10000, '2025-06-22 14:18:50');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (47, 7, 'SystemInterface', '接口管理', '/system/app/manage', '', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:ibm-cloud-vpc-client-vpn', 'f', 0, 1, NULL, 10000, '2025-06-22 20:39:43', 10000, '2025-06-22 20:43:16');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (7, 10, 'SystemApp', '系统应用', '/system/app', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:app', 'f', 0, 1, NULL, 10000, '2025-06-22 14:09:17', 10000, '2025-06-22 14:19:12');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 'f', 'f', 'tenant:member:save,tenant:member:update,sys:user:bindTenantUser,sys:user:unBindTenantUser', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 11:09:21', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (6, 10, 'SystemAuthority', '权限管理', '/system/authority', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:delivery-settings', 'f', 0, 1, NULL, 10000, '2025-06-22 14:07:19', 10000, '2025-06-22 14:19:02');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 'f', 'f', 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 17:56:26', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (52, 51, '查询接口', '查询接口', '', NULL, 1, 'f', 'f', 'sys:function:page,sys:function:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 10:36:10', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (12, 11, '查询菜单', '查询菜单', '', NULL, 1, 'f', 'f', 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:12:33');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 'f', 'f', 'sys:params:save,sys:params:update,sys:params:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (53, 51, '新增修改接口', '新增修改接口', '', NULL, 1, 'f', 'f', 'sys:function:save,sys:function:update,sys:function:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 11:00:12', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (54, 47, 'SystemAppLog', '接口日志', '/system/app/log', '/system/app/log', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog-publish', 'f', 0, 1, NULL, 10000, '2025-06-25 17:27:26', 10000, '2025-06-25 17:30:01');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (55, 54, '查询日志', '查询日志', '', NULL, 1, 'f', 'f', 'sys:app:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 17:28:11', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (56, 54, '删除日志', '删除日志', '', NULL, 1, 'f', 'f', 'sys:app:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 17:28:37', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (57, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 'f', 'f', 'sys:log:login:delete', NULL, NULL, NULL, 'f', NULL, 'menu', 0, NULL, 'f', 0, 0, NULL, 10000, '2025-06-25 19:45:26', 10000, '2025-09-18 13:32:58');
INSERT INTO "oh_sys"."sys_menu" ("id", "parent_id", "name", "title", "path", "menu_path", "status", "hide_in_menu", "hide_in_tab", "authority", "badge", "badge_type", "badge_variants", "affix_tab", "link", "type", "open_style", "icon", "keep_alive", "sort", "db_status", "mark", "creator", "create_time", "updater", "update_time") VALUES (33304128612466688, 7, 'Flow', '绘制流程', '/flow', '/system/flow/draw', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-02 21:38:43.022556', 10000, '2026-04-02 21:39:18.155933');
COMMIT;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_message";
CREATE TABLE "oh_sys"."sys_message" (
  "id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1
),
  "title" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "content" varchar(300) COLLATE "pg_catalog"."default" NOT NULL,
  "type" varchar(10) COLLATE "pg_catalog"."default" DEFAULT 'success'::character varying,
  "state" varchar(1) COLLATE "pg_catalog"."default" NOT NULL DEFAULT '1'::character varying,
  "from_id" int8,
  "from_name" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "to_id" int8 NOT NULL,
  "to_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_message" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_message"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_message"."title" IS '标题';
COMMENT ON COLUMN "oh_sys"."sys_message"."content" IS '内容';
COMMENT ON COLUMN "oh_sys"."sys_message"."type" IS '消息类别success普通消息warning警告error错误';
COMMENT ON COLUMN "oh_sys"."sys_message"."state" IS '状态1未读2已读';
COMMENT ON COLUMN "oh_sys"."sys_message"."from_id" IS '发送用户ID';
COMMENT ON COLUMN "oh_sys"."sys_message"."from_name" IS '发送人';
COMMENT ON COLUMN "oh_sys"."sys_message"."to_id" IS '接收用户ID';
COMMENT ON COLUMN "oh_sys"."sys_message"."to_name" IS '接收人';
COMMENT ON COLUMN "oh_sys"."sys_message"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_message"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_message"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_message"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_message"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_message" IS '系统消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_params";
CREATE TABLE "oh_sys"."sys_params" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "param_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "param_type" int2 NOT NULL,
  "param_key" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "param_value" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "remark" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_params" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_params"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_params"."param_name" IS '参数名称';
COMMENT ON COLUMN "oh_sys"."sys_params"."param_type" IS '系统参数   0：否   1：是';
COMMENT ON COLUMN "oh_sys"."sys_params"."param_key" IS '参数键';
COMMENT ON COLUMN "oh_sys"."sys_params"."param_value" IS '参数值';
COMMENT ON COLUMN "oh_sys"."sys_params"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_params"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_params"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_params"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_params"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_params"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_params" IS '参数管理';

-- ----------------------------
-- Records of sys_params
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_params" ("id", "param_name", "param_type", "param_key", "param_value", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_params" ("id", "param_name", "param_type", "param_key", "param_value", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO "oh_sys"."sys_params" ("id", "param_name", "param_type", "param_key", "param_value", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO "oh_sys"."sys_params" ("id", "param_name", "param_type", "param_key", "param_value", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');
COMMIT;

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_post";
CREATE TABLE "oh_sys"."sys_post" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "post_code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "post_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "status" int2,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_post" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_post"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_post"."post_code" IS '岗位编码';
COMMENT ON COLUMN "oh_sys"."sys_post"."post_name" IS '岗位名称';
COMMENT ON COLUMN "oh_sys"."sys_post"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_post"."status" IS '状态  0：停用   1：正常';
COMMENT ON COLUMN "oh_sys"."sys_post"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_post"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_post"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_post"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_post"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_post"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_post" IS '岗位管理';

-- ----------------------------
-- Records of sys_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role";
CREATE TABLE "oh_sys"."sys_role" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "data_scope" int2,
  "db_status" int2 DEFAULT '1'::smallint,
  "is_system" int2 NOT NULL DEFAULT '0'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_role" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_role"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_role"."code" IS '角色编码';
COMMENT ON COLUMN "oh_sys"."sys_role"."name" IS '角色名称';
COMMENT ON COLUMN "oh_sys"."sys_role"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_role"."data_scope" IS '数据范围  0：全部数据  1：本机构及子机构数据  2：本机构数据  3：本人数据  4：自定义数据';
COMMENT ON COLUMN "oh_sys"."sys_role"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_role"."is_system" IS '是否系统内置角色1是';
COMMENT ON COLUMN "oh_sys"."sys_role"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_role"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_role"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_role"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_role" IS '角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_role" ("id", "code", "name", "remark", "data_scope", "db_status", "is_system", "creator", "create_time", "updater", "update_time", "tenant_id") VALUES (1, 'Tenant', '租户角色', '系统内置', 0, 1, 1, 10000, '2025-01-01 01:23:45', 10000, '2025-06-07 21:53:07', '');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role_data_scope";
CREATE TABLE "oh_sys"."sys_role_data_scope" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "role_id" int8,
  "dept_id" int8,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_role_data_scope" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."role_id" IS '角色ID';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."dept_id" IS '部门ID';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_role_data_scope" IS '角色数据权限';

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role_menu";
CREATE TABLE "oh_sys"."sys_role_menu" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "role_id" int8 NOT NULL,
  "menu_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_role_menu" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."role_id" IS '角色ID';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."menu_id" IS '菜单ID';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_role_menu"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_role_menu" IS '角色菜单关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_tenant_member
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_tenant_member";
CREATE TABLE "oh_sys"."sys_tenant_member" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "tenant_name" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "dept_id" int8,
  "note" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "sort" int4,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_tenant_member" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."tenant_id" IS '租户ID';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."tenant_name" IS '租户名';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."dept_id" IS '默认根部门';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."note" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."status" IS '状态  0：停用   1：正常';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."sort" IS '排序';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_tenant_member"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_tenant_member" IS '租户信息';

-- ----------------------------
-- Records of sys_tenant_member
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user";
CREATE TABLE "oh_sys"."sys_user" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "username" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "password" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "real_name" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "avatar" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "gender" int2,
  "email" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "mobile" varchar(20) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "dept_id" int8,
  "super_admin" int2,
  "status" int2,
  "note" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "pwd_modify_time" timestamp(6),
  "user_key" varchar(60) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "tenant_id" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying
)
;
ALTER TABLE "oh_sys"."sys_user" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_user"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_user"."username" IS '用户名';
COMMENT ON COLUMN "oh_sys"."sys_user"."password" IS '密码';
COMMENT ON COLUMN "oh_sys"."sys_user"."real_name" IS '姓名';
COMMENT ON COLUMN "oh_sys"."sys_user"."avatar" IS '头像';
COMMENT ON COLUMN "oh_sys"."sys_user"."gender" IS '性别   0：男   1：女   2：未知';
COMMENT ON COLUMN "oh_sys"."sys_user"."email" IS '邮箱';
COMMENT ON COLUMN "oh_sys"."sys_user"."mobile" IS '手机号';
COMMENT ON COLUMN "oh_sys"."sys_user"."dept_id" IS '部门ID';
COMMENT ON COLUMN "oh_sys"."sys_user"."super_admin" IS '超级管理员   0：否   1：是';
COMMENT ON COLUMN "oh_sys"."sys_user"."status" IS '状态  0：停用   1：正常';
COMMENT ON COLUMN "oh_sys"."sys_user"."note" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_user"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_user"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_user"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_user"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_user"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_sys"."sys_user"."pwd_modify_time" IS '密码修改时间';
COMMENT ON COLUMN "oh_sys"."sys_user"."user_key" IS '用户密钥，用于第三方系统登录';
COMMENT ON COLUMN "oh_sys"."sys_user"."tenant_id" IS '租户ID';
COMMENT ON TABLE "oh_sys"."sys_user" IS '用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_user" ("id", "username", "password", "real_name", "avatar", "gender", "email", "mobile", "dept_id", "super_admin", "status", "note", "db_status", "creator", "create_time", "updater", "update_time", "pwd_modify_time", "user_key", "tenant_id") VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, NULL, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15', '123', NULL);
COMMIT;

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user_post";
CREATE TABLE "oh_sys"."sys_user_post" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "user_id" int8 NOT NULL,
  "post_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_user_post" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_user_post"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."user_id" IS '用户ID';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."post_id" IS '岗位ID';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_user_post"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_user_post" IS '用户岗位关系';

-- ----------------------------
-- Records of sys_user_post
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user_role";
CREATE TABLE "oh_sys"."sys_user_role" (
  "id" int8 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 9223372036854775807
START 1
CACHE 1
),
  "role_id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_user_role" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_user_role"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."role_id" IS '角色ID';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."user_id" IS '用户ID';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_user_role"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_user_role" IS '用户角色关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
COMMIT;

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_version_info";
CREATE TABLE "oh_sys"."sys_version_info" (
  "id" int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY (
INCREMENT 1
MINVALUE  1
MAXVALUE 2147483647
START 1
CACHE 1
),
  "version_num" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "title" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "content" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "release_time" timestamp(6) NOT NULL,
  "is_curr_version" int2 NOT NULL DEFAULT '0'::smallint,
  "cover_picture" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "re_login" int2,
  "remark" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
ALTER TABLE "oh_sys"."sys_version_info" OWNER TO "postgres";
COMMENT ON COLUMN "oh_sys"."sys_version_info"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."version_num" IS '版本号';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."title" IS '标题';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."content" IS '发布内容';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."release_time" IS '发布时间';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."is_curr_version" IS '是否当前版本';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."cover_picture" IS '封面图片';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."re_login" IS '是否需要重新登录';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."remark" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_version_info"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_version_info" IS '版本信息';

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
BEGIN;
INSERT INTO "oh_sys"."sys_version_info" ("id", "version_num", "title", "content", "release_time", "is_curr_version", "cover_picture", "re_login", "remark", "db_status", "creator", "create_time", "updater", "update_time") VALUES (1, '1.0.0', '初始版本', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1, '', 0, NULL, 1, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');
COMMIT;

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."online_table_column_id_seq"
OWNED BY "oh_sys"."online_table_column"."id";
SELECT setval('"oh_sys"."online_table_column_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."schedule_job_id_seq"
OWNED BY "oh_sys"."schedule_job"."id";
SELECT setval('"oh_sys"."schedule_job_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."schedule_job_log_id_seq"
OWNED BY "oh_sys"."schedule_job_log"."id";
SELECT setval('"oh_sys"."schedule_job_log_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sms_log_id_seq"
OWNED BY "oh_sys"."sms_log"."id";
SELECT setval('"oh_sys"."sms_log_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sms_platform_id_seq"
OWNED BY "oh_sys"."sms_platform"."id";
SELECT setval('"oh_sys"."sms_platform_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_attachment_id_seq"
OWNED BY "oh_sys"."sys_attachment"."id";
SELECT setval('"oh_sys"."sys_attachment_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_dept_id_seq"
OWNED BY "oh_sys"."sys_dept"."id";
SELECT setval('"oh_sys"."sys_dept_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_dict_data_id_seq"
OWNED BY "oh_sys"."sys_dict_data"."id";
SELECT setval('"oh_sys"."sys_dict_data_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_dict_type_id_seq"
OWNED BY "oh_sys"."sys_dict_type"."id";
SELECT setval('"oh_sys"."sys_dict_type_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_log_login_id_seq"
OWNED BY "oh_sys"."sys_log_login"."id";
SELECT setval('"oh_sys"."sys_log_login_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_log_operate_id_seq"
OWNED BY "oh_sys"."sys_log_operate"."id";
SELECT setval('"oh_sys"."sys_log_operate_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_menu_id_seq"
OWNED BY "oh_sys"."sys_menu"."id";
SELECT setval('"oh_sys"."sys_menu_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_message_id_seq"
OWNED BY "oh_sys"."sys_message"."id";
SELECT setval('"oh_sys"."sys_message_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_params_id_seq"
OWNED BY "oh_sys"."sys_params"."id";
SELECT setval('"oh_sys"."sys_params_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_post_id_seq"
OWNED BY "oh_sys"."sys_post"."id";
SELECT setval('"oh_sys"."sys_post_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_role_data_scope_id_seq"
OWNED BY "oh_sys"."sys_role_data_scope"."id";
SELECT setval('"oh_sys"."sys_role_data_scope_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_role_id_seq"
OWNED BY "oh_sys"."sys_role"."id";
SELECT setval('"oh_sys"."sys_role_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_role_menu_id_seq"
OWNED BY "oh_sys"."sys_role_menu"."id";
SELECT setval('"oh_sys"."sys_role_menu_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_tenant_member_id_seq"
OWNED BY "oh_sys"."sys_tenant_member"."id";
SELECT setval('"oh_sys"."sys_tenant_member_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_user_id_seq"
OWNED BY "oh_sys"."sys_user"."id";
SELECT setval('"oh_sys"."sys_user_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_user_post_id_seq"
OWNED BY "oh_sys"."sys_user_post"."id";
SELECT setval('"oh_sys"."sys_user_post_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_user_role_id_seq"
OWNED BY "oh_sys"."sys_user_role"."id";
SELECT setval('"oh_sys"."sys_user_role_id_seq"', 1, false);

-- ----------------------------
-- Alter sequences owned by
-- ----------------------------
ALTER SEQUENCE "oh_sys"."sys_version_info_id_seq"
OWNED BY "oh_sys"."sys_version_info"."id";
SELECT setval('"oh_sys"."sys_version_info_id_seq"', 1, false);

-- ----------------------------
-- Auto increment value for online_table_column
-- ----------------------------
SELECT setval('"oh_sys"."online_table_column_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table online_table_column
-- ----------------------------
ALTER TABLE "oh_sys"."online_table_column" ADD CONSTRAINT "online_table_column_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_blob_triggers" ADD CONSTRAINT "qrtz_blob_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_calendars
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_calendars" ADD CONSTRAINT "qrtz_calendars_pkey" PRIMARY KEY ("sched_name", "calendar_name");

-- ----------------------------
-- Primary Key structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_cron_triggers" ADD CONSTRAINT "qrtz_cron_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_fired_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_fired_triggers" ADD CONSTRAINT "qrtz_fired_triggers_pkey" PRIMARY KEY ("sched_name", "entry_id");

-- ----------------------------
-- Primary Key structure for table qrtz_job_details
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_job_details" ADD CONSTRAINT "qrtz_job_details_pkey" PRIMARY KEY ("sched_name", "job_name", "job_group");

-- ----------------------------
-- Primary Key structure for table qrtz_locks
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_locks" ADD CONSTRAINT "qrtz_locks_pkey" PRIMARY KEY ("sched_name", "lock_name");

-- ----------------------------
-- Primary Key structure for table qrtz_paused_trigger_grps
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_paused_trigger_grps" ADD CONSTRAINT "qrtz_paused_trigger_grps_pkey" PRIMARY KEY ("sched_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_scheduler_state
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_scheduler_state" ADD CONSTRAINT "qrtz_scheduler_state_pkey" PRIMARY KEY ("sched_name", "instance_name");

-- ----------------------------
-- Primary Key structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_simple_triggers" ADD CONSTRAINT "qrtz_simple_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_simprop_triggers" ADD CONSTRAINT "qrtz_simprop_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Primary Key structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_triggers" ADD CONSTRAINT "qrtz_triggers_pkey" PRIMARY KEY ("sched_name", "trigger_name", "trigger_group");

-- ----------------------------
-- Auto increment value for schedule_job
-- ----------------------------
SELECT setval('"oh_sys"."schedule_job_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table schedule_job
-- ----------------------------
ALTER TABLE "oh_sys"."schedule_job" ADD CONSTRAINT "schedule_job_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for schedule_job_log
-- ----------------------------
SELECT setval('"oh_sys"."schedule_job_log_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table schedule_job_log
-- ----------------------------
ALTER TABLE "oh_sys"."schedule_job_log" ADD CONSTRAINT "schedule_job_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sms_log
-- ----------------------------
SELECT setval('"oh_sys"."sms_log_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sms_log
-- ----------------------------
ALTER TABLE "oh_sys"."sms_log" ADD CONSTRAINT "sms_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sms_platform
-- ----------------------------
SELECT setval('"oh_sys"."sms_platform_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sms_platform
-- ----------------------------
ALTER TABLE "oh_sys"."sms_platform" ADD CONSTRAINT "sms_platform_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_attachment
-- ----------------------------
SELECT setval('"oh_sys"."sys_attachment_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_attachment
-- ----------------------------
ALTER TABLE "oh_sys"."sys_attachment" ADD CONSTRAINT "sys_attachment_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_dept
-- ----------------------------
SELECT setval('"oh_sys"."sys_dept_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_dept
-- ----------------------------
ALTER TABLE "oh_sys"."sys_dept" ADD CONSTRAINT "sys_dept_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_dict_data
-- ----------------------------
SELECT setval('"oh_sys"."sys_dict_data_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_dict_data
-- ----------------------------
ALTER TABLE "oh_sys"."sys_dict_data" ADD CONSTRAINT "sys_dict_data_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_dict_type
-- ----------------------------
SELECT setval('"oh_sys"."sys_dict_type_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_dict_type
-- ----------------------------
ALTER TABLE "oh_sys"."sys_dict_type" ADD CONSTRAINT "sys_dict_type_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_log_login
-- ----------------------------
SELECT setval('"oh_sys"."sys_log_login_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_log_login
-- ----------------------------
ALTER TABLE "oh_sys"."sys_log_login" ADD CONSTRAINT "sys_log_login_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_log_operate
-- ----------------------------
SELECT setval('"oh_sys"."sys_log_operate_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_log_operate
-- ----------------------------
ALTER TABLE "oh_sys"."sys_log_operate" ADD CONSTRAINT "sys_log_operate_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_menu
-- ----------------------------
SELECT setval('"oh_sys"."sys_menu_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE "oh_sys"."sys_menu" ADD CONSTRAINT "sys_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_message
-- ----------------------------
SELECT setval('"oh_sys"."sys_message_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_message
-- ----------------------------
ALTER TABLE "oh_sys"."sys_message" ADD CONSTRAINT "sys_message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_params
-- ----------------------------
SELECT setval('"oh_sys"."sys_params_id_seq"', 1, false);

-- ----------------------------
-- Uniques structure for table sys_params
-- ----------------------------
ALTER TABLE "oh_sys"."sys_params" ADD CONSTRAINT "sys_params_param_key_key" UNIQUE ("param_key");

-- ----------------------------
-- Primary Key structure for table sys_params
-- ----------------------------
ALTER TABLE "oh_sys"."sys_params" ADD CONSTRAINT "sys_params_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_post
-- ----------------------------
SELECT setval('"oh_sys"."sys_post_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_post
-- ----------------------------
ALTER TABLE "oh_sys"."sys_post" ADD CONSTRAINT "sys_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_role
-- ----------------------------
SELECT setval('"oh_sys"."sys_role_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE "oh_sys"."sys_role" ADD CONSTRAINT "sys_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_role_data_scope
-- ----------------------------
SELECT setval('"oh_sys"."sys_role_data_scope_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_role_data_scope
-- ----------------------------
ALTER TABLE "oh_sys"."sys_role_data_scope" ADD CONSTRAINT "sys_role_data_scope_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_role_menu
-- ----------------------------
SELECT setval('"oh_sys"."sys_role_menu_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_role_menu
-- ----------------------------
ALTER TABLE "oh_sys"."sys_role_menu" ADD CONSTRAINT "sys_role_menu_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_tenant_member
-- ----------------------------
SELECT setval('"oh_sys"."sys_tenant_member_id_seq"', 1, false);

-- ----------------------------
-- Uniques structure for table sys_tenant_member
-- ----------------------------
ALTER TABLE "oh_sys"."sys_tenant_member" ADD CONSTRAINT "sys_tenant_member_tenant_id_key" UNIQUE ("tenant_id");

-- ----------------------------
-- Primary Key structure for table sys_tenant_member
-- ----------------------------
ALTER TABLE "oh_sys"."sys_tenant_member" ADD CONSTRAINT "sys_tenant_member_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_user
-- ----------------------------
SELECT setval('"oh_sys"."sys_user_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_user
-- ----------------------------
ALTER TABLE "oh_sys"."sys_user" ADD CONSTRAINT "sys_user_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_user_post
-- ----------------------------
SELECT setval('"oh_sys"."sys_user_post_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_user_post
-- ----------------------------
ALTER TABLE "oh_sys"."sys_user_post" ADD CONSTRAINT "sys_user_post_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_user_role
-- ----------------------------
SELECT setval('"oh_sys"."sys_user_role_id_seq"', 1, false);

-- ----------------------------
-- Primary Key structure for table sys_user_role
-- ----------------------------
ALTER TABLE "oh_sys"."sys_user_role" ADD CONSTRAINT "sys_user_role_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Auto increment value for sys_version_info
-- ----------------------------
SELECT setval('"oh_sys"."sys_version_info_id_seq"', 1, false);

-- ----------------------------
-- Indexes structure for table sys_version_info
-- ----------------------------
CREATE INDEX "idx_01" ON "oh_sys"."sys_version_info" USING btree (
  "is_curr_version" "pg_catalog"."int2_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_version_info
-- ----------------------------
ALTER TABLE "oh_sys"."sys_version_info" ADD CONSTRAINT "sys_version_info_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Foreign Keys structure for table qrtz_blob_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_blob_triggers" ADD CONSTRAINT "qrtz_blob_triggers_fk" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "oh_sys"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table qrtz_cron_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_cron_triggers" ADD CONSTRAINT "qrtz_cron_triggers_fk" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "oh_sys"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simple_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_simple_triggers" ADD CONSTRAINT "qrtz_simple_triggers_fk" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "oh_sys"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table qrtz_simprop_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_simprop_triggers" ADD CONSTRAINT "qrtz_simprop_triggers_fk" FOREIGN KEY ("sched_name", "trigger_name", "trigger_group") REFERENCES "oh_sys"."qrtz_triggers" ("sched_name", "trigger_name", "trigger_group") ON DELETE RESTRICT ON UPDATE RESTRICT;

-- ----------------------------
-- Foreign Keys structure for table qrtz_triggers
-- ----------------------------
ALTER TABLE "oh_sys"."qrtz_triggers" ADD CONSTRAINT "qrtz_triggers_fk" FOREIGN KEY ("sched_name", "job_name", "job_group") REFERENCES "oh_sys"."qrtz_job_details" ("sched_name", "job_name", "job_group") ON DELETE RESTRICT ON UPDATE RESTRICT;
