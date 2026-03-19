package ProgramInterface;

import Entities.Card;
import Entities.Enums.CardProvider;
import Entities.IBAN;
import Exceptions.CommandException;
import Exceptions.LoginException;
import Exceptions.UsernameException;
import ProgramInterface.Interfaces.IIBANInterface;
import ProgramInterface.Interfaces.IUserInterface;
import Services.CardService;
import Services.IBANService;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Services.Interfaces.IUserService;
import Services.UserServices;
import Entities.Users.Customer;
import Entities.Users.User;
import Validations.Validations;
import com.sun.jdi.InvalidTypeException;

import java.time.LocalDate;
import java.util.Scanner;


public class UserInterface implements IUserInterface {

    private final IUserService userServices;
    private final IIBANService ibanService;
    private final ICardService cardService;

    public UserInterface(IUserService userServices,
                         IIBANService IBANService,
                         ICardService cardService) {

        this.userServices = userServices;
        this.ibanService = IBANService;
        this.cardService = cardService;
    }

    @Override
    public User Register(){

        Scanner sc = new Scanner(System.in);
        User customer;
        IBAN iban;
        Card card;

        while(true){

            try {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

                if(Validations.CommandCheck(username) == null) return null;

                for(User user: UserServices.users){
                    if(user.getUsername().equals(username)){
                        throw new UsernameException("Username already exist");
                    }
                }

                System.out.println("Enter password: ");
                String password = sc.nextLine();

                if(Validations.CommandCheck(password) == null) return null;

                System.out.println("Enter you birth date: ");
                String birthDate = sc.nextLine();

                if(Validations.CommandCheck(birthDate) == null) return null;

                System.out.println("Enter your monthly income: ");
                String monthlyIncomeStr = sc.nextLine();

                if(Validations.CommandCheck(monthlyIncomeStr) == null) return null;

                double  monthlyIncome = Double.parseDouble(monthlyIncomeStr);

                System.out.println("What type of customer are you student/adult (s/a)");
                String type = sc.nextLine();

                if(Validations.CommandCheck(type) == null) return null;

                if(type.equals("s")){
                    type = "student";
                } else if (type.equals("a")) {
                    type = "adult";
                }
                else{
                    throw new InvalidTypeException("Invalid type");
                }

                CardProvider cardProvider;
                System.out.println("Select your card provider Visa/Mastercard (v/m): ");
                String cardProviderUserChoice = sc.nextLine();

                if(Validations.CommandCheck(cardProviderUserChoice) == null) return null;

                if(cardProviderUserChoice.equals("v")){
                    cardProvider = CardProvider.VISA;
                }else if(cardProviderUserChoice.equals("m")){
                    cardProvider = CardProvider.MASTERCARD;
                }else{
                    throw new InvalidTypeException("Invalid card provider");
                }

                int id = UserServices.users.size() + 1;
                password = MaskPassword(password);
                String IBANName = "BG" + IBANService.ibans.size() + 1;
                int cardId = CardService.cards.size() + 1;
                long cardNumber = cardService.GenerateCardNumber();
                int cvvCode = cardService.GenerateCvvCode();
                String expirationDate = LocalDate.now().plusYears(5).toString();

                customer = new Customer(
                        id,
                        username,
                        password,
                        birthDate,
                        monthlyIncome,
                        type);

                iban = new IBAN(
                     IBANName,
                     0,
                     id
                );

                card = new Card(
                        cardId,
                        cardNumber,
                        cvvCode,
                        expirationDate,
                        cardProvider,
                        id,
                        IBANName
                );

                System.out.println("Your card cvv code is " + cvvCode);

                break;
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        UserServices.users.add(customer);
        IBANService.ibans.add(iban);
        CardService.cards.add(card);

        userServices.CreateUserInFile();
        ibanService.CreateIBANsInFile();
        cardService.CreateCardsInFile();

        return customer;
    }

    @Override
    public User Login(){
        User customer = null;

        Scanner sc = new Scanner(System.in);

        while (true){
            try {
                System.out.println("Enter username: ");
                String username = sc.nextLine();

                if(Validations.CommandCheck(username) == null) return null;

                System.out.println("Enter password: ");
                String password = sc.nextLine();

                if(Validations.CommandCheck(password) == null) return null;

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

    public User UserLoginOrRegisterMenu(IUserInterface userInterface, IIBANInterface ibanInterface){
        Scanner sc = new Scanner(System.in);
        User user;

        while (true) {
            try{
                System.out.println("Welcome please select login or register(l/r)");
                String commend = sc.nextLine();

                if(Validations.CommandCheck(commend) == null){
                    continue;
                }

                if(commend.equals("l")){
                    user = Login();

                    if(user == null){
                        continue;
                    }

                    return user;

                }else if(commend.equals("r")){
                    user = userInterface.Register();

                    if(user == null){
                        continue;
                    }

                    IBAN iban = ibanService.IBANForUser(user.getId());
                    ibanInterface.Deposit(iban);
                    return user;

                }else{
                    throw new CommandException("Invalid command");
                }
            }catch (CommandException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
