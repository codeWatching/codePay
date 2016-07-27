package org.codepay.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * twitter的snowflake 核心代码就是毫秒级时间41位+机器ID 10位+毫秒内序列12位.
 *
 * @author wangliping
 * @date 2015-10-22
 * @since JDK1.6
 */
public class IdWorker {

    protected static final Logger LOG = LoggerFactory.getLogger(IdWorker.class);

    private long workerId;
    private long datacenterId;
    private long sequence = 0l;

    private final long twepoch = 1288834974657l;
    // 机器标识位数
    private final long workerIdBits = 5l;
    // 数据中心标识位数
    private final long datacenterIdBits = 5l;
    // 最大支持机器节点数0~31，一共32个
    private final long maxWorkerId = -1l ^ (-1l << workerIdBits);
    // 最大支持数据中心节点数0~31，一共32个
    private final long maxDatacenterId = -1l ^ (-1l << datacenterIdBits);
    // 毫秒内自增位 12位
    private final long sequenceBits = 12l;
    // 机器ID偏左移12位
    private final long workerIdShift = sequenceBits;
    // 数据中心ID左移17位
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    // 时间毫秒左移22位
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    // 4095
    private final long sequenceMask = -1l ^ (-1l << sequenceBits);

    private long lastTimestamp = -1l;

    /**
     * 构造
     *
     * @param workerId     　节点id
     * @param datacenterId 数据中心id
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be less than 0",
                    maxWorkerId));
        }
        if (datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be less than 0",
                    maxDatacenterId));
        }
        this.workerId = workerId % this.maxWorkerId;
        this.datacenterId = datacenterId % this.maxDatacenterId;
        LOG.info(
                "worker starting. timestamp left shift {},datacenter id bits:{},worker id bits:{},sequence bits:{},workerid:{},datacenterId:{}",
                timestampLeftShift, datacenterIdBits, workerIdBits, sequenceBits, this.workerId, this.datacenterId);
    }

    /**
     * 获取下一个id.
     *
     * @return id
     */
    public synchronized long nextId() {
        // 获取当前毫秒数
        long timestamp = timeGen();
        // 如果服务器时间有问题(时钟后退) 报错
        if (timestamp < this.lastTimestamp) {
            LOG.error("clock is moving backwards.  Rejecting requests until {}.", lastTimestamp);
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果上次生成时间和当前时间相同,在同一毫秒内
        if (this.lastTimestamp == timestamp) {
            // sequence自增，因为sequence只有12bit，所以和sequenceMask相与一下，去掉高位
            this.sequence = (this.sequence + 1) & this.sequenceMask;
            // 判断是否溢出,也就是每毫秒内超过4095，当为4096时，与sequenceMask相与，sequence就等于0
            if (this.sequence == 0) {
                // 自旋等待到下一毫秒
                timestamp = tilNextMillis(this.lastTimestamp);
            }
        } else {
            // 如果和上次生成时间不同,重置sequence，就是下一毫秒开始，sequence计数重新从0开始累加
            this.sequence = 0l;
        }

        this.lastTimestamp = timestamp;
        // 最后按照规则拼出ID。
        // 0 --- 0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---
        // 00000 ---000000000000
        // 未使用 time datacenterId workerId sequence
        return ((timestamp - this.twepoch) << this.timestampLeftShift) | (this.datacenterId << this.datacenterIdShift)
                | (this.workerId << this.workerIdShift) | this.sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }
}
