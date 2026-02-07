package kz.aitu.endtermapi.patterns.factory;
import kz.aitu.endtermapi.dto.BookRequest;
import kz.aitu.endtermapi.model.*;
import kz.aitu.endtermapi.patterns.builder.BookBuilder;
public class BookFactory {
    public static BookBase create(BookRequest req){
        BookType type = BookType.valueOf(req.bookType.toUpperCase());

        return new BookBuilder().id(0).name(req.name).isbn(req.isbn).price(req.price).authorId(req.authorId).type(type).build();
    }
}
