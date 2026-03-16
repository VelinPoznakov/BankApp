package Entities.Users;

import Entities.IBAN;
import Exceptions.CustomerTypeException;
import Exceptions.InvalidMoneyNumber;
import Entities.Users.Enums.CustomerType;

public class Customer extends User {

    private int customerId;
    private double monthlyIncome;
//    private IBAN iban;
    private CustomerType customerType;


    public Customer(int id,
                    String username,
                    String password,
                    String birthDate,
                    int customerId,
                    double monthlyIncome,
                    String customerType) {
        super(id, username, password, birthDate);
        setCustomerId(customerId);
        setMonthlyIncome(monthlyIncome);
        setCustomerType(customerType);
    }

//    @Override
//    public String getUserDetails() {
//        return customerType.toString() + " " + username + " " + iban.getBalance();
//    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(double monthlyIncome) {
        if(monthlyIncome < 0){
            throw new InvalidMoneyNumber("Money cannot be lower than 0");
        }
        this.monthlyIncome = monthlyIncome;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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
}
