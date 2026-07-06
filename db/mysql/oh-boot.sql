SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `bpmn_flow`;

CREATE TABLE `bpmn_flow` (
  `id` bigint NOT NULL COMMENT 'id',
  `key_code` varchar(30) DEFAULT NULL COMMENT '流程code',
  `name` varchar(50) NOT NULL COMMENT '流程名称',
  `xml` longtext NOT NULL COMMENT 'bpmn的xml字符串',
  `svg_str` longtext COMMENT 'svg图片字符串',
  `version_tag` varchar(50) DEFAULT NULL COMMENT '标签',
  `note` varchar(500) DEFAULT NULL COMMENT '说明',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `bpmn_flow` COMMENT = '自定义流程表';

INSERT INTO `bpmn_flow` VALUES (1, 'Process_demo20231222', '流程例子', '<?xml version="1.0" encoding="UTF-8"?>
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

DROP TABLE IF EXISTS `bpmn_flow_node`;

CREATE TABLE `bpmn_flow_node` (
  `id` bigint NOT NULL COMMENT 'id',
  `proc_def_id` varchar(100) NOT NULL COMMENT '流程定义ID',
  `act_def_id` varchar(100) NOT NULL COMMENT '环节ID',
  `node_name` varchar(255) DEFAULT NULL COMMENT '环节名称',
  `element_type` varchar(80) DEFAULT NULL COMMENT '环节类型,UserTask、ExclusiveGateway等',
  `condition_expression` varchar(500) DEFAULT NULL COMMENT '表达式',
  `json_params` varchar(800) DEFAULT NULL COMMENT '自定义json参数',
  `note` varchar(1500) DEFAULT NULL COMMENT '备注',
  `sort` smallint DEFAULT 1 COMMENT '排序',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `bpmn_flow_node` COMMENT = '环节定义表';

DROP TABLE IF EXISTS `bpmn_task_record`;

CREATE TABLE `bpmn_task_record` (
  `id` bigint NOT NULL COMMENT 'id',
  `proc_def_id` varchar(100) NOT NULL COMMENT '流程定义ID',
  `proc_inst_id` varchar(100) DEFAULT NULL COMMENT '环节实例ID',
  `act_inst_id` varchar(100) DEFAULT NULL COMMENT '环节实例ID',
  `task_id` varchar(100) DEFAULT NULL COMMENT '任务ID',
  `task_def_id` varchar(100) DEFAULT NULL COMMENT '环节key',
  `task_name` varchar(255) DEFAULT NULL COMMENT '环节名称',
  `from_act_inst_id` varchar(100) DEFAULT NULL COMMENT '来自于环节实例ID',
  `from_task_id` varchar(100) DEFAULT NULL COMMENT '来自于任务key',
  `from_task_def_id` varchar(100) DEFAULT NULL COMMENT '来自于环节ID',
  `from_task_name` varchar(100) DEFAULT NULL COMMENT '来自于环节名称',
  `run_mark` int DEFAULT 0 COMMENT '当前标识，默认0，1标识当前环节',
  `assignee` varchar(100) DEFAULT NULL COMMENT '受理人ID',
  `assignee_name` varchar(100) DEFAULT NULL COMMENT '受理人',
  `start_time` datetime(6) COMMENT '开始时间',
  `end_time` datetime(6) COMMENT '结束时间',
  `duration` bigint COMMENT '时长',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `bpmn_task_record` COMMENT = '环节运行记录表';

DROP TABLE IF EXISTS `data_app`;

CREATE TABLE `data_app` (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '客户端名称',
  `client_id` varchar(45) NOT NULL COMMENT '客户端ID',
  `secret_key` varchar(45) NOT NULL COMMENT '密钥',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `data_app` COMMENT = '客户端';

INSERT INTO `data_app` VALUES (1, '饿了吧集团', 'C0001', 'c28a8120682d4b4fa50325ed34748e0e', '饿了吧接口对接配置', 10000, '2023-07-29 13:04:34', 10000, '2023-07-29 13:04:34', 1);

INSERT INTO `data_app` VALUES (2, '阿狸芭比公司', 'C0002', '0682d4b4fa50325ed347', '', 10000, '2023-07-29 13:05:28', 10000, '2023-07-29 13:05:28', 1);

INSERT INTO `data_app` VALUES (3, '支付包', 'C003', '8a8120682d4b4fa50325ed', '', 10000, '2023-08-12 19:53:17', 10000, '2023-08-12 19:53:17', 1);

DROP TABLE IF EXISTS `data_function`;

CREATE TABLE `data_function` (
  `id` bigint NOT NULL COMMENT 'id',
  `name` varchar(50) NOT NULL COMMENT '功能名称',
  `func_code` varchar(50) NOT NULL COMMENT '功能号',
  `is_async` tinyint(1) NOT NULL COMMENT '是否异步0否1是',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `data_function` COMMENT = '功能列表';

INSERT INTO `data_function` VALUES (1, '用户信息上传', 'F1001', 1, '用户信息同步', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:13', 1);

INSERT INTO `data_function` VALUES (2, '账单查询', 'F1002', 0, '查询账单信息', 10000, '2023-08-02 21:29:22', 10000, '2023-07-30 13:32:18', 1);

INSERT INTO `data_function` VALUES (3, '新增工单', 'F1003', 1, '', 10000, '2024-02-28 21:50:47', NULL, NULL, 1);

DROP TABLE IF EXISTS `data_function_authority`;

CREATE TABLE `data_function_authority` (
  `id` bigint NOT NULL COMMENT 'id',
  `client_id` varchar(50) NOT NULL COMMENT '客户端ID',
  `func_code` varchar(50) NOT NULL COMMENT '功能号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `data_function_authority` COMMENT = '客户端接口授权';

INSERT INTO `data_function_authority` VALUES (1, 'C0001', 'F1001', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);

INSERT INTO `data_function_authority` VALUES (2, 'C0001', 'F1002', NULL, 10000, '2023-08-13 14:36:19', 10000, '2023-08-13 14:36:19', 1);

INSERT INTO `data_function_authority` VALUES (3, 'C0002', 'F1001', NULL, 10000, '2023-08-13 14:36:29', 10000, '2023-08-13 14:36:29', 1);

INSERT INTO `data_function_authority` VALUES (4, 'C0001', 'F1003', NULL, 10000, '2024-02-28 21:51:00', NULL, NULL, 1);

DROP TABLE IF EXISTS `data_message`;

CREATE TABLE `data_message` (
  `id` bigint NOT NULL COMMENT 'id',
  `client_id` varchar(50) DEFAULT NULL COMMENT '客户端ID',
  `func_code` varchar(200) NOT NULL COMMENT '功能号',
  `topic` varchar(50) DEFAULT NULL COMMENT '主题',
  `json_str` varchar(3000) DEFAULT NULL COMMENT '请求参数',
  `state` varchar(1) DEFAULT '0' COMMENT '状态0未处理1处理2未找到对应的服务类3业务处理失败',
  `result_msg` varchar(3000) DEFAULT NULL COMMENT '响应消息',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(3) DEFAULT NULL COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间',
  `db_status` smallint DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `data_message` COMMENT = '接口参数数据';

DROP TABLE IF EXISTS `ur_event`;

CREATE TABLE `ur_event` (
  `id` bigint NOT NULL,
  `code` varchar(20) COMMENT '事件编码',
  `report_time` datetime(6) NOT NULL COMMENT '上报时间',
  `description` varchar(1000) NOT NULL COMMENT '问题描述',
  `evt_type` varchar(50) COMMENT '案件类别',
  `location` varchar(300) COMMENT '事发位置',
  `longitude` decimal(12,8) COMMENT '经度',
  `latitude` decimal(12,8) COMMENT '纬度',
  `mobile` varchar(30) COMMENT '联系电话',
  `anonymous` smallint DEFAULT 0 COMMENT '匿名标识1匿名',
  `open_id` varchar(80) COMMENT '对应第三方用户ID',
  `accept_status` varchar(2) NOT NULL DEFAULT 1 COMMENT '受理状态，1待处理2处理中3已解决4已驳回',
  `completion_time` datetime(6) COMMENT '完成时间',
  `rejection_opinion` varchar(500) COMMENT '驳回意见',
  `handling_opinion` varchar(500) COMMENT '处理意见',
  `remark` varchar(255) COMMENT '备注',
  `db_status` smallint NOT NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint COMMENT '创建者',
  `create_time` datetime(6) COMMENT '创建时间',
  `updater` bigint COMMENT '更新者',
  `update_time` datetime(6) COMMENT '更新时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `ur_event` COMMENT = '事件表';

CREATE UNIQUE INDEX `bpmn_flow_key_code_key` ON `bpmn_flow` (`key_code`);

ALTER TABLE `bpmn_flow` ADD PRIMARY KEY (`id`);

CREATE INDEX `idx_01` ON `bpmn_flow_node` (`proc_def_id`) USING BTREE;

ALTER TABLE `bpmn_flow_node` ADD PRIMARY KEY (`id`);

CREATE INDEX `bpmn_task_record_proc_inst_id_idx` ON `bpmn_task_record` (`proc_inst_id`) USING BTREE;

ALTER TABLE `bpmn_task_record` ADD PRIMARY KEY (`id`);

CREATE UNIQUE INDEX `data_app_client_id_key` ON `data_app` (`client_id`);

ALTER TABLE `data_app` ADD PRIMARY KEY (`id`);

CREATE INDEX `data_function_index2` ON `data_function` (`func_code`) USING BTREE;

ALTER TABLE `data_function` ADD PRIMARY KEY (`id`);

CREATE INDEX `index2` ON `data_function_authority` (`client_id`) USING BTREE;

ALTER TABLE `data_function_authority` ADD PRIMARY KEY (`id`);

CREATE INDEX `data_message_idx_01` ON `data_message` (`topic`, `client_id`, `func_code`) USING BTREE;

ALTER TABLE `data_message` ADD PRIMARY KEY (`id`);

ALTER TABLE `ur_event` ADD PRIMARY KEY (`id`);

DROP TABLE IF EXISTS `ur_multi_media`;

CREATE TABLE `ur_multi_media` (
  `id` bigint NOT NULL,
  `evt_id` bigint NOT NULL COMMENT '事件ID',
  `file_id` varchar(80) NOT NULL COMMENT '文件ID',
  `file_name` varchar(100) NULL COMMENT '文件名',
  `status_type` varchar(2) NOT NULL COMMENT '所属状态：1待处理2处理中3已解决4已驳回',
  `remark` varchar(255) NULL COMMENT '备注',
  `db_status` int NOT NULL DEFAULT 1 COMMENT '数据状态标识 0：已删除，1：正常',
  `creator` bigint NULL COMMENT '创建者',
  `create_time` datetime NULL COMMENT '创建时间',
  `updater` bigint NULL COMMENT '更新者',
  `update_time` datetime NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

ALTER TABLE `ur_multi_media` COMMENT = '多媒体表';


SET FOREIGN_KEY_CHECKS = 1;
