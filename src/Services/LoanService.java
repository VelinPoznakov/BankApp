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
            if(loan.loanType == LoanType.STUDENT){
                studentLoans.add(loan);
            }
        }

        return studentLoans;
    }

    public List<Loan> AdultLoans(){
        List<Loan> adultLoan = new ArrayList<>();

        for(Loan loan: loans){
            if(loan.loanType == LoanType.ADULT){
                adultLoan.add(loan);
            }
        }

        return adultLoan;
    }

    public List<Loan> GetLoansByUserId(int userId){
        List<Loan> userLoans = new ArrayList<>();

        for(Loan loan: loans){
            if(loan.userId == userId){
                userLoans.add(loan);
            }
        }

        return userLoans;
    }
}
