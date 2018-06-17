package com.naveen.model;

import javax.persistence.*;

@Entity
public class UrlStat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id", unique = true, nullable = false)
    private Url url;
    private int redirectionCount;

    public UrlStat(Url url) {
        this.url = url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getRedirectionCount() {
        return redirectionCount;
    }

    public synchronized void setRedirectionCount(int redirectionCount) {
        this.redirectionCount = redirectionCount;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }
}
