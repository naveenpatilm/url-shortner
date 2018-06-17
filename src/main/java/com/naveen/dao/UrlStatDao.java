package com.naveen.dao;

import com.naveen.model.Url;
import com.naveen.model.UrlStat;
import org.springframework.data.repository.CrudRepository;

public interface UrlStatDao extends CrudRepository<UrlStat, Long> {
    UrlStat findByUrl(Url url);
}
