import Entities.IBAN;
import Entities.Users.User;
import Exceptions.CommandException;
import ProgramInterface.CardInterface;
import ProgramInterface.IBANInterface;
import ProgramInterface.Interfaces.ICardInterface;
import ProgramInterface.Interfaces.IIBANInterface;
import ProgramInterface.Interfaces.ILoanInterface;
import ProgramInterface.Interfaces.IUserInterface;
import ProgramInterface.LoanInterface;
import ProgramInterface.UserInterface;
import Services.CardService;
import Services.IBANService;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.Interfaces.IUserService;
import Services.LoanService;
import Services.UserServices;
import Validations.Validations;

import java.util.Scanner;

public class Application {

    public void Start(){

        // Services
        IUserService userServices = new UserServices();
        IIBANService ibanService = new IBANService();
        ILoanService loanService = new LoanService();
        ICardService cardService = new CardService();

        DataLoader loader = new DataLoader(userServices, ibanService, loanService, cardService);
        loader.Loader();

        // Program Interfaces
        IUserInterface userInterface = new UserInterface(userServices, ibanService, cardService);
        ILoanInterface loanInterface = new LoanInterface(loanService, ibanService);
        IIBANInterface ibanInterface = new IBANInterface(ibanService);
        ICardInterface cardInterface = new CardInterface(cardService, ibanService);

        System.out.println("\u001B[31m!!!!!IMPORTANT!!!!!\u001B[0m");
        System.out.println("\u001B[31mIf you want to exit any action type (e) and will be redirected to the previous" +
                "menu or to home menu\u001B[0m");

        User user = userInterface.UserLoginOrRegisterMenu(userInterface, ibanInterface);

        System.out.println("Welcome " + user.getUsername());

        CustomerMenu(user, loanInterface, cardInterface, ibanInterface);

    }

    public void CustomerMenu(User user,
                             ILoanInterface loanInterface,
                             ICardInterface cardInterface,
                             IIBANInterface ibanInterface){
        while(true){
            Scanner sc = new Scanner(System.in);
            // check commands in all

            try{
                System.out.println("\u001B[34mSelect section: Loans(l), Cards(c), IBAN(i)\u001B[0m");
                System.out.println("If you want to exit type (e)");

                String command = sc.nextLine();

                switch (command) {
                    case "e":
                        return;
                    case "l":
                        loanInterface.LoanMenu(user);
                        break;
                    case "c":
                        cardInterface.CardMenu(user);
                        break;
                    case "i":
                        ibanInterface.IBANMenu(user);
                        break;
                    default:
                        throw new CommandException("Invalid command");
                }
            }catch (CommandException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
