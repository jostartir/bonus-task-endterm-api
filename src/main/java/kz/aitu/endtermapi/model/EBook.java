package kz.aitu.endtermapi.model;

public class EBook extends BookBase {

    public EBook(int id, String title, String isbn, double price, Author author) {
        super(id, title, isbn, price, author, BookType.EBOOK);
    }

    @Override
    public String getType() {
        return "EBook";
    }

}
