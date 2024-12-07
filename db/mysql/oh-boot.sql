/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80022
 Source Host           : localhost:3306
 Source Schema         : oh-boot

 Target Server Type    : MySQL
 Target Server Version : 80022
 File Encoding         : 65001

 Date: 25/10/2024 11:10:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bpmn_flow
-- ----------------------------
DROP TABLE IF EXISTS `bpmn_flow`;
CREATE TABLE `bpmn_flow`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `key_code` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程code',
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程名称',
  `xml` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'bpmn的xml字符串',
  `svg_str` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'svg图片字符串',
  `version_tag` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '说明',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`key_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '自定义流程表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpmn_flow
-- ----------------------------
INSERT INTO `bpmn_flow` VALUES (1, 'Process_demo20231222', '流程例子', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn\" id=\"Definitions_0h3vrm2\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"bpmn-js (https://demo.bpmn.io)\" exporterVersion=\"15.1.3\">\n  <bpmn:process id=\"Process_demo20231222\" name=\"流程例子\" isExecutable=\"true\" camunda:versionTag=\"demo\">\n    <bpmn:startEvent id=\"StartEvent_kaishi\" name=\"开始\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:outgoing>Flow_15u340k</bpmn:outgoing>\n    </bpmn:startEvent>\n    <bpmn:sequenceFlow id=\"Flow_15u340k\" sourceRef=\"StartEvent_kaishi\" targetRef=\"Activity_shenqingjine\" />\n    <bpmn:userTask id=\"Activity_shenqingjine\" name=\"申请金额\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_15u340k</bpmn:incoming>\n      <bpmn:outgoing>Flow_0n95qae</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:exclusiveGateway id=\"Gateway_1175j6c\" default=\"Flow_1sxpx33\">\n      <bpmn:incoming>Flow_0n95qae</bpmn:incoming>\n      <bpmn:outgoing>Flow_1bpt2xh</bpmn:outgoing>\n      <bpmn:outgoing>Flow_1sxpx33</bpmn:outgoing>\n    </bpmn:exclusiveGateway>\n    <bpmn:sequenceFlow id=\"Flow_0n95qae\" sourceRef=\"Activity_shenqingjine\" targetRef=\"Gateway_1175j6c\" />\n    <bpmn:sequenceFlow id=\"Flow_1bpt2xh\" name=\"金额大于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_caiwushenpi\">\n      <bpmn:conditionExpression xsi:type=\"bpmn:tFormalExpression\">${money > 100}</bpmn:conditionExpression>\n    </bpmn:sequenceFlow>\n    <bpmn:sequenceFlow id=\"Flow_104f95a\" sourceRef=\"Activity_caiwushenpi\" targetRef=\"Activity_wanchengshenpi\" />\n    <bpmn:endEvent id=\"Event_jieshu\" name=\"结束\">\n      <bpmn:incoming>Flow_07lrcry</bpmn:incoming>\n    </bpmn:endEvent>\n    <bpmn:sequenceFlow id=\"Flow_07lrcry\" sourceRef=\"Activity_wanchengshenpi\" targetRef=\"Event_jieshu\" />\n    <bpmn:sequenceFlow id=\"Flow_1sxpx33\" name=\"金额小于或等于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_wanchengshenpi\" />\n    <bpmn:userTask id=\"Activity_caiwushenpi\" name=\"财务审批\" camunda:assignee=\"王小费\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_1bpt2xh</bpmn:incoming>\n      <bpmn:outgoing>Flow_104f95a</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:userTask id=\"Activity_wanchengshenpi\" name=\"审批完成\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_104f95a</bpmn:incoming>\n      <bpmn:incoming>Flow_1sxpx33</bpmn:incoming>\n      <bpmn:outgoing>Flow_07lrcry</bpmn:outgoing>\n    </bpmn:userTask>\n  </bpmn:process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n    <bpmndi:BPMNPlane id=\"Process_demo20231222_di\" bpmnElement=\"Process_demo20231222\">\n      <bpmndi:BPMNEdge id=\"Flow_1sxpx33_di\" bpmnElement=\"Flow_1sxpx33\">\n        <di:waypoint x=\"440\" y=\"125\" />\n        <di:waypoint x=\"440\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"140\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"554\" y=\"202\" width=\"83\" height=\"27\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_07lrcry_di\" bpmnElement=\"Flow_07lrcry\">\n        <di:waypoint x=\"800\" y=\"100\" />\n        <di:waypoint x=\"872\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_104f95a_di\" bpmnElement=\"Flow_104f95a\">\n        <di:waypoint x=\"630\" y=\"100\" />\n        <di:waypoint x=\"700\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_1bpt2xh_di\" bpmnElement=\"Flow_1bpt2xh\">\n        <di:waypoint x=\"465\" y=\"100\" />\n        <di:waypoint x=\"530\" y=\"100\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"467\" y=\"82\" width=\"62\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_0n95qae_di\" bpmnElement=\"Flow_0n95qae\">\n        <di:waypoint x=\"350\" y=\"100\" />\n        <di:waypoint x=\"415\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_15u340k_di\" bpmnElement=\"Flow_15u340k\">\n        <di:waypoint x=\"192\" y=\"100\" />\n        <di:waypoint x=\"250\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"StartEvent_kaishi_di\" bpmnElement=\"StartEvent_kaishi\">\n        <dc:Bounds x=\"156\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"163\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_shenqingjine_di\" bpmnElement=\"Activity_shenqingjine\">\n        <dc:Bounds x=\"250\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Gateway_1175j6c_di\" bpmnElement=\"Gateway_1175j6c\" isMarkerVisible=\"true\">\n        <dc:Bounds x=\"415\" y=\"75\" width=\"50\" height=\"50\" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Event_jieshu_di\" bpmnElement=\"Event_jieshu\">\n        <dc:Bounds x=\"872\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"879\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_caiwushenpi_di\" bpmnElement=\"Activity_caiwushenpi\">\n        <dc:Bounds x=\"530\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_wanchengshenpi_di\" bpmnElement=\"Activity_wanchengshenpi\">\n        <dc:Bounds x=\"700\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn:definitions>\n', '<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!-- created with bpmn-js / http://bpmn.io -->\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"764\" height=\"181\" viewBox=\"150 54 764 181\" version=\"1.1\"><defs><marker id=\"sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\" viewBox=\"0 0 20 20\" refX=\"11\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 1 5 L 11 10 L 1 15 Z\" style=\"fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;\"/></marker><marker id=\"conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1\" viewBox=\"0 0 20 20\" refX=\"0\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 6 4 L 10 16\" style=\"fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;\"/></marker></defs><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1sxpx33\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  440,125L440,220 L750,220 L750,140 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\'); marker-start: url(\'#conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"440,125 440,220 750,220 750,140 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"434\" y=\"119\" width=\"322\" height=\"107\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_07lrcry\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  800,100L872,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"800,100 872,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"794\" y=\"94\" width=\"84\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_104f95a\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  630,100L700,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"630,100 700,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"624\" y=\"94\" width=\"82\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1bpt2xh\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  465,100L530,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"465,100 530,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"459\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_0n95qae\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  350,100L415,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"350,100 415,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"344\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_15u340k\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  192,100L250,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"192,100 250,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"186\" y=\"94\" width=\"70\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_kaishi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 156 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_1s3b1oq_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 163 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">开始</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"23\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"35\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_shenqingjine\" style=\"display: block;\" transform=\"matrix(1 0 0 1 250 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">申请金额</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Gateway_1175j6c\" style=\"display: block;\" transform=\"matrix(1 0 0 1 415 75)\"><g class=\"djs-visual\"><polygon points=\"25,0 50,25 25,50 0,25\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><path d=\"m 16,15 7.42857142857143,9.714285714285715 -7.42857142857143,9.714285714285715 3.428571428571429,0 5.714285714285715,-7.464228571428572 5.714285714285715,7.464228571428572 3.428571428571429,0 -7.42857142857143,-9.714285714285715 7.42857142857143,-9.714285714285715 -3.428571428571429,0 -5.714285714285715,7.464228571428572 -5.714285714285715,-7.464228571428572 -3.428571428571429,0 z\" style=\"fill: black; stroke-width: 1px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"50\" height=\"50\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"62\" height=\"62\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_jieshu\" style=\"display: block;\" transform=\"matrix(1 0 0 1 872 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: black; stroke-width: 4px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_04q0avn_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 879 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">结束</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"23\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"35\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_caiwushenpi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 530 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">财务审批</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_wanchengshenpi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 700 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">审批完成</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1bpt2xh_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 467 82)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">金额大于100</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"63\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"75\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1sxpx33_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 554 202)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">金额小于或等于1</tspan><tspan x=\"35.44140625\" y=\"23.099999999999998\">00</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"84\" height=\"27\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"96\" height=\"39\" class=\"djs-outline\" style=\"fill: none;\"/></g></g></svg>', 'demo', '第一个例子', 1, 10000, '2023-12-19 22:27:38', 10000, '2024-02-28 20:50:54');

-- ----------------------------
-- Table structure for bpmn_flow_node
-- ----------------------------
DROP TABLE IF EXISTS `bpmn_flow_node`;
CREATE TABLE `bpmn_flow_node`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `proc_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义ID',
  `act_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '环节ID',
  `node_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节名称',
  `note` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_01`(`proc_def_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环节定义表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpmn_flow_node
