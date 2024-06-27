package org.alfabank.soacorn.pojo.books;


import lombok.Data;


import java.util.List;

@Data
public class GetBooksResponse {

    List<Book> books;

}
