package com.app.manager.service.tasks;

import com.app.manager.model.HelperMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
//    1800000
    @Scheduled(cron = "0 29,59 7-23 * * *")
    public void pingTo() {
        var url = "https://senbonzakura-ping-app.herokuapp.com";
        HelperMethod.pingTo(url);
        logger.info("Ping to " + url);
    }


}
