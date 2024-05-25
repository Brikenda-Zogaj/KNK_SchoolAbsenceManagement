package model;

public class User {
    private  int userId;
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String subject;
    private String email;
    private String phone;

    public User(int id, String username) {
        this.id = id;
        this.username = username;
    }
    public User(int id, String username, String firstName, String lastName, String subject) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
    }
    public User(int userId, String firstName, String lastName, String subject, String email, String phone) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.subject = subject;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
