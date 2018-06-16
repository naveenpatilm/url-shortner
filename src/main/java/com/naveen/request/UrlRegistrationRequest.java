package com.naveen.request;

public class UrlRegistrationRequest {
    private String url;
    private Integer redirectType;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(Integer redirectionType) {
        this.redirectType = redirectType;
    }
}
