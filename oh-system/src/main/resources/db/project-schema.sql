SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpmn_flow
-- ----------------------------
CREATE TABLE `bpmn_flow`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `key_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程code',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程名称',
  `xml` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'bpmn的xml字符串',
  `svg_str` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'svg图片字符串',
  `version_tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`key_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自定义流程表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bpmn_flow_node
-- ----------------------------
CREATE TABLE `bpmn_flow_node`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `proc_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义ID',
  `act_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环节ID',
  `node_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节名称',
  `note` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_01`(`proc_def_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环节定义表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for bpmn_task_record
-- ----------------------------
CREATE TABLE `bpmn_task_record` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `proc_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义ID',
  `proc_inst_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环节实例ID',
  `act_inst_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环节实例ID',
  `task_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '任务ID',
  `task_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环节key',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '环节名称',
  `from_act_inst_id` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来自于环节实例ID',
  `from_task_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来自于任务key',
  `from_task_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来自于环节ID',
  `from_task_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '来自于环节名称',
  `run_mark` int DEFAULT '0' COMMENT '当前标识，默认0，1标识当前环节',
  `assignee` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受理人ID',
  `assignee_name` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '受理人',
  `start_time` datetime DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `duration` bigint DEFAULT NULL COMMENT '时长',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `bpmn_task_record_proc_inst_id_IDX` (`proc_inst_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='环节运行记录表';

-- ----------------------------
-- Table structure for oh_project
-- ----------------------------
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
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_project_log
-- ----------------------------
CREATE TABLE `oh_project_log`  (
  `id` bigint(0) NOT NULL,
  `project_id` int(0) NULL DEFAULT NULL COMMENT '项目ID',
  `task_id` bigint(0) NULL DEFAULT NULL COMMENT '任务ID',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目、任务操作日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_task
-- ----------------------------
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
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_task_user
-- ----------------------------
CREATE TABLE `oh_task_user`  (
  `id` bigint(0) NOT NULL,
  `task_id` bigint(0) NOT NULL COMMENT '任务ID',
  `user_id` bigint(0) NOT NULL COMMENT '人员ID',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `person_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员类型1负责人2协作人',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint(0) NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint(0) NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`task_id`, `user_id`) USING BTREE,
  INDEX `idx_02`(`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务人员表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for oh_work_order
-- ----------------------------
CREATE TABLE `oh_work_order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工单编码',
  `order_source` varchar(2) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '工单来源',
  `report_time` datetime DEFAULT NULL COMMENT '上报时间',
  `incident_time` datetime DEFAULT NULL COMMENT '事发时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `title` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标题',
  `comment` varchar(2000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '位置',
  `geo_x` decimal(12,9) DEFAULT NULL COMMENT '经度',
  `geo_y` decimal(12,9) DEFAULT NULL COMMENT '纬度',
  `category` int DEFAULT NULL COMMENT '类别',
  `note` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `db_status` tinyint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` bigint DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_order_code` (`order_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='工单表';
SET FOREIGN_KEY_CHECKS = 1;
