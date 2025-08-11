package com.pcl.library.model;

public class Book {
   private String bookID;
   private String bookName;
   private  String bookAuthor;
   private String cupboard;
   private String section;
    private boolean availability;

    @Override
    public String toString() {
        return "Book{" +
                "bookID='" + bookID + '\'' +
                ", bookName='" + bookName + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", cupboard='" + cupboard + '\'' +
                ", section='" + section + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }

    public Book() {
    }

    public Book(String bookID, String bookName, String bookAuthor, String cupboard, String section, boolean availability) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.cupboard = cupboard;
        this.section = section;
        this.availability = availability;
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

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public boolean getAvailability() {
        return availability;
    }

    public String getSection() {
        return section;
    }

    public String getCupboard() {
        return cupboard;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookName() {
        return bookName;
    }

    public String getBookID() {
        return bookID;
    }




}
