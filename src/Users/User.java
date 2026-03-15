package Users;

import Exceptions.InvalidPasswordException;
import Exceptions.UsernameException;
import Services.UserServices;
import Users.Enums.UserRoles;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private int id;
    protected String username;
    private String password;
    public String birthDate;
    public String createdAt;

    public abstract String getUserDetails();


    public User(int id,
                String username,
                String password,
                String birthDate
    ){
        setId(id);
        setUsername(username);
        setPassword(password);
        this.birthDate = birthDate;
        this.createdAt = LocalDate.now().toString();

    }

    public void setUsername(String username) {
        if(username.length() < 3 ){
            throw new UsernameException("Username too short");
        }
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String passwordRegex = "^(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";

        if(!password.matches(passwordRegex)){
            throw new InvalidPasswordException("Invalid password, try again password must contain at least 8 characters," +
                    " one upper case character at least one symbol");
        }
        this.password = password;
    }

    public int getId() {
        return id;
    }

    private void setId(int id) {
        this.id = id;
    }
}
