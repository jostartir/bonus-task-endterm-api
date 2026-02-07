package kz.aitu.endtermapi.model;

public class Author extends BaseEntity{

    private Integer birthYear;

    public Author(Integer id, String fullName, int birthYear) {
        super(id,fullName);
        this.birthYear = birthYear;
    }

    public int getAuthorId(){
        return getId();
    }
    public void setAuthorId(int authorId){
        setId(authorId);
    }

    public String getFullName(){
        return getName();
    }
    public void setFullName(String fullName){
        setName(fullName);
    }

    public Integer getBirthYear(){
        return birthYear;
    }
    public void setBirthYear(int birthYear){
        this.birthYear = birthYear;
    }

    @Override
    public String getType(){
        return "Author";
    }

    @Override
    public String getDescription(){
        return getFullName() + (birthYear != null? "(" + birthYear + ")" : "");
    }



    @Override
    public String toString() {
        return getDescription();
    }
}
