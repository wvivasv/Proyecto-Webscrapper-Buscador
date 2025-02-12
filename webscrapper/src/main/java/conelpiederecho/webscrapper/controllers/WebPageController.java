package conelpiederecho.webscrapper.controllers;

import conelpiederecho.webscrapper.models.WebPage;
import conelpiederecho.webscrapper.repository.WebPageRepository;
import conelpiederecho.webscrapper.service.SpiderService;
import conelpiederecho.webscrapper.service.WebScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class WebPageController {

    @Autowired
    WebPageRepository repository;

    @Autowired
    SpiderService spiderService;

    @Autowired
    WebScrapperService webScrapperService;

    // Example: http://localhost:8080/api/search?query=chocolate

    @GetMapping("/api/search")
    public List<WebPage> search(@RequestParam("query") String query){
        List<WebPage> list = new ArrayList<>();
        Iterable<WebPage> result = repository.findByText(query);
        for(WebPage webPage : result){
            list.add(webPage);
        }

        return list;
    }

    @GetMapping("/api/webscrapper")
    public void scrapeAndSave(@RequestParam("url") String url) throws IOException {
        webScrapperService.scrapeAndSave(url);
    }

    @GetMapping("/api/start")
    public void scrapeAndSave() throws IOException{
        spiderService.start();
    }
}
