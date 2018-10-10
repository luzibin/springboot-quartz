package com.Bin.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.sql.Timestamp;

/**
 * <p>
 * <b>quartz</b> is 具体任务
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-09-30 14:26:15Z Bin $
 * @since 2018年09月30日
 **/
public class SchedulerQuartzJob1 implements Job{

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        before();
        System.out.println("job1-开始："+new Timestamp(System.currentTimeMillis()));
        // TODO 业务
        System.out.println("<<<<<<<<< " + SchedulerQuartzJob1.class.getName() +">>>>>>>>>>>>");
        System.out.println("job1-结束："+new Timestamp(System.currentTimeMillis()));
        after();
    }

    private void before(){
        System.out.println("job1-任务开始执行");
    }

    private void after(){
        System.out.println("job1-任务开始执行");
    }
}
