package kz.aitu.endtermapi.model;

public class PrintedBook extends BookBase {

    public PrintedBook(int id, String title, String isbn, double price, Author author) {
        super(id, title, isbn, price, author, BookType.PRINTED);
    }
    
    public String getType() {
        return "PrintedBook";
    }

    @Override
    public String toString() {
        return getDescription(); // у тебя getDescription уже нормальный
    }



}
