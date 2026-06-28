/*
 Navicat Premium Dump SQL

 Source Server         : postgresql
 Source Server Type    : PostgreSQL
 Source Server Version : 180004 (180004)
 Source Host           : localhost:5432
 Source Catalog        : stepping_stone
 Source Schema         : oh_sys

 Target Server Type    : PostgreSQL
 Target Server Version : 180004 (180004)
 File Encoding         : 65001

 Date: 28/06/2026 09:20:53
*/


-- ----------------------------
-- Table structure for sms_log
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sms_log";
CREATE TABLE "oh_sys"."sms_log" (
  "id" int8 NOT NULL,
  "platform_id" int8,
  "platform" int2,
  "mobile" varchar(20) COLLATE "pg_catalog"."default" NOT NULL,
  "params" varchar(200) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "error" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "create_time" timestamp(6)
)
;
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

-- ----------------------------
-- Table structure for sms_platform
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sms_platform";
CREATE TABLE "oh_sys"."sms_platform" (
  "id" int8 NOT NULL,
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

-- ----------------------------
-- Table structure for sys_attachment
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_attachment";
CREATE TABLE "oh_sys"."sys_attachment" (
  "id" int8 NOT NULL,
  "name" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "url" varchar(255) COLLATE "pg_catalog"."default" NOT NULL,
  "size" int8,
  "tmp_flag" int2 NOT NULL DEFAULT 0,
  "platform" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_sys"."sys_attachment"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."name" IS '附件名称';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."url" IS '附件地址';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."size" IS '附件大小';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."tmp_flag" IS '临时文件标识，1为临时文件';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."platform" IS '存储平台';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_attachment"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_attachment" IS '附件管理';

-- ----------------------------
-- Records of sys_attachment
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dept
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dept";
CREATE TABLE "oh_sys"."sys_dept" (
  "id" int8 NOT NULL,
  "parent_id" int8,
  "name" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(800) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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
COMMENT ON TABLE "oh_sys"."sys_dept" IS '机构管理';

-- ----------------------------
-- Records of sys_dept
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dict_data";
CREATE TABLE "oh_sys"."sys_dict_data" (
  "id" int8 NOT NULL,
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
INSERT INTO "oh_sys"."sys_dict_data" VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (32, 12, '设施损坏', 'facility', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01', 10000, '2026-06-18 23:08:33.839675');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (33, 12, '环境卫生', 'hygiene', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16', 10000, '2026-06-18 23:08:52.339996');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (34, 12, '安全隐患', 'safety', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29', 10000, '2026-06-18 23:09:04.405446');
INSERT INTO "oh_sys"."sys_dict_data" VALUES (61231154140807168, 12, '秩序问题', 'order', NULL, NULL, 4, 1, 10000, '2026-06-18 23:10:44.767269', NULL, NULL);
INSERT INTO "oh_sys"."sys_dict_data" VALUES (61231247732506624, 12, '服务质量', 'service', NULL, NULL, 5, 1, 10000, '2026-06-18 23:11:07.081686', NULL, NULL);
INSERT INTO "oh_sys"."sys_dict_data" VALUES (61231295530795008, 12, '其他问题', 'other', NULL, NULL, 1, 1, 10000, '2026-06-18 23:11:18.477354', NULL, NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_dict_type";
CREATE TABLE "oh_sys"."sys_dict_type" (
  "id" int8 NOT NULL,
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
INSERT INTO "oh_sys"."sys_dict_type" VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO "oh_sys"."sys_dict_type" VALUES (12, 'event_type', '事件类型', 0, '', '事件类型', 12, 1, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');

-- ----------------------------
-- Table structure for sys_error_log
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_error_log";
CREATE TABLE "oh_sys"."sys_error_log" (
  "id" int8 NOT NULL,
  "err_code" varchar(20) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "msg" varchar(300) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "stack_info" text COLLATE "pg_catalog"."default",
  "err_time" timestamp(6) NOT NULL,
  "trace_id" varchar(80) COLLATE "pg_catalog"."default" NOT NULL,
  "note" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "score" numeric(5,2),
  "queue_size" int4,
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_sys"."sys_error_log"."err_code" IS '错误编码';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."msg" IS '错误提示';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."stack_info" IS '堆栈信息';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."err_time" IS '错误发生时间';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."trace_id" IS '链路跟踪ID';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."note" IS '备注';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."score" IS '队列拥挤程度0-10';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."queue_size" IS '队列大小';
COMMENT ON COLUMN "oh_sys"."sys_error_log"."create_time" IS '创建时间';
COMMENT ON TABLE "oh_sys"."sys_error_log" IS '系统错误日志';

-- ----------------------------
-- Records of sys_error_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_login
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_log_login";
CREATE TABLE "oh_sys"."sys_log_login" (
  "id" int8 NOT NULL,
  "username" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "ip" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "address" varchar(32) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "user_agent" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status" int2,
  "operation" int2,
  "create_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_sys"."sys_log_login"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."username" IS '用户名';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."ip" IS '登录IP';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."address" IS '登录地点';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."user_agent" IS 'User Agent';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."status" IS '登录状态  0：失败   1：成功';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."operation" IS '操作信息   0：登录成功   1：退出成功  2：验证码错误  3：账号密码错误';
COMMENT ON COLUMN "oh_sys"."sys_log_login"."create_time" IS '创建时间';
COMMENT ON TABLE "oh_sys"."sys_log_login" IS '登录日志';

-- ----------------------------
-- Records of sys_log_login
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log_operate
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_log_operate";
CREATE TABLE "oh_sys"."sys_log_operate" (
  "id" int8 NOT NULL,
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
  "create_time" timestamp(6)
)
;
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
COMMENT ON TABLE "oh_sys"."sys_log_operate" IS '操作日志';

-- ----------------------------
-- Records of sys_log_operate
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_menu";
CREATE TABLE "oh_sys"."sys_menu" (
  "id" int8 NOT NULL,
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
INSERT INTO "oh_sys"."sys_menu" VALUES (15, 6, 'Role', '角色管理', '/system/role', '/system/role/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'mdi:account-group', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:26');
INSERT INTO "oh_sys"."sys_menu" VALUES (16, 15, '查询角色', '查询角色', NULL, NULL, 1, 'f', 'f', 'sys:role:page,sys:role:list,sys:role:info', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (17, 15, '新增修改角色', '新增修改角色', NULL, NULL, 1, 'f', 'f', 'sys:role:save,sys:role:update,sys:role:delete', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (19, 6, 'Dept', '部门管理', '/system/dept', '/system/dept/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:container-services', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:49');
INSERT INTO "oh_sys"."sys_menu" VALUES (20, 19, '查询部门', '查询部门', NULL, NULL, 1, 'f', 'f', 'sys:dept:list,sys:dept:info', NULL, '', NULL, 'f', NULL, 'button', 0, NULL, 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (23, 5, 'Params', '参数管理', '/system/params', '/system/params/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:delivery-parcel', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:47');
INSERT INTO "oh_sys"."sys_menu" VALUES (27, 6, 'Users', '用户管理', '/system/user', '/system/user/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:user-multiple', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:19:59');
INSERT INTO "oh_sys"."sys_menu" VALUES (36, 6, 'Post', '岗位管理', '/system/post', '/system/post/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:calls-all', 't', 0, 1, NULL, 10000, '2025-05-20 17:54:23', 10000, '2025-06-22 14:20:26');
INSERT INTO "oh_sys"."sys_menu" VALUES (2, 1, 'WorkSpace', '工作台', '/workspace', '/dashboard/workspace/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:workspace', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (13, 11, '新增修改菜单', '新增修改菜单', '', NULL, 1, 'f', 'f', 'sys:menu:save,sys:menu:update,sys:menu:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:11:39');
INSERT INTO "oh_sys"."sys_menu" VALUES (1, 0, 'Dashboard', '概览', '/dashboard', NULL, 1, 'f', 'f', '', NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'lucide:layout-dashboard', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-26 13:39:51');
INSERT INTO "oh_sys"."sys_menu" VALUES (3, 1, 'Analytics', '分析页', '/analytics', '/dashboard/analytics/index', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'lucide:area-chart', 't', 1, 1, NULL, 10000, '2023-09-24 20:13:47', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (11, 5, 'Menu', '菜单管理', '/system/menu', '/system/menu/list', 1, 'f', 'f', NULL, 'new', 'normal', 'default', 'f', NULL, 'menu', 0, 'carbon:menu', 't', 0, 1, NULL, 10000, '2023-06-04 21:03:59', 10000, '2025-06-22 14:20:37');
INSERT INTO "oh_sys"."sys_menu" VALUES (10, 0, 'System', '系统管理', '/sys', NULL, 1, 'f', 'f', '', NULL, 'dot', NULL, 'f', NULL, 'menu', 0, 'carbon:settings', 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-05-07 10:16:33');
INSERT INTO "oh_sys"."sys_menu" VALUES (21, 19, '新增修改部门', '新增修改部门', '', NULL, 1, 'f', 'f', 'sys:dept:save,sys:dept:update,sys:dept:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" VALUES (24, 23, '查询参数', '查询参数', '', NULL, 1, 'f', 'f', 'sys:params:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" VALUES (28, 27, '查询用户', '查询用户', '', NULL, 1, 'f', 'f', 'sys:user:page,sys:user:info,sys:user:export', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" VALUES (29, 27, '新增修改用户', '新增修改用户', '', NULL, 1, 'f', 'f', 'sys:user:save,sys:user:update,sys:user:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" VALUES (30, 5, 'Dict', '字典管理', '/system/dict', '/system/dict/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:align-horizontal-left', 'f', 0, 1, NULL, 10000, '2025-05-19 09:30:58', 10000, '2025-06-22 14:20:57');
INSERT INTO "oh_sys"."sys_menu" VALUES (31, 30, '查询字典', '查询字典', '', NULL, 1, 'f', 'f', 'sys:dict:page,sys:dict:info,sys:dict:refreshTransCache', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-19 09:32:48', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (32, 30, '新增修改字典', '新增修改字典', '', NULL, 1, 'f', 'f', 'sys:dict:save,sys:dict:update,sys:dict:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-19 09:33:38', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (37, 36, '查询岗位', '查询岗位', '', NULL, 1, 'f', 'f', 'sys:post:page,sys:post:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 17:55:22', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (39, 7, 'Attachment', '附件管理', '/system/attachment', '/system/attachment/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:file-storage', 'f', 0, 1, NULL, 10000, '2025-06-08 11:26:11', 10000, '2025-06-22 14:21:39');
INSERT INTO "oh_sys"."sys_menu" VALUES (40, 39, '查询附件', '查询附件', '', NULL, 1, 'f', 'f', 'sys:attachment:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-08 11:27:23', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (41, 39, '新增修改附件', '新增修改附件', '', NULL, 1, 'f', 'f', 'sys:attachment:save,sys:attachment:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-08 11:28:09', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (42, 10, 'Log', '日志管理', '/system/log', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog', 'f', 0, 1, NULL, 10000, '2025-06-11 17:24:35', 10000, '2025-06-11 17:29:17');
INSERT INTO "oh_sys"."sys_menu" VALUES (43, 42, 'LoginLog', '登录日志', '/system/loginLog', '/system/log/loginLog', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:change-catalog', 'f', 0, 1, NULL, 10000, '2025-06-11 17:25:59', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (44, 43, '查询登录日志', '查询登录日志', '', NULL, 1, 'f', 'f', 'sys:log:login', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-11 20:35:32', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (45, 42, 'OpLog', '操作日志', '/system/opLog', '/system/log/opLog', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:border-top', 'f', 0, 1, NULL, 10000, '2025-06-11 17:28:36', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (46, 45, '查询操作日志', '查询操作日志', '', NULL, 1, 'f', 'f', 'sys:operate:all', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-11 20:40:14', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (48, 47, 'SystemAppInterface', '客户端管理', '/system/app/list', '/system/app/list', 1, 'f', 'f', '', NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:multiuser-device', 'f', 0, 1, NULL, 10000, '2025-06-22 20:42:06', 10000, '2025-06-22 20:46:05');
INSERT INTO "oh_sys"."sys_menu" VALUES (49, 48, '查询客户端', '查询客户端', '', NULL, 1, 'f', 'f', 'sys:app:page,sys:app:info,sys:function:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-22 20:46:30', 10000, '2025-06-23 15:13:02');
INSERT INTO "oh_sys"."sys_menu" VALUES (50, 48, '新增修改客户端', '新增修改客户端', '', NULL, 1, 'f', 'f', 'sys:app:save,sys:app:update,sys:app:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-24 14:27:08', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (51, 47, 'SystemFunc', '接口管理', '/system/app/func', '/system/app/funcList', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:interface-usage-1', 'f', 0, 1, NULL, 10000, '2025-06-25 10:35:16', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (5, 10, 'SystemSetting', '系统设置', '/system/setting', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:settings-check', 'f', 0, 1, NULL, 10000, '2025-06-22 14:05:38', 10000, '2025-06-22 14:18:50');
INSERT INTO "oh_sys"."sys_menu" VALUES (47, 7, 'SystemInterface', '接口管理', '/system/app/manage', '', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:ibm-cloud-vpc-client-vpn', 'f', 0, 1, NULL, 10000, '2025-06-22 20:39:43', 10000, '2025-06-22 20:43:16');
INSERT INTO "oh_sys"."sys_menu" VALUES (7, 10, 'SystemApp', '系统应用', '/system/app', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:app', 'f', 0, 1, NULL, 10000, '2025-06-22 14:09:17', 10000, '2025-06-22 14:19:12');
INSERT INTO "oh_sys"."sys_menu" VALUES (6, 10, 'SystemAuthority', '权限管理', '/system/authority', NULL, 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:delivery-settings', 'f', 0, 1, NULL, 10000, '2025-06-22 14:07:19', 10000, '2025-06-22 14:19:02');
INSERT INTO "oh_sys"."sys_menu" VALUES (38, 36, '新增修改岗位', '新增修改岗位', '', NULL, 1, 'f', 'f', 'sys:post:save,sys:post:update,sys:post:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-20 17:56:26', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (52, 51, '查询接口', '查询接口', '', NULL, 1, 'f', 'f', 'sys:function:page,sys:function:info', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 10:36:10', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (12, 11, '查询菜单', '查询菜单', '', NULL, 1, 'f', 'f', 'sys:menu:list,sys:menu:info,sys:role:menu', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 't', 0, 1, NULL, 10000, '2023-09-24 20:13:47', 10000, '2025-06-06 10:12:33');
INSERT INTO "oh_sys"."sys_menu" VALUES (25, 23, '新增修改参数', '新增修改参数', '', NULL, 1, 'f', 'f', 'sys:params:save,sys:params:update,sys:params:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-05-08 14:22:58', 10000, '2025-05-08 14:23:29');
INSERT INTO "oh_sys"."sys_menu" VALUES (34, 33, '查询租户', '查询租户', '', NULL, 1, 'f', 'f', 'tenant:member:page,tenant:member:info', NULL, NULL, NULL, 'f', NULL, 'menu', 0, NULL, 'f', 0, 0, NULL, 10000, '2025-05-20 11:08:47', 10000, '2026-06-13 21:34:24.165547');
INSERT INTO "oh_sys"."sys_menu" VALUES (33, 6, 'Tenant', '租户管理', '/system/tenant', '/system/tenant/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog-publish', 'f', 0, 0, NULL, 10000, '2025-05-20 11:07:19', 10000, '2026-06-13 21:34:27.411261');
INSERT INTO "oh_sys"."sys_menu" VALUES (53, 51, '新增修改接口', '新增修改接口', '', NULL, 1, 'f', 'f', 'sys:function:save,sys:function:update,sys:function:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 11:00:12', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (54, 47, 'SystemAppLog', '接口日志', '/system/app/log', '/system/app/log', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:catalog-publish', 'f', 0, 1, NULL, 10000, '2025-06-25 17:27:26', 10000, '2025-06-25 17:30:01');
INSERT INTO "oh_sys"."sys_menu" VALUES (55, 54, '查询日志', '查询日志', '', NULL, 1, 'f', 'f', 'sys:app:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 17:28:11', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (56, 54, '删除日志', '删除日志', '', NULL, 1, 'f', 'f', 'sys:app:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2025-06-25 17:28:37', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (57, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 'f', 'f', 'sys:log:login:delete', NULL, NULL, NULL, 'f', NULL, 'menu', 0, NULL, 'f', 0, 0, NULL, 10000, '2025-06-25 19:45:26', 10000, '2025-09-18 13:32:58');
INSERT INTO "oh_sys"."sys_menu" VALUES (33304128612466688, 7, 'Flow', '绘制流程', '/system/flow', '/system/flow/draw', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:flow-logs-vpc', 'f', 0, 1, NULL, 10000, '2026-04-02 21:38:43.022556', 10000, '2026-04-06 18:10:42.981877');
INSERT INTO "oh_sys"."sys_menu" VALUES (36768990499962880, 33304128612466688, '流程查询', '流程查询', '', NULL, 1, 'f', 'f', 'flow:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-12 11:06:50.469297', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (36769889070874624, 7, 'FlowManage', '流程管理', '/system/flow-manage', '/system/flow/list', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:workflow-automation', 'f', 0, 1, NULL, 10000, '2026-04-12 11:10:24.706571', 10000, '2026-04-12 11:15:00.57417');
INSERT INTO "oh_sys"."sys_menu" VALUES (36770623787106304, 36769889070874624, '流程管理新增修改', '流程管理新增修改', '', NULL, 1, 'f', 'f', 'flow:task:saveOrUpdate,flow:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-12 11:13:19.876445', 10000, '2026-04-12 11:17:29.143403');
INSERT INTO "oh_sys"."sys_menu" VALUES (34701521023139840, 33304128612466688, '流程新增修改', '流程新增修改', '', NULL, 1, 'f', 'f', 'flow:saveOrUpdate,flow:update', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-06 18:11:27.33507', 10000, '2026-04-15 21:21:57.624954');
INSERT INTO "oh_sys"."sys_menu" VALUES (38012448816496640, 36769889070874624, '环节查看', '环节查看', '', NULL, 1, 'f', 'f', 'flow:node:page', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-15 21:27:54.035171', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (38012908344442880, 36769889070874624, '环节修改', '环节修改', '', NULL, 1, 'f', 'f', 'flow:node:update', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-15 21:29:43.593548', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (43462997489745920, 42, 'ErrorLog', '错误日志', '/system/errLog', '/system/log/errorLog', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:ibm-knowledge-catalog-premium', 'f', 0, 1, NULL, 10000, '2026-04-30 22:26:26.104883', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (43463159524098048, 43462997489745920, '查询错误日志', '查询错误日志', '', NULL, 1, 'f', 'f', 'sys:error:log', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-04-30 22:27:04.737104', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (45101480209809408, 43, '删除登录日志', '删除登录日志', '', NULL, 1, 'f', 'f', 'sys:log:login:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-05-05 10:57:10.827503', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (45108924046639104, 43462997489745920, '删除错误日志', '删除错误日志', '', NULL, 1, 'f', 'f', 'sys:error:log:delete', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-05-05 11:26:45.576264', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (51801209756975104, 6, 'LockUser', '用户锁定', '/system/lockUser', '/system/user/lock', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:user-access-locked', 'f', 1, 1, NULL, 10000, '2026-05-23 22:39:30.82555', 10000, '2026-05-24 10:59:42.103162');
INSERT INTO "oh_sys"."sys_menu" VALUES (53949658837286912, 53949269693956096, '查询在线用户', '查询在线用户', '', NULL, 1, 'f', 'f', 'monitor:user:all,monitor:user:tokens', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 1, 1, NULL, 10000, '2026-05-29 20:56:41.003309', 10000, '2026-05-29 20:59:55.515648');
INSERT INTO "oh_sys"."sys_menu" VALUES (53951928605868032, 53949269693956096, '下线操作', '下线操作', '', NULL, 1, 'f', 'f', 'monitor:user:logout', NULL, NULL, NULL, 'f', NULL, 'button', 0, NULL, 'f', 0, 1, NULL, 10000, '2026-05-29 21:05:42.157191', NULL, NULL);
INSERT INTO "oh_sys"."sys_menu" VALUES (53949269693956096, 6, 'OnlineUser', '在线用户', '/system/online-user', '/system/user/monitor', 1, 'f', 'f', NULL, NULL, NULL, NULL, 'f', NULL, 'menu', 0, 'carbon:user-online', 'f', 1, 1, NULL, 10000, '2026-05-29 20:55:08.223114', 10000, '2026-05-29 21:07:06.986522');
INSERT INTO "oh_sys"."sys_menu" VALUES (35, 33, '新增修改租户', '新增修改租户', '', NULL, 1, 'f', 'f', 'tenant:member:save,tenant:member:update,sys:user:bindTenantUser,sys:user:unBindTenantUser', NULL, NULL, NULL, 'f', NULL, 'menu', 0, NULL, 'f', 0, 0, NULL, 10000, '2025-05-20 11:09:21', 10000, '2026-06-13 21:34:20.350493');

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_message";
CREATE TABLE "oh_sys"."sys_message" (
  "id" int4 NOT NULL,
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
  "update_time" timestamp(6)
)
;
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
COMMENT ON TABLE "oh_sys"."sys_message" IS '系统消息';

-- ----------------------------
-- Records of sys_message
-- ----------------------------

-- ----------------------------
-- Table structure for sys_params
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_params";
CREATE TABLE "oh_sys"."sys_params" (
  "id" int8 NOT NULL,
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
INSERT INTO "oh_sys"."sys_params" VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'false', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO "oh_sys"."sys_params" VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO "oh_sys"."sys_params" VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO "oh_sys"."sys_params" VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');

-- ----------------------------
-- Table structure for sys_post
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_post";
CREATE TABLE "oh_sys"."sys_post" (
  "id" int8 NOT NULL,
  "post_code" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "post_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int4,
  "status" int2,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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
COMMENT ON TABLE "oh_sys"."sys_post" IS '岗位管理';

-- ----------------------------
-- Records of sys_post
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role";
CREATE TABLE "oh_sys"."sys_role" (
  "id" int8 NOT NULL,
  "code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "data_scope" int2,
  "db_status" int2 DEFAULT '1'::smallint,
  "is_system" int2 NOT NULL DEFAULT '0'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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
COMMENT ON TABLE "oh_sys"."sys_role" IS '角色管理';

-- ----------------------------
-- Records of sys_role
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_data_scope
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role_data_scope";
CREATE TABLE "oh_sys"."sys_role_data_scope" (
  "id" int8 NOT NULL,
  "role_id" int8,
  "dept_id" int8,
  "db_status" int2 DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."id" IS 'id';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."role_id" IS '角色ID';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."dept_id" IS '部门ID';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."creator" IS '创建者';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."updater" IS '更新者';
COMMENT ON COLUMN "oh_sys"."sys_role_data_scope"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_sys"."sys_role_data_scope" IS '角色数据权限';

-- ----------------------------
-- Records of sys_role_data_scope
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_role_menu";
CREATE TABLE "oh_sys"."sys_role_menu" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "menu_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user";
CREATE TABLE "oh_sys"."sys_user" (
  "id" int8 NOT NULL,
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
  "user_type" varchar(2) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 0,
  "open_id" varchar(100) COLLATE "pg_catalog"."default"
)
;
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
COMMENT ON COLUMN "oh_sys"."sys_user"."user_type" IS '用户类型，0普通用户1微信小程序用户';
COMMENT ON COLUMN "oh_sys"."sys_user"."open_id" IS '外部用户ID';
COMMENT ON TABLE "oh_sys"."sys_user" IS '用户管理';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO "oh_sys"."sys_user" VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, NULL, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15', '123', '0', NULL);

-- ----------------------------
-- Table structure for sys_user_post
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user_post";
CREATE TABLE "oh_sys"."sys_user_post" (
  "id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "post_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_user_role";
CREATE TABLE "oh_sys"."sys_user_role" (
  "id" int8 NOT NULL,
  "role_id" int8 NOT NULL,
  "user_id" int8 NOT NULL,
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
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

-- ----------------------------
-- Table structure for sys_version_info
-- ----------------------------
DROP TABLE IF EXISTS "oh_sys"."sys_version_info";
CREATE TABLE "oh_sys"."sys_version_info" (
  "id" int8 NOT NULL,
  "version_num" varchar(30) COLLATE "pg_catalog"."default" NOT NULL,
  "title" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "content" varchar(500) COLLATE "pg_catalog"."default" NOT NULL,
  "release_time" timestamp(6) NOT NULL,
  "is_curr_version" bool,
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
INSERT INTO "oh_sys"."sys_version_info" VALUES (1, '1.0.0', '初始版本', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 't', '', 0, NULL, 1, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');

-- ----------------------------
-- Indexes structure for table sys_dept
-- ----------------------------
CREATE INDEX "idx_dept_parent_id" ON "oh_sys"."sys_dept" USING btree (
  "parent_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_dict_data
-- ----------------------------
CREATE INDEX "idx_dict_data_type" ON "oh_sys"."sys_dict_data" USING btree (
  "dict_type_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_dict_type
-- ----------------------------
CREATE INDEX "idx_dict_type" ON "oh_sys"."sys_dict_type" USING btree (
  "dict_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_error_log
-- ----------------------------
CREATE INDEX "err_log_01" ON "oh_sys"."sys_error_log" USING btree (
  "trace_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "err_log_02" ON "oh_sys"."sys_error_log" USING btree (
  "err_time" "pg_catalog"."timestamp_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table sys_error_log
-- ----------------------------
ALTER TABLE "oh_sys"."sys_error_log" ADD CONSTRAINT "sys_error_log_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table sys_params
-- ----------------------------
CREATE INDEX "idx_params_key" ON "oh_sys"."sys_params" USING btree (
  "param_key" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_post
-- ----------------------------
CREATE INDEX "idx_post_code" ON "oh_sys"."sys_post" USING btree (
  "post_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------
CREATE INDEX "idx_role_code" ON "oh_sys"."sys_role" USING btree (
  "code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Indexes structure for table sys_user
-- ----------------------------
CREATE INDEX "idx_user_name" ON "oh_sys"."sys_user" USING btree (
  "username" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
CREATE INDEX "idx_user_type" ON "oh_sys"."sys_user" USING btree (
  "user_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);
