/**
CREATE TABLE sys_task (
  id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cronExpression VARCHAR (255),
  methodName VARCHAR (255),
  isConcurrent VARCHAR (255),
  description VARCHAR (255),
  updateBy VARCHAR (64),
  beanClass VARCHAR (255),
  createDate DATETIME,
  jobStatus VARCHAR (255),
  jobGroup VARCHAR (255),
  updateDate DATETIME,
  createBy VARCHAR (64),
  springBean VARCHAR (255),
  jobName VARCHAR (255)
);


insert into test.`sys_task` (cronExpression,beanClass,createDate,jobStatus,jobGroup,jobName)
value ('0/30 * * * * ?','com.Bin.quartz.job.SchedulerQuartzJob1',sysdate(),'1','group1','job1');

INSERT INTO test.`sys_task` (cronExpression,beanClass,createDate,jobStatus,jobGroup,jobName)
VALUE ('0 0/2 * * * ?','com.Bin.quartz.job.SchedulerQuartzJob2',SYSDATE(),'1','group2','job2');

**/