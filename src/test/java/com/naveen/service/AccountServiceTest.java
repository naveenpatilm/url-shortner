package com.naveen.service;

import com.naveen.dao.AccountDao;
import com.naveen.model.Account;
import com.naveen.response.OpenAccountResponse;
import com.naveen.service.impl.AccountServiceImpl;
import com.naveen.util.RandomStringGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import static com.naveen.util.ApplicationConstants.ACCOUNT_OPENED;
import static com.naveen.util.ApplicationConstants.PASSWORD_LENGTH;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountDao accountDao;
    @Mock
    private RandomStringGenerator randomStringGenerator;

    @InjectMocks
    @Autowired
    private AccountServiceImpl accountService;

    @Test
    public void openAccount_shouldOpenAccount_validAccountDetails() {
        when(randomStringGenerator.generateRandomString(PASSWORD_LENGTH))
                .thenReturn("abcd123");
        OpenAccountResponse actualOpenAccountResponse = accountService.openAccount("testAccount");
        OpenAccountResponse expectedOpenAccountResponse = new OpenAccountResponse(true, ACCOUNT_OPENED, "abcd123");
        assertEquals(expectedOpenAccountResponse, actualOpenAccountResponse);
    }

    @Test
    public void validateUser_invalidUser_throwIllegalAccessException() {
        when(accountDao.findByPassword("invalidToken")).thenReturn(null);
        try {
            accountService.validateUser("invalidToken");
            Assert.assertTrue(false);
        } catch (IllegalAccessException e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void validateUser_validUser_doNotThrowIllegalAccessException() {
        when(accountDao.findByPassword("validToken")).thenReturn(new Account());
        try {
            accountService.validateUser("validToken");
            Assert.assertTrue(true);
        } catch (IllegalAccessException e) {
            Assert.assertTrue(false);
        }
    }
}
