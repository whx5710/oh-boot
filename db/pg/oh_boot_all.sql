/*
 Navicat Premium Dump SQL

 Source Server         : postgresql
 Source Server Type    : PostgreSQL
 Source Server Version : 180004 (180004)
 Source Host           : localhost:5432
 Source Catalog        : stepping_stone
 Source Schema         : oh_boot_all

 Target Server Type    : PostgreSQL
 Target Server Version : 180004 (180004)
 File Encoding         : 65001

 Date: 11/07/2026 09:28:37
*/


-- ----------------------------
-- Table structure for bpmn_flow
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."bpmn_flow";
CREATE TABLE "oh_boot_all"."bpmn_flow" (
  "id" int8 NOT NULL,
  "key_code" varchar(30) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "xml" text COLLATE "pg_catalog"."default" NOT NULL,
  "svg_str" text COLLATE "pg_catalog"."default",
  "version_tag" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT 1,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."key_code" IS '流程code';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."name" IS '流程名称';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."xml" IS 'bpmn的xml字符串';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."svg_str" IS 'svg图片字符串';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."version_tag" IS '标签';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."note" IS '说明';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_boot_all"."bpmn_flow" IS '自定义流程表';

-- ----------------------------
-- Records of bpmn_flow
-- ----------------------------
INSERT INTO "oh_boot_all"."bpmn_flow" VALUES (1, 'Process_demo20231222', '流程例子', '<?xml version="1.0" encoding="UTF-8"?>
<bpmn:definitions xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:bpmn="http://www.omg.org/spec/BPMN/20100524/MODEL" xmlns:bpmndi="http://www.omg.org/spec/BPMN/20100524/DI" xmlns:dc="http://www.omg.org/spec/DD/20100524/DC" xmlns:di="http://www.omg.org/spec/DD/20100524/DI" xmlns:camunda="http://camunda.org/schema/1.0/bpmn" id="Definitions_0h3vrm2" targetNamespace="http://bpmn.io/schema/bpmn" exporter="bpmn-js (https://demo.bpmn.io)" exporterVersion="15.1.3">
  <bpmn:process id="Process_demo20231222" name="流程例子" isExecutable="true" camunda:versionTag="demo">
    <bpmn:startEvent id="StartEvent_kaishi" name="开始">
      <bpmn:extensionElements>
        <camunda:formData />
      </bpmn:extensionElements>
      <bpmn:outgoing>Flow_15u340k</bpmn:outgoing>
    </bpmn:startEvent>
    <bpmn:sequenceFlow id="Flow_15u340k" sourceRef="StartEvent_kaishi" targetRef="Activity_shenqingjine" />
    <bpmn:userTask id="Activity_shenqingjine" name="申请金额">
      <bpmn:extensionElements>
        <camunda:formData />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_15u340k</bpmn:incoming>
      <bpmn:outgoing>Flow_0n95qae</bpmn:outgoing>
    </bpmn:userTask>
    <bpmn:exclusiveGateway id="Gateway_1175j6c" default="Flow_1sxpx33">
      <bpmn:incoming>Flow_0n95qae</bpmn:incoming>
      <bpmn:outgoing>Flow_1bpt2xh</bpmn:outgoing>
      <bpmn:outgoing>Flow_1sxpx33</bpmn:outgoing>
    </bpmn:exclusiveGateway>
    <bpmn:sequenceFlow id="Flow_0n95qae" sourceRef="Activity_shenqingjine" targetRef="Gateway_1175j6c" />
    <bpmn:sequenceFlow id="Flow_1bpt2xh" name="金额大于100" sourceRef="Gateway_1175j6c" targetRef="Activity_caiwushenpi">
      <bpmn:conditionExpression xsi:type="bpmn:tFormalExpression">${money > 100}</bpmn:conditionExpression>
    </bpmn:sequenceFlow>
    <bpmn:sequenceFlow id="Flow_104f95a" sourceRef="Activity_caiwushenpi" targetRef="Activity_wanchengshenpi" />
    <bpmn:endEvent id="Event_jieshu" name="结束">
      <bpmn:incoming>Flow_07lrcry</bpmn:incoming>
    </bpmn:endEvent>
    <bpmn:sequenceFlow id="Flow_07lrcry" sourceRef="Activity_wanchengshenpi" targetRef="Event_jieshu" />
    <bpmn:sequenceFlow id="Flow_1sxpx33" name="金额小于或等于100" sourceRef="Gateway_1175j6c" targetRef="Activity_wanchengshenpi" />
    <bpmn:userTask id="Activity_caiwushenpi" name="财务审批" camunda:assignee="王小费">
      <bpmn:extensionElements>
        <camunda:formData />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_1bpt2xh</bpmn:incoming>
      <bpmn:outgoing>Flow_104f95a</bpmn:outgoing>
    </bpmn:userTask>
    <bpmn:userTask id="Activity_wanchengshenpi" name="审批完成">
      <bpmn:extensionElements>
        <camunda:formData />
      </bpmn:extensionElements>
      <bpmn:incoming>Flow_104f95a</bpmn:incoming>
      <bpmn:incoming>Flow_1sxpx33</bpmn:incoming>
      <bpmn:outgoing>Flow_07lrcry</bpmn:outgoing>
    </bpmn:userTask>
  </bpmn:process>
  <bpmndi:BPMNDiagram id="BPMNDiagram_1">
    <bpmndi:BPMNPlane id="Process_demo20231222_di" bpmnElement="Process_demo20231222">
      <bpmndi:BPMNEdge id="Flow_1sxpx33_di" bpmnElement="Flow_1sxpx33">
        <di:waypoint x="440" y="125" />
        <di:waypoint x="440" y="220" />
        <di:waypoint x="750" y="220" />
        <di:waypoint x="750" y="140" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="554" y="202" width="83" height="27" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_07lrcry_di" bpmnElement="Flow_07lrcry">
        <di:waypoint x="800" y="100" />
        <di:waypoint x="872" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_104f95a_di" bpmnElement="Flow_104f95a">
        <di:waypoint x="630" y="100" />
        <di:waypoint x="700" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_1bpt2xh_di" bpmnElement="Flow_1bpt2xh">
        <di:waypoint x="465" y="100" />
        <di:waypoint x="530" y="100" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="467" y="82" width="62" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_0n95qae_di" bpmnElement="Flow_0n95qae">
        <di:waypoint x="350" y="100" />
        <di:waypoint x="415" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNEdge id="Flow_15u340k_di" bpmnElement="Flow_15u340k">
        <di:waypoint x="192" y="100" />
        <di:waypoint x="250" y="100" />
      </bpmndi:BPMNEdge>
      <bpmndi:BPMNShape id="StartEvent_kaishi_di" bpmnElement="StartEvent_kaishi">
        <dc:Bounds x="156" y="82" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="163" y="125" width="23" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_shenqingjine_di" bpmnElement="Activity_shenqingjine">
        <dc:Bounds x="250" y="60" width="100" height="80" />
        <bpmndi:BPMNLabel />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Gateway_1175j6c_di" bpmnElement="Gateway_1175j6c" isMarkerVisible="true">
        <dc:Bounds x="415" y="75" width="50" height="50" />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Event_jieshu_di" bpmnElement="Event_jieshu">
        <dc:Bounds x="872" y="82" width="36" height="36" />
        <bpmndi:BPMNLabel>
          <dc:Bounds x="879" y="125" width="23" height="14" />
        </bpmndi:BPMNLabel>
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_caiwushenpi_di" bpmnElement="Activity_caiwushenpi">
        <dc:Bounds x="530" y="60" width="100" height="80" />
        <bpmndi:BPMNLabel />
      </bpmndi:BPMNShape>
      <bpmndi:BPMNShape id="Activity_wanchengshenpi_di" bpmnElement="Activity_wanchengshenpi">
        <dc:Bounds x="700" y="60" width="100" height="80" />
        <bpmndi:BPMNLabel />
      </bpmndi:BPMNShape>
    </bpmndi:BPMNPlane>
  </bpmndi:BPMNDiagram>
</bpmn:definitions>
', '<?xml version="1.0" encoding="utf-8"?>
<!-- created with bpmn-js / http://bpmn.io -->
<!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd">
<svg xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" width="764" height="181" viewBox="150 54 764 181" version="1.1"><defs><marker id="sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1" viewBox="0 0 20 20" refX="11" refY="10" markerWidth="10" markerHeight="10" orient="auto"><path d="M 1 5 L 11 10 L 1 15 Z" style="fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;"/></marker><marker id="conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1" viewBox="0 0 20 20" refX="0" refY="10" markerWidth="10" markerHeight="10" orient="auto"><path d="M 6 4 L 10 16" style="fill: black; stroke-width: 1px; stroke-linecap: round; stroke-dasharray: 10000, 1; stroke: black;"/></marker></defs><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_1sxpx33" style="display: block;"><g class="djs-visual"><path d="m  440,125L440,220 L750,220 L750,140 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1''); marker-start: url(''#conditional-default-flow-marker-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="440,125 440,220 750,220 750,140 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="434" y="119" width="322" height="107" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_07lrcry" style="display: block;"><g class="djs-visual"><path d="m  800,100L872,100 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="800,100 872,100 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="794" y="94" width="84" height="12" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_104f95a" style="display: block;"><g class="djs-visual"><path d="m  630,100L700,100 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="630,100 700,100 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="624" y="94" width="82" height="12" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_1bpt2xh" style="display: block;"><g class="djs-visual"><path d="m  465,100L530,100 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="465,100 530,100 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="459" y="94" width="77" height="12" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_0n95qae" style="display: block;"><g class="djs-visual"><path d="m  350,100L415,100 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="350,100 415,100 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="344" y="94" width="77" height="12" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-connection" data-element-id="Flow_15u340k" style="display: block;"><g class="djs-visual"><path d="m  192,100L250,100 " style="fill: none; stroke-width: 2px; stroke: black; stroke-linejoin: round; marker-end: url(''#sequenceflow-end-white-black-52u4oo3f2hzaevrbkxd9lssq1'');"/></g><polyline points="192,100 250,100 " class="djs-hit djs-hit-stroke" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="186" y="94" width="70" height="12" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="StartEvent_kaishi" style="display: block;" transform="matrix(1 0 0 1 156 82)"><g class="djs-visual"><circle cx="18" cy="18" r="18" style="stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="36" height="36" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="48" height="48" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="StartEvent_1s3b1oq_label" style="display: block;" transform="matrix(1 0 0 1 163 125)"><g class="djs-visual"><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;"><tspan x="0" y="9.899999999999999">开始</tspan></text></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="23" height="14" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="35" height="26" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Activity_shenqingjine" style="display: block;" transform="matrix(1 0 0 1 250 60)"><g class="djs-visual"><rect x="0" y="0" width="100" height="80" rx="10" ry="10" style="stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;"/><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;"><tspan x="26" y="43.599999999999994">申请金额</tspan></text><path d="m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5" style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 " style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z" style="fill: black; stroke-width: 0.5px; stroke: black;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="100" height="80" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="112" height="92" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Gateway_1175j6c" style="display: block;" transform="matrix(1 0 0 1 415 75)"><g class="djs-visual"><polygon points="25,0 50,25 25,50 0,25" style="stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;"/><path d="m 16,15 7.42857142857143,9.714285714285715 -7.42857142857143,9.714285714285715 3.428571428571429,0 5.714285714285715,-7.464228571428572 5.714285714285715,7.464228571428572 3.428571428571429,0 -7.42857142857143,-9.714285714285715 7.42857142857143,-9.714285714285715 -3.428571428571429,0 -5.714285714285715,7.464228571428572 -5.714285714285715,-7.464228571428572 -3.428571428571429,0 z" style="fill: black; stroke-width: 1px; stroke: black;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="50" height="50" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="62" height="62" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Event_jieshu" style="display: block;" transform="matrix(1 0 0 1 872 82)"><g class="djs-visual"><circle cx="18" cy="18" r="18" style="stroke: black; stroke-width: 4px; fill: white; fill-opacity: 0.95;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="36" height="36" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="48" height="48" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Event_04q0avn_label" style="display: block;" transform="matrix(1 0 0 1 879 125)"><g class="djs-visual"><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;"><tspan x="0" y="9.899999999999999">结束</tspan></text></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="23" height="14" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="35" height="26" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Activity_caiwushenpi" style="display: block;" transform="matrix(1 0 0 1 530 60)"><g class="djs-visual"><rect x="0" y="0" width="100" height="80" rx="10" ry="10" style="stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;"/><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;"><tspan x="26" y="43.599999999999994">财务审批</tspan></text><path d="m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5" style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 " style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z" style="fill: black; stroke-width: 0.5px; stroke: black;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="100" height="80" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="112" height="92" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Activity_wanchengshenpi" style="display: block;" transform="matrix(1 0 0 1 700 60)"><g class="djs-visual"><rect x="0" y="0" width="100" height="80" rx="10" ry="10" style="stroke: black; stroke-width: 2px; fill: white; fill-opacity: 0.95;"/><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 12px; font-weight: normal; fill: black;"><tspan x="26" y="43.599999999999994">审批完成</tspan></text><path d="m 15,12 c 0.909,-0.845 1.594,-2.049 1.594,-3.385 0,-2.554 -1.805,-4.62199999 -4.357,-4.62199999 -2.55199998,0 -4.28799998,2.06799999 -4.28799998,4.62199999 0,1.348 0.974,2.562 1.89599998,3.405 -0.52899998,0.187 -5.669,2.097 -5.794,4.7560005 v 6.718 h 17 v -6.718 c 0,-2.2980005 -5.5279996,-4.5950005 -6.0509996,-4.7760005 zm -8,6 l 0,5.5 m 11,0 l 0,-5" style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m 2.162,1.009 c 0,2.4470005 -2.158,4.4310005 -4.821,4.4310005 -2.66499998,0 -4.822,-1.981 -4.822,-4.4310005 " style="fill: white; stroke-width: 0.5px; stroke: black;"/><path d="m 15,12 m -6.9,-3.80 c 0,0 2.25099998,-2.358 4.27399998,-1.177 2.024,1.181 4.221,1.537 4.124,0.965 -0.098,-0.57 -0.117,-3.79099999 -4.191,-4.13599999 -3.57499998,0.001 -4.20799998,3.36699999 -4.20699998,4.34799999 z" style="fill: black; stroke-width: 0.5px; stroke: black;"/></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="100" height="80" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="112" height="92" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Flow_1bpt2xh_label" style="display: block;" transform="matrix(1 0 0 1 467 82)"><g class="djs-visual"><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;"><tspan x="0" y="9.899999999999999">金额大于100</tspan></text></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="63" height="14" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="75" height="26" class="djs-outline" style="fill: none;"/></g></g><g class="djs-group"><g class="djs-element djs-shape" data-element-id="Flow_1sxpx33_label" style="display: block;" transform="matrix(1 0 0 1 554 202)"><g class="djs-visual"><text lineHeight="1.2" class="djs-label" style="font-family: Arial, sans-serif; font-size: 11px; font-weight: normal; fill: black;"><tspan x="0" y="9.899999999999999">金额小于或等于1</tspan><tspan x="35.44140625" y="23.099999999999998">00</tspan></text></g><rect class="djs-hit djs-hit-all" x="0" y="0" width="84" height="27" style="fill: none; stroke-opacity: 0; stroke: white; stroke-width: 15px;"/><rect x="-6" y="-6" width="96" height="39" class="djs-outline" style="fill: none;"/></g></g></svg>', 'demo', '第一个例子', 1, 10000, '2026-03-29 17:11:40', NULL, NULL);

-- ----------------------------
-- Table structure for bpmn_flow_node
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."bpmn_flow_node";
CREATE TABLE "oh_boot_all"."bpmn_flow_node" (
  "id" int8 NOT NULL,
  "proc_def_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "act_def_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "node_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "element_type" varchar(80) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "condition_expression" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "json_params" varchar(800) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(1500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "sort" int2 DEFAULT 1,
  "db_status" int2 DEFAULT 1,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."proc_def_id" IS '流程定义ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."act_def_id" IS '环节ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."node_name" IS '环节名称';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."element_type" IS '环节类型,UserTask、ExclusiveGateway等';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."condition_expression" IS '表达式';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."json_params" IS '自定义json参数';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."note" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."sort" IS '排序';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_flow_node"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_boot_all"."bpmn_flow_node" IS '环节定义表';

-- ----------------------------
-- Records of bpmn_flow_node
-- ----------------------------
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308288, 'Process_demo20231222:1:61373979654754304', 'StartEvent_kaishi', '开始', 'StartEvent', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308289, 'Process_demo20231222:1:61373979654754304', 'Flow_15u340k', NULL, 'SequenceFlow', NULL, NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308290, 'Process_demo20231222:1:61373979654754304', 'Activity_shenqingjine', '申请金额', 'UserTask', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308291, 'Process_demo20231222:1:61373979654754304', 'Gateway_1175j6c', NULL, 'ExclusiveGateway', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308292, 'Process_demo20231222:1:61373979654754304', 'Flow_0n95qae', NULL, 'SequenceFlow', NULL, NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980279308293, 'Process_demo20231222:1:61373979654754304', 'Flow_1bpt2xh', '金额大于100', 'SequenceFlow', '${money > 100}', NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502592, 'Process_demo20231222:1:61373979654754304', 'Flow_104f95a', NULL, 'SequenceFlow', NULL, NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502593, 'Process_demo20231222:1:61373979654754304', 'Event_jieshu', '结束', 'EndEvent', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502594, 'Process_demo20231222:1:61373979654754304', 'Flow_07lrcry', NULL, 'SequenceFlow', NULL, NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502595, 'Process_demo20231222:1:61373979654754304', 'Flow_1sxpx33', '金额小于或等于100', 'SequenceFlow', NULL, NULL, NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502596, 'Process_demo20231222:1:61373979654754304', 'Activity_caiwushenpi', '财务审批', 'UserTask', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);
INSERT INTO "oh_boot_all"."bpmn_flow_node" VALUES (61373980283502597, 'Process_demo20231222:1:61373979654754304', 'Activity_wanchengshenpi', '审批完成', 'UserTask', NULL, '{"timeLimit":"时限，单位秒","autoComplete":"自动完成 true|false","assigneeName":"受理人名称","assigneeType":"受理人类型1人员2角色3部门","assignee":"受理人编码"}', NULL, 1, 1, 10000, '2026-06-19 08:38:17.171579', NULL, NULL);

-- ----------------------------
-- Table structure for bpmn_task_record
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."bpmn_task_record";
CREATE TABLE "oh_boot_all"."bpmn_task_record" (
  "id" int8 NOT NULL,
  "proc_def_id" varchar(100) COLLATE "pg_catalog"."default" NOT NULL,
  "proc_inst_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "act_inst_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "task_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "task_def_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "task_name" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "from_act_inst_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "from_task_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "from_task_def_id" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "from_task_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "run_mark" int4 DEFAULT 0,
  "assignee" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "assignee_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "start_time" timestamp(6),
  "end_time" timestamp(6),
  "duration" int8,
  "db_status" int2 DEFAULT 1,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."proc_def_id" IS '流程定义ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."proc_inst_id" IS '环节实例ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."act_inst_id" IS '环节实例ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."task_id" IS '任务ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."task_def_id" IS '环节key';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."task_name" IS '环节名称';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."from_act_inst_id" IS '来自于环节实例ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."from_task_id" IS '来自于任务key';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."from_task_def_id" IS '来自于环节ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."from_task_name" IS '来自于环节名称';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."run_mark" IS '当前标识，默认0，1标识当前环节';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."assignee" IS '受理人ID';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."assignee_name" IS '受理人';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."start_time" IS '开始时间';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."end_time" IS '结束时间';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."duration" IS '时长';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."bpmn_task_record"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_boot_all"."bpmn_task_record" IS '环节运行记录表';

-- ----------------------------
-- Records of bpmn_task_record
-- ----------------------------

-- ----------------------------
-- Table structure for data_app
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."data_app";
CREATE TABLE "oh_boot_all"."data_app" (
  "id" int8 NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "client_id" varchar(45) COLLATE "pg_catalog"."default" NOT NULL,
  "secret_key" varchar(45) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "db_status" int2 DEFAULT 1
)
;
COMMENT ON COLUMN "oh_boot_all"."data_app"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."data_app"."name" IS '客户端名称';
COMMENT ON COLUMN "oh_boot_all"."data_app"."client_id" IS '客户端ID';
COMMENT ON COLUMN "oh_boot_all"."data_app"."secret_key" IS '密钥';
COMMENT ON COLUMN "oh_boot_all"."data_app"."remark" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."data_app"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."data_app"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."data_app"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."data_app"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_boot_all"."data_app"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON TABLE "oh_boot_all"."data_app" IS '客户端';

-- ----------------------------
-- Records of data_app
-- ----------------------------
INSERT INTO "oh_boot_all"."data_app" VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 1);
INSERT INTO "oh_boot_all"."data_app" VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 1);
INSERT INTO "oh_boot_all"."data_app" VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 1);

-- ----------------------------
-- Table structure for data_function
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."data_function";
CREATE TABLE "oh_boot_all"."data_function" (
  "id" int8 NOT NULL,
  "name" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "func_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "is_async" bool NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "db_status" int2 DEFAULT 1
)
;
COMMENT ON COLUMN "oh_boot_all"."data_function"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."data_function"."name" IS '功能名称';
COMMENT ON COLUMN "oh_boot_all"."data_function"."func_code" IS '功能号';
COMMENT ON COLUMN "oh_boot_all"."data_function"."is_async" IS '是否异步0否1是';
COMMENT ON COLUMN "oh_boot_all"."data_function"."remark" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."data_function"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."data_function"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."data_function"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."data_function"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_boot_all"."data_function"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON TABLE "oh_boot_all"."data_function" IS '功能列表';

-- ----------------------------
-- Records of data_function
-- ----------------------------
INSERT INTO "oh_boot_all"."data_function" VALUES (1, '用户信息上传', 'F1001', 't', '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 1);
INSERT INTO "oh_boot_all"."data_function" VALUES (2, '账单查询', 'F1002', 'f', '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 1);
INSERT INTO "oh_boot_all"."data_function" VALUES (3, '新增工单', 'F1003', 't', '', 10000, '2024-02-28 21:50:47', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_function_authority
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."data_function_authority";
CREATE TABLE "oh_boot_all"."data_function_authority" (
  "id" int8 NOT NULL,
  "client_id" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "func_code" varchar(50) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "db_status" int2 DEFAULT 1
)
;
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."client_id" IS '客户端ID';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."func_code" IS '功能号';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."remark" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_boot_all"."data_function_authority"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON TABLE "oh_boot_all"."data_function_authority" IS '客户端接口授权';

-- ----------------------------
-- Records of data_function_authority
-- ----------------------------
INSERT INTO "oh_boot_all"."data_function_authority" VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO "oh_boot_all"."data_function_authority" VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);
INSERT INTO "oh_boot_all"."data_function_authority" VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 1);
INSERT INTO "oh_boot_all"."data_function_authority" VALUES (4, 'C0001', 'F1003', NULL, 10000, '2024-02-28 21:51:00', NULL, NULL, 1);

-- ----------------------------
-- Table structure for data_message
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."data_message";
CREATE TABLE "oh_boot_all"."data_message" (
  "id" int8 NOT NULL,
  "client_id" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "func_code" varchar(200) COLLATE "pg_catalog"."default" NOT NULL,
  "topic" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "json_str" varchar(3000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "state" varchar(1) COLLATE "pg_catalog"."default" DEFAULT '0'::character varying,
  "result_msg" varchar(3000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(500) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "creator" int8,
  "create_time" timestamp(3) DEFAULT NULL::timestamp without time zone,
  "updater" int8,
  "update_time" timestamp(6),
  "db_status" int2 DEFAULT 1
)
;
COMMENT ON COLUMN "oh_boot_all"."data_message"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."data_message"."client_id" IS '客户端ID';
COMMENT ON COLUMN "oh_boot_all"."data_message"."func_code" IS '功能号';
COMMENT ON COLUMN "oh_boot_all"."data_message"."topic" IS '主题';
COMMENT ON COLUMN "oh_boot_all"."data_message"."json_str" IS '请求参数';
COMMENT ON COLUMN "oh_boot_all"."data_message"."state" IS '状态0未处理1处理2未找到对应的服务类3业务处理失败';
COMMENT ON COLUMN "oh_boot_all"."data_message"."result_msg" IS '响应消息';
COMMENT ON COLUMN "oh_boot_all"."data_message"."note" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."data_message"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."data_message"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."data_message"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."data_message"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_boot_all"."data_message"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON TABLE "oh_boot_all"."data_message" IS '接口参数数据';

-- ----------------------------
-- Records of data_message
-- ----------------------------

-- ----------------------------
-- Table structure for oh_work_order
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."oh_work_order";
CREATE TABLE "oh_boot_all"."oh_work_order" (
  "id" int8 NOT NULL,
  "order_code" varchar(40) COLLATE "pg_catalog"."default" NOT NULL,
  "order_source" varchar(2) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "report_time" timestamp(6),
  "incident_time" timestamp(6),
  "end_time" timestamp(6),
  "title" varchar(50) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "comment" varchar(2000) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "address" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "geo_x" numeric(12,9) DEFAULT NULL::numeric,
  "geo_y" numeric(12,9) DEFAULT NULL::numeric,
  "category" int4,
  "extend_json" varchar(300) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "note" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int2 DEFAULT 1,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."id" IS 'id';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."order_code" IS '工单编码';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."order_source" IS '工单来源';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."report_time" IS '上报时间';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."incident_time" IS '事发时间';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."end_time" IS '结束时间';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."title" IS '标题';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."comment" IS '内容';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."address" IS '位置';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."geo_x" IS '经度';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."geo_y" IS '纬度';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."category" IS '类别';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."extend_json" IS 'json扩展字段';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."note" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."oh_work_order"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_boot_all"."oh_work_order" IS '工单表';

-- ----------------------------
-- Records of oh_work_order
-- ----------------------------

-- ----------------------------
-- Table structure for ur_event
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."ur_event";
CREATE TABLE "oh_boot_all"."ur_event" (
  "id" int8 NOT NULL,
  "code" varchar(20) COLLATE "pg_catalog"."default",
  "report_time" timestamp(6) NOT NULL,
  "description" varchar(1000) COLLATE "pg_catalog"."default" NOT NULL,
  "location" varchar(300) COLLATE "pg_catalog"."default",
  "longitude" numeric(12,8),
  "latitude" numeric(12,8),
  "mobile" varchar(30) COLLATE "pg_catalog"."default",
  "anonymous" int2 DEFAULT 0,
  "open_id" varchar(80) COLLATE "pg_catalog"."default",
  "accept_status" varchar(2) COLLATE "pg_catalog"."default" NOT NULL DEFAULT 1,
  "completion_time" timestamp(6),
  "rejection_opinion" varchar(500) COLLATE "pg_catalog"."default",
  "handling_opinion" varchar(500) COLLATE "pg_catalog"."default",
  "remark" varchar(255) COLLATE "pg_catalog"."default",
  "db_status" int2 NOT NULL DEFAULT '1'::smallint,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6),
  "evt_type" varchar(255) COLLATE "pg_catalog"."default"
)
;
COMMENT ON COLUMN "oh_boot_all"."ur_event"."code" IS '事件编码';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."report_time" IS '上报时间';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."description" IS '问题描述';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."location" IS '事发位置';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."longitude" IS '经度';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."latitude" IS '纬度';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."mobile" IS '联系电话';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."anonymous" IS '匿名标识1匿名';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."open_id" IS '对应第三方用户ID';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."accept_status" IS '受理状态，1待处理2处理中3已解决4已驳回';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."completion_time" IS '完成时间';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."rejection_opinion" IS '驳回意见';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."handling_opinion" IS '处理意见';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."remark" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."update_time" IS '更新时间';
COMMENT ON COLUMN "oh_boot_all"."ur_event"."evt_type" IS '案件类别';
COMMENT ON TABLE "oh_boot_all"."ur_event" IS '事件表';

-- ----------------------------
-- Records of ur_event
-- ----------------------------
INSERT INTO "oh_boot_all"."ur_event" VALUES (62144444652060672, '2026062100004', '2026-06-21 11:39:50.193564', '1234564666啾啾啾啾啾啾', '望城区永旺梦乐城(长沙湘江新区店)', 112.88053000, 28.27082400, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '1', NULL, NULL, NULL, NULL, 1, 62134255584542720, '2026-06-21 11:39:50.193564', NULL, NULL, 'order');
INSERT INTO "oh_boot_all"."ur_event" VALUES (62142374800785408, '2026062100002', '2026-06-21 11:31:36.701696', '测试一下美美美美美单独', '望城区中粮·鸿云', 112.87427000, 28.26579700, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '1', NULL, NULL, NULL, NULL, 1, 62134255584542720, '2026-06-21 11:31:36.702397', NULL, NULL, 'order');
INSERT INTO "oh_boot_all"."ur_event" VALUES (60496264030060544, '2026061600004', '2026-06-16 22:30:33.3111', '664664673733184884376764944', '望城区中粮·鸿云', 112.87427500, 28.26578500, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '1', NULL, NULL, NULL, NULL, 1, 60478214237061120, '2026-06-16 22:30:33.311866', NULL, NULL, 'facility');
INSERT INTO "oh_boot_all"."ur_event" VALUES (62139465803497472, '2026062100001', '2026-06-21 11:20:03.138365', '卡卡卡卡卡卡里没钱了吗丁啉123', '望城区中粮·鸿云', 112.87445000, 28.26594700, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '2', NULL, NULL, NULL, NULL, 1, 62134255584542720, '2026-06-21 11:20:03.142877', NULL, NULL, 'facility');
INSERT INTO "oh_boot_all"."ur_event" VALUES (60495919346352128, '2026061600003', '2026-06-16 22:29:11.131148', '664664673733184884376764944', '望城区中粮·鸿云', 112.87427500, 28.26578500, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '1', NULL, NULL, NULL, NULL, 1, 60478214237061120, '2026-06-16 22:29:11.132148', NULL, NULL, 'facility');
INSERT INTO "oh_boot_all"."ur_event" VALUES (60490298819608576, '2026061600002', '2026-06-16 22:06:51.094789', '64664664664676676434464', '望城区中粮·鸿云', 112.87427500, 28.26578500, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '4', NULL, '驳回了啊呀', NULL, NULL, 1, 60478214237061120, '2026-06-16 22:06:51.094789', NULL, NULL, 'facility');
INSERT INTO "oh_boot_all"."ur_event" VALUES (62142903673159680, '2026062100003', '2026-06-21 11:33:42.795089', '就斤斤计较啾啾啾啾啾啾', '望城区永旺梦乐城(长沙湘江新区店)', 112.88005000, 28.27020800, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '3', '2026-06-21 18:11:10', NULL, '处理意见哇咔咔', NULL, 1, 62134255584542720, '2026-06-21 11:33:42.795089', NULL, NULL, 'service');
INSERT INTO "oh_boot_all"."ur_event" VALUES (62244221918117888, '2026062100005', '2026-06-21 18:16:18.947989', '问题描述一下，设施损坏了', '望城区长沙星河湾东北(龙湖路北)', 112.86378500, 28.26475300, '15074825710', 0, 'oOcA75aMalCE_KSyD3U0AWwxUUks', '3', '2026-06-21 18:17:22', NULL, '处理意见', NULL, 1, 62134255584542720, '2026-06-21 18:16:18.947989', NULL, NULL, 'facility');

-- ----------------------------
-- Table structure for ur_multi_media
-- ----------------------------
DROP TABLE IF EXISTS "oh_boot_all"."ur_multi_media";
CREATE TABLE "oh_boot_all"."ur_multi_media" (
  "id" int8 NOT NULL,
  "evt_id" int8 NOT NULL,
  "file_id" varchar(80) COLLATE "pg_catalog"."default" NOT NULL,
  "file_name" varchar(100) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "status_type" varchar(2) COLLATE "pg_catalog"."default" NOT NULL,
  "remark" varchar(255) COLLATE "pg_catalog"."default" DEFAULT NULL::character varying,
  "db_status" int4 NOT NULL DEFAULT 1,
  "creator" int8,
  "create_time" timestamp(6),
  "updater" int8,
  "update_time" timestamp(6)
)
;
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."evt_id" IS '事件ID';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."file_id" IS '文件ID';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."file_name" IS '文件名';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."status_type" IS '所属状态：1待处理2处理中3已解决4已驳回';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."remark" IS '备注';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."db_status" IS '数据状态标识 0：已删除，1：正常';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."creator" IS '创建者';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."create_time" IS '创建时间';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."updater" IS '更新者';
COMMENT ON COLUMN "oh_boot_all"."ur_multi_media"."update_time" IS '更新时间';
COMMENT ON TABLE "oh_boot_all"."ur_multi_media" IS '多媒体表';

-- ----------------------------
-- Records of ur_multi_media
-- ----------------------------
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (60496264038449152, 60496264030060544, '3ec0c4deea8d492588bbbc51e5b775f2.jpg', 'tmp_9f02c48cbef05e066da0f6bf361c778ea549665fb654b6d4.jpg', '1', NULL, 1, 60478214237061120, '2026-06-16 22:30:33.313876', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (60496264059420672, 60496264030060544, 'b7571f4e55f6445ab7e1d78889748636.jpg', 'tmp_7bc9d7b6daa63c18a7baafdb218d51f360051b867aa387e9.jpg', '1', NULL, 1, 60478214237061120, '2026-06-16 22:30:33.318882', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62139465887383552, 62139465803497472, '47c3421ca4914cba99a721a73a20e97e.jpg', 'tmp_0543adf925af415b1e6a0e68a5c059352cd8c592d7c1e43e.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 11:20:03.163676', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62142374813368320, 62142374800785408, '6db2a3948f2649619434a4b98b22b9c4.jpg', 'tmp_21204dab2c79a95b3a895a3806c346cf54120826927288aa.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 11:31:36.70554', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62142903689936896, 62142903673159680, '846075c6036a4f5981f9bc4fa9a49094.jpg', 'tmp_3c066ac9f429fc8deb05f3074a4f5b7b24ccb28bf6b59143.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 11:33:42.799346', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62144444668837888, 62144444652060672, 'cc9950000935405fbc169b41d6c0cc5d.jpg', 'tmp_6ca2847dbfab136f23bb9d9d866a0cf3957af82794bbb46b.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 11:39:50.19742', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62244221943283712, 62244221918117888, '8625eea18b524e35a3c829d48efeb155.jpg', 'tmp_436b0ceb243fb36266576a393f7e5187093d20ab3ef87951.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 18:16:18.953733', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62244221960060928, 62244221918117888, 'beefbcf13c5247a99740aecf41744dfb.jpg', 'tmp_9a16c23d57a5a795fb85971977e77e5095322a7cbb0845a5.jpg', '1', NULL, 1, 62134255584542720, '2026-06-21 18:16:18.957336', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62244221972643840, 62244221918117888, '0ae9e7eff5774f528501799ef64addb0.jpg', 'tmp_3b2429cba001ba2c2b05ed76ede26c42f335fd9b73eb274d.jpg', '3', NULL, 1, 62134255584542720, '2026-06-21 18:16:18.960014', NULL, NULL);
INSERT INTO "oh_boot_all"."ur_multi_media" VALUES (62244221968449536, 62244221918117888, '3058e100129b4447bf64ab73cc3d373a.jpg', 'tmp_5b118a7087568474d9702aa1396650e0180c5a687e9b0163.jpg', '3', NULL, 1, 62134255584542720, '2026-06-21 18:16:18.958595', NULL, NULL);

-- ----------------------------
-- Uniques structure for table bpmn_flow
-- ----------------------------
ALTER TABLE "oh_boot_all"."bpmn_flow" ADD CONSTRAINT "bpmn_flow_key_code_key" UNIQUE ("key_code");

-- ----------------------------
-- Primary Key structure for table bpmn_flow
-- ----------------------------
ALTER TABLE "oh_boot_all"."bpmn_flow" ADD CONSTRAINT "bpmn_flow_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table bpmn_flow_node
-- ----------------------------
CREATE INDEX "idx_01" ON "oh_boot_all"."bpmn_flow_node" USING btree (
  "proc_def_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table bpmn_flow_node
-- ----------------------------
ALTER TABLE "oh_boot_all"."bpmn_flow_node" ADD CONSTRAINT "bpmn_flow_node_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table bpmn_task_record
-- ----------------------------
CREATE INDEX "bpmn_task_record_proc_inst_id_idx" ON "oh_boot_all"."bpmn_task_record" USING btree (
  "proc_inst_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table bpmn_task_record
-- ----------------------------
ALTER TABLE "oh_boot_all"."bpmn_task_record" ADD CONSTRAINT "bpmn_task_record_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table data_app
-- ----------------------------
ALTER TABLE "oh_boot_all"."data_app" ADD CONSTRAINT "data_app_client_id_key" UNIQUE ("client_id");

-- ----------------------------
-- Primary Key structure for table data_app
-- ----------------------------
ALTER TABLE "oh_boot_all"."data_app" ADD CONSTRAINT "data_app_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table data_function
-- ----------------------------
CREATE INDEX "data_function_index2" ON "oh_boot_all"."data_function" USING btree (
  "func_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table data_function
-- ----------------------------
ALTER TABLE "oh_boot_all"."data_function" ADD CONSTRAINT "data_function_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table data_function_authority
-- ----------------------------
CREATE INDEX "index2" ON "oh_boot_all"."data_function_authority" USING btree (
  "client_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table data_function_authority
-- ----------------------------
ALTER TABLE "oh_boot_all"."data_function_authority" ADD CONSTRAINT "data_function_authority_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table data_message
-- ----------------------------
CREATE INDEX "data_message_idx_01" ON "oh_boot_all"."data_message" USING btree (
  "topic" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "client_id" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST,
  "func_code" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table data_message
-- ----------------------------
ALTER TABLE "oh_boot_all"."data_message" ADD CONSTRAINT "data_message_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table oh_work_order
-- ----------------------------
ALTER TABLE "oh_boot_all"."oh_work_order" ADD CONSTRAINT "oh_work_order_order_code_key" UNIQUE ("order_code");

-- ----------------------------
-- Primary Key structure for table oh_work_order
-- ----------------------------
ALTER TABLE "oh_boot_all"."oh_work_order" ADD CONSTRAINT "oh_work_order_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Primary Key structure for table ur_event
-- ----------------------------
ALTER TABLE "oh_boot_all"."ur_event" ADD CONSTRAINT "ur_event_pkey" PRIMARY KEY ("id");

-- ----------------------------
-- Indexes structure for table ur_multi_media
-- ----------------------------
CREATE INDEX "idx_media_evt_id" ON "oh_boot_all"."ur_multi_media" USING btree (
  "evt_id" "pg_catalog"."int8_ops" ASC NULLS LAST
);
CREATE INDEX "idx_media_type" ON "oh_boot_all"."ur_multi_media" USING btree (
  "status_type" COLLATE "pg_catalog"."default" "pg_catalog"."text_ops" ASC NULLS LAST
);

-- ----------------------------
-- Primary Key structure for table ur_multi_media
-- ----------------------------
ALTER TABLE "oh_boot_all"."ur_multi_media" ADD CONSTRAINT "ur_multi_media_pkey" PRIMARY KEY ("id");
