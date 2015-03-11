package com.explore.models.wikipedia;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author vgrec, created on 9/5/14.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageDescription implements Serializable{

    private String pageId;
    private long ns;
    private String title;
    private String extract;

    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }

    public long getNs() {
        return ns;
    }

    public void setNs(long ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtract() {
        return extract;
    }

    public void setExtract(String extract) {
        this.extract = extract;
    }

}
