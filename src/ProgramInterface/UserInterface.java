package ProgramInterface;

import Entities.IBAN;
import Exceptions.LoginException;
import Exceptions.UsernameException;
import ProgramInterface.Interfaces.IUserInterface;
import Services.IBANService;
import Services.UserServices;
import Entities.Users.Customer;
import Entities.Users.User;
import com.sun.jdi.InvalidTypeException;

import java.util.Scanner;


public class UserInterface implements IUserInterface {

    private final UserServices userServices;
    private final IBANService ibanService;

    public UserInterface(UserServices userServices,  IBANService IBANService) {

        this.userServices = userServices;
        this.ibanService = IBANService;
    }

    public User Register(){

        Scanner sc = new Scanner(System.in);
        User customer;
        IBAN iban;

        while(true){

            try {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

                for(User user: UserServices.users){
                    if(user.getUsername().equals(username)){
                        throw new UsernameException("Username already exist");
                    }
                }

                System.out.println("Enter password: ");
                String password = sc.nextLine();

                System.out.println("Enter you birth date: ");
                String birthDate = sc.nextLine();

                System.out.println("Enter your monthly income: ");
                double monthlyIncome = Double.parseDouble(sc.nextLine());


                System.out.println("What type of customer are you student/adult (s/a)");
                String type = sc.next();
                sc.nextLine();

                if(type.equals("s")){
                    type = "student";
                } else if (type.equals("a")) {
                    type = "adults";
                }
                else{
                    throw new InvalidTypeException("Invalid type");
                }

                int id = userServices.GetUsersCount() + 1;
                int customerId = userServices.GetCustomerOnly().size() + 1;
                password = MaskPassword(password);
                String balanceId = "BG" + IBANService.ibans.size() + 1;

                customer = new Customer(
                        id,
                        username,
                        password,
                        birthDate,
                        customerId,
                        monthlyIncome,
                        type);

                iban = new IBAN(
                     balanceId,
                     0,
                     id
                );

                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        UserServices.users.add(customer);
        IBANService.ibans.add(iban);

        userServices.CreateUserInFile();
        ibanService.CreateIBANsInFile();

        return customer;
    }

    public User Login(){
        User customer = null;

        Scanner sc = new Scanner(System.in);

        while (true){
            try {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

                System.out.println("Enter password: ");
                String password = sc.nextLine();

                for (User user: UserServices.users){
                    if(user.getUsername().equals(username)){

                        if(MaskPassword(user.getPassword()).equals(password)){
                            customer = user;
                        }
                        break;
                    }
                }

                if(customer != null){
                    break;
                }
                throw new LoginException("Username or password is incorrect");
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
        return customer;
    }

    private String MaskPassword(String password){
        char[] passwordCharsArray = password.toCharArray();

        for(int i = 0; i < passwordCharsArray.length; i++){
            char temp =  passwordCharsArray[i];
            passwordCharsArray[i] = passwordCharsArray[passwordCharsArray.length - i - 1];
            passwordCharsArray[passwordCharsArray.length - i - 1] = temp;
        }

        return new String(passwordCharsArray);
    }
}
