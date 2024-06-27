package org.alfabank.soacorn.pojo.books;

import lombok.Data;

import java.util.ArrayList;

@Data
public class PostBookRequest {
    public String userId;
    public ArrayList<CollectionOfIsbn> collectionOfIsbns = new ArrayList<>();

}

