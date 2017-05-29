package com.development.mobile.heasoft.heasoft;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class News {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("bigcontent")
    @Expose
    private String bigcontent;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("urlToImage")
    @Expose
    private String urlToImage;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBigcontent() {
        return bigcontent;
    }

    public void setBigcontent(String bigcontent) {
        this.bigcontent = bigcontent;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}