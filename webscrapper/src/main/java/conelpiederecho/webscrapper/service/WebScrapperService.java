package conelpiederecho.webscrapper.service;

import conelpiederecho.webscrapper.models.WebPage;
import conelpiederecho.webscrapper.repository.WebPageRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapperService {

    @Autowired
    private WebPageRepository repository;
    public void scrapeAndSave(String url)  {
        try {
            Document document = Jsoup.connect(url).get();

            String domain = getDomainFromUrl(url);
            String title = document.title();
            String description = document.select("meta[name=description]").attr("content");
            String picture = document.select("meta[property=og:image]").attr("content");

            WebPage webPage = new WebPage();
            webPage.setDomain(domain);
            webPage.setTitle(title);
            webPage.setDescription(description);
            webPage.setPicture(picture);
            webPage.setUrl(url);

            repository.save(webPage);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }
    private String getDomainFromUrl(String url){
        String domain = url.replaceFirst("^(https?://)?(www\\.)?","");
        int index = domain.indexOf('/');
        if(index != -1){
            domain = domain.substring(0, index);
        }
        return domain;
    }

    public List<String> getAllLinks(String url) {
            WebPage findWebPage = repository.findByUrl(url);
            if(findWebPage != null){
                return new ArrayList<>();
            }
            List<String> result = new ArrayList<>();
            try{
                Document document = Jsoup.connect(url).get();
                Elements links = document.select("a[href]");
                links.stream().parallel().forEach(link -> {
                    String linkHref = link.attr("href");
                    if(linkHref.startsWith("/")){
                        // https://elpais.com
                        linkHref = "https://" + getDomainFromUrl(url) + linkHref;
                    }
                    if(!result.contains(linkHref)){
                        result.add(linkHref);
                    }
                });





            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return result;

    }
}
