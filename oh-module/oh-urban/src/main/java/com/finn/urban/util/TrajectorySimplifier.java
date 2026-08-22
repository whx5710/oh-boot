package com.finn.urban.util;

import com.finn.urban.entity.TrajectoryEntity;
import com.finn.urban.vo.PointVO;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 轨迹抽稀工具类
 * 使用 Douglas-Peucker 算法简化轨迹点，在保持轨迹形状的同时减少数据量
 *
 * @author whx5710@qq.com
 * @since 2026-08-23
 */
public class TrajectorySimplifier {

    /**
     * 默认抽稀距离阈值（米）
     * 当点到线段的距离小于此值时，该点将被移除
     */
    private static final double DEFAULT_TOLERANCE = 10.0;

    /**
     * 默认最大点数限制
     * 如果抽稀后点数仍超过此值，会继续增大阈值直到满足要求
     */
    private static final int DEFAULT_MAX_POINTS = 300;

    /**
     * 默认最小点数限制
     * 至少保留这么多个点
     */
    private static final int DEFAULT_MIN_POINTS = 10;

    private TrajectorySimplifier() {
    }

    /**
     * 使用默认参数简化轨迹
     *
     * @param points 原始轨迹点列表
     * @return 简化后的轨迹点列表
     */
    public static List<PointVO> simplify(List<PointVO> points) {
        return simplify(points, DEFAULT_TOLERANCE, DEFAULT_MAX_POINTS);
    }

    /**
     * 简化轨迹，指定距离阈值
     *
     * @param points    原始轨迹点列表
     * @param tolerance 距离阈值（米），点到线段距离小于此值将被移除
     * @return 简化后的轨迹点列表
     */
    public static List<PointVO> simplify(List<PointVO> points, double tolerance) {
        if (points == null || points.size() <= 2) {
            return points != null ? new ArrayList<>(points) : new ArrayList<>();
        }

        List<PointVO> result = douglasPeucker(points, tolerance, 0, points.size() - 1);

        // 确保至少保留首尾点
        if (!result.isEmpty()) {
            if (!result.get(0).equals(points.get(0))) {
                result.add(0, points.get(0));
            }
            if (!result.get(result.size() - 1).equals(points.get(points.size() - 1))) {
                result.add(points.get(points.size() - 1));
            }
        }

        return result;
    }

    /**
     * 简化轨迹，指定距离阈值和最大点数
     *
     * @param points     原始轨迹点列表
     * @param tolerance  距离阈值（米）
     * @param maxPoints  最大点数限制
     * @return 简化后的轨迹点列表
     */
    public static List<PointVO> simplify(List<PointVO> points, double tolerance, int maxPoints) {
        if (points == null || points.size() <= maxPoints) {
            return points != null ? new ArrayList<>(points) : new ArrayList<>();
        }

        List<PointVO> result = simplify(points, tolerance);

        // 如果点数仍超过限制，逐步增大阈值
        while (result.size() > maxPoints && tolerance < 200) {
            tolerance *= 1.5;
            result = simplify(points, tolerance);
        }

        // 如果仍然太多，强制等间隔抽样
        if (result.size() > maxPoints) {
            result = downsample(result, maxPoints);
        }

        // 确保至少保留最小点数
        if (result.size() < DEFAULT_MIN_POINTS && points.size() > DEFAULT_MIN_POINTS) {
            result = downsample(points, DEFAULT_MIN_POINTS);
        }

        return result;
    }

    /**
     * 从 TrajectoryEntity 列表提取轨迹点并简化
     * 阈值越大，数据越精简，但轨迹细节丢失越多。建议根据运动类型动态调整
     * @param entities 轨迹实体列表
     * @param tolerance 距离阈值（米）
     * @param maxPoints 最大点数
     * @return 简化后的 PointVO 列表
     */
    public static List<PointVO> simplifyEntities(List<TrajectoryEntity> entities,
                                                  double tolerance, int maxPoints) {
        if (entities == null || entities.isEmpty()) {
            return new ArrayList<>();
        }

        // 转换为 PointVO
        List<PointVO> points = new ArrayList<>(entities.size());
        for (TrajectoryEntity entity : entities) {
            if (entity.getLongitude() != null && entity.getLatitude() != null) {
                points.add(new PointVO(entity.getLongitude(), entity.getLatitude()));
            }
        }

        return simplify(points, tolerance, maxPoints);
    }

