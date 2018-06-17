package com.naveen.service.impl;

import com.naveen.dao.UrlDao;
import com.naveen.model.Url;
import com.naveen.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl implements UrlService {
    @Autowired
    private UrlDao urlDao;

    @Override
    public Url gerUrlDetailsByShortUrlKey(String shortUrlKey) {
        return urlDao.findByShortUrlKey(shortUrlKey);
    }
}
