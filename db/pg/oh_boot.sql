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

 Date: 07/04/2026 15:15:45
*/



-- ----------------------------
-- Table structure for bpmn_flow
-- ----------------------------
DROP TABLE IF EXISTS bpmn_flow;
CREATE TABLE bpmn_flow (
  id bigint NOT NULL,
  key_code varchar(30) NULL DEFAULT NULL,
  name varchar(50) NOT NULL,
  "xml" text NOT NULL,
  svg_str text NULL,
  version_tag varchar(50) NULL DEFAULT NULL,
  note varchar(500) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE bpmn_flow IS '自定义流程表';
COMMENT ON COLUMN bpmn_flow.key_code IS '流程code';
COMMENT ON COLUMN bpmn_flow.name IS '流程名称';
COMMENT ON COLUMN bpmn_flow."xml" IS 'bpmn的xml字符串';
COMMENT ON COLUMN bpmn_flow.svg_str IS 'svg图片字符串';
COMMENT ON COLUMN bpmn_flow.version_tag IS '标签';
COMMENT ON COLUMN bpmn_flow.note IS '说明';
COMMENT ON COLUMN bpmn_flow.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN bpmn_flow.creator IS '创建者';
COMMENT ON COLUMN bpmn_flow.create_time IS '创建时间';
COMMENT ON COLUMN bpmn_flow.updater IS '更新者';
COMMENT ON COLUMN bpmn_flow.update_time IS '更新时间';

-- ----------------------------
-- Records of bpmn_flow
-- ----------------------------
INSERT INTO bpmn_flow VALUES (1, 'Process_demo20231222', '流程例子', '<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<bpmn:definitions xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:bpmn=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:dc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:di=\"http://www.omg.org/spec/DD/20100524/DI\" xmlns:camunda=\"http://camunda.org/schema/1.0/bpmn\" id=\"Definitions_0h3vrm2\" targetNamespace=\"http://bpmn.io/schema/bpmn\" exporter=\"bpmn-js (https://demo.bpmn.io)\" exporterVersion=\"15.1.3\">\n  <bpmn:process id=\"Process_demo20231222\" name=\"流程例子\" isExecutable=\"true\" camunda:versionTag=\"demo\">\n    <bpmn:startEvent id=\"StartEvent_kaishi\" name=\"开始\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:outgoing>Flow_15u340k</bpmn:outgoing>\n    </bpmn:startEvent>\n    <bpmn:sequenceFlow id=\"Flow_15u340k\" sourceRef=\"StartEvent_kaishi\" targetRef=\"Activity_shenqingjine\" />\n    <bpmn:userTask id=\"Activity_shenqingjine\" name=\"申请金额\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_15u340k</bpmn:incoming>\n      <bpmn:outgoing>Flow_0n95qae</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:exclusiveGateway id=\"Gateway_1175j6c\" default=\"Flow_1sxpx33\">\n      <bpmn:incoming>Flow_0n95qae</bpmn:incoming>\n      <bpmn:outgoing>Flow_1bpt2xh</bpmn:outgoing>\n      <bpmn:outgoing>Flow_1sxpx33</bpmn:outgoing>\n    </bpmn:exclusiveGateway>\n    <bpmn:sequenceFlow id=\"Flow_0n95qae\" sourceRef=\"Activity_shenqingjine\" targetRef=\"Gateway_1175j6c\" />\n    <bpmn:sequenceFlow id=\"Flow_1bpt2xh\" name=\"金额大于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_caiwushenpi\">\n      <bpmn:conditionExpression xsi:type=\"bpmn:tFormalExpression\">${money > 100}</bpmn:conditionExpression>\n    </bpmn:sequenceFlow>\n    <bpmn:sequenceFlow id=\"Flow_104f95a\" sourceRef=\"Activity_caiwushenpi\" targetRef=\"Activity_wanchengshenpi\" />\n    <bpmn:endEvent id=\"Event_jieshu\" name=\"结束\">\n      <bpmn:incoming>Flow_07lrcry</bpmn:incoming>\n    </bpmn:endEvent>\n    <bpmn:sequenceFlow id=\"Flow_07lrcry\" sourceRef=\"Activity_wanchengshenpi\" targetRef=\"Event_jieshu\" />\n    <bpmn:sequenceFlow id=\"Flow_1sxpx33\" name=\"金额小于或等于100\" sourceRef=\"Gateway_1175j6c\" targetRef=\"Activity_wanchengshenpi\" />\n    <bpmn:userTask id=\"Activity_caiwushenpi\" name=\"财务审批\" camunda:assignee=\"王小费\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_1bpt2xh</bpmn:incoming>\n      <bpmn:outgoing>Flow_104f95a</bpmn:outgoing>\n    </bpmn:userTask>\n    <bpmn:userTask id=\"Activity_wanchengshenpi\" name=\"审批完成\">\n      <bpmn:extensionElements>\n        <camunda:formData />\n      </bpmn:extensionElements>\n      <bpmn:incoming>Flow_104f95a</bpmn:incoming>\n      <bpmn:incoming>Flow_1sxpx33</bpmn:incoming>\n      <bpmn:outgoing>Flow_07lrcry</bpmn:outgoing>\n    </bpmn:userTask>\n  </bpmn:process>\n  <bpmndi:BPMNDiagram id=\"BPMNDiagram_1\">\n    <bpmndi:BPMNPlane id=\"Process_demo20231222_di\" bpmnElement=\"Process_demo20231222\">\n      <bpmndi:BPMNEdge id=\"Flow_1sxpx33_di\" bpmnElement=\"Flow_1sxpx33\">\n        <di:waypoint x=\"440\" y=\"125\" />\n        <di:waypoint x=\"440\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"220\" />\n        <di:waypoint x=\"750\" y=\"140\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"554\" y=\"202\" width=\"83\" height=\"27\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_07lrcry_di\" bpmnElement=\"Flow_07lrcry\">\n        <di:waypoint x=\"800\" y=\"100\" />\n        <di:waypoint x=\"872\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_104f95a_di\" bpmnElement=\"Flow_104f95a\">\n        <di:waypoint x=\"630\" y=\"100\" />\n        <di:waypoint x=\"700\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_1bpt2xh_di\" bpmnElement=\"Flow_1bpt2xh\">\n        <di:waypoint x=\"465\" y=\"100\" />\n        <di:waypoint x=\"530\" y=\"100\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"467\" y=\"82\" width=\"62\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_0n95qae_di\" bpmnElement=\"Flow_0n95qae\">\n        <di:waypoint x=\"350\" y=\"100\" />\n        <di:waypoint x=\"415\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNEdge id=\"Flow_15u340k_di\" bpmnElement=\"Flow_15u340k\">\n        <di:waypoint x=\"192\" y=\"100\" />\n        <di:waypoint x=\"250\" y=\"100\" />\n      </bpmndi:BPMNEdge>\n      <bpmndi:BPMNShape id=\"StartEvent_kaishi_di\" bpmnElement=\"StartEvent_kaishi\">\n        <dc:Bounds x=\"156\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"163\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_shenqingjine_di\" bpmnElement=\"Activity_shenqingjine\">\n        <dc:Bounds x=\"250\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Gateway_1175j6c_di\" bpmnElement=\"Gateway_1175j6c\" isMarkerVisible=\"true\">\n        <dc:Bounds x=\"415\" y=\"75\" width=\"50\" height=\"50\" />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Event_jieshu_di\" bpmnElement=\"Event_jieshu\">\n        <dc:Bounds x=\"872\" y=\"82\" width=\"36\" height=\"36\" />\n        <bpmndi:BPMNLabel>\n          <dc:Bounds x=\"879\" y=\"125\" width=\"23\" height=\"14\" />\n        </bpmndi:BPMNLabel>\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_caiwushenpi_di\" bpmnElement=\"Activity_caiwushenpi\">\n        <dc:Bounds x=\"530\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n      <bpmndi:BPMNShape id=\"Activity_wanchengshenpi_di\" bpmnElement=\"Activity_wanchengshenpi\">\n        <dc:Bounds x=\"700\" y=\"60\" width=\"100\" height=\"80\" />\n        <bpmndi:BPMNLabel />\n      </bpmndi:BPMNShape>\n    </bpmndi:BPMNPlane>\n  </bpmndi:BPMNDiagram>\n</bpmn:definitions>\n', '<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<!-- created with bpmn-js / http://bpmn.io -->\n<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" width=\"764\" height=\"181\" viewBox=\"150 54 764 181\" version=\"1.1\"><defs><marker id=\"sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\" viewBox=\"0 0 20 20\" refX=\"11\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 1 5 L 11 10 L 1 15 Z\" style=\"fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;\"/></marker><marker id=\"conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1\" viewBox=\"0 0 20 20\" refX=\"0\" refY=\"10\" markerWidth=\"10\" markerHeight=\"10\" orient=\"auto\"><path d=\"M 6 4 L 10 16\" style=\"fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;\"/></marker></defs><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1sxpx33\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  440,125L440,220 L750,220 L750,140 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\'); marker-start: url(\'#conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"440,125 440,220 750,220 750,140 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"434\" y=\"119\" width=\"322\" height=\"107\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_07lrcry\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  800,100L872,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"800,100 872,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"794\" y=\"94\" width=\"84\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_104f95a\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  630,100L700,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"630,100 700,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"624\" y=\"94\" width=\"82\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_1bpt2xh\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  465,100L530,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"465,100 530,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"459\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_0n95qae\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  350,100L415,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"350,100 415,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"344\" y=\"94\" width=\"77\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-connection\" data-element-id=\"Flow_15u340k\" style=\"display: block;\"><g class=\"djs-visual\"><path d=\"m  192,100L250,100 \" style=\"fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(\'#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1\');\"/></g><polyline points=\"192,100 250,100 \" class=\"djs-hit djs-hit-stroke\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"186\" y=\"94\" width=\"70\" height=\"12\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_kaishi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 156 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"StartEvent_1s3b1oq_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 163 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">开始</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"23\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"35\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_shenqingjine\" style=\"display: block;\" transform=\"matrix(1 0 0 1 250 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">申请金额</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Gateway_1175j6c\" style=\"display: block;\" transform=\"matrix(1 0 0 1 415 75)\"><g class=\"djs-visual\"><polygon points=\"25,0 50,25 25,50 0,25\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><path d=\"m 16,15 7.42857142857143,9.714285714285715 -7.42857142857143,9.714285714285715 3.428571428571429,0 5.714285714285715,-7.464228571428572 5.714285714285715,7.464228571428572 3.428571428571429,0 -7.42857142857143,-9.714285714285715 7.42857142857143,-9.714285714285715 -3.428571428571429,0 -5.714285714285715,7.464228571428572 -5.714285714285715,-7.464228571428572 -3.428571428571429,0 z\" style=\"fill: black; stroke-width: 1px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"50\" height=\"50\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"62\" height=\"62\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_jieshu\" style=\"display: block;\" transform=\"matrix(1 0 0 1 872 82)\"><g class=\"djs-visual\"><circle cx=\"18\" cy=\"18\" r=\"18\" style=\"stroke: black; stroke-width: 4px; fill: white; fill-opacity: 0.95;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"36\" height=\"36\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"48\" height=\"48\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Event_04q0avn_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 879 125)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">结束</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"23\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"35\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_caiwushenpi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 530 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">财务审批</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Activity_wanchengshenpi\" style=\"display: block;\" transform=\"matrix(1 0 0 1 700 60)\"><g class=\"djs-visual\"><rect x=\"0\" y=\"0\" width=\"100\" height=\"80\" rx=\"10\" ry=\"10\" style=\"stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;\"/><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;\"><tspan x=\"26\" y=\"43.599999999999994\">审批完成</tspan></text><path d=\"m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5\" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 \" style=\"fill: white; stroke-width: 0.5px; stroke: black;\"/><path d=\"m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z\" style=\"fill: black; stroke-width: 0.5px; stroke: black;\"/></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"100\" height=\"80\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"112\" height=\"92\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1bpt2xh_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 467 82)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">金额大于100</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"63\" height=\"14\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"75\" height=\"26\" class=\"djs-outline\" style=\"fill: none;\"/></g></g><g class=\"djs-group\"><g class=\"djs-element djs-shape\" data-element-id=\"Flow_1sxpx33_label\" style=\"display: block;\" transform=\"matrix(1 0 0 1 554 202)\"><g class=\"djs-visual\"><text lineHeight=\"1.2\" class=\"djs-label\" style=\"font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;\"><tspan x=\"0\" y=\"9.899999999999999\">金额小于或等于1</tspan><tspan x=\"35.44140625\" y=\"23.099999999999998\">00</tspan></text></g><rect class=\"djs-hit djs-hit-all\" x=\"0\" y=\"0\" width=\"84\" height=\"27\" style=\"fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;\"/><rect x=\"-6\" y=\"-6\" width=\"96\" height=\"39\" class=\"djs-outline\" style=\"fill: none;\"/></g></g></svg>', 'demo', '第一个例子', 1, 10000, '2023-12-19 22:27:38', 10000, '2024-02-28 20:50:54');

-- ----------------------------
-- Table structure for bpmn_flow_node
-- ----------------------------
DROP TABLE IF EXISTS bpmn_flow_node;
CREATE TABLE bpmn_flow_node (
  id bigint NOT NULL,
  proc_def_id varchar(100) NOT NULL,
  act_def_id varchar(100) NOT NULL,
  node_name varchar(255) NULL DEFAULT NULL,
  element_type varchar(50) NULL DEFAULT NULL,
  condition_expression varchar(500) NULL DEFAULT NULL,
  note varchar(300) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE bpmn_flow_node IS '环节定义表';

-- ----------------------------
-- Records of bpmn_flow_node
-- ----------------------------

-- ----------------------------
-- Table structure for bpmn_task_record
-- ----------------------------
DROP TABLE IF EXISTS bpmn_task_record;
CREATE TABLE bpmn_task_record (
  id bigint NOT NULL,
  proc_def_id varchar(100) NOT NULL,
  proc_inst_id varchar(100) NULL DEFAULT NULL,
  act_inst_id varchar(100) NULL DEFAULT NULL,
  task_id varchar(100) NULL DEFAULT NULL,
  task_def_id varchar(100) NULL DEFAULT NULL,
  task_name varchar(255) NULL DEFAULT NULL,
  from_act_inst_id varchar(100) NULL DEFAULT NULL,
  from_task_id varchar(100) NULL DEFAULT NULL,
  from_task_def_id varchar(100) NULL DEFAULT NULL,
  from_task_name varchar(100) NULL DEFAULT NULL,
  run_mark int NULL DEFAULT 0,
  assignee varchar(100) NULL DEFAULT NULL,
  assignee_name varchar(100) NULL DEFAULT NULL,
  start_time timestamp NULL DEFAULT NULL,
  end_time timestamp NULL DEFAULT NULL,
  duration bigint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE bpmn_task_record IS '环节运行记录表';

-- ----------------------------
-- Records of bpmn_task_record
-- ----------------------------

-- ----------------------------
-- Table structure for data_app
-- ----------------------------
DROP TABLE IF EXISTS data_app;
CREATE TABLE data_app (
  id bigint NOT NULL,
  name varchar(50) NOT NULL,
  client_id varchar(45) NOT NULL,
  secret_key varchar(45) NOT NULL,
  remark varchar(100) NULL DEFAULT NULL,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE data_app IS '客户端';

-- ----------------------------
-- Records of data_app
-- ----------------------------
INSERT INTO data_app VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 1);
INSERT INTO data_app VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 1);
INSERT INTO data_app VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 1);

-- ----------------------------
-- Table structure for data_function
-- ----------------------------
DROP TABLE IF EXISTS data_function;
CREATE TABLE data_function (
  id bigint NOT NULL,
  name varchar(50) NOT NULL,
  func_code varchar(50) NOT NULL,
  is_async smallint NOT NULL DEFAULT 0,
  remark varchar(100) NULL DEFAULT NULL,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE data_function IS '功能列表';

-- ----------------------------
-- Records of data_function
-- ----------------------------
INSERT INTO data_function VALUES (1, '用户信息上传', 'F1001', 1, '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 1);
INSERT INTO data_function VALUES (2, '账单查询', 'F1002', 0, '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 1);
INSERT INTO data_function VALUES (3, '新增工单', 'F1003', 1, '', 10000, '2024-02-28 21:50:47', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_function_authority
-- ----------------------------
DROP TABLE IF EXISTS data_function_authority;
CREATE TABLE data_function_authority (
  id bigint NOT NULL,
  client_id varchar(50) NOT NULL,
  func_code varchar(50) NOT NULL,
  remark varchar(100) NULL DEFAULT NULL,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE data_function_authority IS '客户端接口授权';

-- ----------------------------
-- Records of data_function_authority
-- ----------------------------
INSERT INTO data_function_authority VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO data_function_authority VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO data_function_authority VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 1);
INSERT INTO data_function_authority VALUES (4, 'C0001', 'F1003', NULL, 10000, '2024-02-28 21:51:00', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_message
-- ----------------------------
DROP TABLE IF EXISTS data_message;
CREATE TABLE data_message (
  id bigint NOT NULL,
  client_id varchar(50) NULL DEFAULT NULL,
  func_code varchar(200) NOT NULL,
  topic varchar(50) NULL DEFAULT NULL,
  json_str varchar(3000) NULL DEFAULT NULL,
  state varchar(1) NULL DEFAULT '0',
  result_msg varchar(3000) NULL DEFAULT NULL,
  note varchar(500) NULL DEFAULT NULL,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp(3) NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE data_message IS '接口参数数据';

-- ----------------------------
-- Records of data_message
-- ----------------------------

-- ----------------------------
-- Table structure for oh_project
-- ----------------------------
DROP TABLE IF EXISTS oh_project;
CREATE TABLE oh_project (
  id bigint NOT NULL,
  project_code varchar(50) NULL DEFAULT NULL,
  project_name varchar(80) NULL DEFAULT NULL,
  project_alias varchar(50) NULL DEFAULT NULL,
  start_time date NULL DEFAULT NULL,
  end_time date NULL DEFAULT NULL,
  director bigint NULL DEFAULT NULL,
  director_name varchar(60) NULL DEFAULT NULL,
  status smallint NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE oh_project IS '项目信息表';

-- ----------------------------
-- Records of oh_project
-- ----------------------------
INSERT INTO oh_project VALUES (2, 'P1001', '垫脚石计划', '垫脚石', '2022-12-01', NULL, 10001, '王小费', 1, 1, NULL, '2022-12-04 14:09:33', NULL, '2022-12-04 14:09:37');

-- ----------------------------
-- Table structure for oh_project_log
-- ----------------------------
DROP TABLE IF EXISTS oh_project_log;
CREATE TABLE oh_project_log (
  id bigint NOT NULL,
  project_id int NULL DEFAULT NULL,
  task_id bigint NULL DEFAULT NULL,
  operation varchar(255) NULL DEFAULT NULL,
  remark varchar(500) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE oh_project_log IS '项目、任务操作日志';

-- ----------------------------
-- Records of oh_project_log
-- ----------------------------

-- ----------------------------
-- Table structure for oh_task
-- ----------------------------
DROP TABLE IF EXISTS oh_task;
CREATE TABLE oh_task (
  id bigint NOT NULL,
  project_id int NOT NULL,
  task_title varchar(255) NOT NULL,
  note varchar(500) NULL DEFAULT NULL,
  task_type char(1) NOT NULL,
  parent_id bigint NOT NULL DEFAULT 0,
  has_child smallint NULL DEFAULT NULL,
  start_time timestamp NULL DEFAULT NULL,
  end_time timestamp NULL DEFAULT NULL,
  status char(1) NULL DEFAULT '1',
  remark varchar(500) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id)
);
COMMENT ON TABLE oh_task IS '任务表';

-- ----------------------------
-- Records of oh_task
-- ----------------------------

-- ----------------------------
-- Table structure for oh_task_user
-- ----------------------------
DROP TABLE IF EXISTS oh_task_user;
CREATE TABLE oh_task_user (
  id bigint NOT NULL,
  task_id bigint NOT NULL,
  user_id bigint NOT NULL,
  nick_name varchar(30) NULL DEFAULT NULL,
  person_type char(1) NOT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE oh_task_user IS '任务人员表';

-- ----------------------------
-- Records of oh_task_user
-- ----------------------------

-- ----------------------------
-- Table structure for oh_work_order
-- ----------------------------
DROP TABLE IF EXISTS oh_work_order;
CREATE TABLE oh_work_order (
  id bigint NOT NULL,
  order_code varchar(40) NOT NULL,
  order_source varchar(2) NULL DEFAULT NULL,
  report_time timestamp NULL DEFAULT NULL,
  incident_time timestamp NULL DEFAULT NULL,
  end_time timestamp NULL DEFAULT NULL,
  title varchar(50) NULL DEFAULT NULL,
  "comment" varchar(2000) NULL DEFAULT NULL,
  address varchar(255) NULL DEFAULT NULL,
  geo_x decimal(12, 9) NULL DEFAULT NULL,
  geo_y decimal(12, 9) NULL DEFAULT NULL,
  category int NULL DEFAULT NULL,
  extend_json varchar(300) NULL DEFAULT NULL,
  note varchar(255) NULL DEFAULT NULL,
  db_status smallint NULL DEFAULT 1,
  creator bigint NULL DEFAULT NULL,
  create_time timestamp NULL DEFAULT NULL,
  updater bigint NULL DEFAULT NULL,
  update_time timestamp NULL DEFAULT NULL,
  PRIMARY KEY (id),
  
);
COMMENT ON TABLE oh_work_order IS '工单表';

-- ----------------------------
-- Records of oh_work_order
-- ----------------------------


-- ----------------------------
-- Field Comments
-- ----------------------------
COMMENT ON COLUMN bpmn_flow_node.proc_def_id IS '流程定义ID';
COMMENT ON COLUMN bpmn_flow_node.act_def_id IS '环节ID';
COMMENT ON COLUMN bpmn_flow_node.node_name IS '环节名称';
COMMENT ON COLUMN bpmn_flow_node.element_type IS '环节类型,UserTask、ExclusiveGateway等';
COMMENT ON COLUMN bpmn_flow_node.condition_expression IS '表达式';
COMMENT ON COLUMN bpmn_flow_node.note IS '备注';
COMMENT ON COLUMN bpmn_flow_node.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN bpmn_flow_node.creator IS '创建者';
COMMENT ON COLUMN bpmn_flow_node.create_time IS '创建时间';
COMMENT ON COLUMN bpmn_flow_node.updater IS '更新者';
COMMENT ON COLUMN bpmn_flow_node.update_time IS '更新时间';
COMMENT ON COLUMN bpmn_task_record.proc_def_id IS '流程定义ID';
COMMENT ON COLUMN bpmn_task_record.proc_inst_id IS '环节实例ID';
COMMENT ON COLUMN bpmn_task_record.act_inst_id IS '环节实例ID';
COMMENT ON COLUMN bpmn_task_record.task_id IS '任务ID';
COMMENT ON COLUMN bpmn_task_record.task_def_id IS '环节key';
COMMENT ON COLUMN bpmn_task_record.task_name IS '环节名称';
COMMENT ON COLUMN bpmn_task_record.from_act_inst_id IS '来自于环节实例ID';
COMMENT ON COLUMN bpmn_task_record.from_task_id IS '来自于任务key';
COMMENT ON COLUMN bpmn_task_record.from_task_def_id IS '来自于环节ID';
COMMENT ON COLUMN bpmn_task_record.from_task_name IS '来自于环节名称';
COMMENT ON COLUMN bpmn_task_record.run_mark IS '当前标识，默认0，1标识当前环节';
COMMENT ON COLUMN bpmn_task_record.assignee IS '受理人ID';
COMMENT ON COLUMN bpmn_task_record.assignee_name IS '受理人';
COMMENT ON COLUMN bpmn_task_record.start_time IS '开始时间';
COMMENT ON COLUMN bpmn_task_record.end_time IS '结束时间';
COMMENT ON COLUMN bpmn_task_record.duration IS '时长';
COMMENT ON COLUMN bpmn_task_record.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN bpmn_task_record.creator IS '创建者';
COMMENT ON COLUMN bpmn_task_record.create_time IS '创建时间';
COMMENT ON COLUMN bpmn_task_record.updater IS '更新者';
COMMENT ON COLUMN bpmn_task_record.update_time IS '更新时间';
COMMENT ON COLUMN data_app.name IS '客户端名称';
COMMENT ON COLUMN data_app.client_id IS '客户端ID';
COMMENT ON COLUMN data_app.secret_key IS '密钥';
COMMENT ON COLUMN data_app.remark IS '备注';
COMMENT ON COLUMN data_app.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN data_function.name IS '功能名称';
COMMENT ON COLUMN data_function.func_code IS '功能号';
COMMENT ON COLUMN data_function.is_async IS '是否异步0否1是';
COMMENT ON COLUMN data_function.remark IS '备注';
COMMENT ON COLUMN data_function.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN data_function_authority.client_id IS '客户端ID';
COMMENT ON COLUMN data_function_authority.func_code IS '功能号';
COMMENT ON COLUMN data_function_authority.remark IS '备注';
COMMENT ON COLUMN data_function_authority.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN data_message.client_id IS '客户端ID';
COMMENT ON COLUMN data_message.func_code IS '功能号';
COMMENT ON COLUMN data_message.topic IS '主题';
COMMENT ON COLUMN data_message.json_str IS '请求参数';
COMMENT ON COLUMN data_message.state IS '状态0未处理1处理2未找到对应的服务类3业务处理失败';
COMMENT ON COLUMN data_message.result_msg IS '响应消息';
COMMENT ON COLUMN data_message.note IS '备注';
COMMENT ON COLUMN data_message.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_project.oh_project IS 'ID';
COMMENT ON COLUMN oh_project.project_code IS '项目编码';
COMMENT ON COLUMN oh_project.project_name IS '项目名称';
COMMENT ON COLUMN oh_project.project_alias IS '别名';
COMMENT ON COLUMN oh_project.start_time IS '开始时间';
COMMENT ON COLUMN oh_project.end_time IS '结束时间';
COMMENT ON COLUMN oh_project.director IS '负责人ID';
COMMENT ON COLUMN oh_project.director_name IS '负责人姓名';
COMMENT ON COLUMN oh_project.status IS '状态（1开始2暂停3关闭）';
COMMENT ON COLUMN oh_project.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_project.creator IS '创建者';
COMMENT ON COLUMN oh_project.create_time IS '创建时间';
COMMENT ON COLUMN oh_project.updater IS '更新者';
COMMENT ON COLUMN oh_project.update_time IS '更新时间';
COMMENT ON COLUMN oh_project_log.project_id IS '项目ID';
COMMENT ON COLUMN oh_project_log.task_id IS '任务ID';
COMMENT ON COLUMN oh_project_log.operation IS '操作类型';
COMMENT ON COLUMN oh_project_log.remark IS '备注';
COMMENT ON COLUMN oh_project_log.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_project_log.creator IS '创建者';
COMMENT ON COLUMN oh_project_log.create_time IS '创建时间';
COMMENT ON COLUMN oh_project_log.updater IS '更新者';
COMMENT ON COLUMN oh_project_log.update_time IS '更新时间';
COMMENT ON COLUMN oh_task.oh_task IS '序号';
COMMENT ON COLUMN oh_task.project_id IS '所属项目';
COMMENT ON COLUMN oh_task.task_title IS '任务标题';
COMMENT ON COLUMN oh_task.note IS '任务描述';
COMMENT ON COLUMN oh_task.task_type IS '1任务2需求3设计4缺陷9其他';
COMMENT ON COLUMN oh_task.parent_id IS '父级任务ID';
COMMENT ON COLUMN oh_task.has_child IS '是否有子任务';
COMMENT ON COLUMN oh_task.start_time IS '计划开始时间';
COMMENT ON COLUMN oh_task.end_time IS '计划结束时间';
COMMENT ON COLUMN oh_task.status IS '状态（1待办项2进行中3已完成）';
COMMENT ON COLUMN oh_task.remark IS '备注';
COMMENT ON COLUMN oh_task.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_task.creator IS '创建者';
COMMENT ON COLUMN oh_task.create_time IS '创建时间';
COMMENT ON COLUMN oh_task.updater IS '更新者';
COMMENT ON COLUMN oh_task.update_time IS '更新时间';
COMMENT ON COLUMN oh_task_user.task_id IS '任务ID';
COMMENT ON COLUMN oh_task_user.user_id IS '人员ID';
COMMENT ON COLUMN oh_task_user.nick_name IS '用户昵称';
COMMENT ON COLUMN oh_task_user.person_type IS '人员类型1负责人2协作人';
COMMENT ON COLUMN oh_task_user.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_task_user.creator IS '创建者';
COMMENT ON COLUMN oh_task_user.create_time IS '创建时间';
COMMENT ON COLUMN oh_task_user.updater IS '更新者';
COMMENT ON COLUMN oh_task_user.update_time IS '更新时间';
COMMENT ON COLUMN oh_work_order.order_code IS '工单编码';
COMMENT ON COLUMN oh_work_order.order_source IS '工单来源';
COMMENT ON COLUMN oh_work_order.report_time IS '上报时间';
COMMENT ON COLUMN oh_work_order.incident_time IS '事发时间';
COMMENT ON COLUMN oh_work_order.end_time IS '结束时间';
COMMENT ON COLUMN oh_work_order.title IS '标题';
COMMENT ON COLUMN oh_work_order.NULL IS '内容';
COMMENT ON COLUMN oh_work_order.address IS '位置';
COMMENT ON COLUMN oh_work_order.NULL IS '经度';
COMMENT ON COLUMN oh_work_order.NULL IS '纬度';
COMMENT ON COLUMN oh_work_order.category IS '类别';
COMMENT ON COLUMN oh_work_order.extend_json IS 'json扩展字段';
COMMENT ON COLUMN oh_work_order.note IS '备注';
COMMENT ON COLUMN oh_work_order.db_status IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN oh_work_order.creator IS '创建者';
COMMENT ON COLUMN oh_work_order.create_time IS '创建时间';
COMMENT ON COLUMN oh_work_order.updater IS '更新者';
COMMENT ON COLUMN oh_work_order.update_time IS '更新时间';


-- ----------------------------
-- Indexes
-- ----------------------------
CREATE UNIQUE INDEX idx_01 ON bpmn_flow(key_code);
CREATE INDEX idx_01 ON bpmn_flow_node(proc_def_id);
CREATE INDEX bpmn_task_record_proc_inst_id_IDX ON bpmn_task_record(proc_inst_id);
CREATE UNIQUE INDEX idx_01 ON data_app(client_id);
CREATE INDEX index2 ON data_function(func_code);
CREATE INDEX index2 ON data_function_authority(client_id);
CREATE INDEX idx_01 ON data_message(topic, client_id, func_code);
CREATE UNIQUE INDEX idx_01 ON oh_task_user(task_id, user_id);
CREATE INDEX idx_02 ON oh_task_user(task_id);
CREATE UNIQUE INDEX idx_order_code ON oh_work_order(order_code);
