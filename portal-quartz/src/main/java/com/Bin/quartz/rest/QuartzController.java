package com.Bin.quartz.rest;

import com.Bin.quartz.entity.SysTask;
import com.Bin.quartz.job.QuartzScheduler;
import com.Bin.quartz.service.SysTaskService;
import com.Bin.quartz.utils.ResponseMessage;
import com.Bin.quartz.utils.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

/**
 * <p>
 * <b>quartz</b> is 任务调度接口
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-09-30 15:22:15Z Bin $
 * @since 2018年09月30日
 **/
@Api(value = "quartz调度接口")
@RestController
@RequestMapping("/lzUtil")
public class QuartzController extends BaseController{

    /**
     * 任务调度管理Swagger2
     */
    @Autowired
    private QuartzScheduler quartzScheduler;
    /**
     * 任务事务
     */
    @Autowired
    private SysTaskService sysTaskService;

    /**
     * 启动任务
     */
    @ApiOperation("启动任务")
    @RequestMapping(value = "/startQuartzJob",method = RequestMethod.GET)
    public void startQuartzJob(){
        logger.info("-==============开始启动所有任务=================-");
        try{
            quartzScheduler.startJob();
        }catch (SchedulerException e){
            e.printStackTrace();
        }
        logger.info("-==============成功启动所有任务=================-");
    }

    @ApiOperation("获取 某任务信息")
    @RequestMapping(value = "/getQuartzJob",method = RequestMethod.GET)
    public ResponseMessage<?> getQuartzJob(@RequestParam("name") String name,@RequestParam("group") String group) {
        String info = null;
        try {
            if(!this.hasJobORIsInStatus(name,group)){
                logger.info("任务"+ name +"-信息不存在或者状态已关闭");
                return Result.success();
            }

            info = quartzScheduler.getJobInfo(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        logger.info("-==============获取任务信息 "+ info +"=================-");
        return Result.success(info);
    }

    @ApiOperation("更新 任务定时时间")
    @RequestMapping(value = "/modifyJobCronTime",method = RequestMethod.GET)
    public ResponseMessage<?> modifyJobCronTime(@RequestParam("name") String name,
                                     @RequestParam("group") String group,
                                     @RequestParam("time") String time) {

        SysTask sysTask = new SysTask();
        try {
            //过滤非法任务
            if(!this.hasJobORIsInStatus(name,group)){
                logger.info("任务"+ name +"-信息不存在或者状态已关闭");
                return Result.success();
            }

            boolean flag = quartzScheduler.modifyJobCronTime(name, group, time);
            if(flag) {
                logger.info("更新任务-" + name + "-的定时时间-" + time);
            }

            //更新 任务定时时间
            sysTask.setJobName(name);
            sysTask.setJobGroup(group);
            sysTask.setCronExpression(time);
            sysTask.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            int result = sysTaskService.modifyByJobName(sysTask);
            if(result > 1){
                logger.info("-============更新成功=============-");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Result.success(sysTask);
    }

    @ApiOperation("暂停 某任务")
    @RequestMapping(value = "/pauseQuartzJob",method = RequestMethod.GET)
    public ResponseMessage<?> pauseQuartzJob(@RequestParam("name") String name,@RequestParam("group") String group) {
        SysTask sysTask = new SysTask();
        try {
            //过滤非法任务
            if(!this.hasJobORIsInStatus(name,group)){
                logger.info("任务"+ name +"-信息不存在或者状态已关闭");
                return Result.success();
            }

            quartzScheduler.pauseJob(name, group);
            logger.info("暂停任务-"+ name );

            //更新 任务状态
            sysTask.setJobName(name);
            sysTask.setJobGroup(group);
            //0：表示未启动；1：表示已启动；-1：表示异常
            sysTask.setJobStatus("0");
            sysTask.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            int result = sysTaskService.modifyByJobName(sysTask);
            if(result > 0){
                logger.info("-============更新成功=============-");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return Result.success(sysTask);
    }

    @ApiOperation("暂停 所有任务")
    @RequestMapping(value = "/pauseAllQuartzJob",method = RequestMethod.GET)
    public void pauseAllQuartzJob() {
        try {
            quartzScheduler.pauseAllJob();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("删除 某任务")
    @RequestMapping(value = "/deleteJob",method = RequestMethod.GET)
    public void deleteJob(@RequestParam("name") String name,@RequestParam("group") String group) {
        try {
            quartzScheduler.deleteJob(name, group);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("新增某条任务信息")
    @RequestMapping(value = "/saveSysTask",method = RequestMethod.POST)
    public ResponseMessage<?> saveSysTask(@RequestParam("jobName") String jobName,
                                          @RequestParam("jobGroup") String jobGroup,
                                          @RequestParam("cronExpression") String cronExpression,
                                          @RequestParam("beanClass") String beanClass){

        int result = sysTaskService.saveSysTask(jobName,jobGroup,cronExpression,beanClass);
        if(result <= 0){
            logger.info("新增任务-"+ jobName +"-失败");
            return Result.success(new SysTask());
        }

        //返回新增 任务信息
        SysTask sysTask = new SysTask();
        sysTask.setJobName(jobName);
        sysTask.setJobGroup(jobGroup);
        return Result.success(sysTaskService.getSysTask(sysTask));
    }

    /**
     * 过滤 任务是否在运行中
     * @param name      任务名称
     * @param group     任务组
     * @return   Boolean
     */
    public Boolean hasJobORIsInStatus(String name,String group){
        SysTask sysTask = new SysTask();
        sysTask.setJobName(name);
        sysTask.setJobGroup(group);
        SysTask newSysTask = sysTaskService.getSysTask(sysTask);
        if(newSysTask == null){
            logger.info("任务-"+ name +"-不存在");
            return false;
        }else if(!"1".equals(newSysTask.getJobStatus())){
            logger.info("任务-"+ name +"-状态已关闭或异常");
            return false;
        }
        return true;
    }
}
