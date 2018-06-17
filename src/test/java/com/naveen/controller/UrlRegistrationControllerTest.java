package com.naveen.controller;

import com.naveen.request.OpenAccountRequest;
import com.naveen.response.OpenAccountResponse;
import com.naveen.service.impl.AccountServiceImpl;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlRegistrationControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Mock
    private AccountServiceImpl accountService;
    @InjectMocks
    private UrlRegistrationController urlRegistrationController;
    @Autowired
    private AccountController accountController;
    private OpenAccountResponse openAccountResponse;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void seedData() {
        OpenAccountRequest openAccountRequest = new OpenAccountRequest();
        openAccountRequest.setAccountId("testAccount");
        openAccountResponse = accountController.OpenAccount(openAccountRequest);
    }

    @Test
    public void OpenAccount_unauthrorizedRequest() throws Exception {
        mockMvc.perform(post("/register")
                .header("Authorization", "invalid_auth_token")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"www.tesurl.com\", \"redirectType\":\"302\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void OpenAccount_authrorizedRequest() throws Exception {
        mockMvc.perform(post("/register")
                .header("Authorization", openAccountResponse.getPassword())
                .content("{\"url\":\"www.tesurl.com\", \"redirectType\":\"302\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void OpenAccount_invalidRedirectTypeRequest() throws Exception {

        mockMvc.perform(post("/register")
                .header("Authorization", openAccountResponse.getPassword())
                .content("{\"url\":\"www.tesurl.com\", \"redirectType\":\"303\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
