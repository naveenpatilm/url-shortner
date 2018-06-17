package com.naveen.controller;

import com.naveen.model.Account;
import com.naveen.service.AccountService;
import com.naveen.service.UrlStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@RestController
public class UrlStatController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    UrlStatService urlStatService;
    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "/statistic/{AccountId}", method = RequestMethod.GET)
    public Map<String, Integer> getUrlStats(@RequestHeader(value = "Authorization") String authToken,
                                            @PathVariable("AccountId") String accountId) throws IllegalAccessException {
        Account account = accountService.validateUser(authToken);
        if (!account.getAccountId().equals(accountId)) {
            LOGGER.error("unauthorized to access account id - " + accountId);
            throw new IllegalAccessException("you are not authorized to access this resource");
        }
        return urlStatService.getUrlStats(account);
    }

    @ExceptionHandler
    private void handleIllegalAccessException(IllegalAccessException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
    }
}
