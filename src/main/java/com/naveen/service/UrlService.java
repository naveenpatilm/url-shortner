package com.naveen.service;

import com.naveen.model.Url;
import org.springframework.stereotype.Service;

public interface UrlService {
    Url gerUrlDetailsByShortUrlKey(String shortUrlKey);
    void incrementRedirectionCount(Url url);
}
