package ProgramInterface;

import Entities.IBAN;
import Entities.Users.User;
import Exceptions.CommandException;
import Exceptions.ImpossibleLoanAmountException;
import Entities.Loans.AdultLoan;
import Entities.Loans.Loan;
import Entities.Loans.StudentLoan;
import ProgramInterface.Interfaces.ILoanInterface;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.LoanService;
import Entities.Enums.CustomerType;
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
    public void TakeLoan(User user) {

        IBAN customerIBAN = ibanService.IBANForUser(user.getId());
        Scanner sc = new Scanner(System.in);

        double interest = (user.getCustomerType() == CustomerType.STUDENT) ? 0.03 : 0.05;

        System.out.println("Enter loan amount:");
        String loanAmountStr = sc.nextLine();

        if(Validations.CommandCheck(loanAmountStr) == null) return;

        double loanAmount = Double.parseDouble(loanAmountStr);

        System.out.println("Enter years to repay:");
        String yearsStr = sc.nextLine();

        if(Validations.CommandCheck(yearsStr) == null) return;

        int years = Integer.parseInt(yearsStr);

        System.out.println("Enter loan name:");
        String name = sc.nextLine();

        if(Validations.CommandCheck(name) == null) return;

        double monthlyPaymentLimit = user.getMonthlyIncome() * 0.3;

        double totalAmount = loanAmount * (1 + interest * years);
        double monthlyPayment = totalAmount / (years * 12);

        if (monthlyPayment > monthlyPaymentLimit) {
            throw new ImpossibleLoanAmountException("Loan is not affordable.");
        }

        int takenYear = LocalDate.now().getYear();
        int dueYear = takenYear + years;
        int id = LoanService.loans.size() + 1;

        Loan loan = (user.getCustomerType() == CustomerType.STUDENT)
                ? new StudentLoan(id, name, loanAmount, monthlyPayment, dueYear, takenYear, user.getId())
                : new AdultLoan(id, name, loanAmount, monthlyPayment, dueYear, takenYear, user.getId());

        LoanService.loans.add(loan);
        loanService.CreateLoanInFile();

        customerIBAN.setBalance(customerIBAN.getBalance() + loanAmount);
        ibanService.CreateIBANsInFile();

        System.out.println("Loan approved successfully");
    }

    @Override
    public void PayLoan(String loanName, User user) {

        IBAN userIban = ibanService.IBANForUser(user.getId());
        Scanner sc = new Scanner(System.in);

        for (Loan loan : loanService.GetLoansByUserId(user.getId())) {

            if (loan.loanName.equals(loanName)) {

                double totalAmount = loan.getTotalAmount();
                double remaining = totalAmount - loan.getReturnedMoney();

                double paymentAmount = Math.min(loan.anualPayment, remaining);

                if (!Validations.ValidatePayment(paymentAmount, userIban.getBalance())) {
                    System.out.println("Low balance. Deposit money.");
                    return;
                }

                System.out.println("Payment amount: " + paymentAmount);

                while (true) {
                    System.out.println("Do you want to pay? (y/n)");
                    String answer = sc.nextLine();

                    if(Validations.CommandCheck(answer) == null) return;

                    if (answer.equalsIgnoreCase("y")) {

                        userIban.setBalance(userIban.getBalance() - paymentAmount);
                        loan.setReturnedMoney(loan.getReturnedMoney() + paymentAmount);

                        if (loan.getReturnedMoney() >= totalAmount) {
                            loan.isLoanPaid = true;
                            System.out.println("Loan fully paid.");
                        } else {
                            System.out.println("Remaining: " + (totalAmount - loan.getReturnedMoney()));
                        }

                        loanService.CreateLoanInFile();
                        ibanService.CreateIBANsInFile();
                        return;

                    } else if (answer.equalsIgnoreCase("n")) {
                        return;
                    } else {
                        System.out.println("Wrong answer, try again");
                    }
                }
            }
        }
    }

    @Override
    public void PayFullLoan(String loanName, User user) {

        IBAN customerIBAN = ibanService.IBANForUser(user.getId());

        for (Loan loan : loanService.GetLoansByUserId(user.getId())) {

            if (loan.loanName.equals(loanName)) {

                double totalAmount = loan.getTotalAmount();
                double remaining = totalAmount - loan.getReturnedMoney();

                if (!Validations.ValidatePayment(remaining, customerIBAN.getBalance())) {
                    System.out.println("Low balance.");
                    return;
                }

                customerIBAN.setBalance(customerIBAN.getBalance() - remaining);
                loan.setReturnedMoney(totalAmount);
                loan.isLoanPaid = true;

                loanService.CreateLoanInFile();
                ibanService.CreateIBANsInFile();

                System.out.println("Loan fully paid: " + remaining);
                return;
            }
        }
    }

    @Override
    public List<Loan> MyLoans(User user) {
        return loanService.GetLoansByUserId(user.getId());
    }

    @Override
    public void LoanMenu(User user){
        Scanner sc = new Scanner(System.in);
        String loanName;

        List<Loan> userLoans = MyLoans(user);
        StringBuilder loans =  new StringBuilder("Loans: ");

        while (true){

            try{
                if(userLoans.isEmpty()){
                    System.out.println("Select section: Take Loan(tl)");
                    System.out.println("You have no loans");

                    String command = sc.nextLine();

                    switch (command){
                        case "e":
                            return;
                        case "tl":
                            TakeLoan(user);
                            return;
                        default:
                            throw new CommandException("Invalid command");
                    }


                }else {
                    for (Loan loan : userLoans) {
                        loans.append(loan.loanName).append(" ");
                    }

                    System.out.println(loans);

                    System.out.println("Select section: Take Loan(tl), Pay Loan(pl), Pay Full Loan(pfl), Loan Details(d)");

                    String command = sc.nextLine();

                    switch (command) {
                        case "e":
                            return;
                        case "tl":
                            TakeLoan(user);
                            return;
                        case "pl":
                            System.out.println("Enter loan name");
                            loanName = sc.nextLine();

                            if(Validations.CommandCheck(loanName) == null) break;

                            PayLoan(loanName, user);
                            return;
                        case "pfl":
                            System.out.println("Enter loan name");
                            loanName = sc.nextLine();

                            if(Validations.CommandCheck(loanName) == null) break;

                            PayFullLoan(loanName, user);
                            return;
                        case "d":
                            System.out.println("Enter loan name");
                            loanName = sc.nextLine();

                            if (Validations.CommandCheck(loanName) == null) break;

                            for (Loan loan : userLoans) {
                                if (loan.loanName.equals(loanName)) {
                                    System.out.println(loan.LoanDetails());
                                    return;
                                }
                            }
                            System.out.println("Wrong loan name please try again");
                            continue;
                        default:
                            throw new CommandException("Invalid command");
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }


}
