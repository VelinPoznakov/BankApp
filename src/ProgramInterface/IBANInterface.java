package ProgramInterface;

import Entities.IBAN;
import Entities.Users.User;
import Exceptions.CommandException;
import Exceptions.InvalidMoneyNumber;
import ProgramInterface.Interfaces.IIBANInterface;
import Services.Interfaces.IIBANService;
import Validations.Validations;

import java.util.Scanner;

public class IBANInterface implements IIBANInterface {

    private final IIBANService ibanService;

    public IBANInterface(IIBANService ibanService){
        this.ibanService = ibanService;
    }

    @Override
    public void Deposit(IBAN iban) {
        Scanner sc = new Scanner(System.in);

        double amount;
        while(true){
            try {
                System.out.println("How much do you want to deposit?");
                String amountStr = sc.nextLine();

                if(Validations.CommandCheck(amountStr) == null) return;

                amount = Double.parseDouble(amountStr);

                if(amount > 0){
                    break;
                }
                throw new InvalidMoneyNumber("Enter correct amount");
            }catch (InvalidMoneyNumber e){
                System.out.println(e.getMessage());
            }
        }

        iban.setBalance(iban.getBalance() + amount);
        ibanService.CreateIBANsInFile();

        System.out.println("You deposited " + amount);
    }

    @Override
    public void Withdrawal(IBAN iban) {
        Scanner sc = new Scanner(System.in);

        double amount;
        while(true){
            try {
                System.out.println("How much do you want to withdraw?");
                String amountStr = sc.nextLine();

                if(Validations.CommandCheck(amountStr) == null) return;

                amount = Double.parseDouble(amountStr);

                if(amount > 0 && Validations.ValidatePayment(amount, iban.getBalance())){
                    iban.setBalance(iban.getBalance() - amount);
                    ibanService.CreateIBANsInFile();
                    System.out.println("You withdraw " + amount);
                    return;
                }

                throw new InvalidMoneyNumber("Enter correct amount");
            }catch (InvalidMoneyNumber e){
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void IBANMenu(User user) {
        Scanner sc = new Scanner(System.in);
        IBAN userIban = ibanService.IBANForUser(user.getId());

        while (true) {
            System.out.println("Select section: Deposit(d), Withdrawal(w), Iban Details(id)");

            try{

                String command = sc.nextLine();

                switch (command) {
                    case "e":
                        return;
                    case "d":
                        Deposit(userIban);
                        break;
                    case "w":
                        Withdrawal(userIban);
                        break;
                    case "id":
                        System.out.println(userIban.IBANDetails());
                        break;
                    default:
                        throw new CommandException("Invalid Command");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }


}
