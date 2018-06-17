package com.naveen.service;

import com.naveen.model.Account;
import com.naveen.model.Url;
import com.naveen.request.UrlRegistrationRequest;
import com.naveen.response.UrlRegistrationResponse;
import org.springframework.stereotype.Service;

public interface UrlService {
    Url gerUrlDetailsByShortUrlKey(String shortUrlKey);
    void incrementRedirectionCount(Url url);
    UrlRegistrationResponse registerUrl(UrlRegistrationRequest urlRegistrationRequest, Account account);
}
