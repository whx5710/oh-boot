package com.iris.workflow.utils;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 工具类
 */
public class Tools {
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
}
