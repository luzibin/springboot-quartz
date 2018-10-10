package com.Bin.quartz.dao;

import com.Bin.quartz.entity.SysTask;

import java.util.List;

/**
 * <p>
 * <b>quartz</b> is 定时任务实体
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-10-08 15:14:15Z Bin $
 * @since 2018年10月08日
 **/
public interface SysTaskDao {

    /**
     * 通過jobName 獲取實體信息
     *
     * @param sysTask 任务实体
     * @return SysTask
     */
    SysTask selectSysTask(SysTask sysTask);

    /**
     *  新增job
     *
     * @param sysTask 任务实体
     * @return int
     */
    int insertSysTask(SysTask sysTask);

    /**
     * 修改cron定时时间
     *
     * @param sysTask 任务实体
     * @return int
     */
    int updateByJobName(SysTask sysTask);

    /**
     *  获取 所有任务信息
     *
     * @return List
     */
    List<SysTask> selectAll();

}
