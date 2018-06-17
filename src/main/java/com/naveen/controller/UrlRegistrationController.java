package com.naveen.controller;

import com.naveen.dao.AccountDao;
import com.naveen.model.Account;
import com.naveen.request.UrlRegistrationRequest;
import com.naveen.response.UrlRegistrationResponse;
import com.naveen.service.AccountService;
import com.naveen.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.naveen.util.ApplicationConstants.PERMANENTLY_MOVED;
import static com.naveen.util.ApplicationConstants.REDIRECTION_FOUND;

@RestController
@ResponseStatus(HttpStatus.OK)
public class UrlRegistrationController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountDao accountDao;
    @Autowired
    UrlService urlService;
    @Autowired
    AccountService accountService;


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
        LOGGER.info("authenticating user");
        Account account = accountService.validateUser(authToken);
        LOGGER.info("registering url - " + urlRegistrationRequest.getUrl());
        return urlService.registerUrl(urlRegistrationRequest, account);
    }

    @ExceptionHandler
    private void handleIllegalAccessException(IllegalAccessException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }

    @ExceptionHandler
    private void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
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
