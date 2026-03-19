package ProgramInterface.Interfaces;

import Entities.IBAN;
import Entities.Users.User;

public interface IIBANInterface {
    void Deposit(IBAN iban);
    void Withdrawal(IBAN iban);
    void IBANMenu(User user);
}
