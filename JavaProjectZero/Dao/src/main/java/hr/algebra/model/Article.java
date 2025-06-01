/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author daniel.bele
 */
public final class Article {
    
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private int id;
    private String title;
    private String link;
    private String description;
    private LocalDateTime publishedDate;
    private String creator;
    private String picturePath;
    private String content;

    public Article(int id, String title, String link, String description, LocalDateTime publishedDate, String creator, String picturePath, String content) {
        this.id = id;
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishedDate = publishedDate;
        this.creator = creator;
        this.picturePath = picturePath;
        this.content = content;
    }

    public Article(String title, String link, String description, LocalDateTime publishedDate, String creator, String picturePath, String content) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publishedDate = publishedDate;
        this.creator = creator;
        this.picturePath = picturePath;
        this.content = content;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Article{" + "id=" + id + ", title=" + title + '}';
    }
    

    
    
}

