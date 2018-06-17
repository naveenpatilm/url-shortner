package com.naveen.service.impl;

import com.naveen.dao.UrlDao;
import com.naveen.dao.UrlStatDao;
import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.model.UrlStat;
import com.naveen.service.UrlStatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UrlStatServiceImpl implements UrlStatService {
    @Autowired
    private UrlDao urlDao;
    @Autowired
    private UrlStatDao urlStatDao;

    @Override
    public Map<String, Integer> getUrlStats(Account account) {
        List<Url> urlList = urlDao.findAllByAccount(account);
        Map<String, Integer> urlStatResponse = new HashMap<>();
        urlList.parallelStream().forEach(url -> {
            UrlStat urlStat = urlStatDao.findByUrl(url);
            int redirectionCount = urlStat == null ? 0 : urlStat.getRedirectionCount();
            urlStatResponse.put(url.getLongUrl(), redirectionCount);
        });
        return urlStatResponse;
    }
}
