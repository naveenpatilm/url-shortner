package com.naveen.controller;

import com.naveen.model.Url;
import com.naveen.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.naveen.util.ApplicationConstants.REDIRECTION_HEADER;

@RestController
public class ShortUrlController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UrlService urlService;

    @RequestMapping(value = "/{short_url_key}", method = RequestMethod.GET)
    public void redirectShortUrl(@PathVariable("short_url_key") String shortUrlKey,
                                 HttpServletResponse httpServletResponse) {
        Url url = urlService.getUrlDetailsByShortUrlKey(shortUrlKey);
        if (url == null) {
            throw new IllegalStateException("url not registered");
        }
        LOGGER.info("redirecting to url - " + url.getLongUrl());
        httpServletResponse.setHeader(REDIRECTION_HEADER, url.getLongUrl());
        httpServletResponse.setStatus(url.getRedirectType());
        LOGGER.info("incrementing redirection count for url - " + url.getLongUrl());
        urlService.incrementRedirectionCount(url);
    }

    @ExceptionHandler
    private void handleIllegalStateException(IllegalStateException e,
                                             HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }
}
