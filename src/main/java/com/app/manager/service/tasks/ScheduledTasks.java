package com.app.manager.service.tasks;

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
    @Scheduled(cron = "0 29,59 7-23 ? * * *")
    public void pingTo() {
        var url = "https://senbonzakura-ping-app.herokuapp.com";
        try {
            var result  = getStatus(url);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        logger.info("Ping to " + url);
    }

    private String getStatus(String url) {
        try {
            var siteURL = new URL(url);
            var connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(20000);
            connection.connect();

            var code = connection.getResponseCode();
            return "Status Code: " + code;
        } catch (Exception e) {
            return  "-> Red <-\t" + "Wrong domain - Exception: " + e.getMessage();
        }
    }
}
