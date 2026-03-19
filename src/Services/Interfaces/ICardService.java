package Services.Interfaces;

import Entities.Card;

import java.util.List;

public interface ICardService {

    List<Card> LoadCards();
    void CreateCardsInFile();
    List<Card> UserCardsByUserId(int userId);
    Card GetUserCardByCvvCode(int userId, int cvv);
    long GenerateCardNumber();
    int GenerateCvvCode();
}
