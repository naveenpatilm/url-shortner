package com.naveen.service.impl;

import com.naveen.dao.UrlDao;
import com.naveen.dao.UrlStatDao;
import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.model.UrlStat;
import com.naveen.request.UrlRegistrationRequest;
import com.naveen.response.UrlRegistrationResponse;
import com.naveen.service.UrlService;
import com.naveen.util.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.naveen.util.ApplicationConstants.SHORT_URL_LENGTH;

@Service
public class UrlServiceImpl implements UrlService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RandomStringGenerator randomStringGenerator;
    @Autowired
    private UrlDao urlDao;
    @Autowired
    private UrlStatDao urlStatDao;
    @Value("${baseUrl}")
    private String baseUrl;

    @Override
    public Url gerUrlDetailsByShortUrlKey(String shortUrlKey) {
        return urlDao.findByShortUrlKey(shortUrlKey);
    }

    @Override
    public synchronized void incrementRedirectionCount(Url url) {
        UrlStat urlStat = urlStatDao.findByUrl(url);
        if (urlStat == null || urlStat.getUrl() == null) {
            urlStat = new UrlStat(url);
        }
        int redirectionCount = urlStat.getRedirectionCount();
        urlStat.setRedirectionCount(++redirectionCount);
        urlStatDao.save(urlStat);
    }

    @Override
    public UrlRegistrationResponse registerUrl(UrlRegistrationRequest urlRegistrationRequest, Account account) {
        Url existingUrl = urlDao.findByLongUrl(urlRegistrationRequest.getUrl());
        if (existingUrl != null) {
            LOGGER.info(urlRegistrationRequest.getUrl() + " url already registered. returning existing short url");
            return new UrlRegistrationResponse(baseUrl + existingUrl.getShortUrlKey());
        }
        String shortUrlKey = saveUrlDetails(urlRegistrationRequest, account);
        return new UrlRegistrationResponse(baseUrl + shortUrlKey);
    }

    private String saveUrlDetails(UrlRegistrationRequest urlRegistrationRequest, Account account) {
        LOGGER.info("generating short url for url - " + urlRegistrationRequest.getUrl());
        String shortUrl = randomStringGenerator.generateRandomString(SHORT_URL_LENGTH);
        Url url = new Url(urlRegistrationRequest.getUrl(), shortUrl, account);
        if (urlRegistrationRequest.getRedirectType() != null) {
            url.setRedirectType(urlRegistrationRequest.getRedirectType());
        }
        try {
            urlDao.save(url);
        } catch (Exception e) {
            LOGGER.error("retrying to generate short url for url - " + urlRegistrationRequest.getUrl());
            saveUrlDetails(urlRegistrationRequest, account);
        }
        return url.getShortUrlKey();
    }
}
