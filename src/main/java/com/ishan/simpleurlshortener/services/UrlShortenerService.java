package com.ishan.simpleurlshortener.services;

public interface UrlShortenerService {
    String getShortenUrl(String url);
    String getLongUrl(String hash);
}
