package ProgramInterface;

import Entities.IBAN;
import Exceptions.ImpossibleLoanAmountException;
import Entities.Loans.AdultLoan;
import Entities.Loans.Loan;
import Entities.Loans.StudentLoan;
import ProgramInterface.Interfaces.ILoanInterface;
import Services.IBANService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.LoanService;
import Entities.Users.Customer;
import Entities.Users.Enums.CustomerType;
import Validations.Validations;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LoanInterface implements ILoanInterface {

    private final ILoanService loanService;
    private final IIBANService ibanService;

    public LoanInterface(ILoanService loanService, IIBANService ibanService)
    {
        this.loanService = loanService;
        this.ibanService = ibanService;
    }

    @Override
    public void TakeLoan(Customer customer) {
        IBAN customerIBAN = ibanService.IBANForUser(customer.getId());

        double interest;
        Scanner sc = new Scanner(System.in);

        if(customer.getCustomerType() == CustomerType.STUDENT) {
            System.out.println("*You can take only Student loans");
            interest = 0.3;
        }else{
            System.out.println("*You can take only Adult loans");
            interest = 0.5;
        }

        System.out.println("Enter the loan amount you want to take: ");
        double loanAmount = sc.nextDouble();

        System.out.println("For How many years you want to take it: ");
        int years = sc.nextInt();

        System.out.println("Enter name for your loan to be references with: ");
        String name = sc.nextLine();
        sc.nextLine();

        double anualPayment = customer.getMonthlyIncome() * 0.3;

        if(loanAmount + (loanAmount * interest ) > (anualPayment) * years){
            throw new ImpossibleLoanAmountException("The loan amount is too high");
        }

        int takenYear = LocalDate.now().getYear();
        int dueYear = Math.toIntExact(Math.round(loanAmount / customer.getMonthlyIncome()));
        int id = LoanService.loans.size() + 1;

        Loan loan;
        if(customer.getCustomerType() == CustomerType.STUDENT) {
            loan = new StudentLoan(
                    id,
                    name,
                    loanAmount,
                    anualPayment,
                    dueYear,
                    takenYear,
                    customer.getId()
            );

        }else{
            loan = new AdultLoan(
                    id,
                    name,
                    loanAmount,
                    anualPayment,
                    dueYear,
                    takenYear,
                    customer.getId()
            );

        }
        LoanService.loans.add(loan);
        loanService.CreateLoanInFile();
        customerIBAN.setBalance(customerIBAN.getBalance() + loanAmount);
        ibanService.CreateIBANsInFile();

        System.out.println("The loan has been taken successfully");

    }

    @Override
    public void PayLoan(String loanName, Customer customer) {
        IBAN customerIBAN = ibanService.IBANForUser(customer.getId());
        boolean fullPayment = false;

        Scanner sc = new Scanner(System.in);

        for(Loan loan: loanService.GetLoansByUserId(customer.getId())){
            if(loan.loanName.equals(loanName)){

                if(!Validations.ValidatePayment(loan.anualPayment, customerIBAN.getBalance())){
                    System.out.println("Low Balance deposit money to pay it");
                    return;
                }

                double paymentAmount;
                if(loan.anualPayment > (loan.getAmount() * loan.interest) - loan.getReturnedMoney()){
                    paymentAmount = (loan.getAmount() * loan.interest) - loan.getReturnedMoney();
                    fullPayment = true;
                }else{
                    paymentAmount = loan.anualPayment;
                }

                System.out.println("Your loan payment is " + paymentAmount);
                System.out.println("Do you want to pay it");

                while (true){
                    String answear = sc.nextLine();
                    sc.nextLine();

                    if(answear.equals("yes")){

                        if(fullPayment){
                            PayFullLoan(loanName, customer);
                            return;
                        }

                        customerIBAN.setBalance(customerIBAN.getBalance() - loan.anualPayment);
                        loan.setReturnedMoney(loan.getReturnedMoney() + loan.anualPayment);

                        System.out.println("You paid " + loan.anualPayment + " and you have left to pay " + ((loan.getAmount() * loan.interest) - loan.getReturnedMoney()));
                        return;
                    }else if(answear.equals("no")){
                        return;
                    }else {
                        System.out.println("Wrong answer please try again");
                    }
                }
            }
        }
    }

    @Override
    public void PayFullLoan(String loanName, Customer customer) {
        IBAN customerIBAN = ibanService.IBANForUser(customer.getId());

        for(Loan loan: LoanService.loans){
            if(loan.loanName.equals(loanName)){
                double amount = (loan.getAmount() * loan.interest) - loan.getReturnedMoney();

                if(!Validations.ValidatePayment(amount, customerIBAN.getBalance())){
                    System.out.println("Low Balance deposit money to pay it");
                }

                customerIBAN.setBalance(customerIBAN.getBalance() + amount);
                loan.setReturnedMoney(loan.getReturnedMoney() + amount);

                System.out.println("You paid " + amount);
                System.out.println("Your loan is paid");
            }
        }

    }

    @Override
    public List<Loan> MyLoans(Customer customer) {
        return loanService.GetLoansByUserId(customer.getCustomerId());
    }
}
