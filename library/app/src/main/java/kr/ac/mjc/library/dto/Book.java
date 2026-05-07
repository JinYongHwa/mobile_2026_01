package kr.ac.mjc.library.dto;

public class Book {
    private String titleStatement;
    private String author;
    private String thumbnailUrl;

    public String getTitleStatement() {
        return titleStatement;
    }

    public void setTitleStatement(String titleStatement) {
        this.titleStatement = titleStatement;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
