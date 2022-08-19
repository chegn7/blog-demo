package xyz.cheng7.blog.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class IDUtil {
    /**
     * 采用雪花算法 snow flake 生成分布式id
     * 64bit
     * 1 bit 符号位
     * 41 bit 时间戳，毫秒，约69年
     * 10 bit 数据机器位 5 bit datacenterId + 5 bit workerId
     * 12 bit 序列 4096 个id每毫秒
     */
    // 开始时间 2022-08-12 16:49:04
    private final long blogEpoch = 1660294144000L;
//    private final long timestampBits = 41L;
    private final long datacenterIdBits = 5L;
    private final long workerIdBits = 5L;
    private final long sequenceBits = 12L;

    // 机器ID向左移sequenceBits位
    private final long workerIdShift = sequenceBits;
    // datacenterID 向左移sequenceBits + workerIdBits位
    private final long datacenterIdShift = workerIdShift + workerIdBits;
    // 时间 左移sequenceBits + workerIdBits + datacenterIdBits位
    private final long timestampShift = datacenterIdShift + datacenterIdBits;

    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    private final long maxSequence = -1L ^ (-1L << sequenceBits);

    private long sequence = 0L;
    private long lastTimestamp = -1L;
    @Value("${util.datacenter.id}")
    private long datacenterId;
    @Value("${util.worker.id}")
    private long workerId;

    public IDUtil() {
        if (datacenterId < 0 || datacenterId > maxDatacenterId) {
            throw new IllegalArgumentException(String.format("datacenterId超出范围，范围[0, %d]", maxDatacenterId));
        }
        if (workerId < 0 || workerId > maxWorkerId) {
            throw new IllegalArgumentException(String.format("workerId，范围[0, %d]", maxWorkerId));
        }
    }

    public synchronized long generateID() {
        long curTimestamp = System.currentTimeMillis();
        if (curTimestamp < lastTimestamp) {
            // 当前时间戳不能小于上一次的时间戳
            throw new RuntimeException(String.format("系统时间回退过"));
        } else if (curTimestamp == lastTimestamp) {
            // 相等的时间戳，序号自增
            sequence = (sequence + 1) & maxSequence;
            // 当sequence + 1 > maxSequence时， sequence == 0
            if (sequence == 0) {
                curTimestamp = waitUntilNextMilliSecond(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        // 更新上次生成的时间戳
        lastTimestamp = curTimestamp;
        return ((curTimestamp - blogEpoch) << timestampShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerId)
                | sequence;
    }

    private long waitUntilNextMilliSecond(long lastTimestamp) {
        long cur = System.currentTimeMillis();
        while (cur <= lastTimestamp) {
            cur = System.currentTimeMillis();
        }
        return cur;
    }

    public void setDatacenterId(long datacenterId) {
        this.datacenterId = datacenterId;
    }

    public void setWorkerId(long workerId) {
        this.workerId = workerId;
    }
}
