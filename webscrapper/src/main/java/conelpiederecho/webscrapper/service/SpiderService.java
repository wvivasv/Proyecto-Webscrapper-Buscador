package conelpiederecho.webscrapper.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SpiderService {

    @Autowired
    private WebScrapperService webScrapperService;
    public void start(){
        String initialLink = "https://elpais.com/";
        scrapeLinkAndSave(initialLink);
    }

    public void scrapeLinkAndSave(String url){
        String initialLink = "https://elpais.com";
        List<String> links =  webScrapperService.getAllLinks(initialLink);
        links.stream().parallel().forEach(link -> {
            webScrapperService.scrapeAndSave(link);
            scrapeLinkAndSave(url);
        });
    }
}
