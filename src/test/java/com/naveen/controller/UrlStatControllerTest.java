package com.naveen.controller;

import com.naveen.request.OpenAccountRequest;
import com.naveen.request.UrlRegistrationRequest;
import com.naveen.response.OpenAccountResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlStatControllerTest {
    private static boolean isSetUpDone = false;
    private MockMvc mockMvc;
    @Autowired
    private AccountController accountController;
    @Autowired
    private UrlRegistrationController urlRegistrationController;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private OpenAccountResponse openAccountResponse;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
/*
    FIXME: IllegalAccessError while performing mvn clean install
    public void seedRegistrationData() throws IllegalAccessException {
        if (isSetUpDone) return;
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.setAccountId("testAccount");
        openAccountResponse = accountController.OpenAccount(openAccountRequest);
        UrlRegistrationRequest urlRegistrationRequest = new UrlRegistrationRequest();
        urlRegistrationRequest.setUrl("www.longurl.com");
        urlRegistrationRequest.setRedirectType(301);
        urlRegistrationController.registerUrl(openAccountResponse.getPassword(), urlRegistrationRequest);
        isSetUpDone = true;
    }

    @Test
    public void getUrlStats_authrorizedRequest() throws Exception {
        seedRegistrationData();
        mockMvc.perform(get("/statistic/testAccount")
                .header("Authorization", openAccountResponse.getPassword())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }*/

    @Test
    public void getUrlStats_unauthrorizedRequest() throws Exception {
        mockMvc.perform(get("/statistic/account-id")
                .header("Authorization", "invalid_auth_token")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
