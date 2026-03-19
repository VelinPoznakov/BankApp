package ProgramInterface.Interfaces;

import Entities.Card;
import Entities.Users.User;

public interface ICardInterface {

    void CreateNewCard(User user);
    void DeactivateCard(Card card);
    void OnlinePayment(Card card, int amount);
    void CardMenu(User user);

}
