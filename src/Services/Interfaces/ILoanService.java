package Services.Interfaces;

import Entities.Loans.Loan;

import java.util.List;

public interface ILoanService {

    List<Loan> LoadLoans();
    void CreateLoanInFile();
    List<Loan> StudentLoans();
    List<Loan> AdultLoans();
    List<Loan> GetLoansByUserId(int userId);
}
