package cn.lyy.dynmaic.thread.pool.sdk.trigger.listen;

import cn.lyy.dynmaic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import cn.lyy.dynmaic.thread.pool.sdk.domain.service.Imp.DynamicThreadServiceImpl;
import cn.lyy.dynmaic.thread.pool.sdk.registry.IRegistry;
import com.alibaba.fastjson.JSON;
import org.redisson.api.listener.MessageListener;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.util.List;

/**
 * 监听变更
 */
public class ThreadPoolConfigAdjustListener implements MessageListener<ThreadPoolConfigEntity> {
    private Logger logger = LoggerFactory.getLogger(ThreadPoolConfigAdjustListener.class);

    private final DynamicThreadServiceImpl dynamicThreadService;
    private final IRegistry registry;
    public ThreadPoolConfigAdjustListener(DynamicThreadServiceImpl dynamicThreadService, IRegistry registry){
        this.dynamicThreadService = dynamicThreadService;
        this.registry = registry;
    }

    @Override
    public void onMessage(CharSequence charSequence, ThreadPoolConfigEntity threadPoolConfigEntity) {
        logger.info("动态线程池，调整线程池配置。线程池名称:{} 核心线程数:{} 最大线程数:{}", threadPoolConfigEntity.getThreadPoolName(), threadPoolConfigEntity.getPoolSize(), threadPoolConfigEntity.getMaximumPoolSize());
        dynamicThreadService.updateThreadPoolConfig(threadPoolConfigEntity);

        // 更新后上报最新数据
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);

        ThreadPoolConfigEntity threadPoolConfigEntityCurrent = dynamicThreadService.queryThreadPoolConfigByName(threadPoolConfigEntity.getThreadPoolName());
        registry.reportThreadPoolConfigParameter(threadPoolConfigEntityCurrent);
        logger.info("动态线程池，上报线程池配置：{}", JSON.toJSONString(threadPoolConfigEntity));
    }



}
