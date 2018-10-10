package com.Bin.quartz.job;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * <p>
 * <b>quartz</b> is 启动SpringBoot监听
 *      注入scheduler
 *      - 采用监听spring容器加载完毕后事件，启动任务调用
 *      - 将Scheduler交给spring初始化管理
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-09-30 15:13:15Z Bin $
 * @since 2018年09月30日
 **/
@Configuration
public class ApplicationStartQuartzJobListener implements ApplicationListener<ContextRefreshedEvent>{
    private Logger logger = LoggerFactory.getLogger(ApplicationStartQuartzJobListener.class);

    /**
     * quartz任务调度管理
     */
    @Autowired
    private QuartzScheduler quartzScheduler;

    /**
     * 初始启动quartz
     *      -采用监听spring容器加载完毕后事件，启动任务调用
     *
     * @param event 事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try{
            quartzScheduler.startJob();
            logger.info("任务已经启动......");
        }catch (SchedulerException e){
            e.printStackTrace();
        }
    }

    /**
     * 初始注入scheduler
     *
     * @return Scheduler
     * @throws SchedulerException
     */
    @Bean
    public Scheduler scheduler() throws SchedulerException{

        //获取 scheduler实例
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        return schedulerFactory.getScheduler();
    }
}
