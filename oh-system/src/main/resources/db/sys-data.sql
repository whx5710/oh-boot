-- ----------------------------
-- Records of data_app
-- ----------------------------
INSERT INTO `data_app` VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 1);
INSERT INTO `data_app` VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 1);
INSERT INTO `data_app` VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 1);

-- ----------------------------
-- Records of data_function
-- ----------------------------
INSERT INTO `data_function` VALUES (1, '用户信息上传', 'F1001', 1, '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 1);
INSERT INTO `data_function` VALUES (2, '账单查询', 'F1002', 0, '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 1);
INSERT INTO `data_function` VALUES (3, '新增工单', 'F1003', 1, '', 10000, '2024-02-28 21:50:47', NULL, NULL, 1);

-- ----------------------------
-- Records of data_function_authority
-- ----------------------------
INSERT INTO `data_function_authority` VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO `data_function_authority` VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO `data_function_authority` VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 1);
INSERT INTO `data_function_authority` VALUES (4, 'C0001', 'F1003', NULL, 10000, '2024-02-28 21:51:00', NULL, NULL, 1);

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', '0 * * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', NULL, 'com.iris.quartz.utils.ScheduleDisallowConcurrentExecution', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B455973720028636F6D2E697269732E71756172747A2E656E746974792E5363686564756C654A6F62456E74697479147D0B7535BCDD0002000A4C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A636F6E63757272656E747400134C6A6176612F6C616E672F496E74656765723B4C000E63726F6E45787072657373696F6E71007E00094C000269647400104C6A6176612F6C616E672F4C6F6E673B4C00086A6F6247726F757071007E00094C00076A6F624E616D6571007E00094C00066D6574686F6471007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C000673746174757371007E000A7872002B636F6D2E697269732E6672616D65776F726B2E636F6D6D6F6E2E656E746974792E42617365456E74697479095796360B5019EA0200054C000A63726561746554696D657400194C6A6176612F74696D652F4C6F63616C4461746554696D653B4C000763726561746F7271007E000B4C0008646253746174757371007E000A4C000A75706461746554696D6571007E000D4C00077570646174657271007E000B78720029636F6D2E697269732E6672616D65776F726B2E636F6D6D6F6E2E656E746974792E4944456E7469747989227716E1C3C0F90200014C0002696471007E000B7870707372000D6A6176612E74696D652E536572955D84BA1B2248B20C00007870770A05000007E7060C1526DF787372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B02000078700000000000002710737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007371007E0010770A05000007E7060C1526DF787371007E00120000000000002710740008746573745461736B71007E001674000D30202A202A202A202A203F202A7371007E0012000000000000000174000673797374656D74000CE6B58BE8AF95E4BBBBE58AA174000372756E74000331323374000071007E00167800);

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('OhScheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('OhScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('OhScheduler', 'DESKTOP-IHJIP1I1709718355949', 1709718405997, 15000);

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('OhScheduler', 'TASK_NAME_1', 'system', 'TASK_NAME_1', 'system', NULL, 1709718360000, -1, 5, 'PAUSED', 'CRON', 1709718356000, 0, NULL, 2, '');

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES (1, '测试任务', 'system', 'testTask', 'run', '123', '0 * * * * ? *', 0, 0, '', 1, 10000, '2023-06-12 21:38:32', 10000, '2023-06-12 21:38:32');

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (2, 1, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (3, 2, '男', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (4, 2, '女', '1', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (5, 2, '未知', '2', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (6, 3, '正常', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (7, 3, '停用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (8, 4, '全部数据', '0', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (9, 4, '本机构及子机构数据', '1', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (10, 4, '本机构数据', '2', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (11, 4, '本人数据', '3', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (12, 4, '自定义数据', '4', '', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (13, 5, '禁用', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (14, 5, '启用', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (15, 6, '失败', '0', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (16, 6, '成功', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (17, 7, '登录成功', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (18, 7, '退出成功', '1', 'warning', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (19, 7, '验证码错误', '2', 'danger', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (20, 7, '账号密码错误', '3', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (21, 8, '否', '0', 'primary', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (22, 8, '是', '1', 'danger', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (23, 9, '是', '1', 'danger', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (24, 9, '否', '0', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (25, 10, '其它', '0', 'info', '', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (26, 10, '查询', '1', 'primary', '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (27, 10, '新增', '2', 'success', '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (28, 10, '修改', '3', 'warning', '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (29, 10, '删除', '4', 'danger', '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (30, 10, '导出', '5', 'info', '', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (31, 10, '导入', '6', 'info', '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_data` VALUES (32, 12, '开始', '1', NULL, '', 1, 1, 10000, '2022-11-27 19:23:01', 10000, '2022-11-27 19:23:01');
INSERT INTO `sys_dict_data` VALUES (33, 12, '暂停', '2', NULL, '', 2, 1, 10000, '2022-11-27 19:23:16', 10000, '2022-11-27 19:23:16');
INSERT INTO `sys_dict_data` VALUES (34, 12, '关闭', '3', NULL, '', 3, 1, 10000, '2022-11-27 19:23:29', 10000, '2022-11-27 19:23:29');
INSERT INTO `sys_dict_data` VALUES (35, 13, '默认', 'default', '', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (36, 13, '系统', 'system', '', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (37, 14, '暂停', '0', 'danger', '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (38, 14, '正常', '1', 'primary', '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_data` VALUES (39, 15, '阿里云', '0', '', '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (40, 15, '腾讯云', '1', '', '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (41, 15, '七牛云', '2', '', '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_dict_data` VALUES (42, 15, '华为云', '3', '', '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, 'post_status', '状态', 0, NULL, '岗位管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (2, 'user_gender', '性别', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (3, 'user_status', '状态', 0, NULL, '用户管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (4, 'role_data_scope', '数据范围', 0, NULL, '角色管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (5, 'enable_disable', '状态', 0, NULL, '功能状态：启用 | 禁用 ', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (6, 'success_fail', '状态', 0, NULL, '操作状态：成功 | 失败', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (7, 'login_operation', '操作信息', 0, NULL, '登录管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (8, 'params_type', '系统参数', 0, NULL, '参数管理', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (9, 'user_super_admin', '用户是否是超管', 0, NULL, '用户是否是超管', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (10, 'log_operate_type', '操作类型', 0, NULL, '操作日志', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_dict_type` VALUES (12, 'project_status', '状态', 0, '', '项目状态', 12, 1, 10000, '2022-11-27 19:19:50', 10000, '2022-11-27 19:19:50');
INSERT INTO `sys_dict_type` VALUES (13, 'schedule_group', '任务组名', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (14, 'schedule_status', '状态', 0, NULL, '定时任务', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_dict_type` VALUES (15, 'sms_platform', '平台类型', 0, NULL, '短信管理', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, 0, '系统管理', '', '', 0, 0, 'icon-Report', 1, 1, 10000, '2023-09-24 20:13:47', 10000, '2023-09-24 20:13:47');
INSERT INTO `sys_menu` VALUES (2, 1, '系统设置', '', '', 0, 0, 'icon-setting', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:15:58');
INSERT INTO `sys_menu` VALUES (3, 2, '菜单管理', 'sys/menu/index', NULL, 0, 0, 'icon-menu', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (4, 3, '查看', '', 'sys:menu:list', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (5, 3, '新增', '', 'sys:menu:save', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (6, 3, '修改', '', 'sys:menu:update,sys:menu:info', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (7, 3, '删除', '', 'sys:menu:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (8, 2, '数据字典', 'sys/dict/type', '', 0, 0, 'icon-insertrowabove', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (9, 8, '查询', '', 'sys:dict:page', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (10, 8, '新增', '', 'sys:dict:save', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (11, 8, '修改', '', 'sys:dict:update,sys:dict:info', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (12, 8, '删除', '', 'sys:dict:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (13, 1, '权限管理', '', '', 0, 0, 'icon-safetycertificate', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:08');
INSERT INTO `sys_menu` VALUES (14, 13, '岗位管理', 'sys/post/index', '', 0, 0, 'icon-solution', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (15, 14, '查询', '', 'sys:post:page', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (16, 14, '新增', '', 'sys:post:save', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (17, 14, '修改', '', 'sys:post:update,sys:post:info', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (18, 14, '删除', '', 'sys:post:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (19, 13, '机构管理', 'sys/org/index', '', 0, 0, 'icon-cluster', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (20, 19, '查询', '', 'sys:org:list', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (21, 19, '新增', '', 'sys:org:save', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (22, 19, '修改', '', 'sys:org:update,sys:org:info', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (23, 19, '删除', '', 'sys:org:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (24, 13, '角色管理', 'sys/role/index', '', 0, 0, 'icon-team', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (25, 24, '查询', '', 'sys:role:page', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (26, 24, '新增', '', 'sys:role:save,sys:role:menu,sys:org:list', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (27, 24, '修改', '', 'sys:role:update,sys:role:info,sys:role:menu,sys:org:list,sys:user:page', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (28, 24, '删除', '', 'sys:role:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (29, 13, '用户管理', 'sys/user/index', '', 0, 0, 'icon-user', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (30, 29, '查询', '', 'sys:user:page', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (31, 29, '新增', '', 'sys:user:save,sys:role:list', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (32, 29, '修改', '', 'sys:user:update,sys:user:info,sys:role:list', 1, 0, '', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (33, 29, '删除', '', 'sys:user:delete', 1, 0, '', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (34, 1, '应用管理', '', '', 0, 0, 'icon-appstore', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:16');
INSERT INTO `sys_menu` VALUES (35, 2, '附件管理', 'sys/attachment/index', NULL, 0, 0, 'icon-folder-fill', 3, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (36, 35, '查看', '', 'sys:attachment:page', 1, 0, '', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (37, 35, '上传', '', 'sys:attachment:save', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (38, 35, '删除', '', 'sys:attachment:delete', 1, 0, '', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (39, 1, '日志管理', '', '', 0, 0, 'icon-filedone', 4, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-09-24 20:16:24');
INSERT INTO `sys_menu` VALUES (40, 39, '登录日志', 'sys/log/login', 'sys:log:login', 0, 0, 'icon-solution', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (41, 29, '导入', '', 'sys:user:import', 1, 0, '', 5, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (42, 29, '导出', '', 'sys:user:export', 1, 0, '', 6, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (43, 2, '参数管理', 'sys/params/index', 'sys:params:all', 0, 0, 'icon-filedone', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (44, 2, '接口文档', 'http://localhost:8080/doc.html', NULL, 0, 1, 'icon-file-text-fill', 10, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (45, 0, '在线开发', '', '', 0, 0, 'icon-cloud', 2, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (46, 45, 'Online表单开发', 'online/table/index', 'online:table:all', 0, 0, 'icon-table', 0, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (47, 39, '操作日志', 'sys/log/operate', 'sys:operate:all', 0, 0, 'icon-file-text', 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_menu` VALUES (48, 69, '踢出', '', 'monitor:user:user', 1, 0, '', 1, 1, 10000, '2023-09-25 21:16:17', 10000, '2023-09-25 21:16:17');
INSERT INTO `sys_menu` VALUES (49, 34, '流程管理', '', '', 0, 0, 'icon-switchuser', 1, 1, 10000, '2023-12-23 16:02:38', NULL, NULL);
INSERT INTO `sys_menu` VALUES (50, 49, '流程设计', 'workflow/index', 'flow:saveOrUpdate', 0, 0, 'icon-expand', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-07-16 11:54:26');
INSERT INTO `sys_menu` VALUES (51, 49, '自定义流程', 'workflow/index-list', 'flow:page,flow:delete', 0, 0, 'icon-menu', 0, 1, 10000, '2023-12-18 04:48:26', 10000, '2023-12-18 04:48:26');
INSERT INTO `sys_menu` VALUES (59, 2, '定时任务', 'quartz/schedule/index', NULL, 0, 0, 'icon-reloadtime', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (60, 59, '查看', '', 'schedule:page', 1, 0, '', 0, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (61, 59, '新增', '', 'schedule:save', 1, 0, '', 1, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (62, 59, '修改', '', 'schedule:update,schedule:info', 1, 0, '', 2, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (63, 59, '删除', '', 'schedule:delete', 1, 0, '', 3, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (64, 59, '立即运行', '', 'schedule:run', 1, 0, '', 2, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (65, 59, '日志', '', 'schedule:log', 1, 0, '', 4, 1, 10000, '2023-06-12 13:45:54', 10000, '2023-06-12 13:45:54');
INSERT INTO `sys_menu` VALUES (66, 34, '系统监控', '', '', 0, 0, 'icon-Report', 10, 1, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (67, 66, '服务监控', 'monitor/server/index', 'monitor:server:all', 0, 0, 'icon-sever', 0, 1, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (68, 66, '缓存监控', 'monitor/cache/index', 'monitor:cache:all', 0, 0, 'icon-fund-fill', 2, 1, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (69, 66, '用户监控', 'monitor/user/index', 'monitor:user:all', 0, 0, 'icon-user', 3, 1, 10000, '2023-06-12 13:46:12', 10000, '2023-06-12 13:46:12');
INSERT INTO `sys_menu` VALUES (70, 34, '消息管理', '', '', 0, 0, 'icon-message', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (71, 70, '短信日志', 'message/sms/log/index', 'sms:log', 0, 0, 'icon-detail', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (72, 70, '短信平台', 'message/sms/platform/index', NULL, 0, 0, 'icon-whatsapp', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (73, 72, '查看', '', 'sms:platform:page', 1, 0, '', 0, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (74, 72, '新增', '', 'sms:platform:save', 1, 0, '', 1, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (75, 72, '修改', '', 'sms:platform:update,sms:platform:info', 1, 0, '', 2, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (76, 72, '删除', '', 'sms:platform:delete', 1, 0, '', 3, 1, 10000, '2023-06-12 13:47:41', 10000, '2023-06-12 13:47:41');
INSERT INTO `sys_menu` VALUES (77, 34, '代码生成器', '{{apiUrl}}/sysApi/oh-generator/index.html', '', 0, 1, 'icon-rocket', 2, 1, 10000, '2023-06-12 13:47:50', 10000, '2023-06-24 21:35:28');
INSERT INTO `sys_menu` VALUES (80, 84, '查看', '', 'sys:app:page,sys:function:page,sys:authority:page', 1, 0, '', 0, 1, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:05:51');
INSERT INTO `sys_menu` VALUES (81, 84, '新增', '', 'sys:app:save,sys:function:save', 1, 0, '', 1, 1, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:04');
INSERT INTO `sys_menu` VALUES (82, 84, '修改', '', 'sys:app:update,sys:app:info,sys:function:update,sys:function:info', 1, 0, '', 2, 1, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:24');
INSERT INTO `sys_menu` VALUES (83, 84, '删除', '', 'sys:app:delete,sys:function:delete', 1, 0, '', 3, 1, 10000, '2023-07-29 12:35:41', 10000, '2024-04-21 20:06:34');
INSERT INTO `sys_menu` VALUES (84, 34, '接口管理', 'sys/app/index', '', 0, 0, 'icon-drag', 5, 1, 10000, '2023-06-12 13:47:41', 10000, '2024-04-21 20:03:08');
INSERT INTO `sys_menu` VALUES (85, 2, '版本信息', 'sys/info/index', '', 0, 0, 'icon-menu', 11, 1, 10000, '2023-08-13 10:35:40', 10000, '2023-09-16 16:02:27');
INSERT INTO `sys_menu` VALUES (86, 85, '查看', '', 'system:info:page', 1, 0, '', 0, 1, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (87, 85, '新增', '', 'system:info:save', 1, 0, '', 1, 1, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (88, 85, '修改', '', 'system:info:update,system:info:info', 1, 0, '', 2, 1, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (89, 85, '删除', '', 'system:info:delete', 1, 0, '', 3, 1, 10000, '2023-08-13 10:35:40', 10000, '2023-08-13 10:35:40');
INSERT INTO `sys_menu` VALUES (100, 0, '协同办公', '', '', 0, 0, 'icon-insertrowleft', 4, 1, 10000, '2022-11-27 17:21:33', 10000, '2022-11-27 17:21:54');
INSERT INTO `sys_menu` VALUES (101, 100, '项目信息表', 'team/project/index', NULL, 0, 0, 'icon-menu', 1, 1, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:44:38');
INSERT INTO `sys_menu` VALUES (102, 101, '查看', '', 'team:project:page', 1, 0, '', 0, 1, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (103, 101, '新增', '', 'team:project:save', 1, 0, '', 1, 1, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (104, 101, '修改', '', 'team:project:update,team:project:info', 1, 0, '', 2, 1, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (105, 101, '删除', '', 'team:project:delete', 1, 0, '', 3, 1, 10000, '2022-11-27 17:27:49', 10000, '2022-11-27 17:27:49');
INSERT INTO `sys_menu` VALUES (106, 100, '任务表', 'team/task/index', NULL, 0, 0, 'icon-menu', 2, 1, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:44:30');
INSERT INTO `sys_menu` VALUES (107, 106, '查看', '', 'team:task:page', 1, 0, '', 0, 1, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (108, 106, '新增', '', 'team:task:save', 1, 0, '', 1, 1, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (109, 106, '修改', '', 'team:task:update,oneHill:task:info', 1, 0, '', 2, 1, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');
INSERT INTO `sys_menu` VALUES (110, 106, '删除', '', 'team:task:delete', 1, 0, '', 3, 1, 10000, '2022-11-27 17:40:53', 10000, '2022-11-27 17:40:53');

-- ----------------------------
-- Records of sys_org
-- ----------------------------
INSERT INTO `sys_org` VALUES (1, 0, '长沙总部', '公司总部', 1, 1, 10000, '2022-12-03 15:48:18', 10000, '2023-06-27 22:35:57');
INSERT INTO `sys_org` VALUES (2, 1, '研发部', NULL, 1, 1, 10000, '2022-12-03 15:48:39', 10000, '2022-12-03 15:48:39');
INSERT INTO `sys_org` VALUES (3, 1, '销售部', NULL, 2, 1, 10000, '2022-12-03 15:48:57', 10000, '2022-12-03 15:48:57');
INSERT INTO `sys_org` VALUES (4, 1, '设计部', NULL, 3, 1, 10000, '2022-12-08 21:22:48', 10000, '2022-12-08 21:22:48');
INSERT INTO `sys_org` VALUES (5, 0, '合作伙伴', '重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司重要的外部合作公司', 2, 1, 10000, '2022-12-08 21:31:18', 10000, '2022-12-08 22:06:30');
INSERT INTO `sys_org` VALUES (6, 5, '阿里哒', '总部在杭州的大公司。', 1, 1, 10000, '2022-12-08 21:31:48', 10000, '2023-06-27 22:52:48');
INSERT INTO `sys_org` VALUES (7, 5, '长沙银行', NULL, 2, 1, 10000, '2022-12-08 21:32:25', 10000, '2022-12-08 21:32:25');
INSERT INTO `sys_org` VALUES (8, 1, '财务部', '', 4, 1, 10000, '2022-12-08 22:29:10', 10000, '2022-12-08 22:29:10');

-- ----------------------------
-- Records of sys_params
-- ----------------------------
INSERT INTO `sys_params` VALUES (1, '用户登录-验证码开关', 1, 'LOGIN_CAPTCHA', 'true', '是否开启验证码（true：开启，false：关闭）', 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-04 21:03:59');
INSERT INTO `sys_params` VALUES (2, '验证码类型', 1, 'CAPTCHA_TYPE', '4', '1-png图形干扰、2-gif类型、3-png圆圈干扰、4-png短线干扰', 1, 10000, '2023-09-09 16:25:06', 10000, '2023-09-09 16:25:06');
INSERT INTO `sys_params` VALUES (3, '验证码长度', 1, 'CAPTCHA_LENGTH', '4', '验证码长度，默认5个', 1, 10000, '2023-10-04 12:15:07', 10000, '2023-10-04 12:15:07');
INSERT INTO `sys_params` VALUES (4, 'websocket连接', 1, 'WS_URL', '/dev/socket', 'websocket连接反向代理名称', 1, 10000, '2023-10-06 15:07:55', 10000, '2023-10-06 15:07:55');

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '业务员', '', 3, 0, 1, 10000, '2023-07-17 21:16:27', 10000, '2023-07-17 21:16:27');

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES (10000, 'admin', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '系统管理员', 'http://localhost:8080/upload/20230624/1671258609873_76453.jpg', 0, 'whx5710@qq.com', '13612345678', 0, 1, 1, 1, 10000, '2023-06-04 21:03:59', 10000, '2023-06-24 21:14:15', '2023-06-24 21:14:15');
INSERT INTO `sys_user` VALUES (10001, 'whx', '{bcrypt}$2a$10$LvFSm4kNXo4HLJh1XmXVKu6/sdjbFcjgTxjKvOCNwUAspaw0TPD9W', '王小费', 'http://localhost:8080/upload/20230717/1671258609873_77092.jpg', 0, 'whx5710@qq.com', '15088885710', 1, 0, 1, 1, 10000, '2023-07-17 21:15:47', 10001, '2023-07-17 21:24:54', '2023-06-24 21:14:15');

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1, 10001, 1, 10000, '2023-07-17 21:16:57', 10000, '2023-07-17 21:16:57');

-- ----------------------------
-- Records of sys_version_info
-- ----------------------------
INSERT INTO `sys_version_info` VALUES (1, '1.0.0', '初始版本。采用SpringBoot3.0、SpringSecurity6.0、Mybatis-Plus，Kafka等框架开发的一套SpringBoot低代码开发平台，支持多数据源，使用门槛极低。', '2023-09-24 20:41:27', 1, '', 1, 10000, '2023-09-24 20:42:39', 10000, '2023-09-24 20:42:39');

