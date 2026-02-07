package kz.aitu.endtermapi.model;

public abstract class BaseEntity {
    private int id;
    private String name;

    protected BaseEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }


    public abstract String getType();
    public abstract String getDescription();


    public String getDisplayName() {
        return id + ": " + name;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString() {
        return getDescription();
    }
}
