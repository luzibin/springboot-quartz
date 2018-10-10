package com.Bin.quartz.job;

import com.Bin.quartz.entity.SysTask;
import com.Bin.quartz.service.SysTaskService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * <b>quartz</b> is 任务调度管理
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-09-30 14:30:15Z Bin $
 * @since 2018年09月30日
 **/
@Configuration
public class QuartzScheduler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 在ApplicationStartQuartzJobListener中scheduler()中注入
     */
    @Autowired
    private Scheduler scheduler;
    @Autowired
    private SysTaskService sysTaskService;

    /**
     * 开始执行所有任务
     *
     * @throws SchedulerException
     */
    public void startJob() throws SchedulerException{
        //方式1
        List<SysTask> list = sysTaskService.listSysTasks();
        if(list == null || list.size() < 0){
            logger.info("不存在任务");
            return;
        }

        for(SysTask sysTask : list){
            if(sysTask != null) {
                this.startJob(scheduler, sysTask);
            }
        }
        /*
        //方式2
        this.startJob1(scheduler);
        this.startJob2(scheduler);
        */
        if(!scheduler.isShutdown()) {
            scheduler.start();
        }
    }

    /**
     * 获取 任务信息
     *
     * @param name  任务名称
     * @param group 任务组
     * @return String
     * @throws SchedulerException
     */
    public String getJobInfo(String name,String group) throws SchedulerException{

        //获取 触发器key
        TriggerKey key = new TriggerKey(name, group);
        //获取 触发器实例
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(key);
        String jobCronInfo  = cronTrigger.getCronExpression();
        String jobState     = scheduler.getTriggerState(key).name();

        return "timer:" + jobCronInfo + ";job-state" + jobState;
    }

    /**
     * 更改任务定时时间
     *
     * @param name  任务名称
     * @param group 任务组
     * @param time  更新后定时时间
     * @return Boolean
     * @throws SchedulerException
     */
    public Boolean modifyJobCronTime(String name,String group,String time) throws SchedulerException{
        Date date = null;

        //获取 旧触发器实例信息
        TriggerKey key = new TriggerKey(name,group);
        CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(key);

        String oldTime = cronTrigger.getCronExpression();
        if(!oldTime.equals(time)){
            //重新设定定时时间
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(time);
            //重新构建 触发器实例: name、group、时间调度器
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(name,group).withSchedule(cronScheduleBuilder).build();

            //重新 调度任务
            date = scheduler.rescheduleJob(key,trigger);
        }

        return date != null;
    }

    /**
     * 暂停所有任务
     *
     * @throws SchedulerException
     */
    public void pauseAllJob() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 暂停某任务
     *
     * @param name  任务名称
     * @param group 任务组
     * @throws SchedulerException
     */
    public void pauseJob(String name,String group)throws SchedulerException{
        //获取 任务key
        JobKey key = new JobKey(name,group);

        //获取 任务信息细节
        JobDetail jobDetail = scheduler.getJobDetail(key);
        if(jobDetail == null){
            return;
        }
        scheduler.pauseJob(key);
    }

    /**
     * 恢复所有任务
     *
     * @throws SchedulerException
     */
    public void resumeAllJob() throws SchedulerException{
        scheduler.resumeAll();
    }

    /**
     * 恢复某个任务
     *
     * @param name  任务名称
     * @param group 任务组
     * @throws SchedulerException
     */
    public void resumeJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除某个任务
     *
     * @param name  任务名称
     * @param group 任务组
     * @throws SchedulerException
     */
    public void deleteJob(String name, String group) throws SchedulerException {
        JobKey jobKey = new JobKey(name, group);
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            return;
        }
        scheduler.deleteJob(jobKey);
    }

    /**
     * 实例化Job,将任务触发器加入任务调度中
     *
     * @param scheduler 任务调度器
     * @throws SchedulerException
     */
    public void startJob1(Scheduler scheduler) throws SchedulerException{
        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class)
                .withIdentity("job1", "group1")
                .build();

        // 基于表达式构建触发器
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/30 * * * * ?");
        // CronTrigger表达式触发器 继承于Trigger
        // TriggerBuilder 用于构建触发器实例
        CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                .withIdentity("job1", "group1")
                .withSchedule(cronScheduleBuilder)
                .build();

        // 把作业和触发器注册到任务调度中
        scheduler.scheduleJob(jobDetail,cronTrigger);
    }

    private void startJob2(Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(SchedulerQuartzJob2.class).withIdentity("job2", "group2").build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0/5 * * * ?");
        CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity("job2", "group2")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, cronTrigger);
    }

    /**
     * 实例化Job,将任务触发器加入任务调度中
     *
     * @param scheduler 任务调度器
     * @param sysTask   任务实体
     * @throws SchedulerException
     */
    private void startJob(Scheduler scheduler,SysTask sysTask) throws SchedulerException {
        String jobName = sysTask.getJobName();
        String jobGroup = sysTask.getJobGroup();
        String cronExpession = sysTask.getCronExpression();

        // 通过JobBuilder构建JobDetail实例，JobDetail规定只能是实现Job接口的实例
        // JobDetail 是具体Job实例：SchedulerQuartzJob1、SchedulerQuartzJob2
        JobDetail jobDetail = null;
        if(SchedulerQuartzJob1.class.getName().equals(sysTask.getBeanClass())){
            jobDetail = JobBuilder.newJob(SchedulerQuartzJob1.class).withIdentity(jobName, jobGroup).build();
        }else if(SchedulerQuartzJob2.class.getName().equals(sysTask.getBeanClass())){
            jobDetail = JobBuilder.newJob(SchedulerQuartzJob2.class).withIdentity(jobName, jobGroup).build();
        }

        if(jobDetail != null) {
            // 基于表达式构建触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpession);

            // CronTrigger表达式触发器 继承于Trigger
            // TriggerBuilder 用于构建触发器实例
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroup)
                    .withSchedule(cronScheduleBuilder).build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        }
    }
}
