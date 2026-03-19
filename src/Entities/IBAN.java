package Entities;

import Exceptions.InvalidMoneyNumber;

import java.io.Serializable;

public class IBAN implements Serializable{

    public String IBANName;
    private double balance;
    private int userId;

    public IBAN(String IBANName, double balance, int userId) {
        this.IBANName = IBANName;
        setBalance(balance);
        setUserId(userId);
    }


    public String IBANDetails(){
        return IBANName + " " + balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if(balance < 0){
            throw new InvalidMoneyNumber("Money cannot be lower than 0");
        }
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int customerId) {
        userId = customerId;
    }
}
