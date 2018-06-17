package com.naveen.service.impl;

import com.naveen.dao.UrlDao;
import com.naveen.dao.UrlStatDao;
import com.naveen.model.Url;
import com.naveen.model.UrlStat;
import com.naveen.service.UrlService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UrlDao urlDao;
    @Autowired
    private UrlStatDao urlStatDao;

    @Override
    public Url gerUrlDetailsByShortUrlKey(String shortUrlKey) {
        return urlDao.findByShortUrlKey(shortUrlKey);
    }

    @Override
    public synchronized void incrementRedirectionCount(Url url) {
        UrlStat urlStat = urlStatDao.findByUrl(url);
        if (urlStat == null) {
            urlStat = new UrlStat(url);
        }
        int redirectionCount = urlStat.getRedirectionCount();
        urlStat.setRedirectionCount(++redirectionCount);
        urlStatDao.save(urlStat);
    }
}
