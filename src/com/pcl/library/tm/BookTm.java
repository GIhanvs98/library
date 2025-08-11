package com.pcl.library.tm;

import javafx.scene.control.Button;

public class BookTm {
    private String bookID;
    private String bookName;
    private String bookAuthor;
    private String cupboard;
    private String section;
    private String availability;
    private Button button;

    @Override
    public String toString() {
        return "BookTm{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", cupboard='" + cupboard + '\'' +
                ", section='" + section + '\'' +
                ", availability='" + availability + '\'' +
                ", button=" + button +
                '}';
    }

    public BookTm() {
    }

    public BookTm(String bookID, String bookName, String bookAuthor, String cupboard, String section, String availability, Button button) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.cupboard = cupboard;
        this.section = section;
        this.availability = availability;
        this.button = button;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setCupboard(String cupboard) {
        this.cupboard = cupboard;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public String getBookID() {
        return bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getCupboard() {
        return cupboard;
    }

    public String getSection() {
        return section;
    }

    public String getAvailability() {
        return availability;
    }

    public Button getButton() {
        return button;
    }
}
