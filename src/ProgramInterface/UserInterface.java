package ProgramInterface;

import Exceptions.LoginException;
import Exceptions.UsernameException;
import Services.UserServices;
import Users.Customer;
import Users.User;
import com.sun.jdi.InvalidTypeException;
import java.util.Scanner;


public class UserInterface {

    private final UserServices userServices;

    public UserInterface(UserServices userServices) {
        this.userServices = userServices;
    }

    public User Register(){

        Scanner sc = new Scanner(System.in);
        User customer;

        while(true){

            try {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

                for(User user: userServices.users){
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

                if(type.equals("s")){
                    type = "student";
                } else if (type.equals("a")) {
                    type = "adults";
                }
                else{
                    throw new InvalidTypeException("Invalid type");
                }

                int id = userServices.getUsersCount() + 1;
                int customerId = userServices.GetCustomerOnly().size() + 1;
                password = MaskPassword(password);

                customer = new Customer(
                        id,
                        username,
                        password,
                        birthDate,
                        customerId,
                        0,
                        monthlyIncome,
                        type);

                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        userServices.users.add(customer);
        userServices.CreateUserInFile();

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

                for (User user: userServices.users){
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
