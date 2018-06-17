package com.naveen.service;

import com.naveen.dao.UrlDao;
import com.naveen.dao.UrlStatDao;
import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.model.UrlStat;
import com.naveen.service.impl.UrlStatServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UrlStatServiceTest {
    @Mock
    private UrlDao urlDao;
    @Mock
    private UrlStatDao urlStatDao;
    @InjectMocks
    private UrlStatServiceImpl urlStatService;

    @Test
    public void getUrlStats_validAccount_getValidResponse() {
        Account validAccount = new Account();
        List<Url> urlList = new ArrayList<>();
        Url validUrl = new Url();
        validUrl.setLongUrl("www.longurl.com");
        validUrl.setId(1);
        validUrl.setAccount(validAccount);
        urlList.add(validUrl);

        UrlStat urlStat = new UrlStat();
        urlStat.setRedirectionCount(10);
        urlStat.setUrl(validUrl);

        when(urlDao.findAllByAccount(validAccount)).thenReturn(urlList);
        when(urlStatDao.findByUrl(validUrl)).thenReturn(urlStat);

        Map<String, Integer> actualUrlStats = urlStatService.getUrlStats(validAccount);
        assertNotNull(actualUrlStats);
        Integer actualRedirectionCount = actualUrlStats.get(validUrl.getLongUrl());
        assertEquals(Long.valueOf(urlStat.getRedirectionCount()), Long.valueOf(actualRedirectionCount));
        assertTrue(actualUrlStats.containsKey(validUrl.getLongUrl()));
    }

}
