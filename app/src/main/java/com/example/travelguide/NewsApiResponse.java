package com.example.travelguide;

import java.util.List;

public class NewsApiResponse {
    private String status;
    private int totalResults;
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public class Article {
        private String title;
        private String description;
        private String url;
        private String urlToImage;

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getUrl() {
            return url;
        }

        public String getUrlToImage() {
            return urlToImage;
        }
    }
}
