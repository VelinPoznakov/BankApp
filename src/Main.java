import Exceptions.CommandException;
import ProgramInterface.Interfaces.ILoanInterface;
import ProgramInterface.Interfaces.IUserInterface;
import ProgramInterface.LoanInterface;
import ProgramInterface.UserInterface;
import Services.IBANService;
import Services.LoanService;
import Services.UserServices;
import Entities.Users.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        * loans logic, loans in user, cards, payments, bank functionality, admin user functionality,
        * seeder, action records,
        * later add the loans to be approved from worker
        */

        // Services
        UserServices userServices = new UserServices();
        IBANService ibanService = new IBANService();
        LoanService loanService = new LoanService();

        // Program Interfaces
        UserInterface userInterface = new UserInterface(userServices, ibanService);
        ILoanInterface loanInterface = new LoanInterface(loanService, ibanService);

        // Config
        Configuration config = new Configuration(userServices, ibanService, loanService);
        config.Loader();

        Scanner sc = new Scanner(System.in);
        User user;

        while (true) {
            try{
                System.out.println("Welcome please select login or register(l/r)");
                String commend = sc.nextLine();

                if(commend.equals("l")){
                    user = userInterface.Login();
                }else if(commend.equals("r")){
                    user = userInterface.Register();
                }else{
                    throw new CommandException("Invalid command");
                }
                break;
            }catch (CommandException e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println("welcome" + " " + user.getUsername());

    }
}