package com.iris.workflow.utils;

import com.iris.workflow.vo.FlowNodeVO;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.camunda.bpm.model.xml.instance.DomElement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 工作流程工具类
 *
 * @author 王小费 whx5710@qq.com
 * @since 1.0.0 2024-01-20
 */
public class BpmnUtils {
    /**
     * 将svgCode转换成png文件，直接输出到流中
     *
     * @param svgCode
     *            svg代码
     * @param outputStream
     *            输出流
     * @throws TranscoderException
     *             异常
     * @throws IOException
     *             io异常
     */
    public static void convertToPng(String svgCode, OutputStream outputStream) throws TranscoderException, IOException {
        try {
            byte[] bytes = svgCode.getBytes("utf-8");
            PNGTranscoder t = new PNGTranscoder();
            TranscoderInput input = new TranscoderInput(new ByteArrayInputStream(bytes));
            TranscoderOutput output = new TranscoderOutput(outputStream);
            // 增加图片的属性设置(单位是像素)---下面是写死了，实际应该是根据SVG的大小动态设置，默认宽高都是400
//            t.addTranscodingHint(ImageTranscoder.KEY_WIDTH, new Float(400));
//            t.addTranscodingHint(ImageTranscoder.KEY_HEIGHT, new Float(300));
            t.transcode(input, output);
            outputStream.flush();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取流程设计的所有节点列表
     * @param e
     * @return
     */
    public static List<FlowNodeVO> getNodeList(DomElement e){
        List<FlowNodeVO> list = new ArrayList<>();
        e.getChildElements().stream()
                .filter(it -> "startEvent".equals(it.getLocalName()) || "endEvent".equals(it.getLocalName()) || "userTask".equals(it.getLocalName()) || "subProcess".equals(it.getLocalName()))
                .forEach(item ->{
                    switch (item.getLocalName()){
                        case "userTask", "startEvent", "endEvent":
                            FlowNodeVO flowNodeVO = new FlowNodeVO();
                            flowNodeVO.setActDefId(item.getAttribute("id"));
                            flowNodeVO.setNodeName(item.getAttribute("name"));
                            list.add(flowNodeVO);
                            break;
                        case "subProcess":
                            list.addAll(getNodeList(item));
                            break;
                    }
                });
        return list;
    }


    /**
     * 获取流程设计的所有连接线
     * @param e
     * @return
     */
    public static HashMap<String,String> getSequenceFlow(DomElement e){
        HashMap<String,String> sequenceFlow = new HashMap<>();
        e.getChildElements().stream()
                .filter(it -> "sequenceFlow".equals(it.getLocalName()))
                .forEach(item ->{
                    if (sequenceFlow.get(item.getAttribute("sourceRef")) == null){
                        String sourceRef = item.getAttribute("sourceRef");
                        if (sourceRef.startsWith("StartEvent")){
                            sequenceFlow.put("StartEvent",item.getAttribute("sourceRef"));
                        }
                        sequenceFlow.put(sourceRef,item.getAttribute("targetRef"));
                    }else{
                        sequenceFlow.put(item.getAttribute("sourceRef"),sequenceFlow.get(item.getAttribute("sourceRef"))+","+item.getAttribute("targetRef"));
                    }
                });
        return sequenceFlow;
    }

    /**
     * 循环获取流程节点信息（非通用,后续优化）
     * @param nodeList 流程节点列表
     * @param seqKey 流程节点key
     * @param seqMap 流程连接线
     * @param nodeMao 流程节点
     */
    private static void nodeList(ArrayList<HashMap<String,String>> nodeList,String seqKey,HashMap<String,String> seqMap,HashMap<String,String> nodeMao){
        if(seqKey == null){
            return;
        }
        if (seqKey.contains(",")){
            String[] keyArr = seqKey.split(",");
            for (int i = 0; i < keyArr.length; i++) {
                nodeList(nodeList,keyArr[i],seqMap,nodeMao);
            }
            return;
        }
        if(seqMap.get(seqKey) == null){
            return;
        }
        /*if (seqKey.startsWith("Task") || seqKey.startsWith("StartEvent")) {
            HashMap<String, String> node = new HashMap<>();
            node.put("code", seqKey);
            node.put("name", nodeMao.get(seqKey));
            nodeList.add(node);
        }*/
        HashMap<String, String> node = new HashMap<>();
        node.put("code", seqKey);
        node.put("name", nodeMao.get(seqKey));
        nodeList.add(node);


        String req = seqMap.get(seqKey);
        seqMap.remove(seqKey);
        nodeMao.remove(seqKey);
        seqKey = req;
        nodeList(nodeList,seqKey,seqMap,nodeMao);
    }
}
