package conelpiederecho.webscrapper.jobs;

import conelpiederecho.webscrapper.service.SpiderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class WebScrapperJob {

    @Autowired
    SpiderService spiderService;

    @Scheduled(cron = "0 0 4 * * *")
    public void executeJob(){
        spiderService.start();
    }
}
