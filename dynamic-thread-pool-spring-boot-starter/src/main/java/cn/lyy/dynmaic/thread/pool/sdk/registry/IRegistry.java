package cn.lyy.dynmaic.thread.pool.sdk.registry;

import cn.lyy.dynmaic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

public interface IRegistry {

    /**
     * 上报多个线程池信息
     * @param threadPoolConfigEntities
     */
    void reportThreadPool(List<ThreadPoolConfigEntity> threadPoolConfigEntities);

    /**
     * 上报单个线程池的配置信息
     */
    void reportThreadPoolConfigParameter(ThreadPoolConfigEntity threadPoolConfigEntity);
}
