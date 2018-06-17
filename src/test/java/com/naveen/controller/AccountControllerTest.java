package com.naveen.controller;

import com.naveen.request.OpenAccountRequest;
import com.naveen.response.OpenAccountResponse;
import com.naveen.service.impl.AccountServiceImpl;
import com.naveen.util.ApplicationConstants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountControllerTest {
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private AccountServiceImpl accountService;
    @InjectMocks
    private AccountController accountController;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void OpenAccount_validRequest() throws Exception {
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"accountId\":\"testAccoundId\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void OpenAccount_badRequest() throws Exception {
        mockMvc.perform(post("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void OpenAccount_newAccount_success() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.setAccountId("testAccount");

        OpenAccountResponse expectedOpenAccountResponse = new OpenAccountResponse(true,
                ApplicationConstants.ACCOUNT_OPENED, "abcd123");

        when(accountService.openAccount(openAccountRequest.getAccountId())).thenReturn(expectedOpenAccountResponse);

        OpenAccountResponse actualOpenAccountResponse = accountController.OpenAccount(openAccountRequest);

        assertEquals(expectedOpenAccountResponse, actualOpenAccountResponse);
    }

    @Test
    public void OpenAccount_sameAccountOpen_throwIllegalArgumentException() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.setAccountId("testAccount");

        accountController.OpenAccount(openAccountRequest);
        try {
            accountController.OpenAccount(openAccountRequest);
        } catch (IllegalArgumentException e) {
            assertTrue(true);
        }

    }
}
