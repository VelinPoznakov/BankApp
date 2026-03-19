package Entities.Loans;

import Entities.Loans.Enums.LoanType;

public class AdultLoan extends Loan {


    public AdultLoan(int id, String loanName, double amount, double anualPayment, int dueYear, int takenYear, int userId) {
        super(id, loanName,  amount, 0.05, anualPayment , dueYear, takenYear, LoanType.ADULT, userId);
    }

    @Override
    public String LoanDetails() {
        return loanType.toString() + " loan " + loanName +"\n"
                + "Amount " + getAmount() + "\n"
                + "Anual Payment " + anualPayment + "\n"
                + "Due Year " + dueYear + "\n";
    }
}
