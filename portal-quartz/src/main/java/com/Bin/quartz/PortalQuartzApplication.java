package com.Bin.quartz;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>
 * <b>quartz</b> is 功能描述
 * </p>
 *
 * @author Bin
 * @version $Id: quartz 62440 2018-09-30 15:34:15Z Bin $
 * @since 2018年09月30日
 **/
@SpringBootApplication
@EnableScheduling
@MapperScan("com.Bin.quartz.dao")
public class PortalQuartzApplication {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(PortalQuartzApplication.class);

        ApplicationContext applicationContext = SpringApplication.run(PortalQuartzApplication.class,args);
        Environment env = applicationContext.getEnvironment();
        String protocol = "http";
        if (env.getProperty("server.ssl.key-store") != null) {
            protocol = "https";
        }

        //访问路径
        String contextPath = env.getProperty("server.context-path");
        if (contextPath == null){
            contextPath = "";
        }
        try {
            //拼接项目的访问路径，并打印出来
            logger.info

                    ("\n----------------------------------------------------------\n\t" +
                                    "Application '{}' is running! Access URLs:\n\t" +
                                    "Local: \t\t{}://localhost:{}\n\t" +
                                    "External: \t{}://{}:{}\n\t" +
                                    "接口文档地址: \t\t{}://localhost:{}\n\t" +
                                    "Profile(s): \t{}\n----------------------------------------------------------",
                            env.getProperty("application.name "),
                            protocol,
                            env.getProperty("server.port")+contextPath,
                            protocol,
                            InetAddress.getLocalHost().getHostAddress(),
                            env.getProperty("server.port")+contextPath,
                            protocol,env.getProperty("server.port")+contextPath+"/swagger-ui.html",
                            env.getActiveProfiles()
                    );
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

    }

}
