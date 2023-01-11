public class User {

    private int id;
    private String name;
    private String password;

    //constructor
    public User( int id, String name,String password) {
        this.name = name;
        this.id = id;
        this.password = password;
    }

    //getter setters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }




}