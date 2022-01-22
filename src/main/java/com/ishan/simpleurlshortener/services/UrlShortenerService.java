package com.ishan.simpleurlshortener.services;

import javax.servlet.http.HttpServletRequest;

public interface UrlShortenerService {
    String getShortenUrl(String url, HttpServletRequest request);
    String getLongUrl(String hash);
}
