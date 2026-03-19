package ProgramInterface;

import Entities.Card;
import Entities.CardProvider;
import Entities.IBAN;
import Entities.Users.User;
import Exceptions.CommandException;
import Exceptions.InvalidMoneyNumber;
import ProgramInterface.Interfaces.ICardInterface;
import Services.CardService;
import Services.IBANService;
import Services.Interfaces.ICardService;
import Services.Interfaces.IIBANService;
import Validations.Validations;
import com.sun.jdi.InvalidTypeException;

import java.time.LocalDate;
import java.util.Scanner;

public class CardInterface implements ICardInterface {

    private final ICardService cardService;
    private final IIBANService ibanService;

    public CardInterface(ICardService cardService,  IIBANService ibanService) {
        this.cardService = cardService;
        this.ibanService = ibanService;
    }

    @Override
    public void CreateNewCard(User user) {
        Scanner sc = new Scanner(System.in);

        String ibanName = ibanService.IBANForUser(user.getId()).IBANName;

        CardProvider cardProvider;

        while (true){
            try{
                System.out.println("What card provider you choose Mastercard/Visa (m/v):");
                String response = sc.nextLine();

                if(Validations.CommandCheck(response) == null) return;

                if(response.equals("m")) {
                    cardProvider = CardProvider.MASTERCARD;
                    break;
                }else if(response.equals("v")) {
                    cardProvider = CardProvider.VISA;
                    break;
                }else{
                    throw new InvalidTypeException("Invalid card provider");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        int cvvCode = cardService.GenerateCvvCode();

        Card card = new Card(
                CardService.cards.size() + 1,
                cardService.GenerateCardNumber(),
                cvvCode,
                LocalDate.now().plusYears(5).toString(),
                cardProvider,
                user.getId(),
                ibanName
        );

        System.out.println("Your cvv code is " + cvvCode);

        CardService.cards.add(card);

        cardService.CreateCardsInFile();
    }

    @Override
    public void DeactivateCard(Card card) {
        card.isCardActive = false;

        cardService.CreateCardsInFile();

    }

    @Override
    public void OnlinePayment(Card card, int amount) {

        if(!Validations.ValidateCardDate(card.getExpirationDate())){
            System.out.println("Your card has expired please try again with valid card");
            return;
        }

        while (true){

            for (IBAN iban : IBANService.ibans) {

                if (iban.IBANName.equals(card.getIBANName())) {

                    if (!Validations.ValidatePayment(amount, iban.getBalance())) {
                        throw new InvalidMoneyNumber("Not enough money");
                    }

                    iban.setBalance(iban.getBalance() - amount);
                    System.out.println("Successful payment");

                    ibanService.CreateIBANsInFile();
                    return;
                }
            }
        }
    }

    @Override
    public void CardMenu(User user){
        Scanner sc = new Scanner(System.in);

        while (true){
            try{
                System.out.println("Select section: Card Details(cd), Create New Card(cnc), Deactivate Card(dc), Online Payment(op)");
                System.out.println("If you want to exit type 'e'");

                int cvvCode;
                Card card;
                String cvvCodeStr;
                String command = sc.nextLine();

                switch (command){
                    case "e":
                        return;
                    case "cnc":
                        CreateNewCard(user);
                        break;
                    case "dc":
                        System.out.println("Enter cvv code of the card you want to deactivate");
                        cvvCodeStr = sc.nextLine();

                        if(Validations.CommandCheck(cvvCodeStr) == null) break;

                        cvvCode = Integer.parseInt(cvvCodeStr);

                        card = cardService.GetUserCardByCvvCode(user.getId(), cvvCode);

                        if(card == null){
                            System.out.println("Invalid card code");
                            continue;
                        }

                        DeactivateCard(card);
                        break;
                    case "op":
                        System.out.println("Witch card to use, enter its cvv code");
                        cvvCodeStr = sc.nextLine();

                        if(Validations.CommandCheck(cvvCodeStr) == null) break;

                        cvvCode = Integer.parseInt(cvvCodeStr);

                        card = cardService.GetUserCardByCvvCode(user.getId(), cvvCode);

                        if(card == null){
                            System.out.println("Invalid card code");
                            continue;
                        }

                        System.out.println("Enter the payment amount");
                        String amountStr = sc.nextLine();

                        if(Validations.CommandCheck(amountStr) == null) break;

                        int amount = Integer.parseInt(amountStr);

                        OnlinePayment(card, amount);
                        break;
                    case "cd":
                        System.out.println("Enter card cvv code");
                        cvvCodeStr = sc.nextLine();

                        if(Validations.CommandCheck(cvvCodeStr) == null) break;

                        cvvCode = Integer.parseInt(cvvCodeStr);

                        card = cardService.GetUserCardByCvvCode(user.getId(), cvvCode);

                        if(card == null){
                            System.out.println("Invalid card code");
                            continue;
                        }

                        System.out.println(card.CardDetails());
                        break;
                    default:
                        throw new CommandException("Invalid command");
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
}
