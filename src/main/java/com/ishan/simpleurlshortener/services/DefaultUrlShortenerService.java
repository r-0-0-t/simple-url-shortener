package com.ishan.simpleurlshortener.services;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private Map<String, String> shortUrls = new HashMap<>();

    @Override
    public String getShortenUrl(String url, HttpServletRequest request) {
        String hash = getHash(url);
        String shortUrlHash = hash.substring(0, 7);
        while(shortUrls.containsKey(shortUrlHash)) {
            if(url.equals(shortUrls.get(shortUrlHash))) {
                return shortUrlHash;
            }
            hash = getHash(hash);
            shortUrlHash = hash.substring(0,7);
        }
        shortUrls.put(shortUrlHash, url);
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl + request.getRequestURI() + '/' + shortUrlHash;
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
