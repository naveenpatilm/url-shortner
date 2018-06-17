package com.naveen.model;

import javax.persistence.*;

@Entity
public class UrlStat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "id", unique = true, nullable = false)
    private Url url;
    private int redirectionCount;

    public UrlStat() {
    }

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

    public void setRedirectionCount(int redirectionCount) {
        this.redirectionCount = redirectionCount;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UrlStat urlStat = (UrlStat) o;

        if (redirectionCount != urlStat.redirectionCount) return false;
        if (id != null ? !id.equals(urlStat.id) : urlStat.id != null) return false;
        return url != null ? url.equals(urlStat.url) : urlStat.url == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + redirectionCount;
        return result;
    }
}
