package com.finn.core.utils;

import com.finn.core.exception.ServerException;

/**
 * Twitter_Snowflake<br>
 * SnowFlake的结构如下(每部分用-分开):<br>
 * 0 - 0000000000 0000000000 0000000000 0000000000 0 - 00000 - 00000 - 000000000000 <br>
 * 1位标识，由于long基本类型在Java中是带符号的，最高位是符号位，正数是0，负数是1，所以id一般是正数，最高位是0<br>
 * 41位时间截(毫秒级)，注意，41位时间截不是存储当前时间的时间截，而是存储时间截的差值（当前时间截 - 开始时间截)
 * 得到的值），这里的的开始时间截，一般是的id生成器开始使用的时间，由程序来指定的（如下下面程序IdWorker类的startTime属性）。41位的时间截，可以使用69年，年T =
 * (1L << 41) / (1000L * 60 * 60 * 24 * 365) = 69<br>
 * 10位的数据机器位，可以部署在1024个节点，包括5位datacenterId和5位workerId<br>
 * 12位序列，毫秒内的计数，12位的计数顺序号支持每个节点每毫秒(同一机器，同一时间截)产生4096个ID序号<br>
 * 加起来刚好64位，为一个Long型。<br>
 * SnowFlake的优点是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞(由数据中心ID和机器ID作区分)，并且效率较高，经测试，SnowFlake每秒能够产生26万ID左右。
 *
 * @since 2025-11-22
 * @author 王小费 whx5710@qq.com
 */
public class SnowflakeIdWorker {

    /** 机器id所占的位数 */
    private final long workerIdBits = 5L;

    /** 数据标识id所占的位数 */
    private final long datacenterIdBits = 5L;

    /** 工作机器ID(0~31) */
    private final long workerId;

    /** 数据中心ID(0~31) */
    private final long datacenterId;

    /** 毫秒内序列(0~4095) */
    private long sequence = 0L;

    private long startTimestamp = -1L;

    /**
     * 构造函数
     *
     * @param workerId 工作ID (0~31)
     * @param datacenterId 数据中心ID (0~31)
     * @param startTimestamp 初始时间戳，在初始时间戳上累加，防止时间回退，但有可能与实际时间出现偏差
     */
    public SnowflakeIdWorker(long workerId, long datacenterId, long startTimestamp) {
        // 支持的最大机器id，结果是31 (这个移位算法可以很快的计算出几位二进制数所能表示的最大十进制数)
        long maxWorkerId = ~(-1L << workerIdBits);
        if (workerId > maxWorkerId || workerId < 0) {
            throw new ServerException(String.format("workerId不能大于%d或小于0", maxWorkerId));
        }
        // 支持的最大数据标识id，结果是31
        long maxDatacenterId = ~(-1L << datacenterIdBits);
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new ServerException(String.format("datacenterId不能大于%d或小于0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.startTimestamp = startTimestamp;
    }

    // ==============================Methods==========================================
    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long sequenceTmp = sequence;
        // 序列在id中占的位数
        long sequenceBits = 12L;
        // 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
        long sequenceMask = ~(-1L << sequenceBits);
        sequence = (sequence + 1) & sequenceMask;
        if (sequence == 0 && sequenceTmp >= 0) {
            // sequence自增到最大了，时间戳自增1
            startTimestamp += 1;
        }
        // 数据标识id向左移17位(12+5)
        long datacenterIdShift = sequenceBits + workerIdBits;
        // 时间截向左移22位(5+5+12)
        long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

        // 开始时间截 (2025-01-01 00:00:00)，初始固定后请勿随意修改，谨防ID重复
        long twepoch = 1735660800000L;

        // 移位并通过或运算拼到一起组成64位的ID
        return ((startTimestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << sequenceBits) // 机器ID向左移12位
                | sequence;
    }
}
