package Entities.Loans;

import Entities.Loans.Enums.LoanType;

import java.time.LocalDate;

public class StudentLoan extends Loan{

    public StudentLoan(int id, String loanName, double amount, double anualPayment,  int yearsPeriod, int takenYear, int userId) {
        super(id, loanName, amount, 0.3, anualPayment, yearsPeriod, takenYear, LoanType.STUDENT, userId);
    }


    @Override
    public String MoneyLeftToReturn() {
        double result = getAmount() + (getAmount() * interest) - getReturnedMoney();
        return "You have " + result + " to return";
    }

    @Override
    public String YearsLeft() {
        int year = LocalDate.now().getYear();
        int result = dueYear - year;
        return "You have " + result + " years left";
    }

    @Override
    public String LoanDetails() {
        return "";
    }
}
