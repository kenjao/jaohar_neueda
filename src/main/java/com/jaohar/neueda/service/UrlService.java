package com.jaohar.neueda.service;

import com.jaohar.neueda.entity.Mapper;
import com.jaohar.neueda.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlService {


    UrlRepository urlRepository;

    private static final String URL_PREFIX = "http://jao.url/";

    @Value("${shortcode.length:6}")
    private int defaultUrlLength;

    @Autowired
    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    /**
     * Generate short Url, if longUrl already exist return what is in existence otherwise generate a new one
     * @param longUrl
     * @return
     */
    public String generateShortUrl(String longUrl){
        Mapper map  = urlRepository.findByLongUrl(longUrl);
        if(map != null){
            return map.getShortUrl();
        }

        String shortUrl = createShortUrl();
        saveUrl(shortUrl, longUrl);
       return shortUrl;
    }

    /**
     * Save a record of the url in the database
     * @param shortUrl
     * @param longUrl
     */
    private void saveUrl(String shortUrl, String longUrl) {
        Mapper mapper = new Mapper();
        mapper.setShortUrl(shortUrl);
        mapper.setLongUrl(longUrl);

        urlRepository.save(mapper);
    }

    /**
     * This method creates a shortUrl, it checks if the created url is in existence,
     * if it is, creates another using the recursive call, else return the created short url
     * @return
     */
    public String createShortUrl(){
        String generatedCode = generateShortCode(defaultUrlLength);
        String shortUrl = URL_PREFIX + generatedCode;
        if(alreadyExist(shortUrl)){
            return createShortUrl();
        }

        return shortUrl;
    }

    /**
     * Generate short code
     * @param lengthOfShortUrl
     * @return
     */
    public String generateShortCode(int lengthOfShortUrl){
        boolean useLetters = true;
        boolean useNumbers = true;
        return RandomStringUtils.random(lengthOfShortUrl, useLetters, useNumbers);
    }

    /**
     * Check if url is already in existence
     * @param shortUrl
     * @return
     */
    public boolean alreadyExist(String shortUrl){
        Mapper map = urlRepository.findByShortUrl(shortUrl);
        if(map != null){
            return true;
        }
        return false;
    }


    /**
     * Retrieves the long url provided a valid shortUrl is provided
     * @param shortUrl
     * @return
     */
    public String getLongUrl(String shortUrl) {
        Mapper map = urlRepository.findByShortUrl(shortUrl);
        if(map == null){
            return null;
        }

        return map.getLongUrl();
    }

    public Long getCount() {
        return urlRepository.count();
    }
}
