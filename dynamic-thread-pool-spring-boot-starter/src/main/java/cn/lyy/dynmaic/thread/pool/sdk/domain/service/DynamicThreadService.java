package cn.lyy.dynmaic.thread.pool.sdk.domain.service;

import cn.lyy.dynmaic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;

import java.util.List;

/**
 * 动态线程池服务
 */
public interface DynamicThreadService {
    /**
     * 查询所有线程池信息
     */
    List<ThreadPoolConfigEntity> queryThreadPoolList();

    /**
     * 根据名字查线程池参数
     */
    ThreadPoolConfigEntity queryThreadPoolConfigByName(String threadPoolName);

    /**
     * 更新线程池信息
     * @param threadPoolConfigEntity
     */
    void updateThreadPoolConfig(ThreadPoolConfigEntity threadPoolConfigEntity);
}
