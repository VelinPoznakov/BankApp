package Entities.Loans;

import Exceptions.InvalidMoneyNumber;
import Entities.Loans.Enums.LoanType;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class Loan implements Serializable {

    private int id;
    public String loanName;
    private double amount;
    public double interest;
    public double anualPayment;
    public int dueYear;
    public int takenYear;
    private double returnedMoney;
    public LoanType loanType;
    public String createdAt;
    public int userId;
    public boolean isLoanPaid = false;


    public Loan(int id, String loanName , double amount, double interest, double anualPayment , int dueYear, int takenYear, LoanType loanType, int userId) {

        setId(id);
        this.loanName = loanName;
        setAmount(amount);
        this.interest = interest;
        this.anualPayment = anualPayment;
        this.dueYear = dueYear;
        this.takenYear = takenYear;
        this.createdAt  = LocalDate.now().toString();
        this.returnedMoney = 0;
        this.loanType = loanType;
        this.userId = userId;
    }

    public abstract String LoanDetails();

    public double getTotalAmount() {
        int years = dueYear - takenYear;
        return amount + (amount * interest * years);
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        if(amount <= 0){
            throw new InvalidMoneyNumber("Amount must be a positive number");
        }
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReturnedMoney() {
        return returnedMoney;
    }

    public void setReturnedMoney(double returnedMoney) {
        this.returnedMoney = returnedMoney;
    }
}
