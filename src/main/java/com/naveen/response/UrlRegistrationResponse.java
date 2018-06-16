package com.naveen.response;

public class UrlRegistrationResponse {
    private String shortUrl;

    public UrlRegistrationResponse(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
