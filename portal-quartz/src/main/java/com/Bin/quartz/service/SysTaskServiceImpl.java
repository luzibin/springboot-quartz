package com.Bin.quartz.service;

import com.Bin.quartz.dao.SysTaskDao;
import com.Bin.quartz.entity.SysTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * <b>quartz</b> is 定时任务实体
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-10-09 10:14:15Z Bin $
 * @since 2018年10月09日
 **/
@Service("sysTaskService")
public class SysTaskServiceImpl implements SysTaskService{

    @Autowired
    private SysTaskDao sysTaskDao;

    @Override
    public SysTask getSysTask(SysTask sysTask) {
        return sysTaskDao.selectSysTask(sysTask);
    }

    @Override
    public int saveSysTask(String jobName,String jobGroup,String cronExpression,String beanClass) {
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setJobGroup(jobGroup);
        sysTask.setCronExpression(cronExpression);
        sysTask.setBeanClass(beanClass);
        //0：表示未启动；1：表示已启动；-1：表示异常
        sysTask.setJobStatus("1");
        sysTask.setCreateDate(new Timestamp(System.currentTimeMillis()));
        return sysTaskDao.insertSysTask(sysTask);
    }

    @Override
    public int modifyByJobName(SysTask sysTask) {
        return sysTaskDao.updateByJobName(sysTask);
    }

    @Override
    public List<SysTask> listSysTasks() {
        return sysTaskDao.selectAll();
    }
}
