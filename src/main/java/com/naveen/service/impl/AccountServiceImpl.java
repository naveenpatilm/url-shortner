package com.naveen.service.impl;

import com.naveen.dao.AccountDao;
import com.naveen.model.Account;
import com.naveen.response.OpenAccountResponse;
import com.naveen.service.AccountService;

import static com.naveen.util.ApplicationConstants.ACCOUNT_EXISTS;
import static com.naveen.util.ApplicationConstants.ACCOUNT_OPENED;
import static com.naveen.util.ApplicationConstants.PASSWORD_LENGTH;
import com.naveen.util.RandomStringGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountDao accountDao;
    @Autowired
    private RandomStringGenerator randomStringGenerator;

    @Override
    public OpenAccountResponse openAccount(String accountId) {
        LOGGER.info("generating random password for account id - " + accountId);
        String password = randomStringGenerator.generateRandomString(PASSWORD_LENGTH);
        LOGGER.info("trying to register account id - " + accountId);
        try {
            accountDao.save(new Account(accountId, password));
        } catch (Exception e) {
            LOGGER.error("Failed to register account", e);
            return new OpenAccountResponse(false, ACCOUNT_EXISTS, "");
        }
        LOGGER.info("account successfully opened for account id - " + accountId);
        return new OpenAccountResponse(true, ACCOUNT_OPENED, password);
    }

    @Override
    public Account validateUser(String authToken) throws IllegalAccessException {
        Account account = accountDao.findByPassword(authToken);
        if (account == null) {
            LOGGER.error("unauthorized request for registering url");
            throw new IllegalAccessException("please register your account and try again");
        }
        return account;
    }
}
