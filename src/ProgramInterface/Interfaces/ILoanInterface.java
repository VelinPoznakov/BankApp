package ProgramInterface.Interfaces;

import Entities.Loans.Loan;
import Entities.Users.User;

import java.util.List;

public interface ILoanInterface {

    void TakeLoan(User user);
    void PayLoan(String loanName, User user);
    void PayFullLoan(String loanName, User user);
    List<Loan> MyLoans(User user);
    void LoanMenu(User user);

}
