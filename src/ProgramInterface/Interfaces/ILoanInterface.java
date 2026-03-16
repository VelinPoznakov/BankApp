package ProgramInterface.Interfaces;

import Entities.Loans.Loan;
import Entities.Users.Customer;

import java.util.List;

public interface ILoanInterface {

    void TakeLoan(Customer customer);
    void PayLoan(String loanName, Customer customer);
    void PayFullLoan(String loanName, Customer customer);
    List<Loan> MyLoans(Customer customer);

}
