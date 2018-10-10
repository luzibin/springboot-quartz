CREATE TABLE `sys_task` (
  `id` INT NOT NULL AUTO_INCREMENT,,
  `cronExpression` VARCHAR (255),
  `methodName` VARCHAR (255),
  `isConcurrent` VARCHAR (255),
  `description` VARCHAR (255),
  `updateBy` VARCHAR (64),
  `beanClass` VARCHAR (255),
  `createDate` DATETIME,
  `jobStatus` VARCHAR (255),
  `jobGroup` VARCHAR (255),
  `updateDate` DATETIME,
  `createBy` VARCHAR (64),
  `springBean` VARCHAR (255),
  `jobName` VARCHAR (255)
) 