package com.naveen.dao;

import com.naveen.model.Url;
import org.springframework.data.repository.CrudRepository;

public interface UrlDao extends CrudRepository<Url, Long> {
    Url findByLongUrl(String longUrl);
    Url findByShortUrlKey(String shortUrlKey);
}
