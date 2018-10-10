package com.Bin.quartz.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * <b>SysTask</b> is 定时任务实体
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-10-08 15:02:15Z Bin $
 * @since 2018年10月08日
 **/
public class SysTask implements Serializable{
    private static final long serialVersionUID = -7661394764953032895L;

    private int id;
    /**
     * cron表达式
     */
    private String cronExpression;
    /**
     * 任务调用的方法名
     */
    private String methodName;
    /**
     * 任务是否并行,1：表示有；0：表示没有
     */
    private String isConcurrent;
    /**
     * 任务描述
     */
    private String description;
    private String updateBy;
    /**
     * 任务执行时调用哪个类的方法 包名+类名
     */
    private String beanClass;
    private Date createDate;
    /**
     * 任务状态，0：表示未启动；1：表示已启动；-1：表示异常
     */
    private String jobStatus;
    /**
     * 任务所属group
     */
    private String jobGroup;
    private Date updateDate;
    private String createBy;
    /**
     * Spring bean
     */
    private String springBean;
    /**
     * 任务名称
     */
    private String jobName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getIsConcurrent() {
        return isConcurrent;
    }

    public void setIsConcurrent(String isConcurrent) {
        this.isConcurrent = isConcurrent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(String beanClass) {
        this.beanClass = beanClass;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getSpringBean() {
        return springBean;
    }

    public void setSpringBean(String springBean) {
        this.springBean = springBean;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
