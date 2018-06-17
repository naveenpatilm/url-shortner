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
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Account account;

    public Url() {
    }

    public Url(String longUrl, String shortUrlKey, Account account) {
        this.longUrl = longUrl;
        this.shortUrlKey = shortUrlKey;
        this.account = account;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url = (Url) o;

        if (id != null ? !id.equals(url.id) : url.id != null) return false;
        if (longUrl != null ? !longUrl.equals(url.longUrl) : url.longUrl != null) return false;
        if (shortUrlKey != null ? !shortUrlKey.equals(url.shortUrlKey) : url.shortUrlKey != null) return false;
        if (redirectType != null ? !redirectType.equals(url.redirectType) : url.redirectType != null) return false;
        return account != null ? account.equals(url.account) : url.account == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (longUrl != null ? longUrl.hashCode() : 0);
        result = 31 * result + (shortUrlKey != null ? shortUrlKey.hashCode() : 0);
        result = 31 * result + (redirectType != null ? redirectType.hashCode() : 0);
        result = 31 * result + (account != null ? account.hashCode() : 0);
        return result;
    }
}
