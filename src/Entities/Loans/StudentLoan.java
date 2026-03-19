package Entities.Loans;

import Entities.Loans.Enums.LoanType;

public class StudentLoan extends Loan{

    public StudentLoan(int id, String loanName, double amount, double anualPayment,  int yearsPeriod, int takenYear, int userId) {
        super(id, loanName, amount, 0.03, anualPayment, yearsPeriod, takenYear, LoanType.STUDENT, userId);
    }

    @Override
    public String LoanDetails() {
        return loanType.toString() + " loan " + loanName +"\n"
                + "Amount " + getAmount() + "\n"
                + "Anual Payment " + anualPayment + "\n"
                + "Due Year " + dueYear + "\n";
    }
}
