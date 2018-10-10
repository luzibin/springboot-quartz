package com.Bin.quartz.service;

import com.Bin.quartz.entity.SysTask;

import java.util.List;

/**
 * <p>
 * <b>quartz</b> is 定时任务实体
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-10-09 10:02:15Z Bin $
 * @since 2018年10月09日
 **/
public interface SysTaskService {

    /**
     * 通過jobName 獲取實體信息
     *
     * @param sysTask 任务实体
     * @return SysTask
     */
    SysTask getSysTask(SysTask sysTask);

    /**
     *  新增job
     *
     * @param jobName           任务名称
     * @param jobGroup          任务组
     * @param cronExpression    任务cron
     * @param beanClass         任务执行类：包名+类名
     * @return int
     */
    int saveSysTask(String jobName,String jobGroup,String cronExpression,String beanClass);

    /**
     * 修改cron定时时间
     *
     * @param sysTask 任务实体
     * @return int
     */
    int modifyByJobName(SysTask sysTask);

    /**
     *  获取所有
     *
     * @return List
     */
    List<SysTask> listSysTasks();
}
