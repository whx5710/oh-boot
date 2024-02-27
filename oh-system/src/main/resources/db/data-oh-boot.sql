-- ----------------------------
-- Records of gen_base_class
-- ----------------------------
INSERT INTO `gen_base_class` VALUES (1, 'com.iris.framework.mybatis.entity', 'BaseEntity', 'id,creator,create_time,updater,update_time,version,deleted', '使用该基类，则需要表里有这些字段', '2023-06-15 05:10:26');

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
-- Records of gen_project_modify
-- ----------------------------
INSERT INTO `gen_project_modify` VALUES (1, 'oh-boot', 'oh', 'com.iris', 'D:/oh/oh-boot', 'oh-boot', 'baba', 'com.iris', '.git,.idea,target,logs', 'java,xml,yml,txt', NULL, '2023-06-15 05:10:26');
INSERT INTO `gen_project_modify` VALUES (2, 'oh-cloud', 'oh', 'com.iris', 'D:/oh/oh-cloud', 'oh-cloud', 'baba', 'com.iris', '.git,.idea,target,logs', 'java,xml,yml,txt', NULL, '2023-06-15 05:10:26');

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 0, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');
-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '停用', '0', 'danger', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (2, 1, '正常', '1', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (3, 2, '男', '0', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (4, 2, '女', '1', 'success', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (5, 2, '未知', '2', 'warning', '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (6, 3, '正常', '1', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (7, 3, '停用', '0', 'danger', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (8, 4, '全部数据', '0', '', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (10, 4, '本机构数据', '2', '', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (11, 4, '本人数据', '3', '', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (12, 4, '自定义数据', '4', '', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (13, 5, '禁用', '0', 'danger', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (14, 5, '启用', '1', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (15, 6, '失败', '0', 'danger', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (16, 6, '成功', '1', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (21, 8, '否', '0', 'primary', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (22, 8, '是', '1', 'danger', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (23, 9, '是', '1', 'danger', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (24, 9, '否', '0', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (25, 10, '其它', '0', 'info', '', 10, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (26, 10, '查询', '1', 'primary', '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (27, 10, '新增', '2', 'success', '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (28, 10, '修改', '3', 'warning', '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (29, 10, '删除', '4', 'danger', '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (30, 10, '导出', '5', 'info', '', 4, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (31, 10, '导入', '6', 'info', '', 5, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (32, 12, '开始', '1', NULL, '', 1, 0, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO `sys_dict_data` VALUES (33, 12, '暂停', '2', NULL, '', 2, 0, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO `sys_dict_data` VALUES (34, 12, '关闭', '3', NULL, '', 3, 0, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO `sys_dict_data` VALUES (35, 13, '默认', 'default', '', '', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (36, 13, '系统', 'system', '', '', 1, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (37, 14, '暂停', '0', 'danger', '', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (38, 14, '正常', '1', 'primary', '', 1, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (39, 15, '阿里云', '0', '', '', 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (40, 15, '腾讯云', '1', '', '', 1, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (41, 15, '七牛云', '2', '', '', 2, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (42, 15, '华为云', '3', '', '', 3, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 0, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO `sys_dict_type` VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', '', 0, 0, 'icon-Report', 1, 0, 10000, '2023-09-24 20:13:47', 10000, '2023-09-24 20:13:47');
INSERT INTO `sys_menu` VALUES (2, 1, '系统设置', '', '', 0, 0, 'icon-setting', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:15:58');
INSERT INTO `sys_menu` VALUES (3, 2, '菜单管理', 'sys/menu/index', NULL, 0, 0, 'icon-menu', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (4, 3, '查看', '', 'sys:menu:list', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (5, 3, '新增', '', 'sys:menu:save', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (6, 3, '修改', '', 'sys:menu:update,sys:menu:info', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (7, 3, '删除', '', 'sys:menu:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (8, 2, '数据字典', 'sys/dict/type', '', 0, 0, 'icon-insertrowabove', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (9, 8, '查询', '', 'sys:dict:page', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (10, 8, '新增', '', 'sys:dict:save', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (11, 8, '修改', '', 'sys:dict:update,sys:dict:info', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (12, 8, '删除', '', 'sys:dict:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (13, 1, '权限管理', '', '', 0, 0, 'icon-safetycertificate', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:08');
INSERT INTO `sys_menu` VALUES (14, 13, '岗位管理', 'sys/post/index', '', 0, 0, 'icon-solution', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (15, 14, '查询', '', 'sys:post:page', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (16, 14, '新增', '', 'sys:post:save', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (17, 14, '修改', '', 'sys:post:update,sys:post:info', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (18, 14, '删除', '', 'sys:post:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (19, 13, '机构管理', 'sys/org/index', '', 0, 0, 'icon-cluster', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (20, 19, '查询', '', 'sys:org:list', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (21, 19, '新增', '', 'sys:org:save', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (22, 19, '修改', '', 'sys:org:update,sys:org:info', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (23, 19, '删除', '', 'sys:org:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (24, 13, '角色管理', 'sys/role/index', '', 0, 0, 'icon-team', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (25, 24, '查询', '', 'sys:role:page', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (26, 24, '新增', '', 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (27, 24, '修改', '', 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (28, 24, '删除', '', 'sys:role:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (29, 13, '用户管理', 'sys/user/index', '', 0, 0, 'icon-user', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (30, 29, '查询', '', 'sys:user:page', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (31, 29, '新增', '', 'sys:user:save,sys:role:list', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (32, 29, '修改', '', 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (33, 29, '删除', '', 'sys:user:delete', 1, 0, '', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (34, 1, '应用管理', '', '', 0, 0, 'icon-appstore', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:16');
INSERT INTO `sys_menu` VALUES (35, 2, '附件管理', 'sys/attachment/index', NULL, 0, 0, 'icon-folder-fill', 3, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (36, 35, '查看', '', 'sys:attachment:page', 1, 0, '', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (37, 35, '上传', '', 'sys:attachment:save', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (38, 35, '删除', '', 'sys:attachment:delete', 1, 0, '', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (39, 1, '日志管理', '', '', 0, 0, 'icon-filedone', 4, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:24');
INSERT INTO `sys_menu` VALUES (40, 39, '登录日志', 'sys/log/login', 'sys:log:login', 0, 0, 'icon-solution', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (41, 29, '导入', '', 'sys:user:import', 1, 0, '', 5, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (42, 29, '导出', '', 'sys:user:export', 1, 0, '', 6, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (43, 2, '参数管理', 'sys/params/index', 'sys:params:all', 0, 0, 'icon-filedone', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (44, 2, '接口文档', '{{apiUrl}}/doc.html', NULL, 0, 1, 'icon-file-text-fill', 10, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (45, 0, '在线开发', '', '', 0, 0, 'icon-cloud', 2, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (46, 45, 'Online表单开发', 'online/table/index', 'online:table:all', 0, 0, 'icon-table', 0, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (47, 39, '操作日志', 'sys/log/operate', 'sys:operate:all', 0, 0, 'icon-file-text', 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (48, 69, '踢出', '', 'monitor:user:user', 1, 0, '', 1, 0, 10000, '2023-09-25 21:16:17', 10000, '2023-09-25 21:16:17');
INSERT INTO `sys_menu` VALUES (49, 34, '流程管理', '', '', 0, 0, 'icon-switchuser', 1, 0, 10000, '2023-12-23 16:02:38', NULL, NULL);
INSERT INTO `sys_menu` VALUES (50, 49, '流程设计', 'workflow/index', 'flow:saveOrUpdate', 0, 0, 'icon-expand', 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-07-16 11:54:26');
INSERT INTO `sys_menu` VALUES (51, 49, '自定义流程', 'workflow/index-list', 'flow:page,flow:delete', 0, 0, 'icon-menu', 0, 0, 10000, '2023-12-18 04:48:26', 10000, '2023-12-18 04:48:26');
INSERT INTO `sys_menu` VALUES (59, 2, '定时任务', 'quartz/schedule/index', NULL, 0, 0, 'icon-reloadtime', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (60, 59, '查看', '', 'schedule:page', 1, 0, '', 0, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (61, 59, '新增', '', 'schedule:save', 1, 0, '', 1, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (62, 59, '修改', '', 'schedule:update,schedule:info', 1, 0, '', 2, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (63, 59, '删除', '', 'schedule:delete', 1, 0, '', 3, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (64, 59, '立即运行', '', 'schedule:run', 1, 0, '', 2, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (65, 59, '日志', '', 'schedule:log', 1, 0, '', 4, 0, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (66, 34, '系统监控', '', '', 0, 0, 'icon-Report', 10, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (67, 66, '服务监控', 'monitor/server/index', 'monitor:server:all', 0, 0, 'icon-sever', 0, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (68, 66, '缓存监控', 'monitor/cache/index', 'monitor:cache:all', 0, 0, 'icon-fund-fill', 2, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (69, 66, '用户监控', 'monitor/user/index', 'monitor:user:all', 0, 0, 'icon-user', 3, 0, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (70, 34, '消息管理', '', '', 0, 0, 'icon-message', 2, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (71, 70, '短信日志', 'message/sms/log/index', 'sms:log', 0, 0, 'icon-detail', 1, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (72, 70, '短信平台', 'message/sms/platform/index', NULL, 0, 0, 'icon-whatsapp', 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (73, 72, '查看', '', 'sms:platform:page', 1, 0, '', 0, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (74, 72, '新增', '', 'sms:platform:save', 1, 0, '', 1, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (75, 72, '修改', '', 'sms:platform:update,sms:platform:info', 1, 0, '', 2, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (76, 72, '删除', '', 'sms:platform:delete', 1, 0, '', 3, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (77, 34, '代码生成器', '{{apiUrl}}/sysApi/oh-generator/index.html', '', 0, 1, 'icon-rocket', 2, 0, 10000, '2023-06-12 13:47:50', 10000, '2023-06-24 21:35:28');
INSERT INTO `sys_menu` VALUES (80, 84, '查看', '', 'external:app:page,external:function:page,external:authority:page', 1, 0, '', 0, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:00');
INSERT INTO `sys_menu` VALUES (81, 84, '新增', '', 'external:app:save,external:function:save', 1, 0, '', 1, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:09');
INSERT INTO `sys_menu` VALUES (82, 84, '修改', '', 'external:app:update,external:app:info,external:function:update,external:function:info', 1, 0, '', 2, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:21');
INSERT INTO `sys_menu` VALUES (83, 84, '删除', '', 'external:app:delete,external:function:delete', 1, 0, '', 3, 0, 10000, '2023-07-29 12:35:41', 10000, '2023-07-29 12:45:33');
INSERT INTO `sys_menu` VALUES (84, 34, '接口管理', 'external/app/index', '', 0, 0, 'icon-drag', 5, 0, 10000, '2023-06-12 13:47:41', 10000, '2023-08-09 21:49:46');
INSERT INTO `sys_menu` VALUES (85, 2, '版本信息', 'sys/info/index', '', 0, 0, 'icon-menu', 11, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-09-16 16:02:27');
INSERT INTO `sys_menu` VALUES (86, 85, '查看', '', 'system:info:page', 1, 0, '', 0, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (87, 85, '新增', '', 'system:info:save', 1, 0, '', 1, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (88, 85, '修改', '', 'system:info:update,system:info:info', 1, 0, '', 2, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (89, 85, '删除', '', 'system:info:delete', 1, 0, '', 3, 0, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (100, 0, '协同办公', '', '', 0, 0, 'icon-insertrowleft', 4, 0, 10000, '2022-11-27 17:21:33', 10000, '2022-11-27 17:21:54');
INSERT INTO `sys_menu` VALUES (101, 100, '项目信息表', 'team/project/index', NULL, 0, 0, 'icon-menu', 1, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:44:38');
INSERT INTO `sys_menu` VALUES (102, 101, '查看', '', 'team:project:page', 1, 0, '', 0, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (103, 101, '新增', '', 'team:project:save', 1, 0, '', 1, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (104, 101, '修改', '', 'team:project:update,team:project:info', 1, 0, '', 2, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (105, 101, '删除', '', 'team:project:delete', 1, 0, '', 3, 0, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (106, 100, '任务表', 'team/task/index', NULL, 0, 0, 'icon-menu', 2, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:44:30');
INSERT INTO `sys_menu` VALUES (107, 106, '查看', '', 'team:task:page', 1, 0, '', 0, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (108, 106, '新增', '', 'team:task:save', 1, 0, '', 1, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (109, 106, '修改', '', 'team:task:update,oneHill:task:info', 1, 0, '', 2, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (110, 106, '删除', '', 'team:task:delete', 1, 0, '', 3, 0, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1, 0, '长沙总部', '公司总部', 1, 0, 10000, '2022-12-03 15:48:18', 10000, '2023-06-27 22:35:57');
INSERT INTO `sys_org` VALUES (2, 1, '研发部', NULL, 1, 0, 10000, '2022-12-03 15:48:39', 10000, '2022-12-03 15:48:39');
INSERT INTO `sys_org` VALUES (3, 1, '销售部', NULL, 2, 0, 10000, '2022-12-03 15:48:57', 10000, '2022-12-03 15:48:57');
INSERT INTO `sys_org` VALUES (4, 1, '设计部', NULL, 3, 0, 10000, '2022-12-08 21:22:48', 10000, '2022-12-08 21:22:48');
INSERT INTO `sys_org` VALUES (5, 0, '合作伙伴', '重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司', 2, 0, 10000, '2022-12-08 21:31:18', 10000, '2022-12-08 22:06:30');
INSERT INTO `sys_org` VALUES (6, 5, '阿里哒', '总部在杭州的大公司。', 1, 0, 10000, '2022-12-08 21:31:48', 10000, '2023-06-27 22:52:48');
INSERT INTO `sys_org` VALUES (7, 5, '长沙银行', NULL, 2, 0, 10000, '2022-12-08 21:32:25', 10000, '2022-12-08 21:32:25');
INSERT INTO `sys_org` VALUES (8, 1, '财务部', '', 4, 0, 10000, '2022-12-08 22:29:10', 10000, '2022-12-08 22:29:10');

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO `sys_params` VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'true', '是否开启验证码（true：开启，false：关闭）', 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_params` VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 0, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO `sys_params` VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 0, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO `sys_params` VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 0, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '业务员', '', 3, 0, 0, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, 0, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15');
INSERT INTO `sys_user` VALUES (10001, 'whx', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '王小费', 'http://localhost:8080/upload/20230717/1671258609873_77092.jpg', 0, 'whx5710@qq.com', '15088885710', 1, 0, 1, 0, 10000, '2023-07-17 21:15:47', 10001, '2023-07-17 21:24:54');

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 10001, 0, 10000, '2023-07-17 21:16:57', 10000, '2023-07-17 21:16:57');

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
INSERT INTO `sys_version_info` VALUES (1, '1.0.0', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis-Plus，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1,'', 0, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');

-- ----------------------------
-- Records of bpmn_flow
-- ----------------------------
INSERT INTO `bpmn_flow` VALUES (1, 'Process_demo20231222', '流程例子', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn\" id=\"Definitions_0h3vrm2\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"bpmn-js (https://demo.bpmn.io)\" exporterVersion=\"15.1.3\">\n  <bpmn:process id=\"Process_demo20231222\" name=\"流程例子\" isExecutable=\"true\" camunda:versionTag=\"demo\">\n    <bpmn:startEvent id=\"StartEvent_1s3b1oq\" name=\"开始\">\n      <bpmn:outgoing>Flow_15u340k</bpmn:outgoing>\n    </bpmn:startEvent>\n    <bpmn:sequenceFlow id=\"Flow_15u340k\" sourceRef=\"StartEvent_1s3b1oq\" targetRef=\"Activity_0e8dnds\" />\n    <bpmn:userTask id=\"Activity_0e8dnds\" name=\"申请金额\">\n      <bpmn:incoming>Flow_15u340k</bpmn:incoming>\n      <bpmn:outgoing>Flow_0n95qae</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:exclusiveGateway id=\"Gateway_1175j6c\" default=\"Flow_1sxpx33\">\n      <bpmn:incoming>Flow_0n95qae</bpmn:incoming>\n      <bpmn:outgoing>Flow_1bpt2xh</bpmn:outgoing>\n      <bpmn:outgoing>Flow_1sxpx33</bpmn:outgoing>\n    </bpmn:exclusiveGateway>\n    <bpmn:sequenceFlow id=\"Flow_0n95qae\" sourceRef=\"Activity_0e8dnds\" targetRef=\"Gateway_1175j6c\" />\n    <bpmn:sequenceFlow id=\"Flow_1bpt2xh\" name=\"金额大于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_11eh56a\">\n      <bpmn:conditionExpression xsi:type=\"bpmn:tFormalExpression\">${money > 100}</bpmn:conditionExpression>\n    </bpmn:sequenceFlow>\n    <bpmn:sequenceFlow id=\"Flow_104f95a\" sourceRef=\"Activity_11eh56a\" targetRef=\"Activity_0vu4duk\" />\n    <bpmn:endEvent id=\"Event_04q0avn\" name=\"结束\">\n      <bpmn:incoming>Flow_07lrcry</bpmn:incoming>\n    </bpmn:endEvent>\n    <bpmn:sequenceFlow id=\"Flow_07lrcry\" sourceRef=\"Activity_0vu4duk\" targetRef=\"Event_04q0avn\" />\n    <bpmn:sequenceFlow id=\"Flow_1sxpx33\" name=\"金额小于或等于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_0vu4duk\" />\n    <bpmn:userTask id=\"Activity_11eh56a\" name=\"财务审批\">\n      <bpmn:incoming>Flow_1bpt2xh</bpmn:incoming>\n      <bpmn:outgoing>Flow_104f95a</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:userTask id=\"Activity_0vu4duk\" name=\"审批完成\">\n      <bpmn:incoming>Flow_104f95a</bpmn:incoming>\n      <bpmn:incoming>Flow_1sxpx33</bpmn:incoming>\n      <bpmn:outgoing>Flow_07lrcry</bpmn:outgoing>\n    </bpmn:userTask>\n  </bpmn:process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n    <bpmndi:BPMNPlane id=\"Process_demo20231222_di\" bpmnElement=\"Process_demo20231222\">\n      <bpmndi:BPMNEdge id=\"Flow_1sxpx33_di\" bpmnElement=\"Flow_1sxpx33\">\n        <di:waypoint x=\"440\" y=\"125\" />\n        <di:waypoint x=\"440\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"140\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"554\" y=\"202\" width=\"83\" height=\"27\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_07lrcry_di\" bpmnElement=\"Flow_07lrcry\">\n        <di:waypoint x=\"800\" y=\"100\" />\n        <di:waypoint x=\"872\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_104f95a_di\" bpmnElement=\"Flow_104f95a\">\n        <di:waypoint x=\"630\" y=\"100\" />\n        <di:waypoint x=\"700\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_1bpt2xh_di\" bpmnElement=\"Flow_1bpt2xh\">\n        <di:waypoint x=\"465\" y=\"100\" />\n        <di:waypoint x=\"530\" y=\"100\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"467\" y=\"82\" width=\"62\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_0n95qae_di\" bpmnElement=\"Flow_0n95qae\">\n        <di:waypoint x=\"350\" y=\"100\" />\n        <di:waypoint x=\"415\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_15u340k_di\" bpmnElement=\"Flow_15u340k\">\n        <di:waypoint x=\"192\" y=\"100\" />\n        <di:waypoint x=\"250\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"_BPMNShape_StartEvent_2\" bpmnElement=\"StartEvent_1s3b1oq\">\n        <dc:Bounds x=\"156\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"163\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_0x77opo_di\" bpmnElement=\"Activity_0e8dnds\">\n        <dc:Bounds x=\"250\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Gateway_1175j6c_di\" bpmnElement=\"Gateway_1175j6c\" isMarkerVisible=\"true\">\n        <dc:Bounds x=\"415\" y=\"75\" width=\"50\" height=\"50\" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Event_04q0avn_di\" bpmnElement=\"Event_04q0avn\">\n        <dc:Bounds x=\"872\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"879\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_0qg85rv_di\" bpmnElement=\"Activity_11eh56a\">\n        <dc:Bounds x=\"530\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_0zd5hro_di\" bpmnElement=\"Activity_0vu4duk\">\n        <dc:Bounds x=\"700\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn:definitions>\n', '<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!-- created with bpmn-js / http://bpmn.io -->\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"764\" height=\"181\" viewBox=\"150 54 764 181\" version=\"1.1\"><defs><marker id=\"sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\" viewBox=\"0 0 20 20\" refX=\"11\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 1 5 L 11 10 L 1 15 Z\" style=\"fill: rgb(139, 35, 143); stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: rgb(139, 35, 143);\"/></marker><marker id=\"conditional-default-flow-marker-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\" viewBox=\"0 0 20 20\" refX=\"0\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 6 4 L 10 16\" style=\"fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: rgb(139, 35, 143);\"/></marker></defs><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1sxpx33\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  440,125L440,220 L750,220 L750,140 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\'); marker-start: url(\'#conditional-default-flow-marker-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"440,125 440,220 750,220 750,140 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"434\" y=\"119\" width=\"322\" height=\"107\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_07lrcry\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  800,100L872,100 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"800,100 872,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"794\" y=\"94\" width=\"84\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_104f95a\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  630,100L700,100 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"630,100 700,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"624\" y=\"94\" width=\"82\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1bpt2xh\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  465,100L530,100 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"465,100 530,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"459\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_0n95qae\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  350,100L415,100 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"350,100 415,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"344\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_15u340k\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  192,100L250,100 \" style=\"fill: none; stroke-width: 2px; stroke: rgb(139, 35, 143); stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-_8b238f-0b1xhhpy6hddw0jru5n8pw43e\');\"/></g><polyline points=\"192,100 250,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"186\" y=\"94\" width=\"70\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_1s3b1oq\" style=\"display: block;\" transform=\"matrix(1 0 0 1 156 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: rgb(139, 35, 143); stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_1s3b1oq_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 163 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"0\" y=\"9.899999999999999\">开始</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"23\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"35\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_0e8dnds\" style=\"display: block;\" transform=\"matrix(1 0 0 1 250 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: rgb(139, 35, 143); stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"25.75\" y=\"43.599999999999994\">申请金额</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: rgb(139, 35, 143); stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Gateway_1175j6c\" style=\"display: block;\" transform=\"matrix(1 0 0 1 415 75)\"><g class=\"djs-visual\"><polygon points=\"25,0 50,25 25,50 0,25\" style=\"stroke: rgb(139, 35, 143); stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><path d=\"m 16,15 7.42857142857143,9.714285714285715 -7.42857142857143,9.714285714285715 3.428571428571429,0 5.714285714285715,-7.464228571428572 5.714285714285715,7.464228571428572 3.428571428571429,0 -7.42857142857143,-9.714285714285715 7.42857142857143,-9.714285714285715 -3.428571428571429,0 -5.714285714285715,7.464228571428572 -5.714285714285715,-7.464228571428572 -3.428571428571429,0 z\" style=\"fill: rgb(139, 35, 143); stroke-width: 1px; stroke: rgb(139, 35, 143);\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"50\" height=\"50\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"62\" height=\"62\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_04q0avn\" style=\"display: block;\" transform=\"matrix(1 0 0 1 872 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: rgb(139, 35, 143); stroke-width: 4px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_04q0avn_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 880 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"0\" y=\"9.899999999999999\">结束</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"22\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"34\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_11eh56a\" style=\"display: block;\" transform=\"matrix(1 0 0 1 530 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: rgb(139, 35, 143); stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"26\" y=\"43.599999999999994\">财务审批</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: rgb(139, 35, 143); stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_0vu4duk\" style=\"display: block;\" transform=\"matrix(1 0 0 1 700 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: rgb(139, 35, 143); stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"25.75\" y=\"43.599999999999994\">审批完成</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: rgb(139, 35, 143); stroke-width: 0.5px; stroke: rgb(139, 35, 143);\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1bpt2xh_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 467 82)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"0\" y=\"9.899999999999999\">金额大于100</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"62\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"74\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1sxpx33_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 554 202)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: rgb(45, 210, 87);\"><tspan x=\"0\" y=\"9.899999999999999\">金额小于或等于1</tspan><tspan x=\"35.19140625\" y=\"23.099999999999998\">00</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"83\" height=\"27\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"95\" height=\"39\" class=\"djs-outline\" style=\"fill: none;\"/></g></g></svg>', 'demo', '第一个例子', 0, 10000, '2023-12-19 22:27:38', 10000, '2023-12-22 23:06:36');



-- ----------------------------
-- Records of data_app
-- ----------------------------
INSERT INTO `data_app` VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 0);
INSERT INTO `data_app` VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 0);
INSERT INTO `data_app` VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 0);

-- ----------------------------
-- Records of data_function
-- ----------------------------
INSERT INTO `data_function` VALUES (1, '用户信息上传', 'F1001', 1, '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 0);
INSERT INTO `data_function` VALUES (2, '账单查询', 'F1002', 0, '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 0);

-- ----------------------------
-- Records of data_function_authority
-- ----------------------------
INSERT INTO `data_function_authority` VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 0);
INSERT INTO `data_function_authority` VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 0);
INSERT INTO `data_function_authority` VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 0);


SET FOREIGN_KEY_CHECKS = 1;