    /**
     * Douglas-Peucker 算法实现
     *
     * @param points    点列表
     * @param tolerance 距离阈值
     * @param start     起始索引
     * @param end       结束索引
     * @return 保留的点列表
     */
    private static List<PointVO> douglasPeucker(List<PointVO> points, double tolerance,
                                                  int start, int end) {
        if (end <= start + 1) {
            List<PointVO> result = new ArrayList<>();
            result.add(points.get(start));
            if (end > start) {
                result.add(points.get(end));
            }
            return result;
        }

        // 找到距离首尾连线最远的点
        double maxDistance = 0;
        int maxIndex = start;

        PointVO startPoint = points.get(start);
        PointVO endPoint = points.get(end);

        for (int i = start + 1; i < end; i++) {
            double distance = pointToLineDistance(points.get(i), startPoint, endPoint);
            if (distance > maxDistance) {
                maxDistance = distance;
                maxIndex = i;
            }
        }

        // 如果最大距离大于阈值，递归处理两段
        if (maxDistance > tolerance) {
            List<PointVO> left = douglasPeucker(points, tolerance, start, maxIndex);
            List<PointVO> right = douglasPeucker(points, tolerance, maxIndex, end);

            // 合并结果，避免重复点
            List<PointVO> result = new ArrayList<>(left);
            for (int i = 1; i < right.size(); i++) {
                result.add(right.get(i));
            }
            return result;
        } else {
            // 否则只保留首尾两点
            List<PointVO> result = new ArrayList<>();
            result.add(startPoint);
            result.add(endPoint);
            return result;
        }
    }

    /**
     * 计算点到线段的距离（使用 Haversine 公式的简化版本）
     * 由于轨迹点距离相对较小，使用平面近似即可
     *
     * @param point  待计算点
     * @param lineStart 线段起点
     * @param lineEnd   线段终点
     * @return 距离（米）
     */
    private static double pointToLineDistance(PointVO point, PointVO lineStart, PointVO lineEnd) {
        double x0 = point.getLongitude().doubleValue();
        double y0 = point.getLatitude().doubleValue();
        double x1 = lineStart.getLongitude().doubleValue();
        double y1 = lineStart.getLatitude().doubleValue();
        double x2 = lineEnd.getLongitude().doubleValue();
        double y2 = lineEnd.getLatitude().doubleValue();

        // 转换为米为单位的近似坐标
        double metersPerDegLat = 111320.0;
        double metersPerDegLng = 111320.0 * Math.cos(Math.toRadians((y1 + y2) / 2));

        double px0 = x0 * metersPerDegLng;
        double py0 = y0 * metersPerDegLat;
        double px1 = x1 * metersPerDegLng;
        double py1 = y1 * metersPerDegLat;
        double px2 = x2 * metersPerDegLng;
        double py2 = y2 * metersPerDegLat;

        // 计算点到直线的距离
        double dx = px2 - px1;
        double dy = py2 - py1;

        if (dx == 0 && dy == 0) {
            return Math.sqrt(Math.pow(px0 - px1, 2) + Math.pow(py0 - py1, 2));
        }

        // 点到直线的垂直距离
        double numerator = Math.abs(dy * px0 - dx * py0 + px2 * py1 - px1 * py2);
        double denominator = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        return numerator / denominator;
    }

    /**
     * 等间隔降采样
     *
     * @param points    原始点列表
     * @param maxPoints 目标点数
     * @return 降采样后的点列表
     */
    private static List<PointVO> downsample(List<PointVO> points, int maxPoints) {
        if (points.size() <= maxPoints) {
            return new ArrayList<>(points);
        }

        List<PointVO> result = new ArrayList<>(maxPoints);

        if (maxPoints == 1) {
            result.add(points.get(0));
            return result;
        }

        // 始终保留首尾点
        result.add(points.get(0));

        // 等间隔采样中间点
        int interval = (points.size() - 1) / (maxPoints - 1);
        for (int i = 1; i < maxPoints - 1; i++) {
            int index = i * interval;
            if (index < points.size() - 1) {
                result.add(points.get(index));
            }
        }

        // 添加最后一个点
        result.add(points.get(points.size() - 1));

        return result;
    }

    /**
     * 计算轨迹总距离（米）
     *
     * @param points 轨迹点列表
     * @return 总距离（米）
     */
    public static double calculateDistance(List<PointVO> points) {
        if (points == null || points.size() < 2) {
            return 0;
        }

        double totalDistance = 0;
        for (int i = 1; i < points.size(); i++) {
            totalDistance += haversineDistance(points.get(i - 1), points.get(i));
        }
        return totalDistance;
    }

    /**
     * 使用 Haversine 公式计算两点间的球面距离
     *
     * @param p1 点1
     * @param p2 点2
     * @return 距离（米）
     */
    public static double haversineDistance(PointVO p1, PointVO p2) {
        double R = 6371000; // 地球半径（米）

        double lat1 = Math.toRadians(p1.getLatitude().doubleValue());
        double lat2 = Math.toRadians(p2.getLatitude().doubleValue());
        double dLat = Math.toRadians(p2.getLatitude().doubleValue() - p1.getLatitude().doubleValue());
        double dLng = Math.toRadians(p2.getLongitude().doubleValue() - p1.getLongitude().doubleValue());

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }
}
