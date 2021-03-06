package com.ishan.simpleurlshortener.services;

import com.ishan.simpleurlshortener.constants.Constants;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Service
public class DefaultUrlShortenerService implements UrlShortenerService {

    private Map<String, String> shortUrls = new HashMap<>();

    @Override
    public String getShortenUrl(String url, HttpServletRequest request) {
        if(isValidURL(url) == false) {
            return Constants.URL_NOT_VALID;
        }
        if(url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        String hash = getHash(url);
        String shortUrlHash = hash.substring(0, 7);
        while(shortUrls.containsKey(shortUrlHash)) {
            if(url.equals(shortUrls.get(shortUrlHash))) {
                return getUrlWithBaseUrl(request) + shortUrlHash;
            }
            hash = getHash(hash);
            shortUrlHash = hash.substring(0,7);
        }
        shortUrls.put(shortUrlHash, url);
        return getUrlWithBaseUrl(request) + shortUrlHash;
    }

    private String getUrlWithBaseUrl(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl + request.getRequestURI() + '/';
    }

    @Override
    public String getLongUrl(String hash) {
        if(shortUrls.containsKey(hash)) return shortUrls.get(hash);
        return Constants.URL_DOES_NOT_EXIST;
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

    private boolean isValidURL(String url) {
        try {
            new URL(url).toURI();
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
        return true;
    }
}
