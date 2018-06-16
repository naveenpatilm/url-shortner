package com.naveen.controller;

import com.naveen.request.OpenAccountRequest;
import com.naveen.response.OpenAccountResponse;
import com.naveen.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AccountController {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/account", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OpenAccountResponse OpenAccount(@RequestBody OpenAccountRequest openAccountRequest) {
        if(isOpenAccountRequestInValid(openAccountRequest)) {
            LOGGER.error("invalid open account request");
            throw new IllegalArgumentException("accound id cannot be empty");
        }
        return accountService.openAccount(openAccountRequest.getAccountId());
    }

    private boolean isOpenAccountRequestInValid(OpenAccountRequest openAccountRequest) {
        LOGGER.info("validating open account request");
        return openAccountRequest == null
                || openAccountRequest.getAccountId() == null
                || openAccountRequest.getAccountId().isEmpty();
    }

    @ExceptionHandler
    private void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
