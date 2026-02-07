package kz.aitu.endtermapi.model;

public abstract class BookBase extends BaseEntity implements Validatable<BookBase>, PricedItem {

    protected String isbn;
    protected double price;
    protected Author author;
    protected BookType bookType;

    public BookBase(int id, String title, String isbn, double price, Author author, BookType bookType) {
        super(id, title);
        this.isbn = isbn;
        this.price = price;
        this.author = author;
        this.bookType = bookType;
    }

    public String getIsbn(){
        return isbn;
    }
    public void setIsbn(String isbn){
        this.isbn = isbn;
    }

    @Override
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }

    public Author getAuthor(){
        return author;
    }
    public void setAuthor(Author author){
        this.author = author;
    }

    public BookType getBookType(){
        return bookType;
    }

    @Override
    public boolean isValid(){
        return !Validatable.isBlank(getName()) && !Validatable.isBlank(isbn) && price > 0 && author != null && author.getAuthorId() > 0;
    }
    @Override
    public String getType(){
        return "Book";
    }

    @Override
    public String getDescription(){
        return getName() + " | " + bookType + " | ISBN=" + isbn + " | " + price + " | Author=" + author;
    }
}
