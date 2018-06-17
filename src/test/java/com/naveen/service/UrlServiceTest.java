package com.naveen.service;

import com.naveen.dao.UrlDao;
import com.naveen.dao.UrlStatDao;
import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.request.UrlRegistrationRequest;
import com.naveen.response.UrlRegistrationResponse;
import com.naveen.service.impl.UrlServiceImpl;
import com.naveen.util.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UrlServiceTest {
    @Mock
    RandomStringGenerator randomStringGenerator;
    @InjectMocks
    UrlServiceImpl urlService;
    @Mock
    private UrlDao urlDao;
    @Mock
    private UrlStatDao urlStatDao;

    @Test
    public void getUrlDetailsByShortUrlKey_returnValidUrlDetails_validShortUrlKey() {
        when(urlDao.findByShortUrlKey("valid_short_key"))
                .thenReturn(new Url("longUrl", "shortUrl", new Account()));

        Url actualUrl = urlService.getUrlDetailsByShortUrlKey("valid_short_key");
        assertEquals("longUrl", actualUrl.getLongUrl());
        assertEquals("shortUrl", actualUrl.getShortUrlKey());
    }

    @Test
    public void getUrlDetailsByShortUrlKey_returnNullUrl_invalidShortKey() {
        when(urlDao.findByShortUrlKey("invalid_short_key")).thenReturn(null);

        Url actualUrl = urlService.getUrlDetailsByShortUrlKey("invalid_short_key");
        assertEquals(null, actualUrl);
    }

    @Test
    public void incrementRedirectionCount_noStats_noError() {
        Url emptyUrl = new Url();
        when(urlStatDao.findByUrl(emptyUrl)).thenReturn(null);

        urlService.incrementRedirectionCount(emptyUrl);
        assertTrue(true);
    }

    @Test
    public void registerUrl_newUrl_urlShouldBeRegistered() {
        when(urlDao.findByLongUrl("newLongUrl")).thenReturn(null);
        UrlRegistrationRequest urlRegistrationRequest = new UrlRegistrationRequest();
        urlRegistrationRequest.setUrl("valid_long_url");

        UrlRegistrationResponse urlRegistrationResponse = urlService.registerUrl(urlRegistrationRequest, new Account());
        assertNotNull(urlRegistrationResponse.getShortUrl());
    }

    @Test
    public void registerUrl_existingUrl_existingShortUrlShouldBeReturned() {
        Url existingUrl = new Url();
        existingUrl.setLongUrl("existingLongUrl");
        existingUrl.setShortUrlKey("short_url_key");
        when(urlDao.findByLongUrl("existingLongUrl")).thenReturn(existingUrl);
        UrlRegistrationRequest urlRegistrationRequest = new UrlRegistrationRequest();
        urlRegistrationRequest.setUrl("existingLongUrl");

        UrlRegistrationResponse urlRegistrationResponse = urlService.registerUrl(urlRegistrationRequest, new Account());
        assertTrue(urlRegistrationResponse.getShortUrl().contains(existingUrl.getShortUrlKey()));
    }
}
