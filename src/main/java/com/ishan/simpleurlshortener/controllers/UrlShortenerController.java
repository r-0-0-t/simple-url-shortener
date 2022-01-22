package com.ishan.simpleurlshortener.controllers;

import com.ishan.simpleurlshortener.services.UrlShortenerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/u")
public class UrlShortenerController {

    private final UrlShortenerService urlShortenerService;

    @RequestMapping( value = "/{hash}", method = RequestMethod.GET)
    public String getShortenUrl(@PathVariable String hash){
        return urlShortenerService.getLongUrl(hash);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String shortenUrl(@RequestBody String url) {
        return urlShortenerService.getShortenUrl(url);
    }


}
