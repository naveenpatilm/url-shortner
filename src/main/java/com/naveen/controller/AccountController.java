package com.naveen.controller;

import com.naveen.request.OpenAccountRequest;
import com.naveen.response.OpenAccountResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AccountController {

    @RequestMapping(value = "/account", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public OpenAccountResponse OpenAccount(@RequestBody OpenAccountRequest openAccountRequest) {
        if(isOpenAccountRequestInValid(openAccountRequest)) {
            throw new IllegalArgumentException("accound id cannot be empty");
        }
        //Create account with account id and generate password, save to db
        return new OpenAccountResponse(false, "", "");
    }

    private boolean isOpenAccountRequestInValid(OpenAccountRequest openAccountRequest) {
        return openAccountRequest == null
                || openAccountRequest.getAccountId() == null
                || openAccountRequest.getAccountId().isEmpty();
    }

    @ExceptionHandler
    private void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }
}