-- ----------------------------

-- ----------------------------
-- Table structure for bpmn_task_record
-- ----------------------------
DROP TABLE IF EXISTS `bpmn_task_record`;
CREATE TABLE `bpmn_task_record`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `proc_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '流程定义ID',
  `proc_inst_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节实例ID',
  `act_inst_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节实例ID',
  `task_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务ID',
  `task_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节key',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '环节名称',
  `from_act_inst_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来自于环节实例ID',
  `from_task_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来自于任务key',
  `from_task_def_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来自于环节ID',
  `from_task_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来自于环节名称',
  `run_mark` int NULL DEFAULT 0 COMMENT '当前标识，默认0，1标识当前环节',
  `assignee` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '受理人ID',
  `assignee_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '受理人',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `duration` bigint NULL DEFAULT NULL COMMENT '时长',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `bpmn_task_record_proc_inst_id_IDX`(`proc_inst_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '环节运行记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of bpmn_task_record
-- ----------------------------

-- ----------------------------
-- Table structure for data_app
-- ----------------------------
DROP TABLE IF EXISTS `data_app`;
CREATE TABLE `data_app`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端名称',
  `client_id` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `secret_key` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '密钥',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_app
-- ----------------------------
INSERT INTO `data_app` VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 1);
INSERT INTO `data_app` VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 1);
INSERT INTO `data_app` VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 1);

-- ----------------------------
-- Table structure for data_function
-- ----------------------------
DROP TABLE IF EXISTS `data_function`;
CREATE TABLE `data_function`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能名称',
  `func_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能号',
  `is_async` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否异步0否1是',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index2`(`func_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '功能列表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_function
-- ----------------------------
INSERT INTO `data_function` VALUES (1, '用户信息上传', 'F1001', 1, '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 1);
INSERT INTO `data_function` VALUES (2, '账单查询', 'F1002', 0, '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 1);
INSERT INTO `data_function` VALUES (3, '新增工单', 'F1003', 1, '', 10000, '2024-02-28 21:50:47', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_function_authority
-- ----------------------------
DROP TABLE IF EXISTS `data_function_authority`;
CREATE TABLE `data_function_authority`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端ID',
  `func_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能号',
  `remark` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index2`(`client_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端接口授权' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_function_authority
-- ----------------------------
INSERT INTO `data_function_authority` VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO `data_function_authority` VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO `data_function_authority` VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 1);
INSERT INTO `data_function_authority` VALUES (4, 'C0001', 'F1003', NULL, 10000, '2024-02-28 21:51:00', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_message
-- ----------------------------
DROP TABLE IF EXISTS `data_message`;
CREATE TABLE `data_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户端ID',
  `func_code` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '功能号',
  `topic` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主题',
  `json_str` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请求参数',
  `state` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态0未处理1处理2未找到对应的服务类3业务处理失败',
  `result_msg` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '响应消息',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `creator` bigint NULL DEFAULT NULL,
  `create_time` datetime(3) NULL DEFAULT NULL,
  `updater` bigint NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_01`(`topic`, `client_id`, `func_code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '接口参数数据' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of data_message
-- ----------------------------

-- ----------------------------
-- Table structure for oh_project
-- ----------------------------
DROP TABLE IF EXISTS `oh_project`;
CREATE TABLE `oh_project`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `project_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目编码',
  `project_name` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '项目名称',
  `project_alias` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  `start_time` date NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` date NULL DEFAULT NULL COMMENT '结束时间',
  `director` bigint NULL DEFAULT NULL COMMENT '负责人ID',
  `director_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '负责人姓名',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态（1开始2暂停3关闭）',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oh_project
-- ----------------------------
INSERT INTO `oh_project` VALUES (2, 'P1001', '垫脚石计划', '垫脚石', '2022-12-01', NULL, 10001, '王小费', 1, 1, NULL, '2022-12-04 14:09:33', NULL, '2022-12-04 14:09:37');

-- ----------------------------
-- Table structure for oh_project_log
-- ----------------------------
DROP TABLE IF EXISTS `oh_project_log`;
CREATE TABLE `oh_project_log`  (
  `id` bigint NOT NULL,
  `project_id` int NULL DEFAULT NULL COMMENT '项目ID',
  `task_id` bigint NULL DEFAULT NULL COMMENT '任务ID',
  `operation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作类型',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '项目、任务操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oh_project_log
-- ----------------------------

-- ----------------------------
-- Table structure for oh_task
-- ----------------------------
DROP TABLE IF EXISTS `oh_task`;
CREATE TABLE `oh_task`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '序号',
  `project_id` int NOT NULL COMMENT '所属项目',
  `task_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务标题',
  `note` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务描述',
  `task_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '1任务2需求3设计4缺陷9其他',
  `parent_id` bigint NOT NULL DEFAULT 0 COMMENT '父级任务ID',
  `has_child` tinyint(1) NULL DEFAULT NULL COMMENT '是否有子任务',
  `start_time` datetime NULL DEFAULT NULL COMMENT '计划开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '计划结束时间',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '状态（1待办项2进行中3已完成）',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oh_task
-- ----------------------------

-- ----------------------------
-- Table structure for oh_task_user
-- ----------------------------
DROP TABLE IF EXISTS `oh_task_user`;
CREATE TABLE `oh_task_user`  (
  `id` bigint NOT NULL,
  `task_id` bigint NOT NULL COMMENT '任务ID',
  `user_id` bigint NOT NULL COMMENT '人员ID',
  `nick_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `person_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '人员类型1负责人2协作人',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_01`(`task_id`, `user_id`) USING BTREE,
  INDEX `idx_02`(`task_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务人员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oh_task_user
-- ----------------------------

-- ----------------------------
-- Table structure for oh_work_order
-- ----------------------------
DROP TABLE IF EXISTS `oh_work_order`;
CREATE TABLE `oh_work_order`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `order_code` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '工单编码',
  `order_source` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '工单来源',
  `report_time` datetime NULL DEFAULT NULL COMMENT '上报时间',
  `incident_time` datetime NULL DEFAULT NULL COMMENT '事发时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `comment` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '内容',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '位置',
  `geo_x` decimal(12, 9) NULL DEFAULT NULL COMMENT '经度',
  `geo_y` decimal(12, 9) NULL DEFAULT NULL COMMENT '纬度',
  `category` int NULL DEFAULT NULL COMMENT '类别',
  `extend_json` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json扩展字段',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `db_status` tinyint NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `updater` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_order_code`(`order_code`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '工单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of oh_work_order
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
