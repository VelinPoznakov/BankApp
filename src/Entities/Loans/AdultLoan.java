package Entities.Loans;

import Entities.Loans.Enums.LoanType;

public class AdultLoan extends Loan {


    public AdultLoan(int id, String loanName, double amount, double anualPayment, int dueYear, int takenYear, int userId) {
        super(id, loanName,  amount, 0.5, anualPayment , dueYear, takenYear, LoanType.ADULT, userId);
    }

    @Override
    public String MoneyLeftToReturn() {
        return "";
    }

    @Override
    public String YearsLeft() {
        return "";
    }

    @Override
    public String LoanDetails() {
        return "";
    }
}
