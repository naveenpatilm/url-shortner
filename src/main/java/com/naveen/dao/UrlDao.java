package com.naveen.dao;

import com.naveen.model.Account;
import com.naveen.model.Url;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UrlDao extends CrudRepository<Url, Long> {
    Url findByLongUrl(String longUrl);
    Url findByShortUrlKey(String shortUrlKey);
    List<Url> findAllByAccount(Account account);
}
