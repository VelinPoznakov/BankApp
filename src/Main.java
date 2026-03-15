import Exceptions.CommandException;
import ProgramInterface.UserInterface;
import Services.UserServices;
import Users.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        /*
        * loans logic, loans in user, cards, payments, bank functionality, admin user functionality,
        * seeder, action records,
        * later add the loans to be approved from worker
        */

        UserServices userServices = new UserServices();
        UserInterface userInterface = new UserInterface(userServices);
        userServices.LoadUsers();
        Scanner sc = new Scanner(System.in);
        User user = null;

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

    public static boolean isInitialLoad(UserServices userServices){
        // make on first run to seed admin user
        userServices.LoadUsers();
        return true;
    }
}