import Loans.AbstractLoan;

import java.util.*;

public class User {

    private String name;
    private int phoneNumber;
    private final int age;

    public List<AbstractLoan> loans;
    public double money;
    public boolean isWorker;
    public HashMap<String, Double> onlineTransactions;

    private int pin;

    public User(String name, int phoneNumber, int age, int myPin) {
        this.name = name;
        setPhoneNumber(phoneNumber);
        this.age = age;
        money = 0;
        setPin(myPin);
        isWorker = false;
        loans = new ArrayList<>();
        onlineTransactions = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setPhoneNumber(int value) {
        if (String.valueOf(value).length() != 10) {
            throw new RuntimeException("Your number must be 10 nums.");
        }
        phoneNumber = value;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(int value) {
        if (String.valueOf(value).length() != 4) {
            throw new RuntimeException("Your pin must 4 digits long");
        }
        pin = value;
    }

    public String userDetails() {

        String loanString = "";

        for (AbstractLoan loan : loans) {
            loanString = loanString + loan.getName() + ", ";
        }

        String details = name + " on " + age +
                " with phone number " + phoneNumber +
                " and have loans " + loanString;

        return details;
    }

    public void pastTransactions() {

        for (Map.Entry<String, Double> kvp : onlineTransactions.entrySet()) {
            System.out.println(kvp.getKey() + " - " + kvp.getValue());
        }

        System.out.println("My current balance is " + money);
    }

    public void changePhoneNumber(int newNumber) {
        setPhoneNumber(newNumber);
    }

    public void changeName(String newName) {
        name = newName;
    }

    public void changePin(int newPin) {
        setPin(newPin);
    }
}