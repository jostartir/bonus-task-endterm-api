package kz.aitu.endtermapi.model;

public interface Validatable<T>{
    boolean isValid();

    default void requireValid(){
        if(!isValid()){
            throw new IllegalArgumentException("Validation failed for: " + getClass().getSimpleName() );
        }

    }
    static boolean isBlank(String s){
        return s == null || s.trim().isEmpty();
    }
}
