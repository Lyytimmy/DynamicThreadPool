package cn.lyy.dynmaic.thread.pool.sdk.registry.redis;

import cn.lyy.dynmaic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.lyy.dynmaic.thread.pool.sdk.domain.model.valobj.RegistryEnumVO;
import cn.lyy.dynmaic.thread.pool.sdk.registry.IRegistry;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * Redis注册中心
 */
public class RedisRegistry implements IRegistry {
    private final RedissonClient redissonClient;

    public RedisRegistry(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }

    @Override
    public void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities) {
        RList<ThreadPoolConfigEntity> list = redissonClient.getList(RegistryEnumVO.THREAD_POOL_CONFIG_LIST_KEY.getKey());
        // 遍历redis中的线程池配置，如果应用名称和线程池名称一样，则更新，否则添加
        for (ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities) {
            String threadPoolName = threadPoolConfigEntity.getThreadPoolName();
            String appName = threadPoolConfigEntity.getAppName();
            for (ThreadPoolConfigEntity entity : list) {
                if (entity.getAppName().equals(appName) && entity.getThreadPoolName().equals(threadPoolName)) {
                    entity.setActiveCount(threadPoolConfigEntity.getActiveCount());
                    entity.setPoolSize(threadPoolConfigEntity.getPoolSize());
                    entity.setQueueSize(threadPoolConfigEntity.getQueueSize());
                    entity.setRemainingCapacity(threadPoolConfigEntity.getRemainingCapacity());
                    return;
                }
            }
            list.add(threadPoolConfigEntity);
        }
    }

    @Override
    public void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity) {
        String cacheKey = RegistryEnumVO.THREAD_POOL_CONFIG_PARAMETER_LIST_KEY.getKey() + "_" + threadPoolConfigEntity.getAppName() + "_" + threadPoolConfigEntity.getThreadPoolName();
        RBucket<ThreadPoolConfigEntity> bucket = redissonClient.getBucket(cacheKey);
        bucket.set(threadPoolConfigEntity, Duration.ofDays(30));
    }
}
