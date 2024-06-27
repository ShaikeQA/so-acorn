package org.alfabank.soacorn.pojo.books;

import lombok.Data;

import java.util.Date;

@Data
public class Book {
    public String isbn;
    public String title;
    public String subTitle;
    public String author;
    public Date publish_date;
    public String publisher;
    public int pages;
    public String description;
    public String website;
}
