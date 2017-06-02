package com.development.mobile.heasoft.heasoft;


class ModelItem {

    private String smallContent;

    private String headerTag;
    private String headerDesk;
    private String headerAuthor;
    private String urlToImage;
    private int likes;
    private String titlePro;
    private String deskPro;
    private String urlToImageProjects;
    private String url;

    private String comboTitle;
    private String news1;
    private String news2;
    private String news3;

    private int i;

    private String type;

    ModelItem(String smallContent, String type){
        this.smallContent = smallContent;
        this.type = type;
    }
    ModelItem(String title,String news1, String news2, String news3, String type, int i){

        this.type = type;
        this.news1 = news1;
        this.news2 = news2;
        this.news3 = news3;
        this.comboTitle = title;
    }

    ModelItem(String headerAuthor, String url, String headerTag, String headerDesk, int i, int likes, String type){
        this.headerAuthor = headerAuthor;
        this.urlToImage = url;
        this.headerDesk = headerDesk;
        this.headerTag = headerTag;
        this.i=i;
        this.likes = likes;
           this.type = type;
    }

    ModelItem(String title, String desk, String urlToImage, String url){
        this.titlePro = title;
        this.deskPro = desk;
        this.urlToImageProjects = urlToImage;
        this.url = url;

    }

    String getHeaderTag() {return headerTag;}
    String getHeaderAuthor() {return headerAuthor;}
    String getHeaderDesk() {return headerDesk;}
    String getUrlToImage() {return urlToImage;}
    String getUrlToImageProjects() {return urlToImageProjects;}

    String getTitlePro() {return titlePro;}
    String getDeskPro() {return deskPro;}
    String getUrl() {return url;}
    String getType() {return type;}

    String getNews1() {return news1;}
    String getNews2() {return news2;}
    String getNews3() {return news3;}
    String getComboTitle() {return comboTitle;}

    int getI() {return i;}
    int getLikes() {return likes;}

    String getSmallContent() {return smallContent;}
}
