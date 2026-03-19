import Entities.Users.Admin;
import Entities.Users.User;
import Exceptions.CommandException;
import Exceptions.UserRoleException;
import ProgramInterface.*;
import ProgramInterface.Interfaces.*;
import Services.CardService;
import Services.IBANService;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.ILoanService;
import Services.Interfaces.IUserService;
import Services.LoanService;
import Services.UserServices;

import java.util.Scanner;

public class Application {

    public void Start(){

        // Services
        IUserService userServices = new UserServices();
        IIBANService ibanService = new IBANService();
        ILoanService loanService = new LoanService();
        ICardService cardService = new CardService();

        // Data Loader
        DataLoader loader = new DataLoader(userServices, ibanService, loanService, cardService);
        loader.Loader();

        // Program Interfaces
        IUserInterface userInterface = new UserInterface(userServices, ibanService, cardService);
        ILoanInterface loanInterface = new LoanInterface(loanService, ibanService);
        IIBANInterface ibanInterface = new IBANInterface(ibanService);
        ICardInterface cardInterface = new CardInterface(cardService, ibanService);
        IAdminPanelInterface adminPanelInterface = new AdminPanelInterface(userServices, loanService, ibanService, cardService);

        System.out.println("\u001B[31m!!!!!IMPORTANT!!!!!\u001B[0m");
        System.out.println("\u001B[31mIf you want to exit any action type (e) and will be redirected to the previous" +
                "menu or to home menu\u001B[0m");

        User user = userInterface.UserLoginOrRegisterMenu(userInterface, ibanInterface);

        System.out.println("Welcome " + user.getUsername());

        CustomerAndAdminMenu(user, loanInterface, cardInterface, ibanInterface, adminPanelInterface);

    }

    public void CustomerAndAdminMenu(User user,
                                     ILoanInterface loanInterface,
                                     ICardInterface cardInterface,
                                     IIBANInterface ibanInterface,
                                     IAdminPanelInterface adminPanelInterface){
        Scanner sc = new Scanner(System.in);

        while(true){

            try{
                if(user instanceof Admin){
                    System.out.println("\u001B[34mSelect section: Loans(l), Cards(c), IBAN(i), Admin Panel(ap)\u001B[0m");
                }else{
                    System.out.println("\u001B[34mSelect section: Loans(l), Cards(c), IBAN(i)\u001B[0m");
                }

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
                    case "ap":
                        if(!(user instanceof Admin)){
                            throw  new UserRoleException("You do not have permission");
                        }
                        adminPanelInterface.AdminMenu();
                    default:
                        throw new CommandException("Invalid command");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
