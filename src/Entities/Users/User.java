package Entities.Users;

import Entities.Enums.CustomerType;
import Entities.Enums.UserRoles;
import Exceptions.CustomerTypeException;
import Exceptions.InvalidMoneyNumber;
import Exceptions.InvalidPasswordException;
import Exceptions.UsernameException;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class User implements Serializable {
    private int id;
    protected String username;
    private String password;
    public String birthDate;
    private double monthlyIncome;
    public String createdAt;
    public UserRoles role;
    private CustomerType customerType;

    public User(int id,
                String username,
                String password,
                String birthDate,
                double monthlyIncome,
                UserRoles role,
                String customerType

    ){
        setId(id);
        setUsername(username);
        setPassword(password);
        this.birthDate = birthDate;
        setMonthlyIncome(monthlyIncome);
        this.createdAt = LocalDate.now().toString();
        this.role = role;
        setCustomerType(customerType);

    }

        public abstract String GetUserDetails();

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        if(monthlyIncome < 0){
            throw new InvalidMoneyNumber("Money cannot be lower than 0");
        }
        this.monthlyIncome = monthlyIncome;
    }

    public CustomerType getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        if(customerType.equals("student")){
            this.customerType = CustomerType.STUDENT;
            return;
        }else if(customerType.equals("adult")){
            this.customerType = CustomerType.ADULT;
            return;
        }

        throw new CustomerTypeException("Invalid Customer Type");
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
