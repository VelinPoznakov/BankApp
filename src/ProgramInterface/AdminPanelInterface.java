package ProgramInterface;

import Entities.Card;
import Entities.IBAN;
import Entities.Loans.Loan;
import Entities.Loans.StudentLoan;
import Entities.Users.User;
import Exceptions.CommandException;
import ProgramInterface.Interfaces.IAdminPanelInterface;
import Services.IBANService;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.Interfaces.IUserService;
import Services.UserServices;
import Validations.Validations;

import java.util.List;
import java.util.Scanner;

public class AdminPanelInterface implements IAdminPanelInterface {

    private final IUserService userServices;
    private final ILoanService loanServices;
    private final IIBANService ibanServices;
    private final ICardService cardServices;

    public AdminPanelInterface(IUserService userServices,
                               ILoanService loanServices,
                               IIBANService ibanServices,
                               ICardService cardServices) {

        this.userServices = userServices;
        this.loanServices = loanServices;
        this.ibanServices = ibanServices;
        this.cardServices = cardServices;
    }

    @Override
    public void AdminMenu(){

        Scanner sc = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Select section: All Customers(ac), Student Loans(sl), Adult Loans(al), IBANs(i), Cards(c), Summary(s)");

                String command = sc.nextLine();

                List<Loan> loans;
                switch (command) {
                    case "e":
                        return;
                    case "ac":
                        List<User> customers = userServices.GetCustomerOnly();

                        if(customers.isEmpty()){
                            System.out.println("No users in the system");
                            break;
                        }

                        AllUsersDisplay(customers);
                        break;
                    case "sl":
                        loans = loanServices.StudentLoans();

                        if(loans.isEmpty()){
                            System.out.println("No student loans in the system");
                            break;
                        }

                        DisplayLoans(loans);
                        break;
                    case "al":
                        loans = loanServices.AdultLoans();

                        if(loans.isEmpty()){
                            System.out.println("No adult loans in the system");
                            break;
                        }

                        DisplayLoans(loans);
                        break;
                    case "i":
                        DisplayIBANs(IBANService.ibans);
                        break;
                    case "c":
                        DisplayCards(UserServices.users);
                        break;
                    case "s":
                        DisplaySummary(UserServices.users);
                        break;
                    default:
                        throw new CommandException("Invalid Command");
                }
            }catch (CommandException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void AllUsersDisplay(List<User> users){

        for(User user : users){
            System.out.println("Id " + user.getId() + "\n"
                    + "Username " + user.getUsername() + "\n"
                    + "Customer type " + user.getCustomerType() + "\n"
                    + "Monthly income " + user.getMonthlyIncome() + "\n"
                    + "Birth date " + user.birthDate + "\n"
                    + "Created At " + user.createdAt);
        }
    }

    @Override
    public void DisplayLoans(List<Loan> loans){

        if(loans.getFirst() instanceof StudentLoan){
            System.out.println("Student Loans interest: 0.03");
        }else{
            System.out.println("Adult Loans interest: 0.05");
        }

        for(Loan loan : loans){
            System.out.println("User ID " + loan.getId());
            System.out.println(loan.LoanDetails());
        }
    }

    @Override
    public void DisplayIBANs(List<IBAN> ibans){


        for(IBAN  iban : ibans){

            User user = userServices.GetUser(iban.getUserId());
            System.out.println(user.getUsername());
            System.out.println(iban.IBANDetails());
            System.out.println();
        }
    }

    @Override
    public void DisplayCards(List<User> users){
        for(User user : users){
            List<Card> userCards = cardServices.UserCardsByUserId(user.getId());
            System.out.println(user.getUsername());

            for(Card card : userCards){
                System.out.println(card.CardDetails());
                System.out.println("====================");
            }
            System.out.println();
        }
    }

    @Override
    public void DisplaySummary(List<User> users){
        for(User user : users){
            System.out.println(user.getUsername() + " " + ibanServices.IBANForUser(user.getId()).IBANName);

            List<Loan> userLoans = loanServices.GetLoansByUserId(user.getId());
            List<Card> userCards = cardServices.UserCardsByUserId(user.getId());

            if(userLoans.isEmpty()){
                System.out.println(user.getUsername() + " has no loans");
            }else{
                for(Loan loan : userLoans){
                    System.out.println(loan.loanName + "\n"
                            + "Total amount " + loan.getTotalAmount() + "\n"
                            + "Anual Payment " + loan.anualPayment + "\n"
                            + "Due Year " + loan.dueYear);
                }
            }

            for(Card card : userCards){
                System.out.println(card.CardDetails());
                System.out.println();
            }
        }
    }
}
