package com.ishan.simpleurlshortener;

import com.ishan.simpleurlshortener.constants.Constants;
import com.ishan.simpleurlshortener.services.UrlShortenerService;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class SimpleUrlShortenerApplicationTests {

    @Autowired
    private UrlShortenerService urlShortenerService;

    private static HttpServletRequest  mockedRequest;

    @Test
    void itShouldNotAcceptInvalidUrl() {
        HttpServletRequest  mockedRequest = Mockito.mock(HttpServletRequest.class);
        assertEquals(Constants.URL_NOT_VALID,urlShortenerService.getShortenUrl("adfafasf", mockedRequest));
    }

    @Test
    void itShouldReturnShortenUrl() {
        String shortenedUrl = urlShortenerService.getShortenUrl("https://google.com", mockedRequest);
        assertNotEquals(Constants.URL_NOT_VALID, shortenedUrl);
    }

    @Test
    void itShouldCreateOneShortenUrlForSameUrl() {
        String shortenedUrl1 = urlShortenerService.getShortenUrl("https://google.com", mockedRequest);
        String shortenedUrl2 = urlShortenerService.getShortenUrl("https://google.com", mockedRequest);
        assertEquals(shortenedUrl1, shortenedUrl2);
    }

    @Test
    void itShouldCreateUniqueShortUrlForDifferentUrl() {
        String shortenedUrl1 = urlShortenerService.getShortenUrl("https://google.com", mockedRequest);
        String shortenedUrl2 = urlShortenerService.getShortenUrl("https://yahoo.com", mockedRequest);
        assertNotEquals(shortenedUrl1, shortenedUrl2);
    }

    @Test
    void itShouldReturnLongUrlFromShortUrl() {
        String url = "https://google.com";
        String shortenedUrl = urlShortenerService.getShortenUrl(url, mockedRequest);
        String baseUrl = getUrlWithBaseUrl(mockedRequest);
        String[] split = shortenedUrl.split(baseUrl);
        String longUrl = urlShortenerService.getLongUrl(split[split.length-1]);
        assertEquals(url,longUrl);
    }

    @Test
    void itShouldHandleInvalidShortenUrl() {
        String invalidHash = "ItShouldNotExist";
        String longUrl = urlShortenerService.getLongUrl(invalidHash);
        assertEquals(Constants.URL_DOES_NOT_EXIST, longUrl);
    }

    @BeforeAll
    static void InitMockedRequest() {
        mockedRequest = Mockito.mock(HttpServletRequest.class);
        when(mockedRequest.getScheme()).thenReturn("https");
        when(mockedRequest.getServerName()).thenReturn("localhost");
        when(mockedRequest.getServerPort()).thenReturn(8080);
        when(mockedRequest.getRequestURI()).thenReturn("/u");
    }

    private @NotNull String getUrlWithBaseUrl(HttpServletRequest request) {
        String baseUrl = ServletUriComponentsBuilder.fromRequestUri(request)
                .replacePath(null)
                .build()
                .toUriString();
        return baseUrl + request.getRequestURI() + '/';
    }

}
