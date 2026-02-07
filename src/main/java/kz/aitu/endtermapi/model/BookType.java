package kz.aitu.endtermapi.model;

public enum BookType {
    EBOOK,
    PRINTED;

    public static BookType fromDb(String value) {
        return BookType.valueOf(value.toUpperCase());
    }
}
