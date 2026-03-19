package Services;

import Entities.Card;
import Entities.Enums.CardProvider;
import Services.Interfaces.ICardService;
import Validations.Validations;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CardService implements ICardService {

    public static final String FILENAME = "cards.bin";
    public static Path file = Path.of("files", FILENAME);
    public static List<Card> cards;

    @Override
    public List<Card> LoadCards() {

        if(!Validations.validateFile(FILENAME)){
            try{
                Files.createFile(file);
            }catch (IOException e){
                throw new RuntimeException(e);
            }
        }

        try{
            if(Files.size(file) == 0){
                return cards = new ArrayList<>();
            }

            ObjectInputStream in =
                    new ObjectInputStream(new FileInputStream(file.toFile()));

            cards = (List<Card>) in.readObject();
            in.close();

            return cards;

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void CreateCardsInFile() {

        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file.toFile()))){
            out.writeObject(cards);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        cards = LoadCards();
    }

    @Override
    public List<Card> UserCardsByUserId(int userId) {
        List<Card> userCards = new ArrayList<>();

        for(Card card : cards){
            if(card.getUserId() == userId && card.isCardActive){
                userCards.add(card);
            }
        }

        return userCards;
    }

    @Override
    public Card GetUserCardByCvvCode(int userId, int cvv) {
        List<Card> userCards = UserCardsByUserId(userId);

        for(Card card: userCards){
            if(card.getCvvCode() == cvv){
                return card;
            }
        }

        return null;
    }

    @Override
    public long GenerateCardNumber() {
        Random random = new Random();

        return 1_000_000_000_000_000L
                + (long)(random.nextDouble() * 9_000_000_000_000_000L);
    }

    @Override
    public int GenerateCvvCode() {
        Random random = new Random();

        return random.nextInt(100, 1000);
    }

    @Override
    public void CreateAdminCard() {
        Card card = new Card(
                1,
                GenerateCardNumber(),
                GenerateCvvCode(),
                LocalDate.now().plusYears(5).toString(),
                CardProvider.MASTERCARD,
                1,
                "BG01"
        );

        System.out.println(card.CardDetails());

        cards.add(card);
        CreateCardsInFile();
    }


}
