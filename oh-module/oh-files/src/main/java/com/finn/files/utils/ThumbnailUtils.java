package com.finn.files.utils;

import com.finn.framework.exception.ServerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 图片缩略图工具类
 * 基于 JDK 原生 ImageIO 实现，不依赖第三方图像处理库
 *
 * 支持能力：
 * 1. 按目标宽度等比缩放（保持宽高比）
 * 2. JPEG 质量压缩（quality 1-100）
 * 3. 圆形裁剪（透明背景，输出 PNG）
 *
 * @author 王小费 whx5710@qq.com
 * @since 2026-08-26
 */
public class ThumbnailUtils {

    private static final Logger log = LoggerFactory.getLogger(ThumbnailUtils.class);

    /** 允许的最大目标宽度，防止恶意参数消耗内存 */
    private static final int MAX_TARGET_WIDTH = 4096;

    /** 最小宽度，小于该值直接原样返回（避免 0/负数） */
    private static final int MIN_TARGET_WIDTH = 8;

    /** 支持的 MIME 类型映射 */
    private static final Map<String, String> FORMAT_BY_MIME = new HashMap<>();
    static {
        FORMAT_BY_MIME.put("image/jpeg", "jpg");
        FORMAT_BY_MIME.put("image/jpg", "jpg");
        FORMAT_BY_MIME.put("image/png", "png");
        FORMAT_BY_MIME.put("image/gif", "gif");
        FORMAT_BY_MIME.put("image/bmp", "bmp");
        FORMAT_BY_MIME.put("image/webp", "png"); // JDK 不支持 webp，降级为 png
    }

    private ThumbnailUtils() {}

    /**
     * 判断指定 MIME 类型是否为支持的图片
     */
    public static boolean isSupportedImage(String contentType) {
        if (contentType == null || contentType.isEmpty()) {
            return false;
        }
        String ct = contentType.toLowerCase();
        return FORMAT_BY_MIME.containsKey(ct) || ct.startsWith("image/");
    }

    /**
     * 生成缩略图（按宽度等比缩放 + 质量压缩 + 可选圆形裁剪）
     *
     * @param originalBytes 原图字节数组
     * @param contentType   原始 Content-Type，用于判断输出格式
     * @param targetWidth   目标宽度（像素），<=0 表示不缩放；大于 MAX_TARGET_WIDTH 会被截断
     * @param quality       JPEG 质量 1-100，建议 70-80；PNG 忽略该参数
     * @param shape         "circle" 表示裁剪为圆形（透明背景，输出 PNG），其他或 null 不变形
     * @return ThumbnailResult { bytes, formatName, contentType }
     */
    public static ThumbnailResult generate(byte[] originalBytes, String contentType,
                                           int targetWidth, int quality, String shape) {
        if (originalBytes == null || originalBytes.length == 0) {
            throw new ServerException("图片数据为空");
        }
        // 归一化参数
        targetWidth = normalizeWidth(targetWidth);
        quality = normalizeQuality(quality);
        boolean isCircle = "circle".equalsIgnoreCase(shape);

        // 决定输出格式
        String srcFormat = detectFormat(contentType, originalBytes);
        String outFormat = isCircle ? "png" : srcFormat; // 圆形必须是 PNG（支持透明）
        String outContentType = formatToContentType(outFormat);

        try (InputStream bais = new ByteArrayInputStream(originalBytes)) {
            BufferedImage srcImg = ImageIO.read(bais);
            if (srcImg == null) {
                throw new ServerException("无法解码图片");
            }

            // 1. 缩放
            BufferedImage scaled = targetWidth > 0
                    ? scaleToWidth(srcImg, targetWidth)
                    : srcImg;

            // 2. 圆形裁剪（如果需要）
            BufferedImage processed = isCircle ? cropCircle(scaled) : scaled;

            // 3. 输出（带质量压缩）
            byte[] outBytes = writeImage(processed, outFormat, quality);

            return new ThumbnailResult(outBytes, outFormat, outContentType);
        } catch (IOException e) {
            log.warn("[ThumbnailUtils] 生成缩略图失败: {}", e.getMessage());
            throw new ServerException("生成缩略图失败", e);
        }
    }

    /**
     * 仅按宽度等比缩放，返回结果字节数组（复用 generate 的简化版）
     */
    public static ThumbnailResult scale(byte[] originalBytes, String contentType, int targetWidth, int quality) {
        return generate(originalBytes, contentType, targetWidth, quality, null);
    }

    // ===================== 内部方法 =====================

