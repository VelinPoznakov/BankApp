import Entities.Users.User;
import Exceptions.CommandException;
import ProgramInterface.Interfaces.ILoanInterface;
import ProgramInterface.Interfaces.IUserInterface;
import ProgramInterface.LoanInterface;
import ProgramInterface.UserInterface;
import Services.IBANService;
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

        DataLoader loader = new DataLoader(userServices, ibanService, loanService);
        loader.Loader();

        // Program Interfaces
        IUserInterface userInterface = new UserInterface(userServices, ibanService);
        ILoanInterface loanInterface = new LoanInterface(loanService, ibanService);

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
