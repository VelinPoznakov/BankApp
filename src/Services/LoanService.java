package Services;

import Entities.Loans.Enums.LoanType;
import Entities.Loans.Loan;
import Services.Interfaces.ILoanService;
import Validations.Validations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class LoanService implements ILoanService {

    public static final String FILENAME = "loans.bin";
    public static Path file = Path.of("files", FILENAME);
    public static List<Loan> loans;

    @Override
    public List<Loan> LoadLoans()
    {
        if(!Validations.validateFile(FILENAME)){
            try{
                Files.createFile(file);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        try{
            if(Files.size(file) == 0){
                return loans = new ArrayList<>();
            }

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file.toFile()));

            loans = (List<Loan>) in.readObject();
            in.close();

            return loans;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateLoanInFile(){

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))){
            out.writeObject(loans);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        loans = LoadLoans();
    }

    public List<Loan> StudentLoans(){
        List<Loan> studentLoans = new ArrayList<>();

        for(Loan loan: loans){
            if(loan.loanType == LoanType.STUDENT && !loan.isLoanPaid){
                studentLoans.add(loan);
            }
        }

        return studentLoans;
    }

    @Override
    public List<Loan> AdultLoans(){
        List<Loan> adultLoan = new ArrayList<>();

        for(Loan loan: loans){
            if(loan.loanType == LoanType.ADULT  && !loan.isLoanPaid){
                adultLoan.add(loan);
            }
        }

        return adultLoan;
    }

    @Override
    public List<Loan> GetLoansByUserId(int userId){
        List<Loan> userLoans = new ArrayList<>();

        for(Loan loan: loans){
            if(loan.userId == userId && !loan.isLoanPaid){
                userLoans.add(loan);
            }
        }

        return userLoans;
    }


}