    private static int normalizeWidth(int w) {
        if (w <= 0) return 0; // 0 表示不缩放
        if (w < MIN_TARGET_WIDTH) return MIN_TARGET_WIDTH;
        if (w > MAX_TARGET_WIDTH) return MAX_TARGET_WIDTH;
        return w;
    }

    private static int normalizeQuality(int q) {
        if (q <= 0) return 80; // 默认 80
        if (q > 100) return 100;
        return q;
    }

    private static String detectFormat(String contentType, byte[] header) {
        if (contentType != null && !contentType.isEmpty()) {
            String fromMime = FORMAT_BY_MIME.get(contentType.toLowerCase());
            if (fromMime != null) {
                return fromMime;
            }
        }
        // 读取文件头魔数兜底
        if (header.length >= 4) {
            if ((header[0] & 0xFF) == 0x89 && (header[1] & 0xFF) == 0x50) return "png";
            if ((header[0] & 0xFF) == 0xFF && (header[1] & 0xFF) == 0xD8) return "jpg";
            if ((header[0] & 0xFF) == 0x47 && (header[1] & 0xFF) == 0x49) return "gif";
        }
        return "jpg";
    }

    private static String formatToContentType(String format) {
        return switch (format) {
            case "png" -> "image/png";
            case "gif" -> "image/gif";
            case "bmp" -> "image/bmp";
            default -> "image/jpeg";
        };
    }

    /**
     * 按目标宽度等比缩放，使用 SCALE_SMOOTH 保持清晰度
     */
    private static BufferedImage scaleToWidth(BufferedImage src, int targetWidth) {
        int srcW = src.getWidth();
        int srcH = src.getHeight();
        if (srcW <= targetWidth) {
            return src; // 原图更小，无需缩放
        }
        // 等比计算高度
        int targetHeight = Math.max(1, Math.round((float) targetWidth * srcH / srcW));
        BufferedImage out = new BufferedImage(targetWidth, targetHeight,
                hasAlpha(src) ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(src, 0, 0, targetWidth, targetHeight, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    /**
     * 裁剪为正方形内接圆形（透明背景）
     * 输出尺寸：若原图为 W×H，取短边为直径居中裁剪，最终输出 D×D 的正方形 PNG
     */
    private static BufferedImage cropCircle(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        int diameter = Math.min(w, h);
        int x = (w - diameter) / 2;
        int y = (h - diameter) / 2;

        BufferedImage out = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = out.createGraphics();
        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            // 裁剪区域：圆形
            g.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
            // 从原图中心取出正方形区域绘制
            g.drawImage(src, 0, 0, diameter, diameter, x, y, x + diameter, y + diameter, null);
        } finally {
            g.dispose();
        }
        return out;
    }

    /**
     * 以指定格式/质量输出图片字节
     */
    private static byte[] writeImage(BufferedImage img, String format, int quality) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(Math.max(1024, img.getWidth() * img.getHeight() / 4));
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName(format);
        if (!writers.hasNext()) {
            // 兜底：尝试以当前格式名直接写
            boolean ok = ImageIO.write(img, format, baos);
            if (!ok) {
                throw new ServerException("不支持的图片输出格式: " + format);
            }
            return baos.toByteArray();
        }
        ImageWriter writer = writers.next();
        try (ImageOutputStream ios = ImageIO.createImageOutputStream(baos)) {
            writer.setOutput(ios);
            ImageWriteParam param = writer.getDefaultWriteParam();
            // 仅 JPG/jpeg 支持有损压缩
            if (("jpg".equalsIgnoreCase(format) || "jpeg".equalsIgnoreCase(format))
                    && param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality / 100.0F);
            }
            writer.write(null, new IIOImage(img, null, null), param);
        } finally {
            writer.dispose();
        }
        return baos.toByteArray();
    }

    private static boolean hasAlpha(BufferedImage img) {
        return img.getColorModel() != null && img.getColorModel().hasAlpha();
    }

    /**
     * 缩略图输出结果
     */
    public static class ThumbnailResult {
        private final byte[] bytes;
        private final String formatName;
        private final String contentType;

        public ThumbnailResult(byte[] bytes, String formatName, String contentType) {
            this.bytes = bytes;
            this.formatName = formatName;
            this.contentType = contentType;
        }

        public byte[] getBytes() { return bytes; }
        public String getFormatName() { return formatName; }
        public String getContentType() { return contentType; }
        public long getContentLength() { return bytes.length; }
    }
}
