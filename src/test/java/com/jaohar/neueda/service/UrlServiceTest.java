package com.jaohar.neueda.service;

import com.jaohar.neueda.entity.Mapper;
import com.jaohar.neueda.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.*;

class UrlServiceTest {

    private static final String DEFAULT_SHORTURL = "jao.url";
    private static final String DEFAULT_LONGURL = "http://www.jaoharoseni.com";

    @Spy
    UrlService service;

    UrlRepository urlRepository;

    @BeforeEach
    void setUp() {
        urlRepository = Mockito.mock(UrlRepository.class);
        service = new UrlService(urlRepository);
        Mockito.when(urlRepository.findByShortUrl(Mockito.anyString())).thenReturn(getSampleMap());
        Mockito.when(urlRepository.findByLongUrl(Mockito.anyString())).thenReturn(getSampleMap());
    }


    @Test
    void generateShortUrl() {
        //When there is no record in the DB
        Mockito.when(urlRepository.findByShortUrl(DEFAULT_SHORTURL)).thenReturn(null);
        String newlyGenerated = service.generateShortUrl(DEFAULT_LONGURL);
        System.out.println(newlyGenerated);
        assertNotNull(newlyGenerated);

        //When there is record in the DB
        Mockito.when(urlRepository.findByShortUrl(DEFAULT_SHORTURL)).thenReturn(getSampleMap());
        String returnedUrl = service.generateShortUrl(DEFAULT_LONGURL);
        System.out.println(returnedUrl);
        assertNotNull(returnedUrl);
        assertEquals(DEFAULT_SHORTURL, returnedUrl);
    }

    @Test
    void generateCode() {
        String generatedCode = service.generateShortCode(8);
        System.out.println(generatedCode);
        assertNotNull(generatedCode);
        assertEquals(8, generatedCode.length());
    }

    @Test
    void createShortUrl() {
        UrlService uService = Mockito.spy(service);
        Mockito.when(uService.generateShortCode(Mockito.anyInt())).thenReturn(Mockito.anyString());
        Mockito.when(urlRepository.findByShortUrl(DEFAULT_SHORTURL)).thenReturn(null);
        String createdUrl = uService.createShortUrl();
        assertNotNull(createdUrl);

    }

    private Mapper getSampleMap(){
        Mapper map = new Mapper();
        map.setShortUrl(DEFAULT_SHORTURL);
        map.setLongUrl(DEFAULT_LONGURL);
        return map;
    }


}