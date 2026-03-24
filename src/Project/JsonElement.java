package Project;

public abstract class JsonElement{

    public abstract void validate();
    public abstract void print();
    public abstract void search(String key);
    public abstract void set(String path, String value);
    public abstract void create(String path, String value);
    public abstract void delete(String path);
    public abstract void move(String from, String to);
    public abstract void save(String path);
    public abstract void saveAs(String file, String path);
}