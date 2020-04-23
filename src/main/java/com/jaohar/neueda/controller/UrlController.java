package com.jaohar.neueda.controller;

import com.jaohar.neueda.exception.NotFoundException;
import com.jaohar.neueda.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("url")
public class UrlController {

    UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    /**
     * Health check
     * @return
     */
    @GetMapping({"","/"})
    public String appCheck(){
        return "App is up and running.";
    }

    /**
     * Resource for short url generation, expects the longUrl as a request parameter
     * @param longUrl
     * @return
     */
    @GetMapping("/short")
    public ResponseEntity<String> generateShortUrl(@RequestParam("longUrl") String longUrl){
        return new ResponseEntity<>(urlService.generateShortUrl(longUrl), HttpStatus.OK);

    }

    /**
     * Resource used to retrieve the longUrl, given a correct shortUrl is provided
     * @param shortUrl
     * @return
     */
    @GetMapping("/long")
    public ResponseEntity<String> retrieveLongUrl(@RequestParam("shortUrl") String shortUrl){
        String url = urlService.getLongUrl(shortUrl);

        if(url == null){
            throw new NotFoundException("Long Url cannot be found for "+ shortUrl);
        }
        return new ResponseEntity<>(url, HttpStatus.OK);
    }

    /**
     * Statistic showing the count of all urls
     * @return
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getCountOfUrls(){
        return new ResponseEntity<>(urlService.getCount(), HttpStatus.OK);
    }

}
