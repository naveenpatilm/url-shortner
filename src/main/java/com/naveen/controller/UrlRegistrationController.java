package com.naveen.controller;

import com.naveen.dao.AccountDao;
import com.naveen.dao.UrlDao;
import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.response.UrlRegistrationResponse;
import com.naveen.request.UrlRegistrationRequest;

import static com.naveen.util.ApplicationConstants.SHORT_URL_LENGTH;

import static com.naveen.util.ApplicationConstants.PERMANENTLY_MOVED;
import static com.naveen.util.ApplicationConstants.REDIRECTION_FOUND;

import com.naveen.util.ApplicationConstants;
import com.naveen.util.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@ResponseStatus(HttpStatus.OK)
public class UrlRegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountDao accountDao;
    @Autowired
    UrlDao urlDao;
    @Autowired
    RandomStringGenerator randomStringGenerator;
    @Value("${baseUrl}")
    private String baseUrl;

    @RequestMapping(value = "/register", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public UrlRegistrationResponse registerUrl(@RequestHeader(value = "Authorization") String authToken,
                                               @RequestBody UrlRegistrationRequest urlRegistrationRequest)
            throws IllegalAccessException {
        if (!isValidRedirectType(urlRegistrationRequest)) {
            throw new IllegalArgumentException("invalid redirect type, can be either 301 or 302");
        }

        if (isInValidUrlRegistrationRequest(urlRegistrationRequest)) {
            throw new IllegalArgumentException("url cannot be empty");
        }

        LOGGER.info("registering url - " + urlRegistrationRequest.getUrl());
        Account account = accountDao.findByPassword(authToken);
        if (account == null) {
            LOGGER.error("unauthorized request for registering url - " + urlRegistrationRequest.getUrl());
            throw new IllegalAccessException("please register your account and try again");
        }
        Url existingUrl = urlDao.findByLongUrl(urlRegistrationRequest.getUrl());
        if (existingUrl != null) {
            LOGGER.info(urlRegistrationRequest.getUrl() + " url already registered. returning existing short url");
            return new UrlRegistrationResponse(baseUrl + existingUrl.getShortUrlKey());
        }
        String shortUrlKey = saveUrlDetails(urlRegistrationRequest);
        return new UrlRegistrationResponse(baseUrl + shortUrlKey);
    }

    @ExceptionHandler
    private void handleIllegalAccessException(IllegalAccessException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler
    private void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    private String saveUrlDetails(UrlRegistrationRequest urlRegistrationRequest) {
        LOGGER.info("generating short url for url - " + urlRegistrationRequest.getUrl());
        String shortUrl = randomStringGenerator.generateRandomString(SHORT_URL_LENGTH);
        Url url = new Url(urlRegistrationRequest.getUrl(), shortUrl);
        if (urlRegistrationRequest.getRedirectType() != null) {
            url.setRedirectType(urlRegistrationRequest.getRedirectType());
        }
        try {
            urlDao.save(url);
        } catch (Exception e) {
            LOGGER.error("retrying to generate short url for url - " + urlRegistrationRequest.getUrl());
            saveUrlDetails(urlRegistrationRequest);
        }
        return url.getShortUrlKey();
    }

    private boolean isValidRedirectType(UrlRegistrationRequest urlRegistrationRequest) {
        return urlRegistrationRequest.getRedirectType().equals(PERMANENTLY_MOVED)
                || urlRegistrationRequest.getRedirectType().equals(REDIRECTION_FOUND);
    }

    private boolean isInValidUrlRegistrationRequest(UrlRegistrationRequest urlRegistrationRequest) {
        return urlRegistrationRequest == null
                || urlRegistrationRequest.getUrl() == null
                || urlRegistrationRequest.getUrl().isEmpty();
    }
}
