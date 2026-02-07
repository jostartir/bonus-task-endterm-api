package kz.aitu.endtermapi.utils;

import kz.aitu.endtermapi.model.BookBase;

import java.util.ArrayList;
import java.util.List;

public final class SortingUtils {

    private SortingUtils() {}


    public static List<BookBase> sortByPriceAsc(List<BookBase> books) {
        List<BookBase> copy = new ArrayList<>(books);
        copy.sort((a, b) -> Double.compare(a.getPrice(), b.getPrice()));
        return copy;
    }




}
