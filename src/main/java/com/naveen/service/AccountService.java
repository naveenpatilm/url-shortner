package com.naveen.service;

import com.naveen.model.Account;
import com.naveen.response.OpenAccountResponse;

public interface AccountService {
    OpenAccountResponse openAccount(String accountId);
    Account validateUser(String authToken) throws IllegalAccessException;
}
