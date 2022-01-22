package com.ishan.simpleurlshortener.services;

import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private Map<String, String> shortUrls = new HashMap<>();

    @Override
    public String getShortenUrl(String url) {
        String hash = getHash(url);
        String shortUrl = hash.substring(0, 7);
        while(shortUrls.containsKey(shortUrl)) {
            if(url.equals(shortUrls.get(shortUrl))) {
                return shortUrl;
            }
            hash = getHash(hash);
            shortUrl = hash.substring(0,7);
        }
        shortUrls.put(shortUrl, url);
        return shortUrl;
    }

    @Override
    public String getLongUrl(String hash) {
        if(shortUrls.containsKey(hash)) return shortUrls.get(hash);
        return "Url does not exist.";
    }


    private String getHash(String url)  {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        byte[] bytes = md.digest(url.getBytes());
        String hash = DatatypeConverter.printHexBinary(bytes).toUpperCase();
        return hash;
    }
}
