package kz.aitu.endtermapi.patterns.builder;
import kz.aitu.endtermapi.model.*;

import java.awt.print.Book;

import static org.apache.coyote.http11.Constants.a;

public class BookBuilder {
    private int id;
    private String name;
    private String isbn;
    private double price;
    private int authorId;
    private BookType type = BookType.PRINTED;

    public BookBuilder id(int id){
        this.id = id;
        return this;
    }
    public BookBuilder name(String name){
        this.name = name;
        return this;
    }
    public BookBuilder isbn(String isbn){
        this.isbn = isbn;
        return this;
    }
    public BookBuilder price(double price){
        this.price = price;
        return this;
    }
    public BookBuilder authorId(int authorId){
        this.authorId = authorId;
        return this;
    }
    public BookBuilder type(BookType type){
        this.type = type;
        return this;
    }

    public BookBase build(){
        Author auth = new Author(authorId,null,0);
        if (type == BookType.EBOOK){
            return new EBook(id,name,isbn,price,auth);
        }
        return new PrintedBook(id,name,isbn,price,auth);
    }

}

