package com.example.iqrakhan.studentapplication;

/**
 * Created by Iqra Khan on 5/4/2016.
 */
public class News {

    private int newsId;
    private  String newsHeading;
    private  String newsDetail;
    private  String imagePath;

    public News() {
    }

    public News(int newsId, String newsHeading, String newsDetail, String imagePath) {
        this.newsId = newsId;
        this.newsHeading = newsHeading;
        this.newsDetail = newsDetail;
        this.imagePath = imagePath;
    }

    public int getNewsId() {
        return newsId;
    }

    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    public String getNewsHeading() {
        return newsHeading;
    }

    public void setNewsHeading(String newsHeading) {
        this.newsHeading = newsHeading;
    }

    public String getNewsDetail() {
        return newsDetail;
    }

    public void setNewsDetail(String newsDetail) {
        this.newsDetail = newsDetail;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
