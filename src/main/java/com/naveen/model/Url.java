package com.naveen.model;

import static com.naveen.util.ApplicationConstants.REDIRECTION_FOUND;

import javax.persistence.*;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String longUrl;
    @Column(nullable = false, unique = true)
    private String shortUrlKey;
    @Column(nullable = false)
    private Integer redirectType = REDIRECTION_FOUND;

    public Url() {
    }

    public Url(String longUrl, String shortUrlKey) {
        this.longUrl = longUrl;
        this.shortUrlKey = shortUrlKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public String getShortUrlKey() {
        return shortUrlKey;
    }

    public void setShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectType) {
        this.redirectType = redirectType;
    }
}
