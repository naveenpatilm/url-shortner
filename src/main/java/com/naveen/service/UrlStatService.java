package com.naveen.service;

import com.naveen.model.Account;

import java.util.Map;

public interface UrlStatService {
    Map<String, Integer> getUrlStats(Account account);
}
